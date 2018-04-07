package com.example.ghulam.campussystem.AdminPanel.Students;

import android.content.Intent;
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

public class DeleteStudent extends AppCompatActivity {

    private ImageView studentImage;
    private TextView studentName, studentEducation,studentContactNo,studentEmail;
    private Button deleteBtn;
    String userID;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student);

        userID = getIntent().getExtras().getString("key");


        mDatabase = FirebaseDatabase.getInstance().getReference().
                child("Campus System").child("Student");

        studentImage = (ImageView) findViewById(R.id.studentImage);
        studentName = (TextView) findViewById(R.id.studentName);
        studentEducation = (TextView) findViewById(R.id.studentQualification);
        studentContactNo = (TextView) findViewById(R.id.studentContactNumber);
        studentEmail = (TextView) findViewById(R.id.studentEmail);
        deleteBtn = (Button)findViewById(R.id.deleteBtn);



        mDatabase.child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Name, Qualification,Contact, Image,Email;
                Name = (String)dataSnapshot.child("studentName").getValue();
                Qualification = (String)dataSnapshot.child("education").getValue();
                Contact = (String)dataSnapshot.child("studentContactNumber").getValue();
                Email = (String)dataSnapshot.child("studentEmail").getValue();

                Image = (String)dataSnapshot.child("studentImage").getValue();


                studentName.setText(Name);
                studentEducation.setText(Qualification);
                studentContactNo.setText(Contact);
                studentEmail.setText(Email);
                Glide.with(getApplicationContext()).load(Image).into(studentImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child(userID).removeValue();
                startActivity(new Intent(getApplicationContext(), FetchStudentForAdmin.class));



            }
        });


    }
}
