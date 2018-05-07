package com.example.ghulam.campussystem.AppliedStudentsForJobs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ghulam.campussystem.CompanyLogin.StudentList;
import com.example.ghulam.campussystem.MainActivity;
import com.example.ghulam.campussystem.PostJobs;
import com.example.ghulam.campussystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CompanyFetchJob extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference ref;
    private ArrayList<JobsStructure> allJobsList;
    private FetchJobsAdapter fetchJobsAdapter;

    private Toolbar toolbar;
    private MenuItem itemToHide;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_job);
        setTitle("Offered Jobs");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        allJobsList = new ArrayList<>();



        recyclerView = (RecyclerView)findViewById(R.id.job_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();



        ref= FirebaseDatabase.getInstance().getReference("Campus System").child("Jobs").child(userID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot node : dataSnapshot.getChildren()) {
                    JobsStructure jobsStructure = node.getValue(JobsStructure.class);
                    allJobsList.add(jobsStructure);
                }

                fetchJobsAdapter = new FetchJobsAdapter(CompanyFetchJob.this, allJobsList);
                recyclerView.setAdapter(fetchJobsAdapter);





            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //  Menu activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.company_menu, menu);
        itemToHide = menu.findItem(R.id.postedJobs);
        itemToHide.setVisible(false);

        return true;
    }

    //  logOut button on Menu activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
//                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;


            case R.id.jobPost:
                startActivity(new Intent(getApplicationContext(),PostJobs.class));
                break;


            case R.id.students:
                startActivity(new Intent(getApplicationContext(),StudentList.class));
                break;

        }
        return true;
    }

}
