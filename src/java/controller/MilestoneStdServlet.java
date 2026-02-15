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
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.MilestoneStdBean;
import model.UserBean;

/**
 *
 * @author PIEKA
 */
//@WebServlet(name = "MilestoneStdServlet", urlPatterns = {"/MilestoneStdServlet"})
public class MilestoneStdServlet extends HttpServlet {

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
        
        String searchQuery = request.getParameter("searchQuery");
        //use sample project_id for testing
        //int project_id = 1;
        List<MilestoneStdBean> milestoneList = new ArrayList<>();
        
        javax.servlet.http.HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("userData");
       
        if (user == null) {
            response.sendRedirect("login.jsp"); 
            return;
        }
        
        int student_id = user.getUser_id();
        
        try{
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/FYPTracker", "app", "app");
            String query = "SELECT M.* FROM MILESTONE M " +
               "JOIN PROJECT P ON M.PROJECT_ID = P.PROJECT_ID " +
               "WHERE P.STUDENT_ID = ?";
            
            //Filtering logic if user search
            boolean isSearching = (searchQuery != null && !searchQuery.trim().isEmpty());
            if (isSearching) {
                query += " AND (LOWER(title) LIKE ? OR LOWER(task) LIKE ?)";
            }
            query += " ORDER BY milestone_id ASC";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, student_id);
            
            // 4. Set parameter for LIKE
        if (isSearching) {
            String keyword = "%" + searchQuery.toLowerCase() + "%";
            stmt.setString(2, keyword);
            stmt.setString(3, keyword);
        }
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                MilestoneStdBean ms = new MilestoneStdBean();
                ms.setMilestone_id(rs.getInt("MILESTONE_ID"));
                ms.setTitle(rs.getString("TITLE"));
                ms.setTask(rs.getString("TASK"));
                ms.setStart_date(rs.getString("START_DATE"));
                ms.setEnd_date(rs.getString("END_DATE"));
                ms.setStatus(rs.getString("STATUS"));
                milestoneList.add(ms);
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        request.setAttribute("milestoneList", milestoneList);
        request.getRequestDispatcher("milestones-std.jsp").forward(request, response);
        
        
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
