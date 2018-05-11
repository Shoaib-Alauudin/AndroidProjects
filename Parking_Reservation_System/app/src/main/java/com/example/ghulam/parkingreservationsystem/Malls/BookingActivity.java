package com.example.ghulam.parkingreservationsystem.Malls;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ghulam.parkingreservationsystem.R;
import com.example.ghulam.parkingreservationsystem.UserRegistration;
import com.example.ghulam.parkingreservationsystem.Users.CheckAvailability;
import com.example.ghulam.parkingreservationsystem.Users.UserActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookingActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private FirebaseAuth mAuth;
    private DatabaseReference mReference;
    private ProgressDialog progressDialog;
    EditText parkingBookingUserName;
    EditText parkingBookingUserEmail;
    EditText parkingBookingUserCnicNumber;
    EditText parkingBookingUserCarName;
    EditText parkingBookingUserCarLicenseNumber;
    UserRegistration userObj;
    Button btnBookParking;
    TextView showTime;
    TextView showDate;
    TextView showDuration;

    String userPickTime;
    String userPickDate;
    String userPickedHours;
    String endDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        progressDialog = new ProgressDialog(BookingActivity.this);
        progressDialog.setMessage("Logging please wait");
        progressDialog.show();

        parkingBookingUserName = findViewById(R.id.txt_Booking_UserName);
        parkingBookingUserEmail = findViewById(R.id.txt_Booking_Email);
        parkingBookingUserCnicNumber = findViewById(R.id.txt_Booking_CnicNumber);
        parkingBookingUserCarName = findViewById(R.id.txt_Booking_CarName);
        parkingBookingUserCarLicenseNumber = findViewById(R.id.txt_Booking_CarLicenseNumber);
        showTime = findViewById(R.id.txt_Booking_showTime);
        showDate = findViewById(R.id.txt_Booking_showDate);
        showDuration = findViewById(R.id.txt_Booking_Duration);

        Intent intent = getIntent();
        userPickTime = intent.getStringExtra("userPickTime");
        userPickDate = intent.getStringExtra("userPickDate");
        userPickedHours = intent.getStringExtra("userPickedHours");
        endDuration = intent.getStringExtra("endDuration");

        showTime.setText(userPickTime);
        showDate.setText(userPickDate);
        showDuration.setText(userPickedHours);

        mAuth = FirebaseAuth.getInstance();
        mReference = FirebaseDatabase.getInstance().getReference().child("Parking Reservation").child("Registered Users");

        mReference.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    userObj = dataSnapshot.getValue(UserRegistration.class);
                    parkingBookingUserName.setText(userObj.getName());
                    parkingBookingUserEmail.setText(userObj.getEmail());
                    parkingBookingUserCnicNumber.setText(userObj.getCnic());
                    parkingBookingUserCarName.setText(userObj.getCarname());
                    parkingBookingUserCarLicenseNumber.setText(userObj.getPlateno());
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        btnBookParking = findViewById(R.id.btn_Book_Parking);
        btnBookParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookParkingNow();
            }
        });
    }

    private void bookParkingNow() {
        Intent intent = getIntent();
        Slot slotObj = intent.getParcelableExtra("obj");
        if (slotObj != null && userObj != null) {

            String slotKey = slotObj.getId();
            String slotName = slotObj.getSlotName();
            String mallKey = slotObj.getMallKey();

            String userName = userObj.getName();
            String userEmail = userObj.getEmail();
            String userCnicNumber = userObj.getCnic();
            String userCarName = userObj.getCarname();
            String userCarLicenseNumber = userObj.getPlateno();
            String userUid = userObj.getUserid();

            String userPickTime = showTime.getText().toString();
            String userPickDate = showDate.getText().toString();

            if (!userPickTime.isEmpty()) {
                if (!userPickDate.isEmpty()) {

                    progressDialog.show();

                    DatabaseReference mReferenceBooking = FirebaseDatabase.getInstance().getReference().child("Parking Reservation").child("Parking Areas").child("Booking History");
                    String bookingKey = mReferenceBooking.push().getKey();
                    Booking booking = new Booking(slotKey, slotName, mallKey, userName, userEmail, userCnicNumber, userCarName, userCarLicenseNumber, userUid, userPickTime, userPickDate, bookingKey, userPickedHours);
                    mReferenceBooking.child(bookingKey).setValue(booking);

                    DatabaseReference mReferenceSlot = FirebaseDatabase.getInstance().getReference().child("Parking Reservation").child("Parking Areas").child(mallKey).child("slots").child(slotKey).child("bookings");
                    String bookingPushKey = mReferenceBooking.push().getKey();
                    CheckAvailability checkAvailabilityObj = new CheckAvailability(userPickTime,endDuration,userPickDate,bookingPushKey,slotKey);
                    mReferenceSlot.child(bookingKey).setValue(checkAvailabilityObj);


                    /*DatabaseReference mReferenceBooking = FirebaseDatabase.getInstance().getReference().child("BookingSystem").child("Malls").child("Booking");
                    String bookingKey = mReferenceBooking.push().getKey();
                    Booking booking = new Booking(slotKey, slotName, mallKey, parkingAreaKey, userName, userEmail, userCnicNumber, userCarName, userCarLicenseNumber, userUid, userPickTime, userPickDate, bookingKey, userPickedHours);
                    mReferenceBooking.child(bookingKey).setValue(booking);

                    DatabaseReference mReferenceSlot = FirebaseDatabase.getInstance().getReference().child("BookingSystem").child("Malls").child("MallsParking").child(mallKey).child(parkingAreaKey).child("CheckSlot");
                    String checkAvailabilityKey = mReferenceBooking.push().getKey();
                    CheckAvailability checkAvailabilityObj = new CheckAvailability(userPickTime,endDuration,userPickDate,checkAvailabilityKey,slotKey);
                    mReferenceSlot.child(checkAvailabilityKey).setValue(checkAvailabilityObj);

                   */

                    btnBookParking.setEnabled(false);
                    Toast.makeText(this, "You have Booked " + slotName + " Successfully", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(BookingActivity.this, UserActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    progressDialog.dismiss();

                } else {
                    showDate.setTextColor(Color.RED);
                    showDate.setText("Please select Date");

                }
            } else {
                showTime.setTextColor(Color.RED);
                showTime.setText("Please select Time");

            }
        }
    }
}
