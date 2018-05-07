package com.example.ghulam.parkingreservationsystem;

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

import com.example.ghulam.parkingreservationsystem.Admin.MallAddingActivity;
import com.example.ghulam.parkingreservationsystem.Users.UserActivity;
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

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText email, password;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private FirebaseUser user;
    private int forAccountCheck = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        

        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
//        progressBar = findViewById(R.id.progressbar);
        progressDialog= new ProgressDialog(this);

        findViewById(R.id.textViewSignup).setOnClickListener(this);
        findViewById(R.id.buttonLogin).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {
            finish();


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewSignup:
                finish();
                startActivity(new Intent(this, RegistrationActivity.class));
                break;

            case R.id.buttonLogin:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String Email = email.getText().toString().trim();
        String Password = password.getText().toString().trim();


        if (Email.isEmpty()) {
            email.setError("Email Address is Required");
            email.requestFocus();
            return;
        }

        if (Password.isEmpty()) {
            password.setError("Password is Required");
            password.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Please enter a valid email");
            email.requestFocus();
            return;
        }


//        progressBar.setVisibility(View.VISIBLE);
        progressDialog.setMessage("Gathering information please wait!");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                user = mAuth.getCurrentUser();

                if (task.isSuccessful()) {
                    email.setText("");
                    password.setText("");

                    Toast.makeText(SignInActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();


                    logginChecker(user.getUid());

                } else {
//                    progressBar.setVisibility(View.INVISIBLE);
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),
                            task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public void logginChecker(String userID){


        DatabaseReference ref1= FirebaseDatabase.getInstance().getReference().child("Parking Reservation").child("Admin").child(userID);

        ref1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    System.out.println("hello");
                    progressDialog.dismiss();

                    //removing this activity from back stack
                    finish();

                    //opening Admin
                    startActivity(new Intent(SignInActivity.this,MallAddingActivity.class));
                } else{
                    forAccountCheck++;
                    //if for account checck here has value >=2 it means that id is not present in either of tables
                    if(forAccountCheck>=2){

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

        DatabaseReference ref2=FirebaseDatabase.getInstance().getReference("Parking Reservation").child("Registered Users").child(userID);
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    progressDialog.dismiss();
                    //removing this activity from back stack
                    finish();

                    //opening Registered Users feed
                    startActivity(new Intent(SignInActivity.this,UserActivity.class));
                } else{

                    forAccountCheck++;
                    //if for account checck here has value >=2 it means that id is not present in either of tables
                    if(forAccountCheck>=2){
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
