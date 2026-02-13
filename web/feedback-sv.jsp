<%-- 
    Document   : feedback-sv
    Created on : Feb 9, 2026, 11:35:15 PM
    Author     : PIEKA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserBean"%>
<%@page import="model.MilestoneStdBean"%>

<%     
    //get feedback data from servlet
    MilestoneStdBean feedback = (MilestoneStdBean) request.getAttribute("feedbackSv");
    
    //if feedback null
    if (feedback == null){
        feedback = new MilestoneStdBean();
    }
    
    String feedbackSv = (feedback.getFeedback_text() != null)
                         ? feedback.getFeedback_text()
                         : "No feedback provided yet by supervisor for this task.";
%>
<html lang="en">

    <head>
        <title>FYP Supervision - Student Dashboard</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <meta name="robots" content="index,follow">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&amp;family=Poppins:wght@600;700&amp;display=swap" rel="stylesheet" />
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;700&amp;family=Inter:wght@400;500;600&amp;display=swap" rel="stylesheet" />
        <link rel="stylesheet" href="dashboard-std-css.css">
        <link rel="stylesheet" href="milestones-css.css">
        <link rel="stylesheet" href="feedback-sv-css.css">
      
    </head>
<%
   //Get data from Session
    UserBean user = (UserBean) session.getAttribute("userData");
    
    String full_name;
    String user_id;
    
    if (user != null) {
        full_name = user.getFull_name();
        user_id = String.valueOf(user.getUser_id());
    } else {
        full_name = "NUR SYAFIQAH";
        user_id = "2025000000";
    }
    
    //Get milestone_id from URL ?milestone_id=XXX
    String milestone_id = request.getParameter("milestone_id");
%>
    <body class="body">   
        
        <header class="header">
        <div class="header-container">
            <div id="id2x5" class="brand-group header-spacer">
                <img src="https://app.grapesjs.com/api/assets/random-image?query=%22student%20portrait%22&amp;w=80&amp;h=80" alt="Student photo" class="avatar-img"/>
                <span class="welcome-text" id="welcomeText">
                     <span id="studentName"><%= full_name %></span><br>
                    <span class="student-id" id="studentId"><%= user_id %></span>
                </span>
            </div>
            <nav id="i97rf" class="header-actions">
                <button type="button" id="i3ep7" class="header-button" onclick="window.location.href='logout.jsp'">
                    <img src="https://api.iconify.design/lucide-log-out.svg?color=white" alt="" aria-hidden="true"/>
                    Logout
                </button>
            </nav>
        </div>
        </header>
      
    <main class="main-content">
          <nav class="top-bar">
             <div class="breadcrumb" id="breadcrumb">
                <a href="dashboardStdServlet" class="breadcrumb-link">Dashboard</a>
                <span class="breadcrumb-divider">/</span>
                <a href="MilestoneStdServlet" class="breadcrumb-link">Milestones</a>
                <span class="breadcrumb-divider">/</span>
                <span class="breadcrumb-current">Feedback</span>
            </div>
          </nav>
    
        
<section class="content-container">
      <div class="card">
        <div class="card-header">
          <div class="title">
            <span class="indicator"></span>
            <h3>Supervisor Feedback</h3>
            
            <p style="margin-top: 10px; font-weight: 600; color: #666;">
                    Task <%= (feedback.getTitle() != null) ? feedback.getTitle() : "N/A" %>
                </p>
            
            <p class="feedback-intro">
                <%= feedbackSv %>
            </p>
          </div>
          <button class="close-btn" onclick="window.location.href='MilestoneStdServlet'" aria-label="Close">
                <i class="fa fa-times">X</i>
            </button>
        </div>

        <hr class="card-divider">
        <div class="card-body">
             <p><small>Note: Attachments are only available if uploaded by supervisor.</small></p>
        </div>
        <div class="card-body">
          <button class="btn primary">
            <i class="fa fa-paperclip"></i>
            <span>View Attachment</span>
          </button>
          <button class="btn secondary">
            <i class="fa fa-download"></i>
            <span>Download Attachment</span>
          </button>
        </div>
      </div>
    </section>

    </main>
    <footer class="footer">
      <div class="footer-container">
        <span class="footer-text">© 2025 FYP Supervision. All rights reserved.</span>
        <div class="footer-links">
          <a href="#" class="footer-link">Privacy</a>
          <a href="#" class="footer-link">Terms</a>
          <a href="#" class="footer-link">Support</a>
        </div>
      </div>
    </footer>
        
    </body>

</html>

