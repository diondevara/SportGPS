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


public class AdapterPicture extends RecyclerView.Adapter<AdapterPicture.MyViewHolder> {
    // datanya bukannya begini arrlist<json>
    List<MyPicture> datanya;
    Context context;
    public AdapterPicture(Context context, ArrayList<MyPicture> data ) {
        this.context=context;
        this.datanya=data;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listgambar, parent, false);
        // pass the view to View Holder
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        MyPicture dat=(MyPicture) datanya.get(position);
        holder.date.setText(dat.getDate());
        holder.gambar.setText(dat.getPicture());
    }


    @Override
    public int getItemCount() {
        return datanya.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView date,gambar;// init the item view's

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            date = (TextView) itemView.findViewById(R.id.timegambar);
            gambar = (TextView) itemView.findViewById(R.id.gambarnya);
        }


    }


}