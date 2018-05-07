package com.example.ghulam.campussystem.StudentLogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ghulam.campussystem.FetchJobs.FetchJob;
import com.example.ghulam.campussystem.MainActivity;
import com.example.ghulam.campussystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CompaniesList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private ArrayList<Company> allCompanyList;
    private Toolbar toolbar;
    private RecyclerViewAdapterCompanies companyAdapter;

    private MenuItem itemToHide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companies_list);

        setTitle("Available Companies");

        allCompanyList = new ArrayList();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.companies_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



//      Firebase Database Reference
        mDatabase = FirebaseDatabase.getInstance().getReference()
                .child("Campus System").child("Company");


        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Company company = dataSnapshot.getValue(Company.class);
                allCompanyList.add(company);
                companyAdapter = new RecyclerViewAdapterCompanies(CompaniesList.this,
                        allCompanyList);
                recyclerView.setAdapter(companyAdapter);
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
        inflater.inflate(R.menu.menu, menu);
        itemToHide = menu.findItem(R.id.companies);
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


            case R.id.Jobs:
                startActivity(new Intent(getApplicationContext(),FetchJob.class));
                break;


        }
        return true;
    }

}
