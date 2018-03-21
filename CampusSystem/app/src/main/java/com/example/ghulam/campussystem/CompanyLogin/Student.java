package com.example.ghulam.campussystem.CompanyLogin;

/**
 * Created by Ghulam on 3/17/2018.
 */

public class Student {
    private String studentName,studentEmail, userId, userPassword, studentContactNumber, studentImage , StudentEdu;


    public Student(String studentName, String studentEmail, String userId, String userPassword) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.userId = userId;
        this.userPassword = userPassword;
    }

    public Student(String studentName, String studentEmail, String userId, String userPassword, String studentContactNumber, String studentImage, String studentEdu) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.userId = userId;
        this.userPassword = userPassword;
        this.studentContactNumber = studentContactNumber;
        this.studentImage = studentImage;
        StudentEdu = studentEdu;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setStudentContactNumber(String studentContactNumber) {
        this.studentContactNumber = studentContactNumber;
    }

    public void setStudentImage(String studentImage) {
        this.studentImage = studentImage;
    }

    public void setStudentEdu(String studentEdu) {
        StudentEdu = studentEdu;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getStudentContactNumber() {
        return studentContactNumber;
    }

    public String getStudentImage() {
        return studentImage;
    }

    public String getStudentEdu() {
        return StudentEdu;
    }
}
