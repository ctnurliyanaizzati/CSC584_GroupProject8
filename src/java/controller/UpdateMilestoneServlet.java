package controller;

import model.DBConnection;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "UpdateMilestoneServlet", urlPatterns = {"/UpdateMilestoneServlet"})
public class UpdateMilestoneServlet extends HttpServlet {

    // 1. GET: This loads the data into your edit-milestone.jsp
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String milestoneId = request.getParameter("milestoneId");
        
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM APP.MILESTONE WHERE MILESTONE_ID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(milestoneId));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                request.setAttribute("milestoneId", rs.getInt("MILESTONE_ID"));
                request.setAttribute("projectId", rs.getInt("PROJECT_ID"));
                request.setAttribute("title", rs.getString("TITLE"));
                request.setAttribute("task", rs.getString("TASK"));
                
                // Format Timestamp for the HTML datetime-local input
                Timestamp startTs = rs.getTimestamp("START_DATE");
                Timestamp endTs = rs.getTimestamp("END_DATE");
                if (startTs != null) request.setAttribute("startDate", startTs.toString().replace(" ", "T").substring(0, 16));
                if (endTs != null) request.setAttribute("endDate", endTs.toString().replace(" ", "T").substring(0, 16));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("edit-milestone.jsp").forward(request, response);
    }

    // 2. POST: This handles the "Update Milestone" button click
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String milestoneIdStr = request.getParameter("milestoneId");
        String projectId = request.getParameter("projectId"); 
        String title = request.getParameter("title"); 
        String task = request.getParameter("task");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        
        try (Connection conn = DBConnection.getConnection()) {
            // Use TITLE to match your database column
            String sql = "UPDATE APP.MILESTONE SET TITLE=?, TASK=?, START_DATE=?, END_DATE=? WHERE MILESTONE_ID=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, title);
            ps.setString(2, task);

            // Clean the datetime-local string for SQL
            String startTs = startDateStr.replace("T", " ") + ":00";
            String endTs = endDateStr.replace("T", " ") + ":00";
            
            ps.setTimestamp(3, java.sql.Timestamp.valueOf(startTs));
            ps.setTimestamp(4, java.sql.Timestamp.valueOf(endTs));
            ps.setInt(5, Integer.parseInt(milestoneIdStr));
            
            ps.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Redirect back to project details
        response.sendRedirect("ProjectDetailsServlet?id=" + projectId + "&status=updated");
    }
}