package com.example.ghulam.campussystem.StudentLogin;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ghulam.campussystem.R;

public class CompanyDetailActivity extends AppCompatActivity {
    private ImageView companyLogo;
    private TextView companyName,jobTitle,jobDescription,companyDescription;
    private CheckBox appliedForJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);

        companyLogo = (ImageView)findViewById(R.id.companyLogo);

        companyName = (TextView)findViewById(R.id.companyName);
        jobTitle = (TextView)findViewById(R.id.jobTitle);
        jobDescription = (TextView)findViewById(R.id.jobDescription);
        companyDescription = (TextView)findViewById(R.id.companyDescription);

        appliedForJob = (CheckBox)findViewById(R.id.checkBox);

    }
}
