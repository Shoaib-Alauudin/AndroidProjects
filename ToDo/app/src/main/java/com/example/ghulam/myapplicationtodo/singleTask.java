package com.example.ghulam.myapplicationtodo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class singleTask extends AppCompatActivity {

    private String pushKey;
    private TextView singleTime;
    private EditText singleTask;
    private DatabaseReference mDatabase;
    private Button updateButton, deleteButton;
    String updateText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task);
        setTitle(R.string.updateLayout);


        pushKey = getIntent().getExtras().getString("key");


        singleTask = (EditText) findViewById(R.id.singleTask);
        singleTime = (TextView)findViewById(R.id.singleTime);
        updateButton = (Button)findViewById(R.id.updateButton);
        deleteButton = (Button)findViewById(R.id.deleteButton);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Tasks");


        mDatabase.child(pushKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String task_title, task_time;
                task_title = (String) dataSnapshot.child("name").getValue();
                task_time = (String) dataSnapshot.child("time").getValue();
                singleTask.setText(task_title);
                singleTime.setText(task_time);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                mDatabase.child(pushKey).removeValue();
                Intent intent = new Intent(context,MainActivity.class);
                startActivity(intent);
            }


        });



        updateText = singleTask.getText().toString();

        if(singleTask.getText().toString() == updateText ){
            updateButton.setEnabled(false);
        }else
        {
            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    updateText = singleTask.getText().toString();


                    String dateString;

                    long date = System.currentTimeMillis();
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyy h:mm a");
                    dateString = sdf.format(date);

                    HashMap<String, Object> result = new HashMap<>();
                    result.put("name", updateText);
                    result.put("time", dateString);
                    mDatabase.child(pushKey).updateChildren(result);

                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);

                }
            });
        }



    }



}

