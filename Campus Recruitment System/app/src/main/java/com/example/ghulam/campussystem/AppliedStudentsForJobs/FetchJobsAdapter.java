package com.example.ghulam.campussystem.AppliedStudentsForJobs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ghulam.campussystem.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Ghulam on 3/26/2018.
 */

public class FetchJobsAdapter extends RecyclerView.Adapter<MyViewHolderJobs> {

    LayoutInflater lf;
    Context context;
    ArrayList<JobsStructure> data;


    DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
            .child("Campus System")
            .child("Jobs");


    public FetchJobsAdapter(Context context, ArrayList<JobsStructure> data) {

        this.context = context;
        this.data = data;

        lf = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolderJobs onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = lf.inflate(R.layout.single_student_row, parent, false);
        MyViewHolderJobs holder = new MyViewHolderJobs(view, data);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolderJobs holder, int position) {

        final String jobTitle;
        final String pushID, uid;

        JobsStructure jobsStructure = data.get(position);
        jobTitle = jobsStructure.getTitle();
        pushID = jobsStructure.getPushid();
        uid = jobsStructure.getUid();


        holder.name.setText(jobTitle.toUpperCase());
        holder.detailIconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                Intent postedJob = new Intent(context, PostedJobDetails.class);
                postedJob.putExtra("pushID",pushID);
                postedJob.putExtra("uid",uid);
                postedJob.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(postedJob);
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}

class MyViewHolderJobs extends RecyclerView.ViewHolder {

    TextView name;
    ImageView detailIconImage;
    public ArrayList<JobsStructure> data;

    //This will be called inside onCreateViewHolder parameter interView contains the inflated single row view
    //here we will find resource ids
    public MyViewHolderJobs(View itemView, final ArrayList<JobsStructure> data) {
        super(itemView);
        name = itemView.findViewById(R.id.singleJob);
        detailIconImage = itemView.findViewById(R.id.detail);
        this.data = data;
    }


}
