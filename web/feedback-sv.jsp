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
    
        
<section class="content-container" style="display: flex; justify-content: center; padding-top: 50px;">
    <div class="card" style="width: 100%; max-width: 650px; background: white; border-radius: 16px; box-shadow: 0 4px 20px rgba(0,0,0,0.08); position: relative; padding: 30px;">
        
        <div style="display: flex; justify-content: space-between; align-items: flex-start; margin-bottom: 20px;">
            <div style="display: flex; flex-direction: column; gap: 5px;">
                <div style="display: flex; align-items: center; gap: 10px;">
                    <span class="indicator" style="height: 22px; width: 4px; background: #2563eb; border-radius: 2px;"></span>
                    <h3 style="margin: 0; font-size: 1.5rem; color: #1e3a8a; font-family: 'Poppins', sans-serif;">Supervisor Feedback</h3>
                </div>
                <div style="margin-left: 14px; display: flex; align-items: center; gap: 5px;">
                    <span style="color: #94a3b8; font-size: 0.9rem; font-weight: 500; text-transform: uppercase;">Task:</span>
                    <span style="color: #475569; font-size: 1.1rem; font-weight: 600;"><%= (feedback.getTitle() != null) ? feedback.getTitle() : "N/A" %></span>
                </div>
            </div>

            <button class="close-btn" onclick="window.location.href='MilestoneStdServlet'" 
                    style="background: #f1f5f9; color: #64748b; border: 1px solid #e2e8f0; width: 32px; height: 32px; border-radius: 8px; cursor: pointer; display: flex; align-items: center; justify-content: center; font-size: 18px; transition: 0.2s;">
                &times;
            </button>
        </div>

        <div style="background: #f8fafc; border-radius: 12px; padding: 25px; border: 1px solid #f1f5f9; margin-bottom: 25px; text-align: center;">
            <p style="color: #334155; font-size: 1.15rem; line-height: 1.6; font-style: italic; margin: 0;">
                "<%= (feedbackSv != null) ? feedbackSv : "No feedback text provided." %>"
            </p>
        </div>

        <div style="border-top: 1px solid #f1f5f9; padding-top: 20px; text-align: center;">
            <p style="margin-bottom: 15px; font-size: 0.8rem; color: #94a3b8;">Note: Attachments are only available if uploaded by supervisor.</p>
            
            <div style="display: flex; align-items: center; gap: 15px; justify-content: center; flex-wrap: wrap;">
                <% 
                    String filePath = feedback.getFeedback_file_path(); 
                    if (filePath != null && !filePath.isEmpty()) { 
                %>
                    <button type="button" class="btn primary" onclick="alert('File Path: <%= filePath %>')" 
                            style="background: #2563eb; color: white; border: none; padding: 12px 24px; border-radius: 8px; cursor: pointer; font-weight: 500; display: flex; align-items: center; gap: 8px; box-shadow: 0 4px 6px rgba(37, 99, 235, 0.15);">
                        <i class="fa fa-paperclip"></i> View Attachment
                    </button>
                    
                    <span style="font-size: 0.85rem; color: #2563eb; font-weight: 500; background: #eff6ff; padding: 10px 15px; border-radius: 8px; border: 1px dashed #bfdbfe;">
                        <strong>File Path:</strong> <%= filePath %>
                    </span>
                <% } else { %>
                    <button type="button" disabled style="background: #f8fafc; color: #cbd5e1; border: 1px solid #f1f5f9; padding: 12px 24px; border-radius: 8px; cursor: not-allowed;">
                        No Attachment
                    </button>
                <% } %>
            </div>
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

