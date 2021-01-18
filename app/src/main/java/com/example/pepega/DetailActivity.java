package com.example.pepega;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.ims.RcsUceAdapter;
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
        AdapterKomen adapterKomen = new AdapterKomen(DetailActivity.this, komenList);
        AdapterPicture adapterPicture = new AdapterPicture(DetailActivity.this, gambarList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext());
        recyclerComment.setLayoutManager(linearLayoutManager);
        recyclerComment.setItemAnimator(new DefaultItemAnimator());
        recyclerComment.setAdapter(adapterKomen);
        recyclerGambar.setLayoutManager(linearLayoutManager2);
        recyclerGambar.setItemAnimator(new DefaultItemAnimator());
        recyclerGambar.setAdapter(adapterPicture);
    }
}
