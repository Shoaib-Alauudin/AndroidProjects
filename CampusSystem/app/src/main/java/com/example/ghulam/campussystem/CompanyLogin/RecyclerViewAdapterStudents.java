package com.example.ghulam.campussystem.CompanyLogin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ghulam.campussystem.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Ghulam on 3/17/2018.
 */

public class RecyclerViewAdapterStudents extends RecyclerView.Adapter<RecyclerViewAdapterStudents.ViewHolder> {

    LayoutInflater mInfalter;
    ArrayList<Student> students;
    Context context;

    public RecyclerViewAdapterStudents(Context context, ArrayList<Student> students) {

        this.context = context;
        this.students = students;
        mInfalter = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInfalter.from(parent.getContext()).inflate(R.layout.student_info_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final String studentName, studentQualification;
        final String userID;


        studentName = students.get(position).getStudentName();
        studentQualification = students.get(position).getEduaction();
        userID = students.get(position).getUserID();


        holder.studentName.setText(studentName);
        holder.studentQualification.setText(studentQualification);
        Picasso.with(context).load(students.get(position).getStudentImage()).
                resize(240, 120).into(holder.studentImage);
/*        Glide.with(context).load(students.get(position).
                getStudentImage()).into(holder.studentImage);*/
        holder.studentDetailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent studentDetailActivity = new Intent(context, StudentDetailActivity.class);
                studentDetailActivity.putExtra("key",userID);
                context.startActivity(studentDetailActivity);
            }
        });

//        Glide.with(context).load()

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    //  ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView studentImage, studentDetailImage;
        private TextView studentName, studentQualification;

        public ViewHolder(View view) {
            super(view);
            studentImage = (ImageView) view.findViewById(R.id.studentImageView);
            studentDetailImage = (ImageView) view.findViewById(R.id.studentDetailImageView);
            studentName = (TextView) view.findViewById(R.id.StudentNameTextView);
            studentQualification = (TextView) view.findViewById(R.id.qualificationTextView);

        }
    }
}
