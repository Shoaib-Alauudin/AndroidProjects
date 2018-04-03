package com.example.ghulam.campussystem.StudentLogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ghulam.campussystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CompanyDetailActivity extends AppCompatActivity {
    private ImageView companyLogo;
    private TextView name, website, contact, emailAddress;
    private Button deleteBtn;

    private String userID;

    private DatabaseReference mDatabase,mDatabaseAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);

/*        mDatabaseAdmin = FirebaseDatabase.getInstance().getReference().child("Campus System").child("Admin").child("qloi0ZYWx8UXhdjQROUz6cbh0f23");

        mDatabaseAdmin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    findViewById(R.id.deleteBtn).setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


        userID = getIntent().getExtras().getString("userID");

        mDatabase = FirebaseDatabase.getInstance().getReference().
                child("Campus System").child("Company");

        companyLogo = (ImageView)findViewById(R.id.companyLogo);

        name = (TextView)findViewById(R.id.companyName);
        emailAddress = (TextView)findViewById(R.id.companyEmail);
        website = (TextView)findViewById(R.id.companyWebsite);
        contact = (TextView)findViewById(R.id.companyContact);

        //For Admin delete button
        deleteBtn = (Button)findViewById(R.id.deleteBtn);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.removeValue();
                mDatabase.notify();
            }
        });

        mDatabase.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String Name, Email,Contact, Image, Website;


                Image = (String)dataSnapshot.child("companyImage").getValue();
                Name = (String)dataSnapshot.child("companyName").getValue();
                Email = (String)dataSnapshot.child("companyEmail").getValue();
                Website = (String)dataSnapshot.child("companyWebsite").getValue();
                Contact = (String)dataSnapshot.child("companyContactNumber").getValue();


                name.setText(Name);
                emailAddress.setText(Email);
                website.setText(Website);
                contact.setText(Contact);

                Glide.with(getApplicationContext()).load(Image).into(companyLogo);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
