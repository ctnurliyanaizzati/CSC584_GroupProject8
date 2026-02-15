<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Milestone | FYP Supervision</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    
    <style>
        /* --- MATCHING STYLES FROM PROJECT.JSP --- */
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

        /* Header Styling */
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

        /* Container */
        .dashboard-container {
            max-width: 800px; /* Slimmer for form focus */
            margin: 0 auto;
            padding: 30px 40px;
            flex: 1;
            width: 100%;
            box-sizing: border-box;
        }

        .breadcrumb { font-size: 13px; color: #94a3b8; margin-bottom: 25px; }
        .breadcrumb a { color: #6366f1; text-decoration: none; font-weight: 500; }

        /* Form Card Styling */
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

        /* Buttons */
        .btn-submit {
            background: #10b981; color: white; border: none;
            padding: 14px 24px; border-radius: 8px;
            font-weight: 700; cursor: pointer; width: 100%;
            display: flex; align-items: center; justify-content: center;
            gap: 8px; font-size: 15px; transition: 0.2s;
        }
        .btn-submit:hover { background: #059669; }

        .btn-cancel {
            display: block; text-align: center; margin-top: 15px;
            color: #64748b; text-decoration: none; font-size: 14px; font-weight: 500;
        }

        /* Footer */
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
                <span class="name">${full_name}</span>
                <span class="id">${user_id}</span>
            </div>
        </div>
    </header>

    <div class="dashboard-container">
        <nav class="breadcrumb">
            <a href="dashboardSvServlet">Dashboard</a> / 
            <a href="ProjectListServlet">Project List</a> / 
            <a href="ProjectDetailsServlet?id=${param.projectId}">Project Details</a> / 
            <span class="current">Add Milestone</span>
        </nav>

        <div class="form-card">
            <h2><i class="fas fa-plus-circle" style="color: #6366f1; margin-right: 10px;"></i>Add New Milestone</h2>
            
            <form action="AddMilestoneServlet" method="POST">
                <input type="hidden" name="projectId" value="${param.projectId}">
                
                <div class="form-group">
                    <label>Task</label>
                    <input type="text" name="title" placeholder="e.g., Literature Review Draft" required>
                </div>

                <div class="form-group">
                    <label>Description</label>
                    <textarea name="task" rows="4" placeholder="Detail the tasks the student needs to complete..." required></textarea>
                </div>

                <div class="date-row">
                    <div class="form-group">
                        <label>Start Date</label>
                        <input type="datetime-local" name="startDate" required>
                    </div>
                    <div class="form-group">
                        <label>End Date</label>
                        <input type="datetime-local" name="endDate" required>
                    </div>
                </div>

                <button type="submit" class="btn-submit">
                    <i class="fas fa-save"></i> Save Milestone
                </button>
                
                <a href="ProjectDetailsServlet?id=${param.projectId}" class="btn-cancel">Cancel and Return</a>
            </form>
        </div>
    </div>

    <footer class="dashboard-footer">
        <div class="footer-content">
            <p>&copy; 2025 FYP Supervision. All rights reserved.</p>
        </div>
    </footer>

</body>
</html>