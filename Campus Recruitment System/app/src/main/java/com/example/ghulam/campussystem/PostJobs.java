package com.example.ghulam.campussystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ghulam.campussystem.CompanyLogin.StudentList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostJobs extends AppCompatActivity {

    EditText title;
    EditText salary;
    EditText description;
    Button submit;
    DatabaseReference ref;
    DatabaseReference ref2;

    int jobNumber=0; //of current user


    FirebaseAuth mAuth;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_jobs);

        title=(EditText) findViewById(R.id.postJobTitle);
        salary=(EditText) findViewById(R.id.postJobSalary);
        description=(EditText) findViewById(R.id.postJobDescription);
        submit=(Button) findViewById(R.id.postJobSubmitBtn);
        mAuth = FirebaseAuth.getInstance();

        userID = mAuth.getCurrentUser().getUid();

        //only to get the job number
        ref= FirebaseDatabase.getInstance().getReference("Campus System").child("Jobs");

        /*ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //Data snapshot will only show job numbers in all iterations
                //the last job number will be saved in our job Number variable and then we will increment it
                //to save new job at new number
                String value = dataSnapshot.getKey().toString();
                jobNumber = Integer.parseInt(value);

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
        });*/



        //adding on click listener to button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                jobNumber++;



                ///to save info
                ref2= FirebaseDatabase.getInstance().getReference("Campus System").child("Jobs")
                        .child(userID).push();

                //checking if all fields are filled
                if(!TextUtils.isEmpty(title.getText())&&
                        !TextUtils.isEmpty(salary.getText())&&
                        !TextUtils.isEmpty(description.getText()))

                {
                    ////Adding post to firebase
                    ref2.child("uid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    ref2.child("title").setValue(title.getText().toString());
                    ref2.child("salary").setValue(salary.getText().toString());
                    ref2.child("description").setValue(description.getText().toString());
                    ref2.child("pushid").setValue(ref2.getKey());


                    //resetting values
                    title.setText(""); salary.setText(""); description.setText("");
                }
                else{

                    Toast.makeText(getApplicationContext(),"Fill all fields",Toast.LENGTH_LONG).show();
                }

                startActivity(new Intent(getApplicationContext(), StudentList.class));

            }
        });

    }
}
