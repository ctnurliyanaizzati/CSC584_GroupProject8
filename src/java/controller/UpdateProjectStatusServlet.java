package controller;

import model.DBConnection;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "UpdateProjectStatusServlet", urlPatterns = {"/UpdateProjectStatusServlet"})
public class UpdateProjectStatusServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Retrieve the data from your project.jsp form hidden inputs
        String projectIdStr = request.getParameter("projectId");
        String newStatus = request.getParameter("newStatus");

        if (projectIdStr != null && newStatus != null) {
            // 2. Use your existing DBConnection to update the status
            try (Connection conn = DBConnection.getConnection()) {
                
                // Ensure your table name matches your Derby DB (e.g., APP.PROJECT)
                String sql = "UPDATE APP.PROJECT SET PROJECT_STATUS = ? WHERE PROJECT_ID = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                
                ps.setString(1, newStatus); // Sets to 'Completed' from your form
                ps.setInt(2, Integer.parseInt(projectIdStr));
                
                int rowsUpdated = ps.executeUpdate();
                
                if (rowsUpdated > 0) {
                    System.out.println("Project ID " + projectIdStr + " successfully updated to " + newStatus);
                }

            } catch (SQLException | NumberFormatException e) {
                // Error handling helps you debug in the NetBeans Output window
                System.out.println("Update Status Error: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // 3. Redirect back to the Dashboard Servlet to refresh the statistic counts
        response.sendRedirect("ProjectListServlet");
    }
}