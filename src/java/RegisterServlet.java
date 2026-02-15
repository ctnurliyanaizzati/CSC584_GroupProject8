import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private static final String JDBC_URL = "jdbc:derby://localhost:1527/FYPTracker";
    private static final String JDBC_USER = "app";
    private static final String JDBC_PASS = "app";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userIdStr = request.getParameter("userId");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String program = request.getParameter("program");
        String sessionVal = request.getParameter("session");

        Connection conn = null;
        PreparedStatement psCheck = null;
        PreparedStatement psUpdate = null;

        try {
            int userId = Integer.parseInt(userIdStr);

            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            String checkSql = "SELECT user_id FROM USERS WHERE user_id = ?";
            psCheck = conn.prepareStatement(checkSql);
            psCheck.setInt(1, userId);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                String updateSql = "UPDATE USERS SET email = ?, password = ?, program_code = ?, sessions = ? WHERE user_id = ?";
                psUpdate = conn.prepareStatement(updateSql);

                psUpdate.setString(1, email);
                psUpdate.setString(2, password);
                psUpdate.setString(3, program);
                psUpdate.setString(4, sessionVal);
                psUpdate.setInt(5, userId);

                int rowsUpdated = psUpdate.executeUpdate();

                if (rowsUpdated > 0) {
                    response.sendRedirect("login.jsp?success=1");
                } else {
                    response.sendRedirect("register.jsp?error=failed");
                }
            } else {
                response.sendRedirect("register.jsp?error=unauthorized");
            }

        } catch (NumberFormatException e) {
            response.getWriter().println("Error: User ID must be a numeric value.");
        } catch (SQLException e) {
            e.printStackTrace();

            if (e.getMessage().contains("duplicate") || e.getMessage().contains("UNIQUE")) {
                response.getWriter().println("Error: Duplicate key violation - This user ID already exists.");
            } else {
                response.getWriter().println("Database Error: " + e.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        } finally {
            try { if (psCheck != null) psCheck.close(); } catch (Exception e) {}
            try { if (psUpdate != null) psUpdate.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}

