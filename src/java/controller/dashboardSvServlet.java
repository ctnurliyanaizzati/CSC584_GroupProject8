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
        
        // 1. Count only students assigned to this supervisor in the APP.PROJECT table
        String sqlStudents = "SELECT COUNT(DISTINCT STUDENT_ID) FROM APP.PROJECT WHERE SUPERVISOR_ID = ?";

        // 2. Ensure these columns also match your DB (likely PROJECT_STATUS and SUPERVISOR_ID)
        String sqlOngoing = "SELECT COUNT(*) FROM APP.PROJECT WHERE PROJECT_STATUS = 'On-going' AND SUPERVISOR_ID = ?";
        String sqlCompleted = "SELECT COUNT(*) FROM APP.PROJECT WHERE PROJECT_STATUS = 'Completed' AND SUPERVISOR_ID = ?";
        
        // --- START OF PREPAREDSTATEMENT IMPLEMENTATION ---

        // 1. Get total students for this specific supervisor
        PreparedStatement ps1 = conn.prepareStatement(sqlStudents);
        ps1.setInt(1, loggedInUserId);
        ResultSet rs1 = ps1.executeQuery();
        if (rs1.next()) totalStudents = rs1.getInt(1);

        // 2. Get ongoing projects for this specific supervisor
        PreparedStatement ps2 = conn.prepareStatement(sqlOngoing);
        ps2.setInt(1, loggedInUserId);
        ResultSet rs2 = ps2.executeQuery();
        if (rs2.next()) ongoingCount = rs2.getInt(1);

        // 3. Get completed projects for this specific supervisor
        PreparedStatement ps3 = conn.prepareStatement(sqlCompleted);
        ps3.setInt(1, loggedInUserId);
        ResultSet rs3 = ps3.executeQuery();
        if (rs3.next()) completedCount = rs3.getInt(1);

        // --- END OF PREPAREDSTATEMENT IMPLEMENTATION ---
        
        // --- ADD THESE LINES TO FIX THE ERROR ---
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