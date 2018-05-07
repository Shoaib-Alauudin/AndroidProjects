package com.example.ghulam.parkingreservationsystem.Users;

/**
 * Created by Ghulam on 4/26/2018.
 */

public class CheckAvailability {

    private String bookedStartTime;
    private String bookedEndTime;
    private String bookedDate;
    private String bookedKey;
    private String slotKey;

    public CheckAvailability() {
    }

    public CheckAvailability(String bookedStartTime, String bookedEndTime, String bookedDate, String bookedKey, String slotKey) {
        this.bookedStartTime = bookedStartTime;
        this.bookedEndTime = bookedEndTime;
        this.bookedDate = bookedDate;
        this.bookedKey = bookedKey;
        this.slotKey = slotKey;
    }

    public String getBookedStartTime() {
        return bookedStartTime;
    }

    public void setBookedStartTime(String bookedStartTime) {
        this.bookedStartTime = bookedStartTime;
    }

    public String getBookedEndTime() {
        return bookedEndTime;
    }

    public void setBookedEndTime(String bookedDndTime) {
        this.bookedEndTime = bookedDndTime;
    }

    public String getBookedDate() {
        return bookedDate;
    }

    public void setBookedDate(String bookedDate) {
        this.bookedDate = bookedDate;
    }

    public String getBookedKey() {
        return bookedKey;
    }

    public void setBookedKey(String bookedKey) {
        this.bookedKey = bookedKey;
    }

    public String getSlotKey() {
        return slotKey;
    }

    public void setSlotKey(String slotKey) {
        this.slotKey = slotKey;
    }

}
