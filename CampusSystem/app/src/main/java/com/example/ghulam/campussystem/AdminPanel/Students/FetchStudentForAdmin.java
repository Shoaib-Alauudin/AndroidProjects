package com.example.ghulam.campussystem.AdminPanel.Students;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ghulam.campussystem.AdminPanel.Jobs.FetchJobsForAdmin;
import com.example.ghulam.campussystem.CompanyLogin.RecyclerViewAdapterStudents;
import com.example.ghulam.campussystem.CompanyLogin.Student;
import com.example.ghulam.campussystem.MainActivity;
import com.example.ghulam.campussystem.R;
import com.example.ghulam.campussystem.StudentLogin.CompaniesList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Ghulam on 4/7/2018.
 */

public class FetchStudentForAdmin extends AppCompatActivity{
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private ArrayList<Student> allStudentList;
    private Toolbar toolbar;
    private RecyclerViewAdapterStudents studentAdapter;

    private MenuItem itemToHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        setTitle("Available Students");

//      allCompanyList = new ArrayList<Company>();
        allStudentList = new ArrayList<Student>();


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.companies_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


//      Firebase Database Reference
        mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("Campus System").child("Student");

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Student student = dataSnapshot.getValue(Student.class);
                allStudentList.add(student);
                studentAdapter = new RecyclerViewAdapterStudents(FetchStudentForAdmin.this,
                        allStudentList);
                recyclerView.setAdapter(studentAdapter);

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

    //  Menu activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.adminmenu, menu);
        itemToHide = menu.findItem(R.id.adminStudents);
        itemToHide.setVisible(false);
        return true;
    }

    //  logOut button on Menu activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
//                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;


            case R.id.adminJobs:
                startActivity(new Intent(getApplicationContext(),FetchJobsForAdmin.class));
                break;


            case R.id.adminCompanies:
                startActivity(new Intent(getApplicationContext(),CompaniesList.class));
                break;


        }
        return true;
    }
}
