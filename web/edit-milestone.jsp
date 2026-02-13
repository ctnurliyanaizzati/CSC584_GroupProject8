<%-- 
    Document   : edit-milestone
    Created on : 11 Feb 2026, 7:50:47 pm
    Author     : Haquimi
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String supervisorName = (String) session.getAttribute("supervisorName");

    String milestoneId = (String) request.getAttribute("milestoneId");
    String milestoneName = (String) request.getAttribute("milestoneName");
    String task = (String) request.getAttribute("task");
    String startDate = (String) request.getAttribute("startDate");
    String endDate = (String) request.getAttribute("endDate");

    if (supervisorName == null) {
        supervisorName = "Supervisor";
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Project Milestone | FYP Tracker</title>
</head>

<body>

<h2>Edit Project Milestone</h2>

<p>
<strong><%= supervisorName %></strong> (Supervisor)
</p>
<!--<form action="UpdateMilestoneServlet" method="post"> 

    <!-- Hidden ID 
    <input type="hidden" name="milestoneId" 
           value="<%= milestoneId != null ? milestoneId : "" %>">

    <label>Task</label><br>
    <input type="text" name="milestoneName"
           value="<%= milestoneName != null ? milestoneName : "" %>" required><br><br>

    <label>Description:</label><br>
    <textarea name="task" required>
<%= task != null ? task : "" %>
    </textarea><br><br>

    <label>Start Date:</label><br>
    <input type="date" name="startDate"
           value="<%= startDate != null ? startDate : "" %>" required><br><br>

    <label>End Date:</label><br>
    <input type="date" name="endDate"
        <!--   value="<%= endDate != null ? endDate : "" %>" required><br><br> 

    <button type="submit">Update</button>
    <a href="MilestoneServlet">Close</a>-->


<form action="<%= request.getContextPath() %>/UpdateMilestoneServlet" method="post">
    <input type="hidden" name="milestoneId" value="<%= milestoneId %>">

    <label>Task</label>
    <input type="text" name="milestoneName" value="<%= milestoneName %>" required><br>

    <label>Description</label>
    <textarea name="task" required><%= task %></textarea><br>

    <label>Start Date</label>
    <input type="date" name="startDate" value="<%= startDate %>" required><br>

    <label>End Date</label>
    <input type="date" name="endDate" value="<%= endDate %>" required><br>

    <button type="submit">Update</button>
    <a href="view-milestone.jsp">Close</a>
</form>

</body>
</html>
