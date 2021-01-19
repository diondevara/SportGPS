package com.example.pepega;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    //private String json = "{\n\t\"users\": [\n\t\t{\n\t\t\t\"timestart\": \"2013-10-07 07:23:19\",\n\t\t\t\"koordinat\": {\n\t\t\t\t\"koordinat1\": {\n\t\t\t\t\t\"lat\": \"0\",\n\t\t\t\t\t\"lng\": \"0\",\n\t\t\t\t\t\"timestamp\": \"2013-10-07 08:23:19\"\n\t\t\t\t}\n\t\t\t},\n\t\t\t\"komentar\": {\n\t\t\t\t\"komentar1\":{\n\t\t\t\t\t\"komen\": \"test\",\n\t\t\t\t\t\"timestamp\": \"2013-10-07 08:23:19\"\n\t\t\t\t},\n\t\t\t\t\"komentar2\": {\n\t\t\t\t\t\"komen\": \"test1\",\n\t\t\t\t\t\"timestamp\": \"2013-10-07 08:23:19\"\n\t\t\t\t}\n\t\t\t},\n\t\t\t\"gambar\": {\n\t\t\t\t\"gambar1\": {\n\t\t\t\t\t\"pic\": \"\",\n\t\t\t\t\t\"timestamp\": \"2013-10-07 08:23:19\"\n\t\t\t\t}\n\t\t\t},\n\t\t\t\"timestop\": \"2013-10-07 09:23:19\",\n\t\t\t\"total_step\": \"69\"\n\t\t}\n\t]\n}\n\n";
    private SQLiteOpenHelper Opendb;
    private SQLiteDatabase dbku;
    private CustomAdapter.RecyclerViewClickListener listener;
    // ArrayList for person names, email Id's and mobile numbers
    JSONArray userArray;
    ArrayList<String> total_step = new ArrayList<>();
    ArrayList<MyData> datalist= new ArrayList<>();
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get the reference of RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // set a LinearLayoutManager with default vertical orientation
        setOnClickListener();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        customAdapter = new CustomAdapter(MainActivity.this, datalist, listener);
        recyclerView.setAdapter(customAdapter);
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
            // JSONObject obj = new JSONObject(loadJSONFromAsset());
            // fetch JSONArray named users
            // simpan();
            String str="";
            Cursor cur = dbku.rawQuery("select * from data",null);
            userArray = new JSONArray();

            if(cur.getCount() >0)
            {
                Toast.makeText(this,"Data Ditemukan Sejumlah " +
                        cur.getCount(),Toast.LENGTH_LONG).show();
                cur.moveToFirst();
                for(int i=0;i<cur.getCount();i++){
                    str = cur.getString(cur.getColumnIndex("j_son"));
                    JSONObject obj = new JSONObject(str);
                    cur.moveToNext();
                    userArray.put(obj);
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(userArray.length()==0){
            datalist.add(new MyData("",""));
        }
        for (int i = 0; i < userArray.length(); i++) {
            JSONObject obj = null;
            try {
                obj = userArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MyData data = null;
            try {
                data = new MyData(
                        obj.getString("timestart"),
                        obj.getString("jarak"));
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Question" + " " + i,
                        "\nQuestion id = " + data.getStart() +
                                " Question text = " + data.getDistance());
            }

            datalist.add(data);
        }
        // notify data set change call missing
        customAdapter.notifyDataSetChanged();
        TextView TextSteps;
        TextSteps = (TextView)findViewById(R.id.TextSteps);
        // TextSteps.setText(String.valueOf(total_step));
        Button record = (Button)findViewById(R.id.butRecord);
        record.setOnClickListener(operasi);
        // call the constructor of CustomAdapter to send the reference and data to Adapter
        // set the Adapter to RecyclerView
    }

    private void setOnClickListener() {
        listener = new CustomAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), DetailActivity.class);

                try {
                    intent.putExtra("data", userArray.get(position).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        };
    }

    View.OnClickListener operasi = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.butRecord:
                    recording();
                    break;
            }
        }
    };

    private void simpan(String json)
    {
        ContentValues dataku = new ContentValues();

        dataku.put("j_son",json);
        dbku.insert("data",null,dataku);
        Toast.makeText(this,"Data Tersimpan",Toast.LENGTH_LONG).show();
    }

    private void recording() {
        Intent myIntent = new Intent(this.getBaseContext(), MainRecording.class);
        startActivityForResult(myIntent, 100);

        // Get data from Intent

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            //doDB things
            String jsondata = null;
            if (data != null) {
                jsondata = Objects.requireNonNull(data.getData()).toString();
                if(jsondata != ""){
                    simpan(jsondata);
                    JSONObject obj = null;
                    try {
                        obj = new JSONObject(jsondata);
                        userArray.put(obj);
                        MyData temp=new MyData(obj.getString("timestart"),
                                obj.getString("jarak"));
                        datalist.add(temp);
                        customAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

        }
    }
}