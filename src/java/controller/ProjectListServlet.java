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
    
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("user_id") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    int loggedInSupervisorId = (int) session.getAttribute("user_id");
    List<ProjectBean> projectList = new ArrayList<>();
    int totalStudents = 0;
    int totalProjects = 0;

    try (Connection conn = DBConnection.getConnection()) {
        // 1. Query to count unique students assigned to this supervisor
        String sqlCountStudents = "SELECT COUNT(DISTINCT STUDENT_ID) FROM APP.PROJECT WHERE SUPERVISOR_ID = ?";
        PreparedStatement ps1 = conn.prepareStatement(sqlCountStudents);
        ps1.setInt(1, loggedInSupervisorId);
        ResultSet rs1 = ps1.executeQuery();
        if (rs1.next()) totalStudents = rs1.getInt(1);

        // 2. Query to count total projects for this supervisor
        String sqlCountProjects = "SELECT COUNT(*) FROM APP.PROJECT WHERE SUPERVISOR_ID = ?";
        PreparedStatement ps2 = conn.prepareStatement(sqlCountProjects);
        ps2.setInt(1, loggedInSupervisorId);
        ResultSet rs2 = ps2.executeQuery();
        if (rs2.next()) totalProjects = rs2.getInt(1);

        // 3. Main query to fetch project details for the table
        String sqlList = "SELECT p.*, u.full_name, u.sessions, u.program_code " +
                         "FROM APP.PROJECT p " +
                         "JOIN APP.USERS u ON p.student_id = u.user_id " +
                         "WHERE p.supervisor_id = ?";
        
        PreparedStatement psList = conn.prepareStatement(sqlList);
        psList.setInt(1, loggedInSupervisorId);
        ResultSet rsList = psList.executeQuery();

        while (rsList.next()) {
            ProjectBean p = new ProjectBean();
            p.setProject_id(rsList.getInt("PROJECT_ID"));
            p.setTitle(rsList.getString("TITLE"));
            p.setProject_status(rsList.getString("PROJECT_STATUS"));
            p.setSessions(rsList.getString("SESSIONS"));
            p.setStudent_name(rsList.getString("FULL_NAME"));
            p.setProgram_code(rsList.getString("PROGRAM_CODE")); 
            projectList.add(p);
        }

        // 4. Set all attributes for the JSP
        request.setAttribute("totalStudents", totalStudents);
        request.setAttribute("totalProjects", totalProjects);
        request.setAttribute("allProjects", projectList);
        
        request.getRequestDispatcher("projectlist.jsp").forward(request, response);

    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("dashboardSvServlet?error=query_failed");
    }
}
}