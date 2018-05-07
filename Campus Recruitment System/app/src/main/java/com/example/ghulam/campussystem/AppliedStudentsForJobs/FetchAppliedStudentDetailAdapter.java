package com.example.ghulam.campussystem.AppliedStudentsForJobs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ghulam.campussystem.CompanyLogin.Student;
import com.example.ghulam.campussystem.R;

import java.util.ArrayList;

/**
 * Created by Ghulam on 4/6/2018.
 */

public class FetchAppliedStudentDetailAdapter extends RecyclerView.Adapter<MyViewHolderAppliedStudent>{

    LayoutInflater lf;
    Context context;
    ArrayList<Student> students;



    public FetchAppliedStudentDetailAdapter(Context context, ArrayList<Student> students) {

        this.context = context;
        this.students = students;

        lf = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolderAppliedStudent onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = lf.inflate(R.layout.single_student_row, parent, false);
        MyViewHolderAppliedStudent holder = new MyViewHolderAppliedStudent(view, students);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolderAppliedStudent holder, int position) {

        final String studentName,uid;

        Student student = students.get(position);
        studentName = student.getStudentName();
        uid = student.getUserID();
        Log.v("studentName",studentName);

        holder.name.setText(studentName.toUpperCase());
        holder.name.setTextColor(Color.parseColor("#138dc8"));
        holder.detailIconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();

                Intent appliedStudent = new Intent(context, AppliedStudentDetails.class);
                appliedStudent.putExtra("uid",uid);
                appliedStudent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(appliedStudent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return students.size();
    }
}

class MyViewHolderAppliedStudent extends RecyclerView.ViewHolder {

    TextView name;
    ImageView detailIconImage;
    public ArrayList<Student> students;

    //This will be called inside onCreateViewHolder parameter interView contains the inflated single row view
    //here we will find resource ids
    public MyViewHolderAppliedStudent(View itemView, final ArrayList<Student> students) {
        super(itemView);
        name = itemView.findViewById(R.id.singleJob);
        detailIconImage = itemView.findViewById(R.id.detail);
        this.students = students;
    }

}
