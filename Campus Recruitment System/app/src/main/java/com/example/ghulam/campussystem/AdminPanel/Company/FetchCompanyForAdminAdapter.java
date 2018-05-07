package com.example.ghulam.campussystem.AdminPanel.Company;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ghulam.campussystem.R;
import com.example.ghulam.campussystem.StudentLogin.Company;

import java.util.ArrayList;

/**
 * Created by Ghulam on 4/7/2018.
 */

public class FetchCompanyForAdminAdapter extends RecyclerView.Adapter<FetchCompanyForAdminAdapter.ViewHolder> {

    LayoutInflater mInfalter;
    ArrayList<Company> companies;
    Context context;


    public FetchCompanyForAdminAdapter(Context context, ArrayList<Company> companies) {

        this.context = context;
        this.companies = companies;
        mInfalter = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInfalter.from(parent.getContext())
                .inflate(R.layout.company_job_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final String companyName, userID, companyLogo;

        companyName = companies.get(position).getCompanyName();
        companyLogo = companies.get(position).getCompanyImage();
        userID = companies.get(position).getUserID();


        holder.companyName.setText(companyName);
        Glide.with(context).load(companyLogo).into(holder.companyLogo);

        holder.companyDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent companyDetailActivity = new Intent(context, DeleteCompany.class);
                companyDetailActivity.putExtra("userID", userID);
                context.startActivity(companyDetailActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return companies.size();
    }




    // ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView companyLogo, companyDetail;
        private TextView companyName;

        public ViewHolder(View view) {
            super(view);
            companyLogo = (ImageView)view.findViewById(R.id.Logo);
            companyDetail = (ImageView)view.findViewById(R.id.Detail);

            companyName = (TextView)view.findViewById(R.id.Name);


        }
    }
}