<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    // --- SAFE DATA RETRIEVAL ---
    // Using String.valueOf() prevents the "Integer cannot be cast to String" error
    
    String supervisorName = String.valueOf(session.getAttribute("full_name"));
    String userId = String.valueOf(session.getAttribute("user_id"));

    // Convert Request Attributes (IDs are often Integers in the Servlet)
    String milestoneId = String.valueOf(request.getAttribute("milestoneId"));
    String projectId = String.valueOf(request.getAttribute("projectId"));
    String title = String.valueOf(request.getAttribute("title"));
    String task = String.valueOf(request.getAttribute("task"));
    String startDate = String.valueOf(request.getAttribute("startDate"));
    String endDate = String.valueOf(request.getAttribute("endDate"));

    // Clean up "null" strings if the data is missing
    if (supervisorName.equals("null")) supervisorName = "Supervisor";
    if (userId.equals("null")) userId = "";
    if (milestoneId.equals("null")) milestoneId = "";
    if (projectId.equals("null")) projectId = "";
    if (title.equals("null")) title = "";
    if (task.equals("null")) task = "";
    if (startDate.equals("null")) startDate = "";
    if (endDate.equals("null")) endDate = "";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Milestone | FYP Tracker</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    
    <style>
        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap');

        body {
            font-family: 'Inter', sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            color: #334155;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        .main-header {
            background: #ffffff;
            padding: 12px 40px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            border-bottom: 1px solid #e2e8f0;
            position: sticky;
            top: 0;
            z-index: 1000;
        }

        .user-profile { display: flex; align-items: center; gap: 12px; }
        .avatar-icon {
            width: 40px; height: 40px;
            background-color: #f1f5f9;
            color: #64748b;
            border-radius: 50%;
            display: flex; align-items: center; justify-content: center;
            font-size: 20px; border: 1px solid #e2e8f0;
        }

        .user-identity .name { display: block; font-weight: 600; font-size: 14px; color: #1e293b; }
        .user-identity .id { font-size: 12px; color: #64748b; }

        .dashboard-container {
            max-width: 800px;
            margin: 0 auto;
            padding: 30px 40px;
            flex: 1;
            width: 100%;
            box-sizing: border-box;
        }

        .breadcrumb { font-size: 13px; color: #94a3b8; margin-bottom: 25px; }
        .breadcrumb a { color: #6366f1; text-decoration: none; font-weight: 500; }

        .form-card {
            background: #fff;
            padding: 40px;
            border-radius: 12px;
            border: 1px solid #e2e8f0;
            box-shadow: 0 1px 3px rgba(0,0,0,0.05);
        }

        .form-card h2 { margin-top: 0; font-size: 22px; color: #1e293b; margin-bottom: 24px; }

        .form-group { margin-bottom: 20px; }
        .form-group label {
            display: block; font-weight: 700; color: #94a3b8;
            text-transform: uppercase; font-size: 11px;
            letter-spacing: 0.05em; margin-bottom: 8px;
        }

        .form-group input, .form-group textarea {
            width: 100%; padding: 12px;
            border: 1px solid #e2e8f0; border-radius: 8px;
            font-family: inherit; font-size: 14px;
            color: #1e293b; box-sizing: border-box;
        }

        .form-group input:focus, .form-group textarea:focus {
            outline: none; border-color: #6366f1;
            box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.1);
        }

        .date-row { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; }

        .btn-submit {
            background: #6366f1; color: white; border: none;
            padding: 14px 24px; border-radius: 8px;
            font-weight: 700; cursor: pointer; width: 100%;
            display: flex; align-items: center; justify-content: center;
            gap: 8px; font-size: 15px; transition: 0.2s;
        }
        .btn-submit:hover { background: #4f46e5; }

        .btn-cancel {
            display: block; text-align: center; margin-top: 15px;
            color: #64748b; text-decoration: none; font-size: 14px; font-weight: 500;
        }

        .dashboard-footer { padding: 40px 0; border-top: 1px solid #e2e8f0; width: 100%; margin-top: 40px;}
        .footer-content { max-width: 800px; margin: 0 auto; padding: 0 40px; display: flex; justify-content: space-between; }
        .footer-content p { font-size: 14px; color: #94a3b8; }
    </style>
</head>
<body>

    <header class="main-header">
        <div class="user-profile">
            <div class="avatar-icon"><i class="fas fa-user-circle"></i></div>
            <div class="user-identity">
                <span class="name"><%= supervisorName %></span>
                <span class="id"><%= userId %></span>
            </div>
        </div>
    </header>

    <div class="dashboard-container">
        <nav class="breadcrumb">
            <a href="dashboardSvServlet">Dashboard</a> / 
            <a href="ProjectListServlet">Project List</a> / 
            <a href="ProjectDetailsServlet?id=<%= projectId %>">Project Details</a> / 
            <span class="current">Edit Milestone</span>
        </nav>

        <div class="form-card">
            <h2><i class="fas fa-edit" style="color: #6366f1; margin-right: 10px;"></i>Edit Milestone</h2>
            
            <form action="UpdateMilestoneServlet" method="POST">
                <input type="hidden" name="milestoneId" value="<%= milestoneId %>">
                <input type="hidden" name="projectId" value="<%= projectId %>">
                
                <div class="form-group">
                    <label>Task Title</label>
                    <input type="text" name="title" value="<%= title %>" required>
                </div>

                <div class="form-group">
                    <label>Description</label>
                    <textarea name="task" rows="4" required><%= task %></textarea>
                </div>

                <div class="date-row">
                    <div class="form-group">
                        <label>Start Date & Time</label>
                        <input type="datetime-local" name="startDate" value="<%= startDate %>" required>
                    </div>
                    <div class="form-group">
                        <label>End Date & Time</label>
                        <input type="datetime-local" name="endDate" value="<%= endDate %>" required>
                    </div>
                </div>

                <button type="submit" class="btn-submit">
                    <i class="fas fa-save"></i> Update Changes
                </button>
                
                <a href="ProjectDetailsServlet?id=<%= projectId %>" class="btn-cancel">Cancel and Return</a>
            </form>
        </div>
    </div>

    <footer class="dashboard-footer">
        <div class="footer-content">
            <p>© 2025 FYP Supervision. All rights reserved.</p>
        </div>
    </footer>

</body>
</html>