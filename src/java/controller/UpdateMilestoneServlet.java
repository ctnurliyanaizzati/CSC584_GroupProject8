package controller;

import model.DBConnection;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "UpdateMilestoneServlet", urlPatterns = {"/UpdateMilestoneServlet"})
public class UpdateMilestoneServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Retrieve data from the form
        String milestoneIdStr = request.getParameter("milestoneId");
        String projectId = request.getParameter("projectId"); 
        String title = request.getParameter("title"); 
        String task = request.getParameter("task");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        
        if (milestoneIdStr != null && !milestoneIdStr.isEmpty()) {
            try (Connection conn = DBConnection.getConnection()) {
                // Ensure SQL matches your database columns (TITLE, not MILESTONE_NAME)
                String sql = "UPDATE APP.MILESTONE SET TITLE=?, TASK=?, START_DATE=?, END_DATE=? WHERE MILESTONE_ID=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                
                ps.setString(1, title);
                ps.setString(2, task);

                // Handle the "T" from datetime-local and add seconds
                String startTs = startDateStr.replace("T", " ") + ":00";
                String endTs = endDateStr.replace("T", " ") + ":00";
                
                ps.setTimestamp(3, java.sql.Timestamp.valueOf(startTs));
                ps.setTimestamp(4, java.sql.Timestamp.valueOf(endTs));
                ps.setInt(5, Integer.parseInt(milestoneIdStr));
                
                ps.executeUpdate();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
        
        // Redirect back to Project Details
        response.sendRedirect("ProjectDetailsServlet?id=" + projectId + "&status=updated");
    }
}