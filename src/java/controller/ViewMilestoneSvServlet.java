package controller;

import java.io.IOException;
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
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.MilestoneStdBean;
import model.UserBean;

@MultipartConfig(location = "C:/FYPTrackerFile",
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB

@WebServlet(name = "ViewMilestoneSvServlet", urlPatterns = {"/ViewMilestoneSvServlet"})
public class ViewMilestoneSvServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mIdParam = request.getParameter("milestone_id");

        if (mIdParam != null) {
            int mId = Integer.parseInt(mIdParam);
            MilestoneStdBean milestone = new MilestoneStdBean();
            
            HttpSession session = request.getSession();
            UserBean user = (UserBean) session.getAttribute("userData");

            try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/FYPTracker", "app", "app")) {
                
                // LEFT JOIN ensures milestone details load even if student has not submitted yet
                // M.PROJECT_ID is retrieved so the "Close" button knows where to return
                String query = "SELECT M.*, U.FULL_NAME AS STD_NAME, S.SUBMISSION_ID, S.SUBMISSION_REMARKS, S.SUBMISSION_FILE_PATH, F.FEEDBACK_TEXT "
                        + "FROM MILESTONE M "
                        + "LEFT JOIN PROJECT P ON M.PROJECT_ID = P.PROJECT_ID "
                        + "LEFT JOIN USERS U ON P.STUDENT_ID = U.USER_ID "
                        + "LEFT JOIN SUBMISSION S ON M.MILESTONE_ID = S.MILESTONE_ID "
                        + "LEFT JOIN FEEDBACK F ON S.SUBMISSION_ID = F.SUBMISSION_ID "
                        + "WHERE M.MILESTONE_ID = ?";

                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setInt(1, mId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    milestone.setMilestone_id(mId);
                    milestone.setSubmission_id(rs.getInt("SUBMISSION_ID"));
                    milestone.setTitle(rs.getString("TITLE"));
                    milestone.setTask(rs.getString("TASK"));
                    milestone.setStudent_name(rs.getString("STD_NAME"));
                    milestone.setStart_date(rs.getString("START_DATE"));
                    milestone.setEnd_date(rs.getString("END_DATE"));
                    milestone.setStatus(rs.getString("STATUS"));
                    milestone.setSubmission_remarks(rs.getString("SUBMISSION_REMARKS"));
                    milestone.setSubmission_file_path(rs.getString("SUBMISSION_FILE_PATH"));
                    milestone.setFeedback_text(rs.getString("FEEDBACK_TEXT"));
                    
                    // CRITICAL: Set the Project ID so the JSP Close button works
                    request.setAttribute("projectId", rs.getInt("PROJECT_ID"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            request.setAttribute("data", milestone);
            request.setAttribute("userData", user);
            request.getRequestDispatcher("view-milestone.jsp").forward(request, response);
        }
    }

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String mIdStr = request.getParameter("milestone_id");
    String feedbackSv = request.getParameter("feedback_text");
    int milestoneId = Integer.parseInt(mIdStr);
    int submissionId = 0;
    int projectId = 0; // We need this for the redirect

    try (Connection conn = DriverManager.getConnection("jdbc:derby://localhost:1527/FYPTracker", "app", "app")) {

        // 1. Fetch SUBMISSION_ID and PROJECT_ID
        String findData = "SELECT S.SUBMISSION_ID, M.PROJECT_ID FROM MILESTONE M " +
                          "LEFT JOIN SUBMISSION S ON M.MILESTONE_ID = S.MILESTONE_ID " +
                          "WHERE M.MILESTONE_ID = ?";
        PreparedStatement psData = conn.prepareStatement(findData);
        psData.setInt(1, milestoneId);
        ResultSet rsData = psData.executeQuery();
        
        if (rsData.next()) {
            submissionId = rsData.getInt("SUBMISSION_ID");
            projectId = rsData.getInt("PROJECT_ID"); // Capture the project ID here
        }

        // 2. Only proceed with feedback if a submission exists
        if (submissionId > 0) {
            Part filePart = request.getPart("feedback_file");
            String filePathSv = "";
            
            if (filePart != null && filePart.getSize() > 0) {
                String fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();
                filePart.write(fileName);
                filePathSv = "C:/FYPTrackerFile/" + fileName;
            }

            UserBean currentUser = (UserBean) request.getSession().getAttribute("userData");
            int supervisor_id = (currentUser != null) ? currentUser.getUser_id() : 1001;

            // Delete old feedback
            String deleteQuery = "DELETE FROM FEEDBACK WHERE SUBMISSION_ID = ?";
            PreparedStatement delStmt = conn.prepareStatement(deleteQuery);
            delStmt.setInt(1, submissionId);
            delStmt.executeUpdate();

            // Insert new feedback
            String insertSQL = "INSERT INTO FEEDBACK (SUBMISSION_ID, SUPERVISOR_ID, FEEDBACK_FILE_PATH, FEEDBACK_TEXT) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(insertSQL);
            stmt.setInt(1, submissionId);
            stmt.setInt(2, supervisor_id);
            stmt.setString(3, filePathSv);
            stmt.setString(4, feedbackSv);
            stmt.executeUpdate();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    // 3. REDIRECT to ProjectDetailsServlet with the correct Project ID
    // We also pass status=updated so your project.jsp can show a success message
    response.sendRedirect("ProjectDetailsServlet?id=" + projectId + "&status=updated");
}
}