package com.example.pepega;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String json = "{\n\t\"users\": [\n\t\t{\n\t\t\t\"timestart\": \"2013-10-07 07:23:19\",\n\t\t\t\"koordinat\": {\n\t\t\t\t\"koordinat1\": {\n\t\t\t\t\t\"lat\": \"0\",\n\t\t\t\t\t\"lng\": \"0\",\n\t\t\t\t\t\"timestamp\": \"2013-10-07 08:23:19\"\n\t\t\t\t}\n\t\t\t},\n\t\t\t\"komentar\": {\n\t\t\t\t\"komentar1\":{\n\t\t\t\t\t\"komen\": \"test\",\n\t\t\t\t\t\"timestamp\": \"2013-10-07 08:23:19\"\n\t\t\t\t},\n\t\t\t\t\"komentar2\": {\n\t\t\t\t\t\"komen\": \"test1\",\n\t\t\t\t\t\"timestamp\": \"2013-10-07 08:23:19\"\n\t\t\t\t}\n\t\t\t},\n\t\t\t\"gambar\": {\n\t\t\t\t\"gambar1\": {\n\t\t\t\t\t\"pic\": \"\",\n\t\t\t\t\t\"timestamp\": \"2013-10-07 08:23:19\"\n\t\t\t\t}\n\t\t\t},\n\t\t\t\"timestop\": \"2013-10-07 09:23:19\",\n\t\t\t\"total_step\": \"69\"\n\t\t}\n\t]\n}\n\n";
    private SQLiteOpenHelper Opendb;
    private SQLiteDatabase dbku;
    // ArrayList for person names, email Id's and mobile numbers
    ArrayList<String> timestart = new ArrayList<>();
    ArrayList<String> lat = new ArrayList<>();
    ArrayList<String> lng = new ArrayList<>();
    ArrayList<String> komen = new ArrayList<>();
    ArrayList<String> komen2 = new ArrayList<>();
    ArrayList<String> timestamp = new ArrayList<>();
    ArrayList<String> pic = new ArrayList<>();
    ArrayList<String> timestop = new ArrayList<>();
    ArrayList<String> total_step = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get the reference of RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        Opendb = new SQLiteOpenHelper(this,"db.sql",null,1) {
            @Override
            public void onCreate(SQLiteDatabase db) {}
            @Override
            public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion){}
        };
        dbku = Opendb.getWritableDatabase();
        dbku.execSQL("create table if not exists data(j_son TEXT);");
        try {
            // get JSONObject from JSON file
            //JSONObject obj = new JSONObject(loadJSONFromAsset());
            // fetch JSONArray named users
//            simpan();
            String str="";
            Cursor cur = dbku.rawQuery("select * from data",null);
            JSONArray userArray = new JSONArray();

            if(cur.getCount() >0)
            {
                Toast.makeText(this,"Data Ditemukan Sejumlah " +
                        cur.getCount(),Toast.LENGTH_LONG).show();
                cur.moveToFirst();
                str = cur.getString(cur.getColumnIndex("j_son"));
                JSONObject obj = new JSONObject(str);
                userArray.put(obj);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView TextSteps;
        TextSteps = (TextView)findViewById(R.id.TextSteps);
        TextSteps.setText(String.valueOf(total_step));
        Button record = (Button)findViewById(R.id.butRecord);
        Button coba = (Button)findViewById(R.id.butCoba);
        record.setOnClickListener(operasi);
        coba.setOnClickListener(operasi);
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, timestart, lat, lng, komen, komen2, timestamp, pic, timestop, total_step);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
    }

    View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.butCoba:
                    show_detail();break;
                case R.id.butRecord:
                    recording();break;
            }
        }
    };

    private void simpan()
    {
        ContentValues dataku = new ContentValues();

        dataku.put("j_son",json);
        dbku.insert("data",null,dataku);
        Toast.makeText(this,"Data Tersimpan",Toast.LENGTH_LONG).show();
    }

    private void show_detail(){
        Intent myIntent = new Intent(this.getBaseContext(), DetailActivity.class);
        startActivityForResult(myIntent, 0);
    }
    private void recording(){
        Intent myIntent = new Intent(this.getBaseContext(), MainRecording.class);
        startActivityForResult(myIntent, 0);
    }


}