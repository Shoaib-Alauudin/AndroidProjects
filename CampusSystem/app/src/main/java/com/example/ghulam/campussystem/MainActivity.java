package com.example.ghulam.campussystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ghulam.campussystem.CompanyLogin.StudentList;
import com.example.ghulam.campussystem.StudentLogin.CompaniesList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private EditText editTextEmail, editTextPassword;
    private ProgressBar progressBar;
    private DatabaseReference myStudentRef,myCompanyRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        myStudentRef = FirebaseDatabase.getInstance().getReference().child("Campus System").child("Student");
        myCompanyRef = FirebaseDatabase.getInstance().getReference().child("Campus System").child("Company");

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

        if (password.length()<6){
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()){
                    editTextEmail.setText("");
                    editTextPassword.setText("");
                    progressBar.setVisibility(View.GONE);

//                    Toast.makeText(MainActivity.this,R.string.loginSuccessful,Toast.LENGTH_SHORT).show();


                    if(myStudentRef.child(mAuth.getCurrentUser().getUid()).getParent().getKey() == "Student"){
                        Intent intent = new Intent(MainActivity.this, CompaniesList.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //Close all the open activity
                        startActivity(intent);
                    }
                    if(myCompanyRef.child(mAuth.getCurrentUser().getUid()).getParent().getKey() == "Company"){
                        Intent intent = new Intent(MainActivity.this, StudentList.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //Close all the open activity
                        startActivity(intent);
                    }
                }
                else {
                    progressBar.setVisibility(View.INVISIBLE);
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

            Intent intent = new Intent(this, ProfileActivity.class);
            String userID = mAuth.getCurrentUser().getUid();
            intent.putExtra("userID",userID);
            startActivity(intent);

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

    /*private void userProfile(){
        FirebaseUser user = mAuth.getCurrentUser();
        if (user!=null){
            UserProfileChangeRequest profileUpdates = new
                    UserProfileChangeRequest.Builder().
                    setDisplayName(editTextName.getText().toString().trim()).build();

            user.updateProfile(profileUpdates).
                    addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Log.d("Testing","UserProfile updated");
                            }
                        }
                    });

        }
    }*/

}
