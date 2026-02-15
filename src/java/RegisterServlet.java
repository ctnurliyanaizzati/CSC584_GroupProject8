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

    // JDBC constants
    private static final String JDBC_URL = "jdbc:derby://localhost:1527/FYPTracker";
    private static final String JDBC_USER = "app";
    private static final String JDBC_PASS = "app";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve parameters from the form
        String userIdStr = request.getParameter("userId");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String program = request.getParameter("program");
        String sessionVal = request.getParameter("session");

        // Database connection and statements
        Connection conn = null;
        PreparedStatement psCheck = null;
        PreparedStatement psUpdate = null;

        try {
            // Convert userId to an integer
            int userId = Integer.parseInt(userIdStr);

            // Load the JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            // Step 1: Check if the user ID exists in the database
            String checkSql = "SELECT user_id FROM USERS WHERE user_id = ?";
            psCheck = conn.prepareStatement(checkSql);
            psCheck.setInt(1, userId);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                // User exists, update their details
                String updateSql = "UPDATE USERS SET email = ?, password = ?, program_code = ?, sessions = ? WHERE user_id = ?";
                psUpdate = conn.prepareStatement(updateSql);

                // Set the PreparedStatement parameters
                psUpdate.setString(1, email);
                psUpdate.setString(2, password); // Consider hashing password here for security
                psUpdate.setString(3, program);
                psUpdate.setString(4, sessionVal);
                psUpdate.setInt(5, userId);

                // Execute the update query
                int rowsUpdated = psUpdate.executeUpdate();

                if (rowsUpdated > 0) {
                    // Redirect to login page on success
                    response.sendRedirect("login.html?success=1");
                } else {
                    // If update failed, show an error message
                    response.sendRedirect("register.html?error=failed");
                }
            } else {
                // User ID does not exist, cannot update. Display an error
                response.sendRedirect("register.html?error=unauthorized");
            }

        } catch (NumberFormatException e) {
            // Handle invalid userId format
            response.getWriter().println("Error: User ID must be a numeric value.");
        } catch (SQLException e) {
            // Handle SQL-related errors (e.g., duplicate key violation)
            e.printStackTrace();

            // Check if the error message contains "duplicate" or "UNIQUE" for duplicate key errors
            if (e.getMessage().contains("duplicate") || e.getMessage().contains("UNIQUE")) {
                response.getWriter().println("Error: Duplicate key violation - This user ID already exists.");
            } else {
                response.getWriter().println("Database Error: " + e.getMessage());
            }
        } catch (Exception e) {
            // Handle general exceptions
            e.printStackTrace();
            response.getWriter().println("Database Error: " + e.getMessage());
        } finally {
            // Close database resources
            try { if (psCheck != null) psCheck.close(); } catch (Exception e) {}
            try { if (psUpdate != null) psUpdate.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}
