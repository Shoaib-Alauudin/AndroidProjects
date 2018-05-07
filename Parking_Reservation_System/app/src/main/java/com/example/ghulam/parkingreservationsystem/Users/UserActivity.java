package com.example.ghulam.parkingreservationsystem.Users;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.ghulam.parkingreservationsystem.Malls.MallList.MallsActivity;
import com.example.ghulam.parkingreservationsystem.R;
import com.example.ghulam.parkingreservationsystem.SignInActivity;
import com.example.ghulam.parkingreservationsystem.UserRegistration;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    private DatabaseReference mReferenceUser;
    private ArrayList<Booking> bookingArrayList;
    private ListView mListView;
    private BookingAdapter mAdapter;
    private DatabaseReference mReferenceBooking;
    private UserRegistration userRegistration;

    private Toolbar toolbar;

    private MenuItem itemHide;

    private Button btn_BookNow;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setTitle("");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(UserActivity.this);
        progressDialog.setMessage("Loading please wait");
        progressDialog.show();


//        Set current userName as title
        mAuth = FirebaseAuth.getInstance();
        mReferenceUser = FirebaseDatabase.getInstance().getReference().child("Parking Reservation").child("Registered Users");
        mReferenceUser.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    userRegistration = dataSnapshot.getValue(UserRegistration.class);
                    if (userRegistration != null) {
                        setTitle("Welcome " + userRegistration.getName().toUpperCase());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mListView = findViewById(R.id.root_ListView_User);
        bookingArrayList = new ArrayList<>();

        mAdapter = new BookingAdapter(UserActivity.this, bookingArrayList);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(findViewById(R.id.root_ListView_User_EmptyError));



        btn_BookNow = findViewById(R.id.btn_User_BookNow);
        btn_BookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity.this, MallsActivity.class);
                startActivity(intent);
            }
        });
        progressDialog.dismiss();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.users, menu);
        itemHide = menu.findItem(R.id.user_Activity_Menu_Bookings);
        itemHide.setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            case R.id.user_Activity_Menu_FeedBack:
                Intent intent = new Intent(UserActivity.this, UserFeedBackActivity.class);
                intent.putExtra("userName", userRegistration.getName());
                startActivity(intent);
                return true;

            case R.id.user_Activity_Menu_LogOut:
                mAuth.signOut();
                startActivity(new Intent(UserActivity.this, SignInActivity.class));
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
