package com.example.ghulam.parkingreservationsystem;

/**
 * Created by Ghulam on 4/24/2018.
 */

public class UserRegistration {
    private String name, email, cnic, carname, plateno, userid;

    public UserRegistration() {
    }

    public UserRegistration(String name, String email, String cnic, String carname, String plateno, String userid) {
        this.name = name;
        this.email = email;
        this.cnic = cnic;
        this.carname = carname;
        this.plateno = plateno;
        this.userid = userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setCnic(String cnic) {
        this.cnic = cnic;
    }




    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }


    public String getCnic() {
        return cnic;
    }


    public void setCarname(String carname) {
        this.carname = carname;
    }


    public String getCarname() {
        return carname;
    }

    public void setPlateno(String plateno) {
        this.plateno = plateno;
    }

    public String getPlateno() {
        return plateno;
    }
}
