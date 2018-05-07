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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;    private EditText editTextName, editTextEmail, editTextPassword, editTextCNIC, editTextCarName, editTextPlateNo;
    private ProgressBar progressBar;
    private DatabaseReference myRef;
    private ProgressDialog progressDialog;


    FirebaseUser user;

    //    SignUp Fields
    private String name,email,password,cnic,carName, plateNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("Parking Reservation");

        progressDialog= new ProgressDialog(this);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextCNIC = findViewById(R.id.editTextCNIC);
        editTextCarName = findViewById(R.id.editTextCarName);
        editTextPlateNo = findViewById(R.id.editTextPlateNo);
//        progressBar = (ProgressBar)findViewById(R.id.progressbar);


        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);

    }

    public void setEmpty(){
        editTextName.setText("");
        editTextEmail.setText("");
        editTextPassword.setText("");
        editTextCNIC.setText("");
        editTextCarName.setText("");
        editTextPlateNo.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.textViewLogin:
                startActivity(new Intent(getApplicationContext(), SignInActivity.class));
                break;

            case R.id.buttonSignUp:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        name = editTextName.getText().toString().trim();
        email =  editTextEmail.getText().toString().trim();
        password =  editTextPassword.getText().toString().trim();
        cnic = editTextCNIC.getText().toString().trim();
        carName = editTextCarName.getText().toString().trim();
        plateNumber = editTextPlateNo.getText().toString().trim();

        if(name.isEmpty()){
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if(cnic.isEmpty()){
            editTextCNIC.setError("CNIC Number is required");
            editTextCNIC.requestFocus();
            return;
        }

        if(carName.isEmpty()){
            editTextCarName.setError("Car Name is required");
            editTextCarName.requestFocus();
            return;
        }

        if(plateNumber.isEmpty()){
            editTextPlateNo.setError("Plate Number is required");
            editTextPlateNo.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Invalid Email Address");
            editTextEmail.requestFocus();
            return;
        }

        if (password.length()<6){
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

//        progressBar.setVisibility(View.VISIBLE);


        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    finish();
                    userProfile();
                    user = mAuth.getCurrentUser();


                    myRef.child("Registered Users").child(user.getUid())
                            .setValue(new UserRegistration(name, email, cnic, carName, plateNumber, user.getUid()));


                    setEmpty();

                    FirebaseAuth.getInstance().signOut();

                    // Close Previous Activity
                    finish();

                    Toast.makeText(getApplicationContext(), "User Register Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegistrationActivity.this, SignInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clean all previous activity
                    startActivity(intent);


                }
                else{
//                    progressBar.setVisibility(View.GONE);
                    if(task.getException() instanceof FirebaseAuthUserCollisionException){

                        Toast.makeText(getApplicationContext(),
                                "You're Already Register",Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),
                                task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void userProfile(){
        user = mAuth.getCurrentUser();

        if (user!=null){
            UserProfileChangeRequest profileUpdates = new
                    UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build();

            user.updateProfile(profileUpdates).
                    addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){

//                                Toast.makeText(getApplicationContext(),"Profile Updated Successful", Toast.LENGTH_SHORT).show();

                            }

                        }

                    });

        }
    }

}
