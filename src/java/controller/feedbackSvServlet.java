/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MilestoneStdBean;

/**
 *
 * @author PIEKA
 */
@WebServlet(name = "feedbackSvServlet", urlPatterns = {"/feedbackSvServlet"})
public class feedbackSvServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // 1. Get milestone_id from URL (?milestone_id=X)
        String milestoneIdStr = request.getParameter("milestone_id");
        
        if (milestoneIdStr == null) {
            response.sendRedirect("MilestoneStdServlet");
            return;
        }

        int milestoneId = Integer.parseInt(milestoneIdStr);
        MilestoneStdBean milestone = new MilestoneStdBean();
        
        // 2. Query to bridge Milestone -> Submission -> Feedback
        try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/FYPTracker", "app", "app")) {
            
            // The logic: Find the submission linked to the milestone, then find feedback linked to that submission
            String query = "SELECT m.title, f.feedback_text, f.feedback_file_path " + 
                           "FROM MILESTONE m " +
                           "LEFT JOIN SUBMISSION s ON m.milestone_id = s.milestone_id " + 
                           "LEFT JOIN FEEDBACK f ON s.submission_id = f.submission_id " + 
                           "WHERE m.milestone_id = ?";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, milestoneId);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                milestone.setMilestone_id(milestoneId);
                milestone.setTitle(rs.getString("title"));
                milestone.setFeedback_text(rs.getString("feedback_text"));
                milestone.setFeedback_file_path(rs.getString("feedback_file_path"));
            } 
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        // 3. Store the bean and send to the JSP
        request.setAttribute("feedbackSv", milestone);
        request.getRequestDispatcher("feedback-sv.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Redirect to GET if POST is accidentally called
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet to retrieve supervisor feedback for students";
    }
}