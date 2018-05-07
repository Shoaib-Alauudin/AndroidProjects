package com.example.ghulam.campussystem.AppliedStudentsForJobs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.ghulam.campussystem.CompanyLogin.Student;
import com.example.ghulam.campussystem.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PostedJobDetails extends AppCompatActivity {
    private TextView jobTitle,jobDescription;
    private Button deleteBtn;

    private RecyclerView recyclerView;
    private ArrayList<Student> appliedStudents;
    private FetchAppliedStudentDetailAdapter fetchAppliedStudentDetailAdapter;


    private DatabaseReference mDatabase;
    private String pushID, ComUid;

//  show menu option
    private Toolbar toolbar;

//  Menuitem for hide in item list
    private MenuItem itemToHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posted_job_details);
        setTitle("Job Detail");


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        appliedStudents = new ArrayList<>();


        jobTitle = findViewById(R.id.jobtitle_comPost);
        jobDescription = findViewById(R.id.description_comPost);


        pushID = getIntent().getExtras().getString("pushID");
        ComUid = getIntent().getExtras().getString("uid");


        recyclerView = (RecyclerView)findViewById(R.id.applied_student_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Campus System").child("Jobs");
        mDatabase.child(ComUid).child(pushID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String title,des;

                title = dataSnapshot.child("title").getValue().toString();
                des = dataSnapshot.child("description").getValue().toString();

                jobTitle.setText(title);
                jobDescription.setText(des);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child(ComUid).child(pushID).child("jobapplied").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    for (DataSnapshot child:dataSnapshot.getChildren()){

//                      Log.v("AppliedStudentList",child.getKey());
                        Student applied_student_detail = child.getValue(Student.class);
                        appliedStudents.add(applied_student_detail);
                    }
                    fetchAppliedStudentDetailAdapter = new FetchAppliedStudentDetailAdapter(getApplicationContext(), appliedStudents);
                    recyclerView.setAdapter(fetchAppliedStudentDetailAdapter);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
