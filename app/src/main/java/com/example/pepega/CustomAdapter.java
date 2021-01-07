package com.example.pepega;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    ArrayList<String> timestart;
    ArrayList<String> lat;
    ArrayList<String> lng;
    ArrayList<String> komen;
    ArrayList<String> komen2;
    ArrayList<String> timestamp;
    ArrayList<String> pic;
    ArrayList<String> timestop;
    ArrayList<String> total_step;
    Context context;

    public CustomAdapter(Context context, ArrayList<String> timestart, ArrayList<String> lat, ArrayList<String> lng, ArrayList<String> komen, ArrayList<String> komen2, ArrayList<String> timestamp, ArrayList<String> pic, ArrayList<String> timestop, ArrayList<String> total_step) {
        this.context = context;
        this.timestart = timestart;
        this.lat = lat;
        this.lng = lng;
        this.komen = komen;
        this.komen2 = komen2;
        this.timestamp = timestamp;
        this.pic = pic;
        this.timestop = timestop;
        this.total_step = total_step;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        holder.time_start.setText(timestart.get(position));
        holder.lati.setText(lat.get(position));
        holder.lang.setText(lng.get(position));
        holder.komens.setText(komen.get(position));
        holder.komens2.setText(komen2.get(position));
        //holder.times_tamp.setText(timestamp.get(position));
        holder.pics.setText(pic.get(position));
        holder.time_stop.setText(timestop.get(position));
        holder.step.setText(total_step.get(position));
        // implement setOnClickListener event on item view.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // display a toast with person name on item click
                Toast.makeText(context, timestart.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return timestart.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView time_start,lati,lang,komens,komens2,pics,time_stop,step;// init the item view's

        public MyViewHolder(View itemView) {
            super(itemView);

            // get the reference of item view's
            time_start = (TextView) itemView.findViewById(R.id.timesrat);
            lati = (TextView) itemView.findViewById(R.id.latitude);
            lang = (TextView) itemView.findViewById(R.id.langitude);
            komens = (TextView) itemView.findViewById(R.id.comment1);
            komens2 = (TextView) itemView.findViewById(R.id.comment2);
            pics = (TextView) itemView.findViewById(R.id.picture);
            time_stop = (TextView) itemView.findViewById(R.id.timestop);
            step = (TextView) itemView.findViewById(R.id.timestep);

        }
    }
}