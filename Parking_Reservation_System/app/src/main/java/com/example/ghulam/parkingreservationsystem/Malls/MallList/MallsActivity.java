package com.example.ghulam.parkingreservationsystem.Malls.MallList;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.ghulam.parkingreservationsystem.Admin.Mall;
import com.example.ghulam.parkingreservationsystem.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MallsActivity extends AppCompatActivity {

    ArrayList<Mall> mallArrayList;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private DatabaseReference mReference;

    private Toolbar toolbar;

    private MenuItem itemHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_malls);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(MallsActivity.this);
        progressDialog.setMessage("Loading please wait");
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerViewMallList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mallArrayList = new ArrayList<Mall>();

        final RecyclerViewMallsAdapter mAdapter = new RecyclerViewMallsAdapter(this, mallArrayList);
        recyclerView.setAdapter(mAdapter);


        // Firebase database reference for parking Areas
        mReference = FirebaseDatabase.getInstance().getReference().
                child("Parking Reservation").child("Parking Areas");


        mReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Mall mall = dataSnapshot.getValue(Mall.class);
                mallArrayList.add(mall);
                mAdapter.notifyDataSetChanged();
                progressDialog.cancel();
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
