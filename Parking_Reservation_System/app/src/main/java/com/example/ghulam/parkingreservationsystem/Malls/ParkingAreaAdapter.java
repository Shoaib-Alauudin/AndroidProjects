package com.example.ghulam.parkingreservationsystem.Malls;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ghulam.parkingreservationsystem.Users.CheckAvailability;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ghulam on 5/5/2018.
 */

class ParkingAreaAdapter extends RecyclerView.Adapter<ParkingAreaAdapter.ViewHolder> {

    private static final String TAG = "TAG";
    private ArrayList<Slot> slotArrayList;
    private Context context;
    customButtonListener listener;

    private ArrayList<CheckAvailability> checkAvailabilityArrayList;
    private String userSelectTime;
    private String userSelectDate;
    private String endTime;

    public interface customButtonListener {
        void onButtonClickListener(int position);
    }


    public ParkingAreaAdapter(Context context, ArrayList<Slot> slotArrayList,
                              ArrayList<CheckAvailability> checkAvailabilityArrayList, String userSelectTime,
                              String userSelectDate, String endTime, customButtonListener listener) {
        this.context = context;
        this.slotArrayList = slotArrayList;
        this.checkAvailabilityArrayList = checkAvailabilityArrayList;
        this.userSelectTime = userSelectTime;
        this.userSelectDate = userSelectDate;
        this.endTime = endTime;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.parking_area_booking_button_item,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Slot slot = slotArrayList.get(position);
        holder.slotbtn.setText(slot.getSlotName());

        int colorGreen = Color.parseColor("#ccfcj9");
        int colorRed = Color.parseColor("ffs7f8");

        if (checkAvailabilityArrayList.size() != 0){
            for (int i = 0; i<checkAvailabilityArrayList.size(); i++){

                if (slot.getId().equals(checkAvailabilityArrayList.get(i).getSlotKey())){

                    if (CheckDateAndTime(userSelectDate, userSelectTime, endTime, checkAvailabilityArrayList.get(i).getBookedDate(),checkAvailabilityArrayList.get(i).getBookedStartTime(), checkAvailabilityArrayList.get(i).getBookedEndTime())){
                        holder.slotbtn.setBackgroundColor(colorGreen);
                        holder.slotbtn.setEnabled(true);
                    }
                    else {
                        holder.slotbtn.setBackgroundColor(colorRed);
                        holder.slotbtn.setEnabled(false);
                        holder.slotbtn.setClickable(false);
                        break;
                    }
                }
                else {
                    holder.slotbtn.setBackgroundColor(colorGreen);
                    holder.slotbtn.setEnabled(true);
                }
            }
        }
    }

    private boolean CheckDateAndTime(String userSelectedDate, String userSelectedTime,
                                     String userSelectedEndTime, String bookedDate,
                                     String bookedStartTime,
                                     String bookedEndTime) {
        boolean Flag = false;
        String slectedStartTimeAndDate = userSelectedDate+" "+userSelectedTime;
        String selectedEndTimeAndDate = userSelectedDate+" "+userSelectedEndTime;

        String bookedStartTimeAndDate = bookedDate+" "+bookedStartTime;
        String bookedEndTimeAndDate = bookedDate+" "+bookedEndTime;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm aaa");

        try{
            // Convert current user slected Time and Date String into SimpleDateFormat
            Date currentUserStartTimeAndDate = sdf.parse(slectedStartTimeAndDate);
            Date currentUserEndTimeAndDate = sdf.parse(selectedEndTimeAndDate);

            // Convert Booked Time and Date String into SimpleDateFormat
            Date bookStartTimeAndDate = sdf.parse(bookedStartTimeAndDate);
            Date bookEndTimeAndDate = sdf.parse(bookedEndTimeAndDate);


            // Convert current start time and end time into long
            long currentUserStartTimeStamp = currentUserStartTimeAndDate.getTime();
            long currentUserEndTimeStamp = currentUserEndTimeAndDate.getTime();

            // Convert toCheck Start TIme and End TIme into Long
            long bookingStartTimeStamp = bookStartTimeAndDate.getTime();
            long bookingEndTimeStamp = bookEndTimeAndDate.getTime();

            if (!(currentUserStartTimeStamp < bookingStartTimeStamp && currentUserEndTimeStamp > bookingStartTimeStamp || currentUserStartTimeStamp < bookingEndTimeStamp && currentUserEndTimeStamp > bookingEndTimeStamp)) {
                Log.e("TAG", "checkDateAndTime: True OMG ");
                Flag = true;
            } else {
                Log.e("TAG", "checkDateAndTime: False DAMN ");
                Flag = false;
            }

        }catch (ParseException e){
            Log.e("TAG", "CheckDateAndTime: Exception"+e.getMessage());
        }

        return Flag;
    }

    @Override
    public int getItemCount() {
        return slotArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button slotbtn;

        public ViewHolder(View itemView) {
            super(itemView);
            slotbtn = itemView.findViewById(R.id.parking_area_slot_button);
        }
    }
}
