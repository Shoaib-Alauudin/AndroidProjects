package com.example.ghulam.parkingreservationsystem.Malls;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ghulam.parkingreservationsystem.Users.CheckAvailability;

/**
 * Created by Ghulam on 5/3/2018.
 */

public class Slot implements Parcelable{
    private String slotName;
    private String id;
    private String mallKey;
    private CheckAvailability checkAvailability;

    public Slot() {
    }

    public Slot(String slotName, String id, String mallKey) {
        this.slotName = slotName;
        this.id = id;
        this.mallKey = mallKey;
    }

    protected Slot(Parcel in) {
        slotName = in.readString();
        id = in.readString();
        mallKey = in.readString();
    }

    public static final Creator<Slot> CREATOR = new Creator<Slot>() {
        @Override
        public Slot createFromParcel(Parcel in) {
            return new Slot(in);
        }

        @Override
        public Slot[] newArray(int size) {
            return new Slot[size];
        }
    };

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMallKey(String mallKey) {
        this.mallKey = mallKey;
    }


    public void setCheckAvailability(CheckAvailability checkAvailability) {
        this.checkAvailability = checkAvailability;
    }

    public String getSlotName() {
        return slotName;
    }

    public String getId() {
        return id;
    }

    public String getMallKey() {
        return mallKey;
    }


    public CheckAvailability getCheckAvailability() {
        return checkAvailability;
    }

    public static Creator<Slot> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(slotName);
        dest.writeString(id);
        dest.writeString(mallKey);
    }
}
