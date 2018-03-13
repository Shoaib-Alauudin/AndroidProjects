package com.example.ghulam.myapplicationtodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity{


    private RecyclerView mTaskList;
    private DatabaseReference mDatabase;
    TextView bannerDay, bannerDate;
    private ArrayList<Task> allTask;
    private Toolbar toolbar;
    RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        allTask = new ArrayList<Task>();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//      RecyclerView
        mTaskList = (RecyclerView) findViewById(R.id.task_list);
        mTaskList.setHasFixedSize(true);
        mTaskList.setLayoutManager(new LinearLayoutManager(this));

//      Firebase Database Reference
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Tasks");

//      Adding the Date values
        bannerDay = (TextView) findViewById(R.id.bannerDay);
        bannerDate = (TextView) findViewById(R.id.bannerDate);

//      Get the value of Day
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        bannerDay.setText(dayOfTheWeek);

//      Get the value of date
        final long date = System.currentTimeMillis();
        SimpleDateFormat sdff = new SimpleDateFormat("MMM MM dd,yyy h:mm a");
        String dateString = sdff.format(date);
        bannerDate.setText(dateString);




        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Task task = dataSnapshot.getValue(Task.class);
                allTask.add(task);
                adapter = new RecyclerViewAdapter(getApplicationContext(), allTask);
                mTaskList.setAdapter(adapter);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Task task = dataSnapshot.getValue(Task.class);
//                int index = indexOf(task);
                allTask.remove(task);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });


        /*mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Task todoTask = dataSnapshot1.getValue(Task.class);
                    allTask.add(todoTask);

                }
                adapter = new RecyclerViewAdapter(MainActivity.this, allTask);
                mTaskList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//      Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_signout) {
            return true;
        }
//        else if(id == R.id.action_signin){
//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//            startActivity(intent);
//
//        }

        else if(id == R.id.addTask){
            Intent intent = new Intent(MainActivity.this, AddTask.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }


}


