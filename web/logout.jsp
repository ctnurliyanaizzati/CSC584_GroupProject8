<%-- 
    Document   : logout.jsp
    Created on : Feb 11, 2026, 9:59:42 PM
    Author     : Alis anissa
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    // Invalidate session bila user buka logout.jsp
    HttpSession sessionObj = request.getSession(false);
    if (sessionObj != null) {
        sessionObj.invalidate();
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Logout</title>
    <style>
        body {
            font-family: Segoe UI, sans-serif;
            background: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .logout-box {
            background: white;
            padding: 30px;
            width: 350px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            text-align: center;
        }
        h2 {
            margin-bottom: 15px;
        }
        p {
            color: #555;
            margin-bottom: 25px;
        }
        a {
            display: inline-block;
            padding: 12px 20px;
            background: purple;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        a:hover {
            opacity: 0.9;
        }
    </style>
</head>
<body>

    <div class="logout-box">
        <h2>You have logged out</h2>
        <p>Thank you for using Final Year Project Progress Tracker.</p>
        <a href="login.jsp">Login Again</a>
    </div>

</body>
</html>