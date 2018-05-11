package com.example.ghulam.parkingreservationsystem.Malls;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.ghulam.parkingreservationsystem.R;
import com.example.ghulam.parkingreservationsystem.Users.CheckAvailability;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MallBookingActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    private Toolbar toolbar;
    private static Spinner spinnerDuration;
    private Button showSlots;
    private ImageButton datePicker,timePicker;
    private static TextView showDate, showTime;
    private ParkingAreaAdapter parkingAreaAdapter;

    private static int endTimeInt;
    private ArrayList<CheckAvailability> checkAvailabilityArrayList;
    static long currentUserPickTimeCheck;

    private ArrayList<Slot> slotArrayList;
    private static String endTime;

    private RecyclerView recyclerView;

    private DatabaseReference mRef;

    private String mallKey;
    private DatabaseReference mReferenceSlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_booking);

        mallKey = getIntent().getExtras().getString("mallID");

        toolbar = findViewById(R.id.toolbar);
        datePicker = findViewById(R.id.btn_Pick_Date);
        timePicker = findViewById(R.id.btn_Pick_Time);
        showDate = findViewById(R.id.txtView_showDate);
        showTime = findViewById(R.id.txtView_showTime);
        showSlots = findViewById(R.id.btn_Show_Slots);


        mReferenceSlot = FirebaseDatabase.getInstance().getReference().child("Parking Reservation").child("Parking Areas").child(mallKey).child("slots");
        recyclerView = findViewById(R.id.recycler_parking_slots);
        slotArrayList = new ArrayList<>();
        checkAvailabilityArrayList = new ArrayList<>();


        DatabaseReference checkSlotReference = FirebaseDatabase.getInstance()
                .getReference().child("Parking Reservation")
                .child("Parking Areas").child(mallKey).child("slots");

        checkSlotReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Log.d("dataSnapShot",dataSnapshot1.getKey());
                    if (dataSnapshot1.child("bookings").exists()){
                        for (DataSnapshot data:dataSnapshot1.getChildren()){
                            Log.d("dataSnapShot",data.getKey());
                            for (DataSnapshot bookings:data.getChildren()){

                                Log.d("dataSnapShot",bookings.getValue().toString());
                                CheckAvailability checkAvailabilityObj = bookings.getValue(CheckAvailability.class);
                                checkAvailabilityArrayList.add(checkAvailabilityObj);

                            }

                        }
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        timePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!showDate.getText().toString().isEmpty()) {
                    showDate.setError(null);
                    DialogFragment newFragment = new TimePickerFragment();
                    newFragment.show(getSupportFragmentManager(), "timePicker");
                } else {
                    showDate.requestFocus();
                    showDate.setText(null);
                    showDate.setError("Please...! Select Date First");
                }
            }
        });

        spinnerDuration = findViewById(R.id.spinner_Duration_Time);
        Integer[] durations = new Integer[]{1,2,3,4,5,6};
        ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item, durations);
        spinnerDuration.setAdapter(spinnerAdapter);



        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDate.setError(null);
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        showSlots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String userSelectedTime = showTime.getText().toString(); // booking time
                final String userSelectedDate = showDate.getText().toString(); // booking date
                slotArrayList.clear(); // model class arraylist

                // If the booking time and date are not empty
                if (!userSelectedDate.isEmpty()){
                    if (!userSelectedTime.isEmpty()){
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, 23);
                        calendar.set(Calendar.MINUTE, 59);
                        calendar.set(Calendar.SECOND, 59);

                        long todayInMillis = calendar.getTimeInMillis();
                        if (currentUserPickTimeCheck < todayInMillis){

                            showDate.setError(null);
                            parkingAreaAdapter = new ParkingAreaAdapter(getApplicationContext(), slotArrayList, checkAvailabilityArrayList, userSelectedTime, userSelectedDate, endTime, new ParkingAreaAdapter.customButtonListener() {
                                @Override
                                public void onButtonClickListener(int position) {
                                    Intent intent = new Intent(getApplicationContext(),BookingActivity.class);
                                    Slot slotPosition = slotArrayList.get(position);
                                    intent.putExtra("obj", slotPosition);
                                    intent.putExtra("userPickTime", userSelectedTime);
                                    intent.putExtra("userPickDate", userSelectedDate);
                                    intent.putExtra("userPickedHours", spinnerDuration.getSelectedItem().toString());
                                    intent.putExtra("endDuration", endTime);
                                    startActivity(intent);
                                }
                            });

                            RecyclerView.LayoutManager recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
                            recyclerView.setLayoutManager(recyclerViewLayoutManager);
                            recyclerView.setAdapter(parkingAreaAdapter);

                            mReferenceSlot.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                                    Slot slot = dataSnapshot.getValue(Slot.class);
                                    slotArrayList.add(slot);
                                    parkingAreaAdapter.notifyDataSetChanged();


                                }

                                @Override
                                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onChildRemoved(DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });


                        }
                    }
                }
            }
        });



    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this,hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            Calendar getUserTime = Calendar.getInstance();
            getUserTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            getUserTime.set(Calendar.MINUTE, minute);
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm aaa");

            String userCurrentPickDate = showDate.getText().toString();
            String userCurrentPickTime = timeFormat.format(getUserTime.getTime());
            String userPickTimeAndDate = userCurrentPickDate + " " + userCurrentPickTime;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm aaa");

            try {
                Date mDateTimeAndDateCurrent = sdf.parse(userPickTimeAndDate);
                long userCurrentTimeAndDate = mDateTimeAndDateCurrent.getTime();

                if (userCurrentTimeAndDate > Calendar.getInstance().getTimeInMillis()) {
                    endTime(hourOfDay, minute);
                    showTime.setText(timeFormat.format(getUserTime.getTime()));
                } else {
                    showDate.requestFocus();
                    showDate.setError("Please select future date for the giving time");
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        private void endTime(final int hourOfDay, final int minute) {

            spinnerDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    showDate.setError(null);
                    endTimeInt = Integer.parseInt(spinnerDuration.getSelectedItem().toString());
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm a");
                    Calendar getEndTime = Calendar.getInstance();
                    getEndTime.set(Calendar.HOUR_OF_DAY, hourOfDay + endTimeInt);
                    getEndTime.set(Calendar.MINUTE, minute);

                    currentUserPickTimeCheck = getEndTime.getTimeInMillis();
                    endTime = timeFormat.format(getEndTime.getTime());

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm a");
            Calendar getEndTime = Calendar.getInstance();
            getEndTime.set(Calendar.HOUR_OF_DAY, hourOfDay + endTimeInt);
            getEndTime.set(Calendar.MINUTE, minute);

            currentUserPickTimeCheck = getEndTime.getTimeInMillis();
            endTime = timeFormat.format(getEndTime.getTime());

        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
            //pickerDialog.getDatePicker().setMaxDate((System.currentTimeMillis() - 1000)+(1000*60*60*24*2));
            pickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            return pickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            String dateYouChoose = day + "-" + (month + 1) + "-" + year;
            showDate.setText(dateYouChoose);

        }
    }

}
