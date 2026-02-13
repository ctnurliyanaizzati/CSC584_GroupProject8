/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.DBConnection;
import model.MilestoneStdBean;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 *
 * @author Alis anissa
 */
@WebServlet(name = "MilestoneServlet", urlPatterns = {"/MilestoneServlet"})
public class MilestoneServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MilestoneStdBean> milestoneList = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT MILESTONE_ID, MILESTONE_NAME, TASK, START_DATE, END_DATE, STATUS FROM APP.MILESTONE";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MilestoneStdBean ms = new MilestoneStdBean();
                ms.setMilestone_id(rs.getInt("MILESTONE_ID"));
                ms.setTitle(rs.getString("MILESTONE_NAME"));
                ms.setTask(rs.getString("TASK"));
                ms.setStart_date(rs.getDate("START_DATE").toString());
                ms.setEnd_date(rs.getDate("END_DATE").toString());
                ms.setStatus(rs.getString("STATUS"));
                milestoneList.add(ms);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } // Hantar list ke JSP request.setAttribute("milestoneList", milestoneList);
            
        // Forward ke JSP 
        RequestDispatcher rd = request.getRequestDispatcher("milestones-std.jsp");
        rd.forward(request, response);
    } 
}
