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

import com.bumptech.glide.Glide;
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
    private EditText editTextName,editTextContact,editTextEducation;
    private android.support.v7.widget.Toolbar toolbar;

//    Used for store a uri of the Image --> data.getData()
    Uri uriProfileImage;
    ProgressBar progressBar;

    String profileImageUrl;

    FirebaseAuth mAuth;
    DatabaseReference myStudentRef,myCompanyRef,myRef;

    String userID, category,userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle(R.string.userProfile);

//      SignIn UserId
//        userID = getIntent().getExtras().getString("userID");
//        category = getIntent().getExtras().getString("parentNode");
//        userPassword = getIntent().getExtras().getString("password");

        Toast.makeText(getApplicationContext(),category, Toast.LENGTH_LONG).show();

        myRef = FirebaseDatabase.getInstance().getReference()
                .child("Campus System");



        mAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextName =  findViewById(R.id.editTextDisplayName);
//        editTextContact =  findViewById(R.id.editTextContact);
//        editTextEducation =  findViewById(R.id.editTextEducation);
        imageView =  findViewById(R.id.imageView);
        progressBar =  findViewById(R.id.progressbar);
        textView =  findViewById(R.id.textViewVerified);


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
                saveUserInformation();
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

            if (user.isEmailVerified()){
                textView.setText("Email Verifired");
            }else {
                textView.setText("Email Not Verified (Click to verify)");
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user.sendEmailVerification()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(ProfileActivity.this,
                                                "Verification Email Sent",Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });
            }
        }
    }


    private void saveUserInformation(){

        String displayName = editTextName.getText().toString();
//        String Education = editTextEducation.getText().toString();
//        String Contact = editTextContact.getText().toString();

        if (displayName.isEmpty()){
            editTextName.setError("Name Required");
            editTextName.requestFocus();
            return;
        }

        /*if (Education.isEmpty()){
            editTextEducation.setError("Name Required");
            editTextEducation.requestFocus();
            return;
        }

        if (Contact.isEmpty()){
            editTextContact.setError("Name Required");
            editTextContact.requestFocus();
            return;
        }*/


        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null && profileImageUrl != null){
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
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

        /*if (category.matches("Student")){

            myRef
                    .child(category)
                    .child(new Student(editTextName,user.);
        }
        else
            myRef
                    .child(category)
                    .child(new Company(displayName,user.getUid(),user.getEmail(),userPassword,user.getPhotoUrl()));

*/
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
