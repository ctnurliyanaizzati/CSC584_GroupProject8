<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="project" class="model.ProjectBean" scope="request" />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Project Details | FYP Supervision</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    
    <style>
        /* --- INTEGRATED STYLES --- */
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
            display: flex;
            align-items: center;
            gap: 8px;
            transition: 0.2s;
        }

        .logout-btn:hover { background: #dc2626; }

        /* Container & Navigation */
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
            font-weight: 500;
        }

        /* Project Info Card */
        .project-info-card {
            background: #fff;
            padding: 32px;
            border-radius: 12px;
            border: 1px solid #e2e8f0;
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            margin-bottom: 35px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.05);
        }

        .info-group {
            display: grid;
            grid-template-columns: 180px 1fr;
            gap: 12px;
            flex: 1;
        }

        .info-item { display: contents; }

        .info-item label {
            font-weight: 700;
            color: #94a3b8;
            text-transform: uppercase;
            font-size: 11px;
            letter-spacing: 0.05em;
        }

        .info-item .value {
            color: #1e293b;
            font-size: 15px;
            font-weight: 600;
        }

        /* Action Buttons */
        .action-buttons {
            display: flex;
            flex-direction: column;
            gap: 12px;
        }

        .btn-primary {
            background: #6366f1;
            color: white;
            border: none;
            padding: 12px 20px;
            border-radius: 8px;
            font-weight: 600;
            cursor: pointer;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .btn-success {
            background: #10b981;
            color: white;
            border: none;
            padding: 12px 20px;
            border-radius: 8px;
            font-weight: 600;
            cursor: pointer;
            display: flex;
            align-items: center;
            gap: 8px;
        }

        /* Table Section */
        .summary-section h1 {
            font-size: 24px;
            color: #1e293b;
            margin-bottom: 20px;
        }

        .table-wrapper {
            background: white;
            border: 1px solid #e2e8f0;
            border-radius: 12px;
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
            padding: 6px 14px;
            border-radius: 20px;
            font-weight: 600;
            font-size: 12px;
            display: inline-block;
            min-width: 100px;
            text-align: center;
        }

        .open { background: #e0f2fe; color: #0369a1; }
        .completed { background: #dcfce7; color: #15803d; }

        /* Action Links */
        .action-links { display: flex; gap: 15px; }
        .action-links a {
            text-decoration: none;
            color: #6366f1;
            font-weight: 600;
            font-size: 13px;
            display: flex;
            align-items: center;
            gap: 5px;
        }

        .text-danger { color: #ef4444 !important; }

        /* Footer Alignment Fix */
        .dashboard-footer {
            margin-top: auto;
            padding: 40px 0;
            border-top: 1px solid #e2e8f0;
            width: 100%;
        }

        .footer-content {
            max-width: 1250px;
            margin: 0 auto;
            padding: 0 40px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            box-sizing: border-box;
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
            <div class="avatar-icon">
                <i class="fas fa-user-circle"></i>
            </div>
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
            <a href="ProjectListServlet">Project List</a> / 
            <span class="current">Project Details</span>
        </nav>

        <div class="project-info-card">
            <div class="info-group">
                <div class="info-item">
                    <label>Student Name</label>
                    <span class="value">${requestScope.studentName}</span>
                </div>
                <div class="info-item">
                    <label>Project Name</label>
                    <span class="value">${project.title}</span> 
                </div>
                <div class="info-item">
                    <label>Project Status</label>
                    <span class="value">${project.project_status}</span> 
                </div>
                <div class="info-item">
                    <label>Session</label>
                    <span class="value">${project.sessions}</span>
                </div>
            </div>

            <div class="action-buttons">
                <button class="btn-primary" onclick="location.href='add-milestone.jsp'">
                    <i class="fas fa-plus"></i> Add Milestone
                </button>

                <form action="UpdateProjectStatusServlet" method="POST" style="display:inline;" 
                        onsubmit="return confirm('Are you sure you want to mark this project as completed?');">

                      <input type="hidden" name="projectId" value="${project.project_id}">
                      <input type="hidden" name="newStatus" value="Completed">

                      <button type="submit" class="btn-success">
                          <i class="fas fa-check-double"></i> Mark Project as Completed
                      </button>
                  </form>
            </div>
        </div> <%-- <--- ADDED THIS MISSING CLOSING DIV --%>

        <div class="summary-section">
            <h1>Milestone List</h1>
        </div>

        <div class="table-wrapper">
            <table class="project-table">
                <thead>
                    <tr>
                        <th style="width: 60px;">No</th>
                        <th>Milestone</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th style="width: 140px;">Status</th>
                        <th style="width: 180px;">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <%-- Note: Later you will replace this with <c:forEach items="${milestones}" var="m"> --%>
                    <tr>
                        <td>1</td>
                        <td><strong>Milestone 1</strong></td>
                        <td>2025-01-02</td>
                        <td>2025-01-05</td>
                        <td><span class="status-pill open">Open</span></td>
                        <td class="action-links">
                            <a href="ViewMilestoneServlet?milestone_id=1"><i class="far fa-eye"></i> View</a>
                            <a href="edit-milestone.html"><i class="far fa-edit"></i> Edit</a>
                            <a href="#" class="text-danger" onclick="return confirm('Are you sure?')"><i class="far fa-trash-alt"></i> Delete</a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>

    <footer class="dashboard-footer">
        <div class="footer-content">
            <p>&copy; 2025 FYP Supervision. All rights reserved.</p>
            <div class="footer-links">
                <a href="#">Privacy</a>
                <a href="#">Terms</a>
                <a href="#">Support</a>
            </div>
        </div>
    </footer>
</body>
</html>