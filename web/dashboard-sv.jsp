<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Supervisor Dashboard | FYP Supervision</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    
    <style>
        /* --- INTEGRATED DASHBOARD STYLES --- */
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

        /* 1. Header Styling */
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

        /* 2. Layout Container & Breadcrumb */
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

        .current {
            color: #1e293b;
            font-weight: 600;
        }

        /* 3. Stat Cards Grid */
        .summary-section h1 {
            font-size: 28px;
            color: #1e293b;
            margin: 0 0 30px 0;
        }

        .dashboard-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 24px;
        }

        .stat-card {
            background: white;
            padding: 24px;
            border-radius: 12px;
            border: 1px solid #e2e8f0;
            display: flex;
            align-items: center;
            gap: 20px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.05);
        }

        .card-icon {
            width: 48px;
            height: 48px;
            border-radius: 10px;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 20px;
        }

        /* Card Colors */
        .blue .card-icon { background: #e0f2fe; color: #0369a1; }
        .orange .card-icon { background: #fff7ed; color: #c2410c; }
        .green .card-icon { background: #dcfce7; color: #15803d; }

        .card-info h3 { margin: 0; font-size: 24px; color: #1e293b; }
        .card-info p { margin: 0; font-size: 14px; color: #64748b; font-weight: 500; }

        /* 4. Quick Actions (Aligned Neat Card) */
        .quick-actions-section { margin-top: 50px; }
        .quick-actions-section h2 { font-size: 20px; color: #1e293b; margin-bottom: 20px; }

        .action-item {
            background: white;
            border: 1px solid #e2e8f0;
            border-radius: 12px;
            padding: 40px;
            text-align: center;
            text-decoration: none;
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 15px;
            transition: 0.2s;
            box-shadow: 0 1px 3px rgba(0,0,0,0.05);
        }

        .action-item:hover {
            border-color: #6366f1;
            transform: translateY(-3px);
            box-shadow: 0 4px 12px rgba(99, 102, 241, 0.1);
        }

        .action-item i { font-size: 32px; color: #6366f1; }
        .action-item span { font-weight: 600; color: #1e293b; }

        /* 5. Footer Alignment Fix */
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
                <span class="name">${userName}</span>
                <span class="id">${userID}</span>
            </div>
        </div>
        <a href="logout.html" style="text-decoration: none;">
            <button class="logout-btn"><i class="fas fa-sign-out-alt"></i> Logout</button>
        </a>
    </header>

    <div class="dashboard-container">
        <nav class="breadcrumb">
            <span class="current">Dashboard</span>
        </nav>

        <div class="summary-section">
            <div class="text-block">
                <h1>Welcome Back, Supervisor</h1>
            </div>
        </div>

        <div class="dashboard-grid">
            <div class="stat-card blue">
                <div class="card-icon"><i class="fas fa-user-graduate"></i></div>
                <div class="card-info">
                    <h3>${totalStudents}</h3>
                    <p>Total Students</p>
                </div>
            </div>

            <div class="stat-card orange">
                <div class="card-icon"><i class="fas fa-project-diagram"></i></div>
                <div class="card-info">
                    <h3>${ongoingCount}</h3>
                    <p>On-going Projects</p>
                </div>
            </div>

            <div class="stat-card green">
                <div class="card-icon"><i class="fas fa-check-circle"></i></div>
                <div class="card-info">
                    <h3>${completedCount}</h3>
                    <p>Completed Projects</p>
                </div>
            </div>
        </div>

        <div class="quick-actions-section">
            <h2>Quick Actions</h2>
            <div class="quick-actions-grid">
                <a href="ProjectListServlet" class="action-item">
                    <i class="fas fa-list-ul"></i>
                    <span>View Project List</span>
                </a>
            </div>
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