package com.example.ghulam.campussystem.StudentLogin;

/**
 * Created by Ghulam on 3/15/2018.
 */

public class Company {

    private String companyName, companyOfferedJob, companyDes, userID,companyEmail,companyPassword;
    private Boolean applied;
    private int companyLogo;


    public Company(String companyName, String userID, String companyEmail, String companyPassword) {
        this.companyName = companyName;
        this.userID = userID;
        this.companyEmail = companyEmail;
        this.companyPassword = companyPassword;
    }


    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public void setCompanyPassword(String companyPassword) {
        this.companyPassword = companyPassword;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public String getCompanyPassword() {
        return companyPassword;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyOfferedJob(String companyOfferedJob) {
        this.companyOfferedJob = companyOfferedJob;
    }

/*    public void setCompanyLogo(int companyLogo) {
        this.companyLogo = companyLogo;
    }*/

    public void setCompanyDes(String companyDes) {
        this.companyDes = companyDes;
    }

    public void setApplied(Boolean applied) {
        this.applied = applied;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyOfferedJob() {
        return companyOfferedJob;
    }

    public int getCompanyLogo() {
        return companyLogo;
    }

    public String getCompanyDes() {
        return companyDes;
    }

    public Boolean getApplied() {
        return applied;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }
}
