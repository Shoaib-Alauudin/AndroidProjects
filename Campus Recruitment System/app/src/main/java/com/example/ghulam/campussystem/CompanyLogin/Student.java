package com.example.ghulam.campussystem.CompanyLogin;

/**
 * Created by Ghulam on 3/17/2018.
 */

public class Student {
    private String studentName, userID, studentEmail , studentPassword, category;
    private String education, studentContactNumber, studentImage, studentSkills;


    public Student() {
    }


    public Student(String studentName, String userID, String studentEmail, String category) {
        this.studentName = studentName;
        this.userID = userID;
        this.studentEmail = studentEmail;
        this.category = category;
    }

    public Student(String studentName, String userID, String studentEmail,String studentContactNumber,String studentSkills) {
        this.studentName = studentName;
        this.userID = userID;
        this.studentEmail = studentEmail;
        this.studentContactNumber = studentContactNumber;
        this.studentSkills = studentSkills;
    }
    public Student(String studentName, String userID, String studentEmail, String studentPassword,
                   String category, String education, String studentContactNumber,
                   String studentImage, String studentSkills) {
        this.studentName = studentName;
        this.userID = userID;
        this.studentEmail = studentEmail;
        this.studentPassword = studentPassword;
        this.category = category;
        this.education = education;
        this.studentContactNumber = studentContactNumber;
        this.studentImage = studentImage;
        this.studentSkills = studentSkills;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setStudentContactNumber(String studentContactNumber) {
        this.studentContactNumber = studentContactNumber;
    }

    public void setStudentImage(String studentImage) {
        this.studentImage = studentImage;
    }

    public void setStudentSkills(String studentSkills) {
        this.studentSkills = studentSkills;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getUserID() {
        return userID;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public String getCategory() {
        return category;
    }

    public String getEducation() {
        return education;
    }

    public String getStudentContactNumber() {
        return studentContactNumber;
    }

    public String getStudentImage() {
        return studentImage;
    }

    public String getStudentSkills() {
        return studentSkills;
    }
}
