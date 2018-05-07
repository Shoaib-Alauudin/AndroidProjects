package com.example.ghulam.campussystem.AdminPanel.Jobs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ghulam.campussystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteCompPost extends AppCompatActivity {

    private TextView jobTitle, companyName, jobDes, jobSalary;
    private Button deleteButton;

    private DatabaseReference mDatabase, comRef, studRef;

    private String pushID, ComUid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_comp_post);

        jobTitle = (TextView)findViewById(R.id.jobtitle_post);
        companyName = (TextView)findViewById(R.id.compname_post);
        jobDes = (TextView)findViewById(R.id.description_post);
        jobSalary = (TextView)findViewById(R.id.salary_post);
        deleteButton = (Button)findViewById(R.id.delete_button);


        pushID = getIntent().getExtras().getString("pushID");
        ComUid = getIntent().getExtras().getString("uid");


//      For Testing
        Log.v("DeleteComPost",pushID+"    "+ComUid);


        comRef = FirebaseDatabase.getInstance().getReference().child("Campus System").child("Company");
        comRef.child(ComUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String comName;
                comName = (String) dataSnapshot.child("companyName").getValue();
                companyName.setText(comName);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Campus System").child("Jobs");
        mDatabase.child(ComUid).child(pushID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String title,des,salary;

                title = (String) dataSnapshot.child("title").getValue();
                des = (String) dataSnapshot.child("description").getValue();
                salary = (String) dataSnapshot.child("salary").getValue();

                jobTitle.setText(title);
                jobDes.setText(des);
                jobSalary.setText(salary);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.child(ComUid).child(pushID).removeValue();
                startActivity(new Intent(getApplicationContext(), FetchJobsForAdmin.class));
            }
        });


    }
}
