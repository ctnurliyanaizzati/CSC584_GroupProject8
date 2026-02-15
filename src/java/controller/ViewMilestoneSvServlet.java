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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MilestoneStdBean;
import model.UserBean;

@MultipartConfig( location = "C:/FYPTrackerFile",
                  fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                  maxFileSize = 1024 * 1024 * 10,      // 10MB
                  maxRequestSize = 1024 * 1024 * 50)   // 50MB
/**

/**
 *
 * @author PIEKA
 */
@WebServlet(name = "ViewMilestoneSvServlet", urlPatterns = {"/ViewMilestoneSvServlet"})
public class ViewMilestoneSvServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewMilestoneSvServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewMilestoneSvServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
        //processRequest(request, response);
        
        // get ID from URL (?milestone_id=1) testing
        String mIdParam = request.getParameter("milestone_id");
        
        if (mIdParam != null) {
            int mId = Integer.parseInt(mIdParam);
            MilestoneStdBean milestone = new MilestoneStdBean();
            //UserBean user = new UserBean();
            
            javax.servlet.http.HttpSession session = request.getSession(); //get data SV from session
            UserBean user = (UserBean) session.getAttribute("userData");
               
        try {
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/FYPTracker", "app", "app");
            String query = "SELECT M.*, U.FULL_NAME AS STD_NAME, S.SUBMISSION_REMARKS, S.SUBMISSION_FILE_PATH, F.FEEDBACK_TEXT " +
               "FROM MILESTONE M " +
               "JOIN PROJECT P ON M.PROJECT_ID = P.PROJECT_ID " +
               "JOIN USERS U ON P.STUDENT_ID = U.USER_ID " +
               "LEFT JOIN SUBMISSION S ON M.MILESTONE_ID = S.MILESTONE_ID " + // Ambil remarks student
               "LEFT JOIN FEEDBACK F ON S.SUBMISSION_ID = f.SUBMISSION_ID " + // Ambil feedback SV
               "WHERE M.MILESTONE_ID = ?";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            //stmt.setInt(1, milestoneId);
            stmt.setInt(1, mId);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
                
                //milestone.setMilestone_id(milestoneId);
                milestone.setMilestone_id(mId);
                milestone.setTitle(rs.getString("TITLE"));
                milestone.setTask(rs.getString("TASK"));
                milestone.setStudent_name(rs.getString("STD_NAME"));
                milestone.setStart_date(rs.getString("START_DATE"));
                milestone.setEnd_date(rs.getString("END_DATE"));
                milestone.setStatus(rs.getString("STATUS"));
                milestone.setSubmission_remarks(rs.getString("SUBMISSION_REMARKS"));
                milestone.setSubmission_file_path(rs.getString("SUBMISSION_FILE_PATH"));
                
                milestone.setFeedback_text(rs.getString("FEEDBACK_TEXT"));
                
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        request.setAttribute("data", milestone);
        request.setAttribute("userData", user);
        request.getRequestDispatcher("view-milestone.jsp").forward(request, response);

        }
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
       int submission_id = Integer.parseInt(mIdStr);
       String feedbackSv = request.getParameter("feedback_text");
       
       javax.servlet.http.Part filePart = request.getPart("feedback_file");
       String filePathSv = "";
        
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = filePart.getSubmittedFileName();
                    
            /*String folderPath = "C:/FYPTrackerFile";
            java.io.File fileSaveDir = new java.io.File(folderPath);
            
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdirs();
            }
            
            filePathSv = folderPath + "/" + fileName;*/
            
            filePart.write(fileName);
            filePathSv = "C:/FYPTrackerFile/" + fileName; // Simpan path penuh dalam DB
        }
        
        //get SV id from login session
        UserBean currentUser = (UserBean) request.getSession().getAttribute("userData");
        int supervisor_id = (currentUser != null) ? currentUser.getUser_id() : 1001;
        
        try{
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/FYPTracker", "app", "app");
            
            // DELETE old feedback if exist to avoid duplicate
            String deleteQuery = "DELETE FROM FEEDBACK WHERE SUBMISSION_ID = ?";
            PreparedStatement delStmt = conn.prepareStatement(deleteQuery);
            delStmt.setInt(1, submission_id);
            delStmt.executeUpdate();
            
            // INSERT feedback
            String insertSQL = "INSERT INTO FEEDBACK (SUBMISSION_ID, SUPERVISOR_ID, FEEDBACK_FILE_PATH, FEEDBACK_TEXT) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertSQL);
            stmt.setInt(1, submission_id);
            stmt.setInt(2, supervisor_id);
            stmt.setString(3, filePathSv);
            stmt.setString(4, feedbackSv);
            stmt.executeUpdate();
            
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
        
        response.sendRedirect("ViewMilestoneSvServlet?milestone_id=" + submission_id + "&status=success");
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
