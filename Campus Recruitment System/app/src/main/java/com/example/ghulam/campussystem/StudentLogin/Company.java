package com.example.ghulam.campussystem.StudentLogin;

/**
 * Created by Ghulam on 3/15/2018.
 */

public class Company {
/*Company(displayName,user.getEmail(),user.getUid(),
                        userPassword,category,Website,uriProfileImage.toString(),
                        Contact)*/
    private String companyName, companyEmail, userID, companyPassword, category;
    private String companyWebsite, companyImage, companyContactNumber;

    public Company() {
    }

    public Company(String companyName, String companyEmail,
                   String userID, String companyPassword, String category) {
        this.companyName = companyName;
        this.companyEmail = companyEmail;
        this.userID = userID;
        this.companyPassword = companyPassword;
        this.category = category;
    }

    public Company(String companyName, String companyEmail, String userID, String companyPassword,
                   String category, String companyWebsite,
                   String companyImage, String companyContactNumber) {
        this.companyName = companyName;
        this.companyEmail = companyEmail;
        this.userID = userID;
        this.companyPassword = companyPassword;
        this.category = category;
        this.companyWebsite = companyWebsite;
        this.companyImage = companyImage;
        this.companyContactNumber = companyContactNumber;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setCompanyPassword(String companyPassword) {
        this.companyPassword = companyPassword;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public void setCompanyImage(String companyImage) {
        this.companyImage = companyImage;
    }

    public void setCompanyContactNumber(String companyContactNumber) {
        this.companyContactNumber = companyContactNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyEmail() {
        return companyEmail;
    }

    public String getUserID() {
        return userID;
    }

    public String getCompanyPassword() {
        return companyPassword;
    }

    public String getCategory() {
        return category;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public String getCompanyImage() {
        return companyImage;
    }

    public String getCompanyContactNumber() {
        return companyContactNumber;
    }
}
