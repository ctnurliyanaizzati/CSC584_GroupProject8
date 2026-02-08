<%-- 
    Document   : std-upload
    Created on : Jan 29, 2026, 9:52:27 PM
    Author     : PIEKA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

    <head>
        <title>FYP Supervision - Student Dashboard</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <meta name="robots" content="index,follow">
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&amp;family=Poppins:wght@600;700&amp;display=swap" rel="stylesheet" />
        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@500;700&amp;family=Inter:wght@400;500;600&amp;display=swap" rel="stylesheet" />
        <link rel="stylesheet" href="dashboard-std-css.css">
        <link rel="stylesheet" href="milestones-css.css">
        <link rel="stylesheet" href="std-upload-css.css">
    </head>

    <body class="body">   
        
        <header class="header">
        <div class="header-container">
            <div id="id2x5" class="brand-group header-spacer">
                <img src="https://app.grapesjs.com/api/assets/random-image?query=%22student%20portrait%22&amp;w=80&amp;h=80" alt="Student photo" class="avatar-img"/>
                <span class="welcome-text" id="welcomeText">
                    <span id="studentName">NUR SYAFIQAH BINTI MAT RADZI</span><br>
                    <span class="student-id" id="studentId">2025107905</span>
                </span>
            </div>
            <nav id="i97rf" class="header-actions">
                <button type="button" id="i3ep7" aria-label="Logout" class="header-button">
                    <img src="https://api.iconify.design/lucide-log-out.svg?color=white" alt="" aria-hidden="true"/>
                    Logout
                </button>
            </nav>
        </div>
        </header>
      
    <main class="main-content">
          <nav class="top-bar">
             <div class="breadcrumb" id="breadcrumb">
                <a href="dashboard-std.html" class="breadcrumb-link">Dashboard</a>
                <span class="breadcrumb-divider">/</span>
                <a href="milestones-std.html" class="breadcrumb-link">Milestones</a>
                <span class="breadcrumb-divider">/</span>
                <span class="breadcrumb-current">Upload</span>
            </div>
          </nav>
    </main>
        
    
<!-- ===== Upload Section ===== -->

<% String milestone_id = request.getParameter("milestone_id"); %> 

    <section class="page-section">
        <form action="SubmissionStdServlet" method="POST" enctype="multipart/form-data">
            <input type="hidden" name="milestone_id" value="<%= milestone_id %>">
        <div class="upload-card__header">
            <h1 class="upload-title">Upload your file</h1>
        </div>
        <div class="upload-card__body">
        <!-- Helper text -->
            <div class="upload-helper">
            <span class="upload-info-icon" aria-hidden="true">i</span>
            <span>
                Drag and drop a file into the area below or click the browse button to select from your device.
            </span>
            </div>
        
        <!-- File -->
            <label class="upload-label">File</label>
            <div id="dropzone" class="upload-dropzone" tabindex="0" aria-label="Drag and drop area">
                <div class="upload-dropzone__inner">
                    <div class="upload-icon" aria-hidden="true">⬆</div>
                    <div class="upload-dz-text">Drag &amp; drop your file here</div>
                    <div class="upload-dz-or">or</div>
                    <label class="upload-browse-btn" for="fileInput">Browse files</label>
                    <input id="fileInput" name="submission_file_path" type="file" hidden />
                </div>
            </div>
            <div id="fileName" class="upload-file-name" aria-live="polite"></div>

        <!-- Remarks -->
            <label class="upload-label" for="remarks">Remarks</label>
            <textarea id="remarks" name="remarks" class="upload-input" rows="4"
                placeholder="Add any notes or context for the file..."></textarea>
        </div>

    <div class="upload-card__footer">
        <button type="button" class="btn btn-secondary" id="btnCancel">Cancel</button>
      <div class="upload-actions">
          <button type="submit" class="btn btn-primary" id="btnSubmit">Submit</button>
      </div>
       </div>
  </form>
</section>
     
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
