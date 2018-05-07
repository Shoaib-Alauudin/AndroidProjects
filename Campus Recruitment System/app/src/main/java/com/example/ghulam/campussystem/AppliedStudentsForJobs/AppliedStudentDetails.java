package com.example.ghulam.campussystem.AppliedStudentsForJobs;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ghulam.campussystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AppliedStudentDetails extends AppCompatActivity {

    private TextView studentName, studentContact, studentEmail, studentSkill;
    private ImageView studentImage;

    private DatabaseReference mDatabase;

    private String studentId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_student_details);

        studentName = findViewById(R.id.studentName);
        studentSkill = findViewById(R.id.studentSkills);
        studentEmail = findViewById(R.id.studentEmail);
        studentContact = findViewById(R.id.studentContactNumber);

        studentImage = findViewById(R.id.studentImage);


        studentId = getIntent().getExtras().getString("uid");
        mDatabase = FirebaseDatabase.getInstance().getReference().
                child("Campus System").child("Student");

        mDatabase.child(studentId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Name, Skills,Contact, Image,Email;
                Name = (String)dataSnapshot.child("studentName").getValue();
                Skills = (String)dataSnapshot.child("studentSkills").getValue();
                Contact = (String)dataSnapshot.child("studentContactNumber").getValue();
                Email = (String)dataSnapshot.child("studentEmail").getValue();

                Image = (String)dataSnapshot.child("studentImage").getValue();


                studentName.setText(Name);
                studentSkill.setText(Skills);
                studentContact.setText(Contact);
                studentEmail.setText(Email);
                Glide.with(getApplicationContext()).load(Image).into(studentImage);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//      Underline textView

        studentEmail.setPaintFlags(studentEmail.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        studentEmail.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        studentEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");

                intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
                intent.putExtra(Intent.EXTRA_TEXT, "mail body");

                // this will make such that when user returns to your app,
                // your app is displayed, instead of the email app.
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, ""));
            }
        });







    }
}
