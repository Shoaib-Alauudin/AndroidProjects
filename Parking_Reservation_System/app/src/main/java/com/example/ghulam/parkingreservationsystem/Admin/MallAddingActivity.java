package com.example.ghulam.parkingreservationsystem.Admin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ghulam.parkingreservationsystem.Malls.Slot;
import com.example.ghulam.parkingreservationsystem.R;
import com.example.ghulam.parkingreservationsystem.SignInActivity;
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

public class MallAddingActivity extends AppCompatActivity {


    private static final int CHOOSE_IMAGE = 101;

    //    Used for store a uri of the Image --> data.getData()
    Uri uriMallImage;


    String mallImageUrl;

    private StorageReference mStorageRef;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;



    private Toolbar toolbar;
    private ImageView mallImage;
    private EditText mallName;
    private Spinner parkingSlots;
    private ProgressBar progressBar;
    private Button uploadBtn;

    private Mall mallSlots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mall_adding);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressBar =  findViewById(R.id.progressbar);

        uploadBtn = findViewById(R.id.buttonUpload);
        mallImage = findViewById(R.id.imageView);
        mallName = findViewById(R.id.edittextMallName);
        parkingSlots = findViewById(R.id.spinner);


        mAuth = FirebaseAuth.getInstance();

        uploadBtn.setVisibility(View.GONE);


//        Parking Mall Image select option
        mallImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();
            }
        });






        // upload new mall parking info by admin
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInformation();
                Toast.makeText(getApplicationContext(),"Upload Successful",Toast.LENGTH_SHORT).show();
                Log.v("ref", mRef.getKey());
            }
        });

    }

    private void showImageChooser() {
        Intent imageIntent = new Intent();
        imageIntent.setType("image/*");
        imageIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(imageIntent,"Select Mall Image"),CHOOSE_IMAGE);

        loadUserInformation();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK &&
                data != null && data.getData() != null){

            uriMallImage = data.getData(); // Return uri of the image

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriMallImage);
                mallImage.setImageBitmap(bitmap);
                uploadImageToFirebaseStorage();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                uploadBtn.setVisibility(View.GONE);

            } catch (IOException e) {
                e.printStackTrace();
                uploadBtn.setVisibility(View.GONE);
            }
        }
    }


    private void uploadImageToFirebaseStorage(){

        mStorageRef = FirebaseStorage.getInstance().getReference().child("mallpics/" + System.currentTimeMillis()+ ".jpg");

        if (uriMallImage != null){
            progressBar.setVisibility(View.VISIBLE);
            mStorageRef.putFile(uriMallImage). //Store image uri in firebase Storage
                    addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            progressBar.setVisibility(View.GONE);
                            mallImageUrl = taskSnapshot.getDownloadUrl().toString();
                            uploadBtn.setVisibility(View.VISIBLE);

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


    private void loadUserInformation() {

        final FirebaseUser user = mAuth.getCurrentUser();

        if (user != null){
            if (user.getPhotoUrl() != null){
                Glide.with(getApplicationContext())
                        .load(user.getPhotoUrl().toString())
                        .into(mallImage);
                mallImage.setScaleType(ImageView.ScaleType.FIT_XY);
                mallImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            /*else
                Toast.makeText(getApplicationContext(), "Empty", Toast.LENGTH_SHORT).show();*/
        }
    }

    private void saveUserInformation(){

        String displayName = mallName.getText().toString().trim();
        String selectedSlots = parkingSlots.getSelectedItem().toString();


        if (displayName.isEmpty()){
            mallName.setError("Name Required");
            mallName.requestFocus();
            return;
        }
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null && mallImageUrl != null){
            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(Uri.parse(mallImageUrl))
                    .build();

            user.updateProfile(profileChangeRequest)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){

                                Log.v("profile", "Mall Profile Updated");
//                                Toast.makeText(getApplicationContext(), "Mall Profile Updated", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


        }


        // Firebase database reference for parking Areas
        mRef = FirebaseDatabase.getInstance().getReference().
                child("Parking Reservation").child("Parking Areas").push();

        Log.v("mall",String.valueOf(mallImageUrl));
        mRef.setValue(new Mall(displayName, mallImageUrl, mRef.getKey()));

        for (int i=1; i <= Integer.valueOf(selectedSlots); i++){

            mRef.child("slots").child(String.valueOf(i)).setValue(new Slot("slot "+i, String.valueOf(i), mRef.getKey()));

//            mRef.child("slots").child(String.valueOf(i)).setValue(new Mall(String.valueOf(i), mRef.getKey(), false));
        }


    }

    //  Menu activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.admin, menu);

        return true;
    }

    //  logOut button on Menu activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.admin_Activity_Menu_LogOut:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(getApplicationContext(),SignInActivity.class));
                break;
        }

        return true;
    }

}
