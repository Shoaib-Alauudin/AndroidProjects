package com.example.ghulam.campussystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ghulam.campussystem.CompanyLogin.Student;
import com.example.ghulam.campussystem.StudentLogin.Company;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private EditText editTextEmail, editTextPassword, editTextName, editTextConfirmPassword;
    private ProgressBar progressBar;
    private Spinner selectSpinner;
    DatabaseReference myRef;

    FirebaseUser user;

//    SignUp Fields
    private String name,email,password,confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference().child("Campus System");



        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        editTextName = (EditText)findViewById(R.id.editTextName);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);
        editTextConfirmPassword = (EditText)findViewById(R.id.editTextConfirmPassword);
        selectSpinner = (Spinner) findViewById(R.id.spinner);



        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);


    }

    public void setEmpty(){
        editTextName.setText("");
        editTextEmail.setText("");
        editTextPassword.setText("");
        editTextConfirmPassword.setText("");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.textViewLogin:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
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
        confirmPassword = editTextConfirmPassword.getText().toString().trim();



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


        if(!confirmPassword.equals(password)){
            editTextConfirmPassword.setError("Password not match");
            editTextConfirmPassword.requestFocus();
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

        progressBar.setVisibility(View.VISIBLE);


        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    finish();
                    userProfile();
                    user = mAuth.getCurrentUser();
                    String category = selectSpinner.getSelectedItem().toString();

                    myRef.child(category).child(user.getUid())
                            .setValue(new Student(name,user.getEmail(),user.getUid(),password));
                    if(category.equals("Company")){
                        myRef.child(category).child(user.getUid())
                                .setValue(new Company(name,user.getEmail(),user.getUid(),password));

                        /*Student(String studentName, String studentEmail, String userId, String userPassword)*/
                    }
                    else
                        myRef.child(category).child(user.getUid())
                                .setValue(new Student(name,user.getEmail(),user.getUid(),password));

                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(SignUpActivity.this,"User Register Successful", Toast.LENGTH_SHORT).show();
                    setEmpty();
                    Intent intent = new Intent(SignUpActivity.this, ProfileActivity.class);
//                    intent.putExtra("userID",mAuth.getCurrentUser().getUid());

                    intent.putExtra("password",password);
                    intent.putExtra("parentNode",category);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //Close all the open activity
                    startActivity(intent);
                }
                else{
                    progressBar.setVisibility(View.GONE);

                    if(task.getException() instanceof FirebaseAuthUserCollisionException){

                        Toast.makeText(getApplicationContext(),
                                "You're Already Register",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),
                                task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        editTextPassword.setText("");
                        editTextConfirmPassword.setText("");
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
                    .setDisplayName(editTextName.getText().toString().trim())
                    .build();

            user.updateProfile(profileUpdates).
                    addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Name Added", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }

    }

}
