package controller;

import model.DBConnection;
import model.ProjectBean; 
import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "ProjectDetailsServlet", urlPatterns = {"/ProjectDetailsServlet"})
public class ProjectDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("DEBUG 1: Servlet started");
        String pid = request.getParameter("id");
        
        if (pid == null || pid.isEmpty()) {
            System.out.println("DEBUG ERROR: No ID found in URL!");
            response.sendRedirect("projectlist.html"); 
            return;
        }

        System.out.println("DEBUG 2: Received ID = " + pid);

        ProjectBean project = new ProjectBean();
        String studentName = "";

        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("DEBUG 3: Database connection successful");

        String sql = "SELECT p.*, u.full_name, u.sessions FROM PROJECT p " +
             "JOIN USERS u ON p.student_id = u.user_id " +
             "WHERE p.project_id = ?";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(pid));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
    project.setProject_id(rs.getInt("PROJECT_ID"));
    project.setTitle(rs.getString("TITLE"));
    project.setProject_status(rs.getString("PROJECT_STATUS"));
    
    // Now pulling SESSIONS from the USERS table part of the join
    project.setSessions(rs.getString("SESSIONS")); 
    
    studentName = rs.getString("FULL_NAME");
    
    request.setAttribute("project", project);
    request.setAttribute("studentName", studentName);
}

            // CRITICAL: You must forward to the view here
            request.getRequestDispatcher("project.jsp").forward(request, response);

        } catch (Exception e) {
            System.out.println("DEBUG ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}