package com.example.ghulam.myapplicationtodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;

public class AddTask extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference myRef,mDatabase;
    private EditText editTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        setTitle(R.string.todoActivityTitleName);



        editTask = (EditText)findViewById(R.id.edittask);

    }





    public void addButtonClicked(View view){

        String name, dateString;


        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM MM dd, yyy h:mm a");
        dateString = sdf.format(date);

        myRef = FirebaseDatabase.getInstance().getReference().child("Tasks");
        DatabaseReference newtask = myRef.push();
        String key = newtask.getKey();


        name = editTask.getText().toString();

        if(name.isEmpty()){
            Toast.makeText(this,"Please Enter Some text", Toast.LENGTH_LONG).show();
            Toast.makeText(this,"Psuh KEy"+key, Toast.LENGTH_LONG).show();

            return;
        }
        else{


            newtask.setValue(new Task(name,dateString,key));
            editTask.setText("");
        }

    }
   /* private void writeNewTask(String userId, String taskTitle, String date){
        Task task = new Task(taskTitle,date);

        mDatabase.child("Tasks").child(userId).setValue(task);
    }*/
}
