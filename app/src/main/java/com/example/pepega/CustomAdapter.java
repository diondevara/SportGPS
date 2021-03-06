package com.example.pepega;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    // datanya bukannya begini arrlist<json>
    List<MyData> datanya;
    Context context;
    public CustomAdapter(Context context, ArrayList<MyData> data, RecyclerViewClickListener listener) {
        this.context=context;
        this.datanya=data;
        this.listener = listener;
    }
    private RecyclerViewClickListener listener;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        // pass the view to View Holder
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        MyData dat=(MyData)datanya.get(position);
        holder.time_start.setText(dat.getStart());
        holder.distance.setText(dat.getDistance());
    }


    @Override
    public int getItemCount() {
        return datanya.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView time_start,distance;// init the item view's

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            time_start = (TextView) itemView.findViewById(R.id.timesrat);
            distance = (TextView) itemView.findViewById(R.id.distance);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    public interface RecyclerViewClickListener{
        void onClick(View view, int position);
    }
}