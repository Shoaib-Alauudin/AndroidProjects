package com.example.ghulam.campussystem.AppliedStudentsForJobs;

/**
 * Created by Ghulam on 3/26/2018.
 */

class JobsStructure {
    String description;
    String title;
    String salary;
    String pushid;
    String uid;

    public JobsStructure() {

    }

    public JobsStructure(String description, String title, String salary, String pushid, String uid) {
        this.description = description;
        this.title = title;
        this.salary = salary;
        this.pushid = pushid;
        this.uid = uid;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }


    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getSalary() {
        return salary;
    }

    public String getPushid() {
        return pushid;
    }

    public String getUid() {
        return uid;
    }

    public void setPushid(String pushid) {
        this.pushid = pushid;
    }
}
