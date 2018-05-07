package com.example.ghulam.parkingreservationsystem.Malls.MallList;

/**
 * Created by Ghulam on 4/25/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ghulam.parkingreservationsystem.Admin.Mall;
import com.example.ghulam.parkingreservationsystem.Malls.MallBookingActivity;
import com.example.ghulam.parkingreservationsystem.R;

import java.util.ArrayList;


public class RecyclerViewMallsAdapter extends RecyclerView.Adapter<RecyclerViewMallsAdapter.ViewHolder> {

    LayoutInflater mInfalter;
    ArrayList<Mall> MallList;
    Context context;

    public RecyclerViewMallsAdapter(Context context, ArrayList<Mall> mall){
        this.context = context;
        this.MallList = mall;
        mInfalter = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInfalter.from(parent.getContext()).
                inflate(R.layout.malls_info_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final String mallImage, mallName;
        final String mallID;

        mallImage = MallList.get(position).getImage();
        mallName = MallList.get(position).getName();
        mallID = MallList.get(position).getMallid();


        holder.mallName.setText(mallName);
        Glide.with(context).load(mallImage).into(holder.mallImage);
        holder.mallImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mallDetailActivity = new Intent(context, MallBookingActivity.class);
                mallDetailActivity.putExtra("mallID",mallID);
                context.startActivity(mallDetailActivity);
            }
        });
    }

    @Override
    public int getItemCount() {
        return MallList.size();
    }


    //  ViewHolder Class
    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mallImage;
        private TextView mallName;


        public ViewHolder(View view) {
            super(view);

            mallImage = view.findViewById(R.id.imageViewMall);
            mallName = view.findViewById(R.id.textViewMallName);


        }
    }
}
