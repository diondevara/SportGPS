package com.example.pepega;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetailActivity extends FragmentActivity implements OnMapReadyCallback {
    Button backbutt;
    TextView texDist;
    TextView texStep;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        backbutt = (Button)findViewById(R.id.backbutton);
        backbutt.setOnClickListener(op);

        texDist = (TextView)findViewById(R.id.texDist);
        texStep = (TextView)findViewById(R.id.texStep);
        String steps = "0";
        String distance = "Kosong";
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            distance = extras.getString("jarak");
            steps = extras.getString("timestart");
        }
        texDist.setText(distance);
        texStep.setText(steps);
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
        LatLng ITS = new LatLng(-7.2819705,112.795323);
        mMap.addMarker(new MarkerOptions().position(ITS).title("Marker in ITS"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ITS, 15));
        // Add a marker in Sydney and move the camera
//        LatLng target = new LatLng(getIntent().getDoubleExtra("lat",0),getIntent().getDoubleExtra("lng",0));
//        mMap.addMarker(new MarkerOptions().position(target).title("Marker in target"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(target, 7));
    }
}
