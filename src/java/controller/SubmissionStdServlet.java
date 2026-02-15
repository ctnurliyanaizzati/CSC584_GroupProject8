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
import model.MilestoneStdBean;
import model.UserBean;

@WebServlet(name = "SubmissionStdServlet", urlPatterns = {"/SubmissionStdServlet"})
@MultipartConfig(location = "C:/FYPTrackerFile/StudentSubmission",
                 fileSizeThreshold = 1024 * 1024 * 2, // 2MB
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
        
        /*//Get data from form JSP
        String milestoneId = request.getParameter("milestone_id");
        String remarks = request.getParameter("remarks");
        Part filePart = request.getPart("submission_file_path"); 
        //String fileName = (filePart != null) ? filePart.getSubmittedFileName(): "no_file";
        
        //String savePath = "C:\\FYPTrackerFile\\StudentSubmission";
        //String fileName = "no_file";
        String fullFilePath = "";
        
        if (filePart != null && filePart.getSize() > 0) {
            
            String fileName = milestoneId + "_" + filePart.getSubmittedFileName();
            
            String savePath = "C:/FYPTrackerFile/StudentSubmission/";
            java.io.File uploadDir = new java.io.File(savePath);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            
            filePart.write(savePath + fileName); 
            fullFilePath = savePath + fileName;
        
            // 3. Simpan fail ke folder fizikal
            //filePart.write(fullFilePath);
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
        } */
        
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
        //processRequest(request, response);
        
        String mIdStr = request.getParameter("milestone_id");
        int milestone_id = Integer.parseInt(mIdStr);
        String submission_remarks = request.getParameter("submission_remarks");
       //int submission_id = Integer.parseInt(mIdStr);
              
       javax.servlet.http.Part filePart = request.getPart("submission_file");
       String filePathStd = "";
        
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();
                    
            /*String folderPath = "C:/FYPTrackerFile";
            java.io.File fileSaveDir = new java.io.File(folderPath);
            
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdirs();
            }
            
            filePathSv = folderPath + "/" + fileName;*/
            
            filePart.write(fileName);
            filePathStd = "C:/FYPTrackerFile/StudentSubmission/" + fileName; // Simpan path penuh dalam DB
        }
        
        //get SV id from login session
        UserBean currentUser = (UserBean) request.getSession().getAttribute("userData");
        int student_id = (currentUser != null) ? currentUser.getUser_id() : 1001;
        
        try{
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/FYPTracker", "app", "app");
            
            // DELETE old feedback if exist to avoid duplicate
            String deleteQuery = "DELETE FROM SUBMISSION WHERE MILESTONE_ID = ?";
            PreparedStatement delStmt = conn.prepareStatement(deleteQuery);
            delStmt.setInt(1, milestone_id);
            delStmt.executeUpdate();
            
            // INSERT feedback
            String insertSQL = "INSERT INTO SUBMISSION (MILESTONE_ID, SUBMISSION_DATE, SUBMISSION_FILE_PATH, SUBMISSION_REMARKS) VALUES (?, CURRENT_TIMESTAMP, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertSQL);
            stmt.setInt(1, milestone_id);
            stmt.setString(2, filePathStd);
            stmt.setString(3, submission_remarks);
            stmt.executeUpdate();
            
            //UPDATE status
            String updateStatusSQL = "UPDATE MILESTONE SET STATUS = 'Submitted' WHERE MILESTONE_ID = ?";
            PreparedStatement upStmt = conn.prepareStatement(updateStatusSQL);
            upStmt.setInt(1, milestone_id);
            upStmt.executeUpdate();
            
            /*String query = "INSERT INTO FEEDBACK (SUBMISSION_ID, SUPERVISOR_ID, FEEDBACK_FILE_PATH, FEEDBACK_TEXT) VALUES (?, ?, ?, ?)";
                        
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, milestone_id);
            stmt.setInt(2, supervisor_id); //user id from session
            stmt.setString(3, filePathSv);
            stmt.setString(4, feedbackSv);
            stmt.executeUpdate();*/

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        response.sendRedirect("MilestoneStdServlet?milestone_id=" + milestone_id + "&status=success");
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
