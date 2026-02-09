package controller;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/login")
public class loginservlet extends HttpServlet {

    private static final String DB_URL =
        "jdbc:mysql://localhost:3306/fyp_db";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASS);

            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                HttpSession session = request.getSession();
                session.setAttribute("email", rs.getString("email"));
                session.setAttribute("role", rs.getString("role"));

                response.sendRedirect("dashboard.jsp");
            } else {
           
                request.setAttribute("error", "Invalid email or password");
                request.getRequestDispatcher("login.jsp")
                       .forward(request, response);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}