<%-- 
    Document   : milestones-std
    Created on : Jan 22, 2026, 9:47:01 PM
    Author     : PIEKA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.UserBean"%>
<%@page import="model.MilestoneStdBean"%>


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
%>

<%
    //Get list from Servlet
    List<MilestoneStdBean> list = (List<MilestoneStdBean>) request.getAttribute("milestoneList");
    
    int pending_count = 0;
    int submitted_count = 0;
    
    if (list != null){
        for (MilestoneStdBean m : list){
            if("Pending".equalsIgnoreCase(m.getStatus())) pending_count++;
            else if ("Completed".equalsIgnoreCase(m.getStatus()) || "Submitted".equalsIgnoreCase(m.getStatus())) submitted_count++;
        }
    }
%>
<!DOCTYPE html>
<html>
    <head>
    <title>FYP Supervision - Student Dashboard</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="robots" content="index,follow">
    <!--<meta name="generator" content="GrapesJS Studio">-->
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&amp;family=Poppins:wght@600;700&amp;display=swap" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;700&amp;family=Inter:wght@400;500;600&amp;display=swap" rel="stylesheet" />
    <link rel="stylesheet" href="dashboard-std-css.css">
    <link rel="stylesheet" href="milestones-css.css">
  </head>

  <body class="gjs-t-body body">
      
    <header class="header" id="i5bdo">
      <div class="header-container" id="i77ma">
        <div id="id2x5" class="brand-group header-spacer">
          <img src="https://app.grapesjs.com/api/assets/random-image?query=%22student%20portrait%22&amp;w=80&amp;h=80" alt="Student photo" class="avatar-img" id="ifs3pk" />
          <span class="welcome-text" id="welcomeText">
               <span id="studentName"><%= full_name %></span><br>
                <span class="student-id" id="studentId"><%= user_id %></span>
            </span>
        </div>
        <nav id="i97rf" class="header-actions">
          <button type="button" id="i3ep7" aria-label="Logout" class="header-button">
            <img src="https://api.iconify.design/lucide-log-out.svg?color=white" alt="" aria-hidden="true" id="ioe0dd" />
            Logout
          </button>
        </nav>
      </div>
    </header>
      
    <main id="main-content" class="main-content">
          <nav class="top-bar" id="top-bar">
             <div class="breadcrumb" id="breadcrumb">
                <a href="dashboardStdServlet" class="gjs-t-link breadcrumb-link" id="ixx2y">Dashboard</a>
                <span class="breadcrumb-divider" id="i9h82">/</span>
                <span class="breadcrumb-current" id="ioi6w">Milestones</span>
            </div>
          </nav>
          <div id="project-summary-card" class="project-summary-card">
              <div id="summary-header" class="summary-header">
                <div class="summary-title-block" id="summary-title-block">
                    <h2 class="gjs-t-h2 summary-title" id="summary-title">Milestone Progress</h2>
                    <p class="summary-description" id="summary-description">Track tasks, timelines, submissions, and supervisor feedback for your Final Year Project.</p>
                </div>
                <div class="summary-stats" id="summary-stats">
                    <div class="stat-pending" id="i64o7">
                        <img src="https://api.iconify.design/lucide-clock.svg?color=%23f59e0b" alt="" aria-hidden="true" class="icon-pending" id="im2o7" />
                        <span class="stat-pending-label">Pending: <%= pending_count %></span>
                    </div>
                    <div class="stat-submitted" id="inedo">
                        <img src="https://api.iconify.design/lucide-check-circle.svg?color=%2310b981" alt="" aria-hidden="true" class="icon-submitted" id="iu9b7" />
                        <span class="stat-submitted-label">Submitted: <%= submitted_count %></span>
                    </div>
                </div>
              </div>
          </div>
          <div class="filters-bar" id="filters-bar">
              <div class="status-filters" id="ibtlf">
                <span class="status-label" id="idxdm">Status:</span>
                    <fieldset class="status-fieldset" id="ig25s">
                    <label class="status-option" id="if1ph">
                    <input type="radio" name="statusFilter" value="all" class="peer status-radio" id="iifid" />
                        <span class="peer-checked:bg-indigo-600 peer-checked:border-indigo-600 custom-radio-indicator" id="i752m">
                            <span class="peer-checked:block custom-radio-dot" id="i5hki"></span>
                        </span>
                        <span class="status-text" id="itgg8">All</span>
                    </label>
                    <label class="status-option" id="ib6ue">
                        <input type="radio" name="statusFilter" value="pending" class="peer status-radio" id="io78g" />
                        <span id="inq9h" class="peer-checked:bg-amber-500 peer-checked:border-amber-500 custom-radio-indicator">
                            <span class="peer-checked:block custom-radio-dot" id="iathx"></span>
                        </span>
                        <span class="status-text" id="ibjrp">Pending</span>
                    </label>
                    <label class="status-option" id="i5i8l">
                        <input type="radio" name="statusFilter" value="submitted" class="peer status-radio" id="itmkr" />
                        <span class="peer-checked:bg-emerald-600 peer-checked:border-emerald-600 custom-radio-indicator" id="ir8uj">
                            <span class="peer-checked:block custom-radio-dot" id="izwdl"></span>
                        </span>
                        <span class="status-text" id="ilgke">Submitted</span>
                    </label>
                    </fieldset>
             </div>
             <div class="search-bar" id="itpje">
                <label for="searchTasks" class="search-label" id="i1pm3">Search tasks</label>
                <form action="MilestoneStdServlet" method="GET">
                    <div class="search-field">
                        <img src="https://api.iconify.design/lucide-search.svg" class="icon-search" />
                        <input type="text" name="searchQuery" id="searchTasks" placeholder="Search milestone tasks..." class="search-input" />
                        <button type="submit" style="display:none;">Search</button>
                    </div>
                </form>
            </div>
          </div>
          <div class="divider" id="i46jg"></div>
          
            
              <div class="table-scroll" id="iscqr">
                <table aria-label="Milestones" class="milestones-table" id="i2i9m">
                  <thead class="table-head" id="im5e6v">
                    <colgroup>
                        <col style="width: 3%;">   <!-- No -->
                        <col style="width: 14%;">  <!-- Task  -->
                        <col style="width: 26%;">  <!-- Description -->
                        <col style="width: 10%;">  <!-- Start Date -->
                        <col style="width: 10%;">  <!-- End Date -->
                        <col style="width: 12%;">  <!-- Status -->
                        <col style="width: 9%;">   <!-- Upload -->
                        <col style="width: 16%;">  <!-- View Feedback -->
                    </colgroup>
                    <tr class="table-header-row" id="icf3mj">
                      <th class="header-no" id="ifo8dp">No</th>
                      <th class="header-task" id="ixza6h">Task</th>
                      <th class="header-description" id="itxuu1">Description</th>
                      <th class="header-start" id="if17dd">Start Date</th>
                      <th class="header-end" id="iv9u7o">End Date</th>
                      <th class="header-status" id="izwckr">Status</th>
                      <th class="header-upload" id="imeweh">Upload</th>
                      <th class="header-feedback" id="igecjh">View Feedback</th>
                    </tr>
                </thead>
                  
                <tbody class="table-body" id="ik5i84">
                    <%
                        if (list != null && !list.isEmpty()){
                            int no = 1;
                            for (MilestoneStdBean ms : list){
                                String status = ms.getStatus();
                                String badgeClass = "badge-pending";
                                String icon = "https://api.iconify.design/lucide-clock.svg?color=%23f59e0b";
                                
                                if ("Completed".equalsIgnoreCase(status) || "Submitted".equalsIgnoreCase(status)) {
                                    badgeClass = "badge-submitted";
                                    icon = "https://api.iconify.design/lucide-check-circle.svg?color=%2310b981";
                                }
                                 
                    %>
                    <tr data-status="<%= status.toLowerCase() %>" class="milestone-row">
                        <td class="cell-no"><%= no++ %></td>
                        <td class="cell-task">
                        <div class="task-block">
                            <span class="task-title"><%= ms.getTitle() %></span>
                        </div>
                        </td>
        <td class="cell-description"><%= ms.getTask() %></td>
        <td class="cell-start"><%= ms.getStart_date() %></td>
        <td class="cell-end"><%= ms.getEnd_date() %></td>
        <td class="cell-status">
            <span class="status-badge <%= badgeClass %>">
                <img src="<%= icon %>" alt="" aria-hidden="true" class="icon-clock" />
                <span class="status-text"><%= status %></span>
            </span>
        </td>
        <td class="cell-upload">
            <a href="std-upload.jsp?milestone_id=<%= ms.getMilestone_id() %>" style="text-decoration: none;">
                <button type="button" class="gjs-t-button upload-button">
                    <img src="https://api.iconify.design/lucide-upload.svg?color=white" alt="" aria-hidden="true" class="icon-upload"/>
                    <span>Upload</span>
                </button>
            </a>
        </td>
        <td class="cell-feedback">
            <a href="feedbackSvServlet?milestone_id=<%= ms.getMilestone_id() %>" style="text-decoration: none;">
            <button type="button" class="feedback-button">
                <img src="https://api.iconify.design/lucide-eye.svg?color=white" alt="" aria-hidden="true" class="icon-eye" />
                <span>View Feedback</span>
            </button>
        </td>
    </tr>
    <%
            } // Penutup for loop
        } else { // Jika list kosong
    %>
    <tr>
        <td colspan="8" style="text-align:center; padding: 20px;">No milestones found for this project.</td>
    </tr>
    <%
        } // Penutup if
    %>
</tbody>
                </table>
              </div>
    </main>
      
    <footer class="footer" id="ibjqz7">
      <div class="footer-container" id="i2ohil">
        <span class="footer-text" id="iblskp">© 2025 FYP Supervision. All rights reserved.</span>
        <div class="footer-links" id="ivvyxm">
          <a href="#" class="gjs-t-link footer-link" id="iuiau2">Privacy</a>
          <a href="#" class="gjs-t-link footer-link" id="iru3zg">Terms</a>
          <a href="#" class="gjs-t-link footer-link" id="i1ajv5">Support</a>
        </div>
      </div>
    </footer>
</body>
