<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register | FYP Tracker</title>

    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/register.css">
</head>

<body>

<header class="app-header">
    <div class="brand">
        <span class="brand-title">FYP Tracker</span>
    </div>
</header>

<main class="main-content">
    <div class="card">

        <h2 class="card-title">Create your account</h2>
        <p class="card-subtitle">
            Register to track milestones and supervisor feedback.
        </p>
        
        <%
            String error = request.getParameter("error");
            if (error != null) {
        %>
            <div class="error-banner" style="display:block;">
                <%
                    if ("unauthorized".equals(error)) {
                        out.print("Access Denied: Your ID is not in our system.");
                    } else if ("failed".equals(error)) {
                        out.print("Registration Failed: Please try again later.");
                    }
                %>
            </div>
        <%
            }
        %>

        <form id="registerForm" class="form-grid"
              action="<%= request.getContextPath() %>/RegisterServlet" method="post">
            
            <div class="form-field">
                <label for="role">Role</label>
                <select id="role" name="role" class="input-bar" required>
                    <option value="student">Student</option>
                    <option value="supervisor">Supervisor</option>
                </select>
            </div>

            <div class="form-field">
                <label for="fullName">Full Name</label>
                <input type="text" id="fullName" name="fullName"
                       class="input-bar" placeholder="FULL NAME" required />
            </div>

            <div class="form-field">
                <label for="userId">ID</label>
                <input type="text" id="userId" name="userId"
                       class="input-bar" placeholder="Student / Supervisor ID" required />
            </div>

            <div class="form-field">
                <label for="email">Email</label>
                <input type="email" id="email" name="email"
                       class="input-bar" placeholder="ID@uitm.edu.my" required />
            </div>

            <div class="form-field">
                <label for="password">Password</label>
                <input type="password" id="password" name="password"
                       class="input-bar" placeholder="Min 8 characters"
                       minlength="8" required />
            </div>

            <div class="form-field">
                <label for="confirmPassword">Confirm Password</label>
                <input type="password" id="confirmPassword" name="confirmPassword"
                       class="input-bar" placeholder="Confirm password"
                       minlength="8" required />
            </div>

            <div class="form-field">
                <label for="program">Program</label>
                <input type="text" id="program" name="program"
                       class="input-bar" placeholder="CS240 / CDCS240" required />
            </div>

            <div class="form-field">
                <label for="session">Session</label>
                <input type="text" id="session" name="session"
                       class="input-bar" placeholder="20264" required />
            </div>

            <div class="form-actions">
                <button type="submit" class="btn-primary">Register</button>
                <a href="<%= request.getContextPath() %>/login.jsp" class="btn-secondary">Login</a>
            </div>

        </form>
    </div>
</main>

</body>
</html>
