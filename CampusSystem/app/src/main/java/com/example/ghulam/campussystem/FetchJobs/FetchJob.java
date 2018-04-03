package com.example.ghulam.campussystem.FetchJobs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ghulam.campussystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FetchJob extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference ref;
    private ArrayList<JobsStructure> allJobsList;
    private FetchJobsAdapter fetchJobsAdapter;
    private int jobNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_job);

        allJobsList = new ArrayList<>();



        recyclerView = (RecyclerView)findViewById(R.id.job_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();



        ref= FirebaseDatabase.getInstance().getReference("Campus System").child("Jobs");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {



                for(DataSnapshot node : dataSnapshot.getChildren()) {

                    for (DataSnapshot nodeChild:node.getChildren()){
//                        Log.v("key",nodeChild.getKey());
                        JobsStructure jobsStructure = nodeChild.getValue(JobsStructure.class);
                        allJobsList.add(jobsStructure);
                    }
//                    Log.v("key",node.getKey());
/*
                    // you will get all userID
                    String userID = node.getKey();
                    Log.v("userID",userID);

*/
                }
                fetchJobsAdapter = new FetchJobsAdapter(FetchJob.this, allJobsList);
                recyclerView.setAdapter(fetchJobsAdapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
