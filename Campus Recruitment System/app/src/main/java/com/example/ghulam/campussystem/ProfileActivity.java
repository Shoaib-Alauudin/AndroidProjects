package com.example.ghulam.campussystem;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.ghulam.campussystem.CompanyLogin.Student;
import com.example.ghulam.campussystem.StudentLogin.CompaniesList;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {


    private static final int CHOOSE_IMAGE = 101;

    private TextView textView;
    private ImageView imageView;
    private EditText editTextName,editTextContact,editTextEducation, editViewSkills;
    private Toolbar toolbar;

//    Used for store a uri of the Image --> data.getData()
    Uri uriProfileImage;
    ProgressBar progressBar;

    String profileImageUrl;

    FirebaseAuth mAuth;
    DatabaseReference myRef;

    String category,userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle(R.string.userProfile);

        editTextName =  findViewById(R.id.editTextDisplayName);
        editTextContact =  findViewById(R.id.editTextContact);
        editTextEducation =  findViewById(R.id.editTextEducation);
        imageView =  findViewById(R.id.imageView);
        progressBar =  findViewById(R.id.progressbar);
        editViewSkills =  findViewById(R.id.editViewSkills);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mAuth = FirebaseAuth.getInstance();


//      SignIn UserId
        category = getIntent().getExtras().getString("parentNode");



        myRef = FirebaseDatabase.getInstance().getReference("Campus System");








        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });

        loadUserInformation();

        findViewById(R.id.buttonSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.buttonSave).setEnabled(false);
                saveUserInformation();
                Intent intent = new Intent(getApplicationContext(), CompaniesList.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }


    //Image chooser method when camera button pressed
    private void showImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Profile Image"),CHOOSE_IMAGE);
    }

    private void loadUserInformation() {
        final FirebaseUser user = mAuth.getCurrentUser();

        if (user != null){
            if (user.getPhotoUrl() != null){
                Glide.with(getApplicationContext())
                        .load(user.getPhotoUrl().toString())
                        .into(imageView);
            }

            if(user.getDisplayName() != null){
                editTextName.setText(user.getDisplayName());
            }

            /*if (user.isEmailVerified()){
                textView.setText("Email Verifired");
            }else {
                textView.setText("Email Not Verified (Click to verify)");
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(ProfileActivity.this,"Verification Email Sent",Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
            }*/

        }
    }


    private void saveUserInformation(){

        String displayName = editTextName.getText().toString().trim();
        String Education = editTextEducation.getText().toString().trim();
        String Contact = editTextContact.getText().toString().trim();
        String Skills = editViewSkills.getText().toString().trim();

        if (displayName.isEmpty()){
            editTextName.setError("Name Required");
            editTextName.requestFocus();
            return;
        }

        if (Education.isEmpty()){
            editTextEducation.setError("Education Required");
            editTextEducation.requestFocus();
            return;
        }

        if (Contact.isEmpty()){
            editTextContact.setError("Contact Required");
            editTextContact.requestFocus();
            return;
        }

        if (Skills.isEmpty()){
            editViewSkills.setError("Skills Required");
            editViewSkills.requestFocus();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null && profileImageUrl != null){
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build();

            user.updateProfile(profileChangeRequest)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){

                        Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }


        myRef.child(category).child(user.getUid())
                .setValue(new Student(displayName,user.getUid(),
                        user.getEmail(),userPassword, category, Education,
                        Contact, profileImageUrl, Skills));


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK &&
                data != null && data.getData() != null){

            uriProfileImage = data.getData(); // Return uri of the image

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                imageView.setImageBitmap(bitmap);
                uploadImageToFirebaseStorage();

            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void uploadImageToFirebaseStorage(){
        StorageReference profileImageRef = FirebaseStorage.getInstance().
                getReference("profilepics/" + System.currentTimeMillis()+ ".jpg");

        if (uriProfileImage != null){
            progressBar.setVisibility(View.VISIBLE);
            profileImageRef.putFile(uriProfileImage).
                    addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressBar.setVisibility(View.GONE);
                            profileImageUrl = taskSnapshot.getDownloadUrl().toString();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    //  Menu activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

//  logOut button on Menu activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;
        }
        return true;
    }
}
