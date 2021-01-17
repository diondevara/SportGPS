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
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainRecording extends AppCompatActivity {
    private static final int kodekamera = 222;
    private int num_call = 0;
    private double lat = 0, lng = 0,total_jarak = 0;
    EditText textdialog;
    LocationManager locationManager;
    LocationListener ll;
    JSONObject jsonObject;
    JSONArray gambar,komentar,lokasi;
    Sensor stepsensor;
    SensorManager mSensorManager;
    Button butfin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recording);
        mSensorManager =
                (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepsensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        butfin=(Button) findViewById(R.id.finish);
        jsonObject = new JSONObject();
        gambar= new JSONArray();
        komentar = new JSONArray();
        lokasi = new JSONArray();
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.getDefault());
        try {
            jsonObject.put("timestart", df.format(c));
            jsonObject.put("jarak",0.0);
            jsonObject.put("total_step",0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        butfin.setOnClickListener(operasi);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        ll = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                double jarak=distance(lat,lng,location.getLatitude(),location.getLongitude());
                lat = location.getLatitude();
                lng = location.getLongitude();
                num_call++;
                Log.d("dbg", "count: "+num_call);
                if(num_call>1){
                    total_jarak+=jarak;
                }

                JSONObject temp = new JSONObject();
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                JSONObject templok= new JSONObject();
                try {
                    jsonObject.put("jarak",total_jarak);
                    templok.put("date",df.format(c));
                    templok.put("lat",lat);
                    templok.put("lng",lng);

                    lokasi.put(templok);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                TextView latText = (TextView) findViewById(R.id.lat);
                TextView lngText = (TextView) findViewById(R.id.lng);
                TextView counterText = (TextView) findViewById(R.id.cnt);
                latText.setText(String.valueOf(lat));
                lngText.setText(String.valueOf(lng));
                counterText.setText(String.valueOf(total_jarak));
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
    View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent data= new Intent();
            Date c = Calendar.getInstance().getTime();

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.getDefault());
            //Get the EditText view and typecast here.
            try {
                jsonObject.put("koment",komentar);
                jsonObject.put("koordinat",lokasi);
                jsonObject.put("gambar",gambar);
                jsonObject.put("timestop", df.format(c));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            //set the value/data to pass back
            data.setData(Uri.parse(jsonObject.toString()));

            //set a result code, It is either RESULT_OK or RESULT_CANCELLED
            setResult(RESULT_OK,data);
            //Close the activity
            finish();
        }
    };
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
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
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                    JSONObject tempkoment= new JSONObject();
                    try {
                        tempkoment.put("date",df.format(c));
                        tempkoment.put("komentar",textdialog.getText().toString());
                        komentar.put(tempkoment);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

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
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        JSONObject tempcam= new JSONObject();
        try {
            tempcam.put("date",df.format(c));
            tempcam.put("gambar",bm);
            komentar.put(tempcam);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
