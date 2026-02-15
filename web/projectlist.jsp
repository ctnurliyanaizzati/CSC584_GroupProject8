<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FYP Supervision | Project List</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    
    <style>
        /* --- INTEGRATED SUPERVISOR STYLES --- */
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

        .user-profile {
            display: flex;
            align-items: center;
            gap: 12px;
        }

        .avatar-icon {
            width: 40px;
            height: 40px;
            background-color: #f1f5f9;
            color: #64748b;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 20px;
            border: 1px solid #e2e8f0;
        }

        .user-identity .name {
            display: block;
            font-weight: 600;
            font-size: 14px;
            color: #1e293b;
        }

        .user-identity .id {
            font-size: 12px;
            color: #64748b;
        }

        .logout-btn {
            background: #ef4444;
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 8px;
            font-weight: 600;
            cursor: pointer;
            transition: 0.2s;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        /* Layout Container */
        .dashboard-container {
            max-width: 1250px;
            margin: 0 auto;
            padding: 30px 40px;
            flex: 1;
            width: 100%;
            box-sizing: border-box;
        }

        .breadcrumb {
            font-size: 13px;
            color: #94a3b8;
            margin-bottom: 25px;
        }

        .breadcrumb a {
            color: #6366f1;
            text-decoration: none;
        }

        /* Summary & Stats */
        .summary-section {
            display: flex;
            justify-content: space-between;
            align-items: flex-end;
            margin-bottom: 25px;
        }

        .summary-section h1 {
            margin: 0;
            font-size: 28px;
            color: #1e293b;
        }

        .stats-block {
            display: flex;
            gap: 24px;
        }

        .stats-block span {
            font-size: 14px;
            font-weight: 600;
            display: flex;
            align-items: center;
            gap: 8px;
            color: #334155;
        }

        .icon-students { color: #6366f1; }
        .icon-projects { color: #10b981; }

        /* Toolbar & Search Fix */
        .toolbar {
            background: white;
            padding: 15px 25px;
            border-radius: 12px 12px 0 0;
            border: 1px solid #e2e8f0;
            border-bottom: none;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .filter-group {
            display: flex;
            align-items: center;
            gap: 15px;
            font-size: 14px;
        }

        .search-group {
            position: relative;
            width: 320px;
            display: flex;
            align-items: center;
        }

        .search-group i {
            position: absolute;
            left: 14px;
            color: #94a3b8;
            z-index: 1;
        }

        .search-group input {
            width: 100%;
            padding: 10px 15px 10px 40px;
            border: 1px solid #e2e8f0;
            border-radius: 8px;
            font-size: 14px;
            outline: none;
            background: #ffffff;
        }

        /* Table Styling */
        .table-wrapper {
            background: white;
            border: 1px solid #e2e8f0;
            border-radius: 0 0 12px 12px;
            overflow: hidden;
            box-shadow: 0 1px 3px rgba(0,0,0,0.05);
        }

        .project-table {
            width: 100%;
            border-collapse: collapse;
        }

        .project-table th {
            background: #f8fafc;
            padding: 16px 24px;
            text-align: left;
            font-size: 11px;
            font-weight: 700;
            text-transform: uppercase;
            color: #64748b;
            border-bottom: 1px solid #e2e8f0;
        }

        .project-table td {
            padding: 18px 24px;
            border-bottom: 1px solid #f1f5f9;
            font-size: 14px;
            color: #475569;
        }

        /* Status Pills */
        .status-pill {
            display: inline-block;
            padding: 6px 14px;
            border-radius: 20px;
            font-weight: 600;
            font-size: 12px;
            min-width: 100px;
            text-align: center;
        }

        .completed { background: #dcfce7; color: #15803d; }
        .on-going { background: #e0f2fe; color: #0369a1; }

        /* Action Links */
        .action-links a {
            text-decoration: none;
            color: #6366f1;
            font-weight: 600;
            font-size: 14px;
            display: inline-flex;
            align-items: center;
            gap: 5px;
        }

        /* Footer */
        .dashboard-footer {
            width: 100%;
            margin-top: 60px;
            padding: 30px 0;
            border-top: 1px solid #e2e8f0;
        }

        .footer-content {
            max-width: 1250px;
            margin: 0 auto;
            padding: 0 40px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .footer-content p, .footer-links a {
            font-size: 14px;
            color: #94a3b8;
            text-decoration: none;
        }

        .footer-links { display: flex; gap: 24px; }
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
        <a href="logout.html" style="text-decoration: none;">
            <button class="logout-btn"><i class="fas fa-sign-out-alt"></i> Logout</button>
        </a>
    </header>

    <div class="dashboard-container">
       <nav class="breadcrumb">
            <a href="dashboardSvServlet">Dashboard</a> / 
            <span class="current">Project List</span>
        </nav>
      

        <div class="summary-section">
            <h1>Project List</h1>
            <div class="stats-block">
                <span><i class="fas fa-users icon-students"></i>Total Students: ${totalStudents}</span>
                <span><i class="fas fa-briefcase icon-projects"></i>Total Projects: ${totalProjects}</span>
            </div>
        </div>

        <div class="table-wrapper">
            <table class="project-table">
                <thead>
                    <tr>
                        <th style="width: 50px;">NO</th>
                        <th>STUDENT NAME</th>
                        <th>PROJECT TITLE</th>
                        <th>PROGRAM</th>
                        <th>SESSION</th>
                        <th style="width: 130px;">STATUS</th>
                        <th style="width: 130px;">ACTION</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="proj" items="${allProjects}" varStatus="status">
                        <tr>
                            <td>${status.count}</td>

                            <td><strong>${proj.student_name}</strong></td> 
                            <td>${proj.title}</td>
                            <td>${proj.program_code}</td>
                            <td>${proj.sessions}</td>
                            <td>
                                <span class="status-pill ${proj.project_status == 'Completed' ? 'completed' : 'on-going'}">
                                    ${proj.project_status}
                                </span>
                            </td>
                            <td class="action-links">
                                <a href="ProjectDetailsServlet?id=${proj.project_id}"><i class="far fa-eye"></i> View Project</a>
                            </td>
                        </tr>
                    </c:forEach>
                  </tbody>
            </table>
        </div>
    </div>

    <footer class="dashboard-footer">
        <div class="footer-content">
            <p>&copy; 2025 FYP Supervision. All rights reserved.</p>
            <div class="footer-links">
                <a href="#">Privacy</a><a href="#">Terms</a><a href="#">Support</a>
            </div>
        </div>
    </footer>

</body>
</html>