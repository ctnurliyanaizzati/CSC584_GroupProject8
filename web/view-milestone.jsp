<%-- 
    Document   : view-milestone
    Created on : Feb 12, 2026, 10:28:24 PM
    Author     : PIEKA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>View Project Milestone | FYP Tracker</title>
<style>
    body {
        font-family: "Segoe UI", Arial, sans-serif;
        background-color: #f5f7fa;
        margin: 0;
        padding: 0;
        color: #333;
    }

    /* HEADER */
    .header {
        background: #ffffff;
        padding: 16px 32px;
        display: flex;
        justify-content: space-between;
        align-items: center;
        border-bottom: 1px solid #ddd;
    }
    .breadcrumb {
    font-size: 14px;
    color: #555;
    margin: 20px auto;
    max-width: 2500px; 
    padding: 0 30px;
    }
    .breadcrumb a {
    text-decoration: none;   
    color: #555;            
    font-weight: normal;
    cursor: pointer;
    }


    .user-info {
        display: flex;
        align-items: center;
        gap: 12px;
    }

    .avatar {
        width: 42px;
        height: 42px;
        border-radius: 50%;
        background: #bbb;
    }

    .logout {
        background: #ef4444;
        color: white;
        padding: 8px 16px;
        border: none;
        border-radius: 6px;
        cursor: pointer;
        font-size: 14px;
    }

    /* CONTENT */
    .container {
        max-width: 900px;
        margin: 40px auto;
        background: #ffffff;
        padding: 30px;
        border-radius: 10px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.1);
    }

    h2 {
        margin-bottom: 25px;
        font-size: 22px;
        border-bottom: 1px solid #ddd;
        padding-bottom: 10px;
    }

    .info-grid {
        display: grid;
        grid-template-columns: repeat(2, 1fr);
        gap: 15px;
        margin-bottom: 20px;
    }

    .info-item {
        font-size: 14px;
    }

    .info-item b {
        display: inline-block;
        width: 130px;
        color: #555;
    }

    .status {
        padding: 4px 12px;
        background-color: #d1fae5;
        color: #065f46;
        border-radius: 20px;
        font-size: 12px;
        font-weight: bold;
    }

    .section {
        margin-top: 25px;
    }

    .attachment-buttons button {
        background-color: #2563eb;
        color: #fff;
        border: none;
        padding: 8px 16px;
        margin-right: 10px;
        border-radius: 5px;
        cursor: pointer;
    }

    .attachment-buttons button:last-child {
        background-color: #374151;
    }

    textarea {
        width: 100%;
        height: 90px;
        padding: 10px;
        border-radius: 5px;
        border: 1px solid #ccc;
        margin-top: 8px;
        resize: none;
    }

    input[type="file"] {
        margin-top: 10px;
    }

    .btn-group {
        text-align: right;
        margin-top: 30px;
    }

    .btn-group button {
        padding: 8px 20px;
        border: none;
        border-radius: 5px;
        cursor: pointer;
        font-size: 14px;
    }

    .btn-submit {
        background-color: #10b981;
        color: #fff;
        margin-right: 10px;
    }

    .btn-close {
        background-color: #9ca3af;
        color: #fff;
    }
    .dashboard-footer {
        max-width: 22500px; 
    }
    .dashboard-footer {
    display: flex;
    flex-direction: column;     /* stack text & links */
    align-items: center;        /* horizontal center */
    justify-content: center;    /* vertical center */
    padding: 20px 0;
    font-family: Arial, sans-serif;
    font-size: 14px;
    color: #555;
}

.dashboard-footer p {
    margin: 0 0 8px 0;
}

.footer-links {
    display: flex;
    gap: 15px;                  /* space between links */
}

.footer-links a {
    text-decoration: none;      /* remove underline */
    color: #555;
    font-size: 14px;
}

.footer-links a:hover {
    color: #000;
}

</style>
</head>
<body>

<!-- HEADER -->
<div class="header">
    <div class="user-info">
        <div class="avatar"></div>
        <div>
            <strong>DR. AHMAD BIN ALI</strong><br>
            <small>Supervisor</small>
        </div>
    </div>
    <button class="logout">Logout</button>
</div>
 <div class="breadcrumb">
           <a href="dashboard-sv.html">Dashboard</a> / <a href="projectlist.html"><strong> Project List</strong></a>
    </div>
<!-- CONTENT -->
<div class="container">
    <h2>View Project Milestone</h2>

    <div class="info-grid">
        <div class="info-item"><b>Student Name:</b> HAQUIMI AMDAN</div>
        <div class="info-item"><b>Project Title:</b> Methodology Design</div>
        <div class="info-item"><b>Milestone Name:</b> Milestone 1</div>
        <div class="info-item"><b>Task:</b> Make proposal</div>
        <div class="info-item"><b>Start Date:</b> 01/02/2025</div>
        <div class="info-item"><b>End Date:</b> 05/02/2025</div>
        <div class="info-item"><b>Status:</b> <span class="status">Submitted</span></div>
        <div class="info-item"><b>Student Remarks:</b> Methodology final version and updated</div>
    </div>

    <div class="section">
        <b>Attachment</b>
        <div class="attachment-buttons">
            <button>View</button>
            <button>Download</button>
        </div>
    </div>

    <div class="section">
        <label><b>Supervisor Feedback</b></label>
        <textarea placeholder="Write feedback here..."></textarea>
    </div>

    <div class="section">
        <label><b>Upload Attachment</b></label><br>
        <input type="file">
    </div>

   <div class="btn-group">
        <a href="edit-milestone.jsp">
            <button class="btn-submit">Update</button>
        </a>

        <a href="project.jsp">
            <button class="btn-close">Close</button>
        </a>
    </div>

</div>
<footer class="dashboard-footer">
            <p>&copy; 2025 FYP Supervision. All rights reserved.</p>
            <div class="footer-links">
                <a href="#">Privacy</a>
                <a href="#">Terms</a>
                <a href="#">Support</a>
            </div>
 </footer>
</body>
</html>

