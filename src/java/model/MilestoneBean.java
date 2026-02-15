package model;

import java.io.Serializable;
import java.sql.Timestamp; // Import Timestamp

public class MilestoneBean implements Serializable {
    private int milestone_id;
    private int project_id;
    private String title;
    private String task;
    private Timestamp start_date; // Changed to Timestamp
    private Timestamp end_date;   // Changed to Timestamp
    private String status;

    public MilestoneBean() {}

    public int getMilestone_id() { return milestone_id; }
    public void setMilestone_id(int milestone_id) { this.milestone_id = milestone_id; }

    public int getProject_id() { return project_id; }
    public void setProject_id(int project_id) { this.project_id = project_id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getTask() { return task; }
    public void setTask(String task) { this.task = task; }

    // Updated Getters/Setters
    public Timestamp getStart_date() { return start_date; }
    public void setStart_date(Timestamp start_date) { this.start_date = start_date; }

    public Timestamp getEnd_date() { return end_date; }
    public void setEnd_date(Timestamp end_date) { this.end_date = end_date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}