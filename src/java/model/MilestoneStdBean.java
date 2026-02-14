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
    private String feedback_file_path;
    private String feedback_text;
    private String student_name;
    private String submission_file_path;
    private String submission_remarks;
    
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

    public String getFeedback_file_path() {
        return feedback_file_path;
    }

    public void setFeedback_file_path(String feedback_file_path) {
        this.feedback_file_path = feedback_file_path;
    }

    
    
    public String getFeedback_text() {
        return feedback_text;
    }

    public void setFeedback_text(String feedback_text) {
        this.feedback_text = feedback_text;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getSubmission_file_path() {
        return submission_file_path;
    }

    public void setSubmission_file_path(String submission_file_path) {
        this.submission_file_path = submission_file_path;
    }

    public String getSubmission_remarks() {
        return submission_remarks;
    }

    public void setSubmission_remarks(String submission_remarks) {
        this.submission_remarks = submission_remarks;
    }
    
    
}
