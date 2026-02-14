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
        
        String milestoneIdStr = request.getParameter("milestoneId");
        
        if (milestoneIdStr != null) {
            try (Connection conn = DBConnection.getConnection()) {
                String sql = "DELETE FROM APP.MILESTONE WHERE MILESTONE_ID=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(milestoneIdStr));
                ps.executeUpdate();
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
            }
        } 
       // Redirect balik ke senarai milestone 
   response.sendRedirect("MilestoneServlet");
    }
}