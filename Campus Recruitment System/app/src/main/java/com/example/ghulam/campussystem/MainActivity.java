package com.example.ghulam.campussystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ghulam.campussystem.AdminPanel.Jobs.FetchJobsForAdmin;
import com.example.ghulam.campussystem.CompanyLogin.StudentList;
import com.example.ghulam.campussystem.StudentLogin.CompaniesList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private EditText editTextEmail, editTextPassword;
    private ProgressBar progressBar;
    private DatabaseReference myStudentRef,myCompanyRef;
    private ProgressDialog progressDialog;
    int forAccountCheck=0;

    private FirebaseUser user;
    private String userID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog= new ProgressDialog(this);


        mAuth = FirebaseAuth.getInstance();
        myStudentRef = FirebaseDatabase.getInstance().getReference().child("Campus System").child("Student");
        myCompanyRef = FirebaseDatabase.getInstance().getReference().child("Campus System").child("Company");
//        myRef = FirebaseDatabase.getInstance().getReference().child("Campus System");



        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);

        findViewById(R.id.textViewSignup).setOnClickListener(this);
        findViewById(R.id.buttonLogin).setOnClickListener(this);

    }

    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        if (email.isEmpty()){
            editTextEmail.setError("Email Address is Required");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            editTextPassword.setError("Password is Required");
            editTextPassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        /*if (password.length()<6){
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }*/

//        progressBar.setVisibility(View.VISIBLE);
        progressDialog.setMessage("Gathering information please wait!");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                user = mAuth.getCurrentUser();

                if (task.isSuccessful()){
                    editTextEmail.setText("");
                    editTextPassword.setText("");

                    Toast.makeText(MainActivity.this,R.string.loginSuccessful,Toast.LENGTH_SHORT).show();


                    logginChecker(user.getUid());

                }
                else {
                    progressBar.setVisibility(View.INVISIBLE);
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),
                            task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            finish();


        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewSignup:
                finish();
                startActivity(new Intent(this, SignUpActivity.class));
                break;

            case R.id.buttonLogin:
                userLogin();
                break;
        }
    }

    public void logginChecker(String userID){


        DatabaseReference ref1=FirebaseDatabase.getInstance().getReference().child("Campus System").child("Admin").child(userID);

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    System.out.println("hello");
                    progressDialog.dismiss();

                    //removing this activity from back stack
                    finish();

                    //opening Admin
                    startActivity(new Intent(MainActivity.this,FetchJobsForAdmin.class));
                } else{
                    forAccountCheck++;
                    //if for account checck here has value >=3 it means that id is not present in either of tables
                    if(forAccountCheck>=3){

                        progressDialog.dismiss();
//                        Toast.makeText(MainActivity.this,"No user is logged in ",Toast.LENGTH_LONG).show();
                        finish();


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference ref2=FirebaseDatabase.getInstance().getReference("Campus System").child("Company").child(userID);
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    progressDialog.dismiss();
                    //removing this activity from back stack
                    finish();

                    //opening company feed
                    startActivity(new Intent(MainActivity.this,StudentList.class));
                } else{

                    forAccountCheck++;
                    //if for account check here has value >=3 it means that id is not present in either of tables
                    if(forAccountCheck>=3){
                        progressDialog.dismiss();
                        finish();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference ref3=FirebaseDatabase.getInstance().getReference("Campus System").child("Student").child(userID);
        ref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    progressDialog.dismiss();
                    //removing this activity from back stack
                    finish();

                    //opening company feed
                    startActivity(new Intent(MainActivity.this,CompaniesList.class));
                } else{

                    forAccountCheck++;
                    //if for account check here has value >=3 it means that id is not present in either of tables
                    if(forAccountCheck>=3){
                        progressDialog.dismiss();
                        finish();


                        mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "User deleted by Admin", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        progressDialog.dismiss();

    }
}
