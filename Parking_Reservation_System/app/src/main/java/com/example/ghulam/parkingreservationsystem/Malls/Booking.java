package com.example.ghulam.parkingreservationsystem.Malls;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Ghulam on 4/25/2018.
 */

class Booking implements Parcelable {

    private String slotKey;
    private String slotName;
    private String mallKey;

    private String userName;
    private String userEmail;
    private String userCnicNumber;
    private String userCarName;
    private String userCarLicenseNumber;
    private String userUid;

    private String userPickTime;
    private String userPickDate;

    private String bookingKey;
    private String selectedHours;

    public Booking() {
    }

    public Booking(String slotKey, String slotName, String mallKey, String userName,
                   String userEmail, String userCnicNumber, String userCarName, String userCarLicenseNumber,
                   String userUid, String userPickTime, String userPickDate, String bookingKey, String selectedHours) {
        this.slotKey = slotKey;
        this.slotName = slotName;
        this.mallKey = mallKey;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userCnicNumber = userCnicNumber;
        this.userCarName = userCarName;
        this.userCarLicenseNumber = userCarLicenseNumber;
        this.userUid = userUid;
        this.userPickTime = userPickTime;
        this.userPickDate = userPickDate;
        this.bookingKey = bookingKey;
        this.selectedHours = selectedHours;
    }

    protected Booking(Parcel in) {
        slotKey = in.readString();
        slotName = in.readString();
        mallKey = in.readString();
        userName = in.readString();
        userEmail = in.readString();
        userCnicNumber = in.readString();
        userCarName = in.readString();
        userCarLicenseNumber = in.readString();
        userUid = in.readString();
        userPickTime = in.readString();
        userPickDate = in.readString();
        bookingKey = in.readString();
        selectedHours = in.readString();
    }

    public static final Creator<Booking> CREATOR = new Creator<Booking>() {
        @Override
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }

        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

    public void setSlotKey(String slotKey) {
        this.slotKey = slotKey;
    }

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public void setMallKey(String mallKey) {
        this.mallKey = mallKey;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserCnicNumber(String userCnicNumber) {
        this.userCnicNumber = userCnicNumber;
    }

    public void setUserCarName(String userCarName) {
        this.userCarName = userCarName;
    }

    public void setUserCarLicenseNumber(String userCarLicenseNumber) {
        this.userCarLicenseNumber = userCarLicenseNumber;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public void setUserPickTime(String userPickTime) {
        this.userPickTime = userPickTime;
    }

    public void setUserPickDate(String userPickDate) {
        this.userPickDate = userPickDate;
    }

    public void setBookingKey(String bookingKey) {
        this.bookingKey = bookingKey;
    }

    public void setSelectedHours(String selectedHours) {
        this.selectedHours = selectedHours;
    }

    public String getSlotKey() {
        return slotKey;
    }

    public String getSlotName() {
        return slotName;
    }

    public String getMallKey() {
        return mallKey;
    }


    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserCnicNumber() {
        return userCnicNumber;
    }

    public String getUserCarName() {
        return userCarName;
    }

    public String getUserCarLicenseNumber() {
        return userCarLicenseNumber;
    }

    public String getUserUid() {
        return userUid;
    }

    public String getUserPickTime() {
        return userPickTime;
    }

    public String getUserPickDate() {
        return userPickDate;
    }

    public String getBookingKey() {
        return bookingKey;
    }

    public String getSelectedHours() {
        return selectedHours;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

        parcel.writeString(slotKey);
        parcel.writeString(slotName);
        parcel.writeString(mallKey);
        parcel.writeString(userName);
        parcel.writeString(userEmail);
        parcel.writeString(userCnicNumber);
        parcel.writeString(userCarName);
        parcel.writeString(userCarLicenseNumber);
        parcel.writeString(userUid);
        parcel.writeString(userPickTime);
        parcel.writeString(userPickDate);
        parcel.writeString(bookingKey);
        parcel.writeString(selectedHours);

    }
}
