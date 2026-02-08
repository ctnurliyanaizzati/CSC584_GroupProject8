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
import model.ProjectBean;
import model.UserBean;
/**
 *
 * @author PIEKA
 */
//@WebServlet(name = "dashboardStdServlet", urlPatterns = {"/dashboardStdServlet"})
public class dashboardStdServlet extends HttpServlet {

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
    
        //temp id untuk testing before siap login
        int tempUserId = 2025123456;
        
        //doGet
        ProjectBean project = new ProjectBean();
        UserBean user = new UserBean();
        
        try {
            Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/FYPTracker", "app", "app");
            String query = "SELECT p.title, p.project_status, " +
                           "u_std.full_name, u_std.user_id, u_std.sessions, " + 
                           "u_sv.full_name AS sv_name, " +
                           "(SELECT COUNT(*) FROM MILESTONE m WHERE m.project_id = p.project_id) AS total_milestones, " +
                           "(SELECT COUNT(*) FROM MILESTONE m WHERE m.project_id = p.project_id AND m.status = 'Pending') AS pending_count, " +
                           "(SELECT COUNT(*) FROM MILESTONE m WHERE m.project_id = p.project_id AND m.status = 'Completed') AS completed_count " +
                           "FROM PROJECT p " +
                           "JOIN USERS u_std ON p.student_id = u_std.user_id " +
                           "JOIN USERS u_sv ON p.supervisor_id = u_sv.user_id " +
                           "WHERE p.student_id = ?";
            
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, tempUserId);
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()) {
                
                //Student Details
                user.setFull_name(rs.getString("full_name")); 
                user.setUser_id(rs.getInt("user_id"));
                
                //Project Overview
                user.setSessions(rs.getString("sessions"));    
                project.setTitle(rs.getString("title")); 
                project.setSupervisor_name(rs.getString("sv_name"));  
                project.setProject_status(rs.getString("project_status"));
                
                //Project Status
                project.setTotal_tasks(rs.getInt("total_milestones"));
                project.setPending_tasks(rs.getInt("pending_count"));
                project.setCompleted_tasks(rs.getInt("completed_count"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        request.setAttribute("userData", user);
        request.setAttribute("projectData", project);
        
        request.getRequestDispatcher("dashboard-std.jsp").forward(request, response);
        
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
