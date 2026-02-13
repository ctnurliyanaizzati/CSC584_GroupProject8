/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
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

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        //get milestone_id from url
        String milestoneIdStr = request.getParameter("milestone_id");
        
        if (milestoneIdStr == null) {
            response.sendRedirect("MilestoneStdServlet");
            return;
        }

        int milestoneId = Integer.parseInt(milestoneIdStr);
        MilestoneStdBean milestone = new MilestoneStdBean();
        
        // query to get data from milestone and feedback table
         try {
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/FYPTracker", "app", "app");
            String query = "SELECT m.title, f.feedback_text " + 
                           "FROM MILESTONE m " +
                           "LEFT JOIN FEEDBACK f ON m.milestone_id = f.submission_id " + 
                           "WHERE m.milestone_id = ?";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, milestoneId);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                milestone.setMilestone_id(milestoneId);
                milestone.setTitle(rs.getString("title"));
                milestone.setFeedback_text(rs.getString("feedback_text"));
            } 
            conn.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    
    //store request and send to JSP
    request.setAttribute("feedbackSv", milestone);
    request.getRequestDispatcher("feedback-sv.jsp").forward(request, response);
    
    }
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
