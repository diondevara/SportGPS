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


public class AdapterKomen extends RecyclerView.Adapter<AdapterKomen.MyViewHolder> {
    // datanya bukannya begini arrlist<json>
    List<MyKomen> datanya;
    Context context;
    public AdapterKomen(Context context, ArrayList<MyKomen> data, RecyclerViewClickListener listener) {
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
        MyKomen dat=(MyKomen) datanya.get(position);
        holder.date.setText(dat.getDate());
        holder.komentar.setText(dat.getKomentar());
    }


    @Override
    public int getItemCount() {
        return datanya.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView date,komentar;// init the item view's

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            date = (TextView) itemView.findViewById(R.id.date);
            komentar = (TextView) itemView.findViewById(R.id.komentar);
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