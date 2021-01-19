package com.example.pepega;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.telephony.ims.RcsUceAdapter;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailActivity extends FragmentActivity implements OnMapReadyCallback {
    Button backbutt;
    TextView texDist, texStep;
    private GoogleMap mMap;
    JSONObject fulldata;
    private ArrayList<MyKomen> komenList = new ArrayList<>();
    private ArrayList<MyPicture> gambarList = new ArrayList<>();
    private RecyclerView recyclerComment;
    private RecyclerView recyclerGambar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        texDist = (TextView)findViewById(R.id.texDist);
        texStep = (TextView)findViewById(R.id.texStep);

        String steps = "0";
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String data = extras.getString("data");
            Log.d("tt", data);
            try {
                fulldata= new JSONObject(data);
                Log.d("TAG", fulldata.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {
            texDist.setText(fulldata.get("jarak").toString());
            texStep.setText(fulldata.get("total_step").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        recyclerComment = findViewById(R.id.recyclerComment);
        recyclerGambar = findViewById(R.id.recyclerGambar);
        setAdapter();
    }
    View.OnClickListener op = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        JSONArray koordinat = null;
        try {
             koordinat=new JSONArray(fulldata.get("koordinat").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (koordinat != null) {
            for(int i = 0; i<koordinat.length(); i++){
                try {
                    JSONObject row= new JSONObject(koordinat.get(i).toString());
                    LatLng ITS = new LatLng(Double.parseDouble(row.get("lat").toString()),Double.parseDouble(row.get("lng").toString()));
                    mMap.addMarker(new MarkerOptions().position(ITS).title("Marker"));
                    if(i==0)mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ITS, 15));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        // Add a marker in Sydney and move the camera
//        LatLng target = new LatLng(getIntent().getDoubleExtra("lat",0),getIntent().getDoubleExtra("lng",0));
//        mMap.addMarker(new MarkerOptions().position(target).title("Marker in target"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(target, 7));
    }

    private void setAdapter(){
        JSONArray komentar = null;
        JSONArray gambar = null;
        try {
            komentar=new JSONArray(fulldata.get("koment").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (komentar != null) {
            for(int i = 0; i<komentar.length(); i++){
                try {
                    JSONObject row= new JSONObject(komentar.get(i).toString());
                    MyKomen temp=new MyKomen(row.getString("date"),row.getString("komentar"));
                    komenList.add(temp);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        try {
            gambar=new JSONArray(fulldata.get("gambar").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (gambar != null) {
            for(int i = 0; i<gambar.length(); i++){
                try {
                    JSONObject row= new JSONObject(gambar.get(i).toString());
                    MyPicture temp=new MyPicture(row.getString("date"),StringToBitMap(row.getString("gambar")) );
                    gambarList.add(temp);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
        Log.d("gambar", gambarList.toString());
        Log.d("komen", komenList.toString());
        AdapterKomen adapterKomen = new AdapterKomen(DetailActivity.this, komenList);
        AdapterPicture adapterPicture = new AdapterPicture(DetailActivity.this, gambarList);

        //LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        recyclerComment.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerComment.setItemAnimator(new DefaultItemAnimator());
        recyclerComment.setAdapter(adapterKomen);
        recyclerGambar.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerGambar.setItemAnimator(new DefaultItemAnimator());
        recyclerGambar.setAdapter(adapterPicture);
    }
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
