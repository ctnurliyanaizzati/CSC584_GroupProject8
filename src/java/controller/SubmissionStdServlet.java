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
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

//@WebServlet("/SubmissionStdServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 10,      // 10MB
                 maxRequestSize = 1024 * 1024 * 50)   // 50MB
/**
 *
 * @author PIEKA
 */
public class SubmissionStdServlet extends HttpServlet {

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
        
        //Get data from form JSP
        String milestoneId = request.getParameter("milestone_id");
        String remarks = request.getParameter("remarks");
        Part filePart = request.getPart("submission_file_path"); 
        //String fileName = (filePart != null) ? filePart.getSubmittedFileName(): "no_file";
        
        String savePath = "C:\\FYPTrackerFile\\StudentSubmission";
        String fileName = "no_file";
        String fullFilePath = "";
        
        if (filePart != null && filePart.getSize() > 0) {
            fileName = filePart.getSubmittedFileName();
            fullFilePath = savePath + "\\" + fileName;
        
        // 3. Simpan fail ke folder fizikal
        filePart.write(fullFilePath);
    }
        
        
                
        try{
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/FYPTracker", "app", "app");
            //Query 1: Store record to SUBMISSIOM table
            String query = "INSERT INTO SUBMISSION(MILESTONE_ID, SUBMISSION_DATE, SUBMISSION_FILE_PATH, SUBMISSION_REMARKS, SUBMISSION_STATUS) VALUES (?, CURRENT_TIMESTAMP, ?, ?, 'Submitted')";
            
            //insert into submission
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(milestoneId));
            stmt.setString(2, fullFilePath);
            stmt.setString(3, remarks);
            stmt.executeUpdate();
            
            //Query 2: Update status from OPEN to SUBMITTED
            String queryUpdate = "UPDATE MILESTONE SET STATUS = 'SUBMITTED' WHERE MILESTONE_ID = ?";
            
            PreparedStatement stmtUpdate = conn.prepareStatement(queryUpdate);
            stmtUpdate.setInt(1, Integer.parseInt(milestoneId));
            stmtUpdate.executeUpdate();
            
            response.sendRedirect("MilestoneStdServlet");
        
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error Database: " + e.getMessage());
        } 
        
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
        processRequest(request, response);
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
