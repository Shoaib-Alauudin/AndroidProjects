package com.example.ghulam.campussystem.AdminPanel;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.ghulam.campussystem.R;

import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        viewPager = (ViewPager)findViewById(R.id.myViewPage);

        ArrayList<String> fragment = new ArrayList<>();


    }
}
