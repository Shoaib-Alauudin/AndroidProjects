package com.example.ghulam.parkingreservationsystem.Malls.MallList;

/**
 * Created by Ghulam on 4/25/2018.
 */

public class Malls {
    private String mallImage;
    private String mallName;
    private String mallID;


    public Malls() {
    }

    public Malls(String mallImage, String mallName, String mallID) {
        this.mallImage = mallImage;
        this.mallName = mallName;
        this.mallID = mallID;
    }

    public String getMallImage() {
        return mallImage;
    }

    public void setMallImage(String mallImage) {
        this.mallImage = mallImage;
    }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public String getMallID() {
        return mallID;
    }

    public void setMallID(String mallID) {
        this.mallID = mallID;
    }
}
