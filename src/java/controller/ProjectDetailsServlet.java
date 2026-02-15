package controller;

import model.DBConnection;
import model.ProjectBean;
import model.MilestoneBean; // Ensure you have this model class
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "ProjectDetailsServlet", urlPatterns = {"/ProjectDetailsServlet"})
public class ProjectDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pid = request.getParameter("id");
        
        if (pid == null || pid.isEmpty()) {
            response.sendRedirect("ProjectListServlet"); 
            return;
        }

        ProjectBean project = new ProjectBean();
        String studentName = "";
        List<MilestoneBean> milestoneList = new ArrayList<>(); // List to store milestones

        try (Connection conn = DBConnection.getConnection()) {
            
            // --- QUERY 1: FETCH PROJECT AND STUDENT DETAILS ---
            String sqlProject = "SELECT p.*, u.full_name, u.sessions FROM APP.PROJECT p " +
                         "JOIN APP.USERS u ON p.student_id = u.user_id " +
                         "WHERE p.project_id = ?";
            
            PreparedStatement ps1 = conn.prepareStatement(sqlProject);
            ps1.setInt(1, Integer.parseInt(pid));
            ResultSet rs1 = ps1.executeQuery();

            if (rs1.next()) {
                project.setProject_id(rs1.getInt("PROJECT_ID"));
                project.setTitle(rs1.getString("TITLE"));
                project.setProject_status(rs1.getString("PROJECT_STATUS"));
                project.setSessions(rs1.getString("SESSIONS")); 
                studentName = rs1.getString("FULL_NAME");
                
                request.setAttribute("project", project);
                request.setAttribute("studentName", studentName);
            }

            // --- QUERY 2: FETCH MILESTONES FOR THIS PROJECT ---
            String sqlMilestones = "SELECT * FROM APP.MILESTONE WHERE PROJECT_ID = ? ORDER BY START_DATE ASC";
            PreparedStatement ps2 = conn.prepareStatement(sqlMilestones);
            ps2.setInt(1, Integer.parseInt(pid));
            ResultSet rs2 = ps2.executeQuery();

            while (rs2.next()) {
                MilestoneBean m = new MilestoneBean();
                m.setMilestone_id(rs2.getInt("MILESTONE_ID"));
                m.setTitle(rs2.getString("TITLE"));
                m.setTask(rs2.getString("TASK"));
                m.setStart_date(rs2.getTimestamp("START_DATE")); 
                m.setEnd_date(rs2.getTimestamp("END_DATE"));
                m.setStatus(rs2.getString("STATUS"));
                
                milestoneList.add(m); // Add to the list
            }

            // Set the milestones attribute for project.jsp to read
            request.setAttribute("milestones", milestoneList);

            // Forward to the project.jsp view
            request.getRequestDispatcher("project.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}