package controller;

import model.DBConnection;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import model.ProjectBean;
import model.UserBean;

@WebServlet(name = "dashboardSvServlet", urlPatterns = {"/dashboardSvServlet"})
public class dashboardSvServlet extends HttpServlet {

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    // 1. Check if user is logged in
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("user_id") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    int loggedInUserId = (int) session.getAttribute("user_id");
    String fullName = "";
    int totalStudents = 0;
    int ongoingCount = 0;
    int completedCount = 0;

    try (Connection conn = DBConnection.getConnection()) {
        // Query to get the user's full name directly from the DB
        String sqlUser = "SELECT FULL_NAME FROM USERS WHERE USER_ID = ?";
        PreparedStatement psUser = conn.prepareStatement(sqlUser);
        psUser.setInt(1, loggedInUserId);

        ResultSet rsUser = psUser.executeQuery();
        if (rsUser.next()) {
            fullName = rsUser.getString("FULL_NAME");
        }

        // Statistics Queries
        String sqlStudents = "SELECT COUNT(*) FROM USERS WHERE ROLE = 'STUDENT'"; 
        String sqlOngoing = "SELECT COUNT(*) FROM PROJECT WHERE PROJECT_STATUS = 'On-going'";
        String sqlCompleted = "SELECT COUNT(*) FROM PROJECT WHERE PROJECT_STATUS = 'Completed'";

        Statement st = conn.createStatement();

        ResultSet rs1 = st.executeQuery(sqlStudents);
        if (rs1.next()) totalStudents = rs1.getInt(1);

        ResultSet rs2 = st.executeQuery(sqlOngoing);
        if (rs2.next()) ongoingCount = rs2.getInt(1);

        ResultSet rs3 = st.executeQuery(sqlCompleted);
        if (rs3.next()) completedCount = rs3.getInt(1);

        // 3. Set attributes for the JSP to read
        request.setAttribute("full_name", fullName);
        request.setAttribute("user_id", loggedInUserId);
        request.setAttribute("totalStudents", totalStudents);
        request.setAttribute("ongoingCount", ongoingCount);
        request.setAttribute("completedCount", completedCount);

        // 4. Forward to the JSP page
        request.getRequestDispatcher("dashboard-sv.jsp").forward(request, response);

    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("login.jsp?error=db_error");
    }
}
}