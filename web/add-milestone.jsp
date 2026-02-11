<%-- 
    Document   : add-milestone
    Created on : 11 Feb 2026, 7:48:38 pm
    Author     : Haquimi
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String supervisorName = (String) session.getAttribute("supervisorName");
    if (supervisorName == null) {
        supervisorName = "Supervisor";
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Project Milestone | FYP Tracker</title>
</head>

<body>

<h2>Add Project Milestone</h2>

<p>
<strong><%= supervisorName %></strong> (Supervisor)
</p>

<form action="AddMilestoneServlet" method="post">

    <label>Milestone:</label><br>
    <input type="text" name="milestoneName" required><br><br>

    <label>Task:</label><br>
    <textarea name="task" required></textarea><br><br>

    <label>Start Date:</label><br>
    <input type="date" name="startDate" required><br><br>

    <label>End Date:</label><br>
    <input type="date" name="endDate" required><br><br>

    <button type="submit">Add</button>
    <a href="MilestoneServlet">Close</a>

</form>

</body>
</html>
