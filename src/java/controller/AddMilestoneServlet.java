package controller;

import model.DBConnection;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "AddMilestoneServlet", urlPatterns = {"/AddMilestoneServlet"})
public class AddMilestoneServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String projectId = request.getParameter("projectId");
        String title = request.getParameter("title");
        String task = request.getParameter("task");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        String defaultStatus = "Pending"; 

        if (projectId == null || projectId.isEmpty() || startDateStr == null || endDateStr == null) {
            response.sendRedirect("add-milestone.jsp?projectId=" + projectId + "&error=missing_data");
            return;
        }

        try (Connection conn = model.DBConnection.getConnection()) {
            String sql = "INSERT INTO APP.MILESTONE (PROJECT_ID, TITLE, TASK, START_DATE, END_DATE, STATUS) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(projectId));
            ps.setString(2, title);
            ps.setString(3, task);

            // --- THE "T" REPLACEMENT LOGIC ---
            String cleanStart = startDateStr.replace("T", " ") + ":00";
            String cleanEnd = endDateStr.replace("T", " ") + ":00";

            ps.setTimestamp(4, java.sql.Timestamp.valueOf(cleanStart)); 
            ps.setTimestamp(5, java.sql.Timestamp.valueOf(cleanEnd));   
            
            ps.setString(6, defaultStatus);

            int result = ps.executeUpdate();

            if (result > 0) {
                // Add "&status=success" to the end of your redirect URL
                response.sendRedirect("ProjectDetailsServlet?id=" + projectId + "&status=success");
            } else {
                response.sendRedirect("add-milestone.jsp?projectId=" + projectId + "&error=failed");
            }

        } catch (Exception e) {
            e.printStackTrace(); 
            response.sendRedirect("add-milestone.jsp?projectId=" + projectId + "&error=db_error");
        }
    }
}