package controller;

import model.DBConnection;
import model.ProjectBean;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "ProjectListServlet", urlPatterns = {"/ProjectListServlet"})
public class ProjectListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<ProjectBean> projectList = new ArrayList<>();

        // 1. Connect to DB and fetch all projects
        try (Connection conn = DBConnection.getConnection()) {
            // Add u.program_code to the SELECT
String sql = "SELECT p.*, u.full_name, u.sessions, u.program_code FROM PROJECT p " +
             "JOIN USERS u ON p.student_id = u.user_id";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

          while (rs.next()) {
    ProjectBean p = new ProjectBean();
    p.setProject_id(rs.getInt("PROJECT_ID"));
    p.setTitle(rs.getString("TITLE"));
    p.setProject_status(rs.getString("PROJECT_STATUS"));
    p.setSessions(rs.getString("SESSIONS"));
    p.setStudent_name(rs.getString("FULL_NAME"));
    
    // FETCH THE PROGRAM CODE HERE
    p.setProgram_code(rs.getString("PROGRAM_CODE")); 
    
    projectList.add(p);
}

            // 2. Set the attribute "allProjects" (matches your JSP c:forEach items)
            request.setAttribute("allProjects", projectList);
            
            // 3. Forward to the JSP
            request.getRequestDispatcher("projectlist.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}