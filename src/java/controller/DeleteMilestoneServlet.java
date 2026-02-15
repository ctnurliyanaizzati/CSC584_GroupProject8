/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;


import model.DBConnection;
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alis anissa
 */
/**
 * * Servlet untuk delete milestone berdasarkan milestoneId
 */
@WebServlet(name = "DeleteMilestoneServlet", urlPatterns = {"/DeleteMilestoneServlet"})
public class DeleteMilestoneServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // 1. Capture the IDs from your form
        String milestoneIdStr = request.getParameter("milestoneId");
        String projectId = request.getParameter("projectId"); 

        // 2. Validate and Execute Delete
        if (milestoneIdStr != null && !milestoneIdStr.isEmpty()) {
            try (Connection conn = model.DBConnection.getConnection()) {
                // Include APP. prefix for Derby schema compatibility
                String sql = "DELETE FROM APP.MILESTONE WHERE MILESTONE_ID=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(milestoneIdStr));

                ps.executeUpdate();
                System.out.println("DEBUG: Milestone " + milestoneIdStr + " deleted successfully.");

            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
            }
        } 

        // 3. The most important part: Redirect back to the correct Project Details page
        // This uses the projectId you passed from project.jsp
        if (projectId != null && !projectId.isEmpty()) {
            response.sendRedirect("ProjectDetailsServlet?id=" + projectId);
        } else {
            // Fallback if projectId is somehow missing
            response.sendRedirect("ProjectListServlet");
        }
    }
}