package com.example.ghulam.campussystem.AdminPanel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ghulam.campussystem.CompanyLogin.StudentList;
import com.example.ghulam.campussystem.FetchJobs.FetchJob;
import com.example.ghulam.campussystem.MainActivity;
import com.example.ghulam.campussystem.R;
import com.example.ghulam.campussystem.StudentLogin.CompaniesList;
import com.google.firebase.auth.FirebaseAuth;



public class AdminActivity extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }
    //  Menu activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.adminmenu, menu);

        return true;
    }


    //  logOut button on Menu activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){
            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent main = new Intent(getApplicationContext(),MainActivity.class);
                main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);
//                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;

            case R.id.adminJobs:
                Intent intent = new Intent(getApplicationContext(), FetchJob.class);
                startActivity(intent);
                break;

            case R.id.adminStudents:
                Intent student = new Intent(getApplicationContext(), StudentList.class);
                startActivity(student);
                break;


            case R.id.adminCompanies:
                Intent company = new Intent(getApplicationContext(), CompaniesList.class);
                startActivity(company);
                break;


        }

        return true;
    }
}
