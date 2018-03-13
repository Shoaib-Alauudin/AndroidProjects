package com.example.ghulam.myapplicationtodo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ghulam on 3/4/2018.
 */

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    LayoutInflater mInflater;
    ArrayList<Task> todoTaskList;
    Context context;


    public RecyclerViewAdapter(Context context, ArrayList<Task> TempList) {

        this.context = context;
        this.todoTaskList = TempList;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = mInflater.from(parent.getContext()).inflate(R.layout.task_row, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        final String taskTitle, taskTime, Key;
        final Task todoItem = todoTaskList.get(position);

        taskTitle = todoItem.getName();
        taskTime = todoItem.getTime();
        Key = todoItem.getKey();

        holder.todoTextViewName.setText(taskTitle);
        holder.todoTextViewTime.setText(taskTime);
        holder.todoTextViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();

                Intent singleActivity = new Intent(v.getContext(), singleTask.class);
                singleActivity.putExtra("key", Key);
                context.startActivity(singleActivity);
            }
        });


    }

    @Override
    public int getItemCount() {
        return todoTaskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView todoTextViewName;
        private TextView todoTextViewTime;



        public ViewHolder(View itemView) {

            super(itemView);
            todoTextViewName = (TextView) itemView.findViewById(R.id.taskName);
            todoTextViewTime = (TextView) itemView.findViewById(R.id.taskTime);
//            itemView.setOnClickListener(this);

        }

//        @Override
//        public void onClick(final View v) {
//            final Context context = v.getContext();
//
//            Toast.makeText(context,"The Item Clicked is: "+getAdapterPosition(),Toast.LENGTH_SHORT).show();
//            Intent singleActivity = new Intent(v.getContext(), singleTask.class);
////            singleActivity.putExtra("key", key);
//            context.startActivity(singleActivity);
//
//        }


    }
}