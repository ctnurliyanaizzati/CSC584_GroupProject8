package controller;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/login")
public class loginservlet extends HttpServlet {

    //private static final String DB_URL =
        //"jdbc:mysql://localhost:3306/fyp_db";
    //private static final String DB_USER = "root";
    //private static final String DB_PASS = "";
    
    // Default connection details for Java DB (Derby) in NetBeans
    private static final String URL = "jdbc:derby://localhost:1527/FYPTracker";
    private static final String USER = "app";
    private static final String PASS = "app";

    /*@Override
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
    } */
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String email = request.getParameter("email");
    String password = request.getParameter("password");

    // Use your existing DBConnection class to avoid driver errors
    try (Connection con = model.DBConnection.getConnection()) {
        
        // Derby is case-sensitive; ensure table and column names match your DB structure
        String sql = "SELECT * FROM USERS WHERE EMAIL=? AND PASSWORD=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, email);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
    HttpSession session = request.getSession();
    
    // 1. Get the role from the database
    String role = rs.getString("ROLE");
    
    // 2. Set session attributes for your JSP headers
    session.setAttribute("full_name", rs.getString("FULL_NAME")); 
    session.setAttribute("user_id", rs.getInt("USER_ID"));
    session.setAttribute("role", role);

    // 3. ROLE-BASED REDIRECTION LOGIC
    if ("STUDENT".equalsIgnoreCase(role)) {
        // Sends Student to their specific dashboard
        response.sendRedirect("dashboardStdServlet");
    } else if ("SUPERVISOR".equalsIgnoreCase(role)) {
        // Sends Supervisor to their specific dashboard
        response.sendRedirect("dashboard-sv.jsp");
    } else {
        // Optional: Handle unknown roles
        request.setAttribute("error", "Access Denied: Unknown Role");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
} else {
    request.setAttribute("error", "Invalid email or password");
    request.getRequestDispatcher("login.jsp").forward(request, response);
}

    } catch (Exception e) {
        System.out.println("Login Error: " + e.getMessage());
        e.printStackTrace();
        throw new ServletException(e);
    }
}
}