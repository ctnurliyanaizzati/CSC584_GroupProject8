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

/**
 *
 * @author Alis anissa
 */
@WebServlet(name = "UpdateMilestoneServlet", urlPatterns = {"/UpdateMilestoneServlet"})
public class UpdateMilestoneServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String milestoneIdStr = request.getParameter("milestoneId");
        String milestoneName = request.getParameter("milestoneName");
        String task = request.getParameter("task");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        
        if (milestoneIdStr != null) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "UPDATE APP.MILESTONE SET MILESTONE_NAME=?, TASK=?, START_DATE=?, END_DATE=? WHERE MILESTONE_ID=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, milestoneName);
                ps.setString(2, task);
                ps.setDate(3, java.sql.Date.valueOf(startDate));
                ps.setDate(4, java.sql.Date.valueOf(endDate));
                ps.setInt(5, Integer.parseInt(milestoneIdStr));
                
                int rowsUpdated = ps.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Milestone " + milestoneIdStr + " updated successfully.");
                }
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
            }
        } // Redirect back to milestone list 
        response.sendRedirect(request.getContextPath() + "/MilestoneServlet");
    } 
}