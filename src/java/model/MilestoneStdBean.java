/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.io.Serializable;

/**
 *
 * @author PIEKA
 */
public class MilestoneStdBean {
    
    private int milestone_id;
    private String title;
    private String task;
    private String start_date;
    private String end_date;
    private String status;
    
    public MilestoneStdBean(){
        
    }

    public int getMilestone_id() {
        return milestone_id;
    }

    public void setMilestone_id(int milestone_id) {
        this.milestone_id = milestone_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
