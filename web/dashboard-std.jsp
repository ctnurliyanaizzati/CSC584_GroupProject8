<%-- 
    Document   : dashboard-std
    Created on : Jan 21, 2026, 10:47:02 PM
    Author     : PIEKA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserBean"%>
<%@page import="model.ProjectBean"%>
<!DOCTYPE html>
<html>
    <head>
    <title>FYP Supervision - Student Dashboard</title>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="robots" content="index,follow">
    <meta name="generator" content="GrapesJS Studio">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&amp;family=Poppins:wght@600;700&amp;display=swap" rel="stylesheet" />
    <link rel="stylesheet" href="dashboard-std-css.css">
  </head>
  
  <%
      //Get data from DashboardStdServlet
      ProjectBean project = (ProjectBean) session.getAttribute("projectData");
      UserBean user = (UserBean) session.getAttribute("userData");
      
      //Error checking
      if(project == null){
          project = new ProjectBean();
          project.setTitle("No Project Assigned");
          project.setSupervisor_name("N/A");
          //project.setSessions("N/A");
      }
      if(user == null){
          user = new UserBean();
          user.setFull_name("N/A");
          user.setUser_id(0);
          user.getSessions();
      }
  %>    
    <body class="body" id="iz0d">
    <header class="header" id="i5bdo">
      <div class="header-container" id="i77ma">
        <div id="id2x5" class="brand-group header-spacer">
          <img src="https://app.grapesjs.com/api/assets/random-image?query=%22student%20portrait%22&amp;w=80&amp;h=80" alt="Student photo" class="avatar-img" id="ifs3pk" />
          <span class="welcome-text" id="welcomeText">
                <span class="studentName" id="studentName"><%=user.getFull_name()%></span><br>
                <span class="student-id" id="studentId"><%=user.getUser_id()%></span>
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
    <main class="main" id="ilu9u">
      <section class="dashboard-section" id="irw9p">
        <div class="dashboard-grid" id="i1n9e">
          <div id="irfld" class="left-pane">
        

            <div role="region" aria-labelledby="project-overview-title" class="project-card" id="i272qj">
              <div class="project-card-header" id="ind50b">
                <img src="https://api.iconify.design/lucide-briefcase.svg?color=%23374151" alt="" aria-hidden="true" width="20" height="20" loading="lazy" id="iudpql" />
                <span id="project-overview-title" class="project-card-title">Project Overview</span>
              </div>
              <div class="overview-body" id="iua6ma">
                <div class="info-row" id="ikhuqe">
                  <span class="info-label" id="isn3x1">Project:</span>
                  <span id="iy3ky7"><%= project.getTitle() %></span>
                </div>
                <div class="info-row" id="igekvh">
                  <span class="info-label" id="io163n">Supervisor:</span>
                  <span id="iracqe"><%= project.getSupervisor_name() %></span>
                </div>
                <div class="info-row" id="i0ymff">
                  <span class="info-label" id="il6t4v">Session:</span>
                  <span id="ibs313"><%= user.getSessions() %></span>
                </div>
              </div>
            </div>
            <div role="region" aria-labelledby="metrics-title" class="project-card" id="iormdl">
              <div class="project-card-header" id="ipmpmg">
                <img src="https://api.iconify.design/lucide-bar-chart-3.svg?color=%23374151" alt="" aria-hidden="true" width="20" height="20" loading="lazy" id="i6xbaq" />
                <span id="metrics-title" class="project-card-title">Project Status</span>
              </div>
              <div class="metrics-grid" id="id7x8g">
                <div class="metric-item" id="inrinm">
                  <img src="https://api.iconify.design/lucide-list-check.svg?color=%23374151" alt="" aria-hidden="true" loading="lazy" class="metric-icon" id="i3remy" />
                  <div id="iy4olz">
                    <div class="metric-value" id="ikcrfa"><%= project.getTotal_tasks() %></div>
                    <div class="metric-label" id="i2396h">Total Tasks</div>
                  </div>
                </div>
                <div class="metric-item" id="iozcp7">
                  <img src="https://api.iconify.design/lucide-hourglass.svg?color=%23374151" alt="" aria-hidden="true" loading="lazy" class="metric-icon" id="iz7o1r" />
                  <div id="i44cvb">
                    <div class="metric-value" id="i5hbso"><%= project.getPending_tasks() %></div>
                    <div class="metric-label" id="iagm68">Pending Tasks</div>
                  </div>
                </div>
                <div class="metric-item" id="ief3kd">
                  <img src="https://api.iconify.design/lucide-check-circle.svg?color=%23374151" alt="" aria-hidden="true" loading="lazy" class="metric-icon" id="i6famy" />
                  <div id="i3awfd">
                    <div class="metric-value" id="iax1eu"><%= project.getPending_tasks() %></div>
                    <div class="metric-label" id="inm4ry">Completed Tasks</div>
                  </div>
                </div>
              </div>
              <div class="cta-wrap" id="iq7lml">
                <a href="MilestoneStdServlet" aria-label="View all tasks" class="gjs-t-button cta-button" id="idtdqc">
                    View All Tasks
                </a>
              </div>
            </div>
          </div>
          <aside class="right-pane" id="iljcj">
            <div class="important-dates-card" id="ism4u">
              <div class="important-dates-header" id="i8e65">
                <div class="important-dates-title-group" id="idrus">
                  <img src="https://api.iconify.design/lucide-calendar.svg?color=%23374151" alt="Calendar Icon" class="important-dates-icon" id="i86b5" />
                  <h2 class="gjs-t-h2 important-dates-title" id="ijljio">Important Dates</h2>
                </div>
                <span class="important-dates-badge" id="iw70wq">Upcoming</span>
              </div>
              <ul class="important-dates-list" id="iqt83s">
                <li class="important-date-item" id="iy5kah">
                  <div class="date-badge" id="io4ijf">
                    <span class="date-badge-text" id="io701n">01 Feb</span>
                  </div>
                  <div class="date-content" id="iozc4l">
                    <span class="date-title" id="icpnns">Final submission of FYP report</span>
                    <p class="date-description" id="ifnhh3">Submit the comprehensive project documentation to UFuture</p>
                  </div>
                </li>
                <li class="important-date-item" id="iqs7a6">
                  <div class="date-badge" id="ieoiuz">
                    <span class="date-badge-text" id="iy1wpu">03 - 07 Feb</span>
                  </div>
                  <div class="date-content" id="iaco69">
                    <span class="date-title" id="i0ds1j">Final Year Project Presentation Week</span>
                    <p class="date-description" id="ixbdii">Students will present their FYP outcomes and demonstrate key deliverables to assessors.</p>
                  </div>
                </li>
              </ul>
              <div id="iy8em5" class="important-dates-footer"></div>
            </div>
            <div id="i3totj" class="support-card"></div>
          </aside>
        </div>
      </section>
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

</html>
