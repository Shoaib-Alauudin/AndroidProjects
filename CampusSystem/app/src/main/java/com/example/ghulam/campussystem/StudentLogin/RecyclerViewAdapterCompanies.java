package com.example.ghulam.campussystem.StudentLogin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ghulam.campussystem.R;

import java.util.ArrayList;

/**
 * Created by Ghulam on 3/15/2018.
 */

public class RecyclerViewAdapterCompanies extends RecyclerView.Adapter<RecyclerViewAdapterCompanies.ViewHolder> {

    LayoutInflater mInfalter;
    ArrayList<Company> companies;
    Context context;


    public RecyclerViewAdapterCompanies(Context context, ArrayList<Company> companies) {

        this.context = context;
        this.companies = companies;
        mInfalter = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInfalter.from(parent.getContext()).inflate(R.layout.company_job_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final String companyName,jobTitle, companyKey;
        final int companyLogo;

/*      final Company companyList = companies.get(position);
        companyName = companyList.getCompanyName();
        jobTitle = companyList.getCompanyOfferedJob();
        companyLogo = companyList.getCompanyLogo();
        companyKey = companyList.getcompanyKey();*/

        companyName = companies.get(position).getCompanyName();
        jobTitle = companies.get(position).getCompanyOfferedJob();
        companyLogo = companies.get(position).getCompanyLogo();
        companyKey = companies.get(position).getUserID();


        holder.companyName.setText(companyName);
        holder.jobTitle.setText(jobTitle);
        holder.companyLogo.setImageResource(companyLogo);
        holder.companyDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                Intent companyJobDetail = new Intent(context,CompanyDetailActivity.class);
                companyJobDetail.putExtra("key",companyKey);
                context.startActivity(companyJobDetail);
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
        private TextView jobTitle,companyName;

        public ViewHolder(View view) {
            super(view);
            companyLogo = (ImageView)view.findViewById(R.id.companyLogo);
            jobTitle = (TextView)view.findViewById(R.id.jobTitle);
            companyName = (TextView)view.findViewById(R.id.companyName);
            companyDetail = (ImageView)view.findViewById(R.id.companyDetail);


        }
    }
}
