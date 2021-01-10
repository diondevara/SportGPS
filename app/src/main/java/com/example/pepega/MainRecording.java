package com.example.pepega;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

public class MainRecording extends AppCompatActivity {
    private static final int kodekamera = 222;
    private int num_call = 0;
    private double lat = 0, lng = 0;
    EditText textdialog;
    LocationManager locationManager;
    LocationListener ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ll = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                lat = location.getLatitude();
                lng = location.getLongitude();
                num_call++;
                TextView latText = (TextView) findViewById(R.id.lat);
                TextView lngText = (TextView) findViewById(R.id.lng);
                TextView counterText = (TextView) findViewById(R.id.cnt);
                latText.setText(String.valueOf(lat));
                lngText.setText(String.valueOf(lng));
                counterText.setText(String.valueOf(num_call));
                Toast.makeText(getBaseContext(), "GPS capture",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, ll);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d("TAG", "onOptionsItemSelected: ");
        switch (item.getItemId()){
            case R.id.gambar : callcamera(); break;
            case R.id.komentar: callkomentar();break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void callkomentar() {
        LayoutInflater li= LayoutInflater.from(this);
        View dialogBox = li.inflate(R.layout.form_komentar,null);
        AlertDialog.Builder dialogView= new AlertDialog.Builder(this);
        dialogView.setView(dialogBox);
        textdialog= (EditText) dialogBox.findViewById(R.id.komentar);
        dialogView
                .setCancelable(false)
                .setPositiveButton("Ok",oknya)
                .setNegativeButton("Batal",oknya);
        dialogView.show();
    }
    DialogInterface.OnClickListener oknya = new DialogInterface.OnClickListener()
    {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            //positif -1 negatif -2
            switch (which) {
                case -1:
                    //store this shit textdialog

                    //help any1
                    break;
                case -2:
                    break;
            }
        }
    };
    private void callcamera() {
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(it, kodekamera);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("dbg", "onActivityResult: "+requestCode+" res:"+resultCode+" "+ Activity.RESULT_CANCELED);
        if (resultCode == Activity.RESULT_OK) {

            switch (requestCode) {
                case (kodekamera):prosesKamera(data);break;
            }
        }
    }
    private void prosesKamera(Intent datanya){
        Bitmap bm;

        bm = (Bitmap)datanya.getExtras().get("data");
        //store this shit bm

        //help any1
    }

}
