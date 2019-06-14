package com.example.ageblock.model;

public class Request {

    private String uid;
    private String type;
    private String parentID;
    private User elder;
    private String elderID;
    private String volunteerID;
    private int status;

    private boolean approvedByParent = false;
    private boolean notifiedElder = false;

    private boolean notifiedVolunteer = false;
    private boolean notifiedParent = false;

    public String getType() {
        return type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public User getElder() {
        return elder;
    }

    public void setElder(User elder) {
        this.elder = elder;
    }

    public String getElderID() {
        return elderID;
    }

    public void setElderID(String elderID) {
        this.elderID = elderID;
    }

    public String getVolunteerID() {
        return volunteerID;
    }

    public void setVolunteerID(String volunteerID) {
        this.volunteerID = volunteerID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusText() {
        if (status == 0) return "Waiting for a Volunteer";
        else if (status == 1) return "Pending Parent Approval";
        else if (status == 2) return "In Progress";
        else if (status == 3) return "Finished";
        return  null;
    }

    public String getTextInfo() {
        String result = "Type: " + type;
        result += "\nStatus: " + getStatusText();
        result += "\n\nBy: " + elder.getName();
        result += "\nPhone: " + elder.getPhone();
        result += "\nNational ID: " + elder.getNationalID();
        result += "\nEmail: " + elder.getEmail();
        result += "\n\n\nAre you sure you want to apply?";

        return result;
    }

    public boolean isApprovedByParent() {
        return approvedByParent;
    }

    public void setApprovedByParent(boolean approvedByParent) {
        this.approvedByParent = approvedByParent;
    }

    public boolean isNotifiedElder() {
        return notifiedElder;
    }

    public void setNotifiedElder(boolean notifiedElder) {
        this.notifiedElder = notifiedElder;
    }

    public boolean isNotifiedVolunteer() {
        return notifiedVolunteer;
    }

    public void setNotifiedVolunteer(boolean notifiedVolunteer) {
        this.notifiedVolunteer = notifiedVolunteer;
    }

    public boolean isNotifiedParent() {
        return notifiedParent;
    }

    public void setNotifiedParent(boolean notifiedParent) {
        this.notifiedParent = notifiedParent;
    }
}
