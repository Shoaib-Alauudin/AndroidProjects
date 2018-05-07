package com.example.ghulam.parkingreservationsystem.Admin;

/**
 * Created by Ghulam on 4/28/2018.
 */

public class Mall {
    private String slotno, mallid,  name, image;
    private Boolean booked;

    public Mall() {
    }

    public Mall(String name, String image, String mallid) {
        this.name = name;
        this.image = image;
        this.mallid = mallid;

    }

    public Mall(String slotno, String mallid, Boolean booked) {
        this.slotno = slotno;
        this.mallid = mallid;
        this.booked = booked;
    }



    public void setSlotno(String slotno) {
        this.slotno = slotno;
    }

    public void setMallid(String mallid) {
        this.mallid = mallid;
    }

    public void setBooked(Boolean booked) {
        this.booked = booked;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }



    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }


    public String getSlotno() {
        return slotno;
    }

    public String getMallid() {
        return mallid;
    }

    public Boolean getBooked() {
        return booked;
    }
}
