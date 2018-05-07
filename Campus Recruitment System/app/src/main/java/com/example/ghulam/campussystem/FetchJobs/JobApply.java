package com.example.ghulam.campussystem.FetchJobs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ghulam.campussystem.CompanyLogin.Student;
import com.example.ghulam.campussystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JobApply extends AppCompatActivity {

    private TextView jobTitle, companyName, jobDes, jobSalary;
    private Button applyButton;

    private DatabaseReference mDatabase, comRef, studRef;

    private String pushID, ComUid;

//  Student Class object to get applied student details
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_apply);

        jobTitle = (TextView)findViewById(R.id.jobtitle_post);
        companyName = (TextView)findViewById(R.id.compname_post);
        jobDes = (TextView)findViewById(R.id.description_post);
        jobSalary = (TextView)findViewById(R.id.salary_post);
        applyButton = (Button)findViewById(R.id.apply_button);


        pushID = getIntent().getExtras().getString("pushID");
        ComUid = getIntent().getExtras().getString("uid");


        comRef = FirebaseDatabase.getInstance().getReference().child("Campus System").child("Company");
        comRef.child(ComUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String comName;
                comName = dataSnapshot.child("companyName").getValue().toString();
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

                title = dataSnapshot.child("title").getValue().toString();
                des = dataSnapshot.child("description").getValue().toString();
                salary = dataSnapshot.child("salary").getValue().toString();

                jobTitle.setText(title);
                jobDes.setText(des);
                jobSalary.setText(salary);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//      Get Current Student id
        final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        studRef = FirebaseDatabase.getInstance().getReference().child("Campus System").child("Student");
        studRef.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                student = dataSnapshot.getValue(Student.class);
//                Log.v("stu",""+student.getStudentName());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase.child(ComUid).child(pushID).child("jobapplied").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (snapshot.child(userId).exists()) {
                            // run some code
                            Toast.makeText(JobApply.this,"You Have Already Applied for this Job", Toast.LENGTH_SHORT).show();

                        }else{

                            mDatabase.child(ComUid).child(pushID).child("jobapplied").child(userId)
                                    .setValue(new Student(student.getStudentName(),
                                    student.getUserID(),student.getStudentEmail(),
                                            student.getStudentContactNumber(),student.getStudentSkills()));
                            Toast.makeText(JobApply.this, "Job Apply Successful",Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }
        });


    }
}
