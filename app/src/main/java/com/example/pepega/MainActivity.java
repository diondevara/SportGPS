package com.example.pepega;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

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
            simpan();
            String str="";
            Cursor cur = dbku.rawQuery("select * from data",null);

            if(cur.getCount() >0)
            {
                Toast.makeText(this,"Data Ditemukan Sejumlah " +
                        cur.getCount(),Toast.LENGTH_LONG).show();
                cur.moveToFirst();
                str = cur.getString(cur.getColumnIndex("j_son"));
            }
            JSONObject obj = new JSONObject(str);
            JSONArray userArray = obj.getJSONArray("users");
            // implement for loop for getting users list data
            for (int i = 0; i < userArray.length(); i++) {
                //fetch user data
                JSONObject user_detail = userArray.getJSONObject(i);
                //fetch timestart
                timestart.add(user_detail.getString("timestart"));
                //create object koordinat
                JSONObject koordinat = user_detail.getJSONObject("koordinat");
                //create object koordinat1,fetch lat lng
                JSONObject koordinat1 = koordinat.getJSONObject("koordinat1");
                lat.add(koordinat1.getString("lat"));
                lng.add(koordinat1.getString("lng"));
                //create object komen
                JSONObject komentar = user_detail.getJSONObject("komentar");
                //create object komentar1, fetch komen
                JSONObject komentar1 = komentar.getJSONObject("komentar1");
                komen.add(komentar1.getString("komen"));
                //create object komentar2, fetch komen
                JSONObject komentar2 = komentar.getJSONObject("komentar2");
                komen2.add(komentar2.getString("komen"));
                //create object gambar
                JSONObject gambar = user_detail.getJSONObject("gambar");
                //create object gambar1, fetch pic
                JSONObject gambar1 = gambar.getJSONObject("gambar1");
                pic.add(gambar1.getString("pic"));
                //fetch timestop
                timestop.add(user_detail.getString("timestop"));
                //fetch totalstep
                total_step.add(user_detail.getString("total_step"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView TextSteps;
        TextSteps = (TextView)findViewById(R.id.TextSteps);
        TextSteps.setText(String.valueOf(total_step));
        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, timestart, lat, lng, komen, komen2, timestamp, pic, timestop, total_step);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
    }

    private void simpan()
    {
        ContentValues dataku = new ContentValues();

        dataku.put("j_son",json);
        dbku.insert("data",null,dataku);
        Toast.makeText(this,"Data Tersimpan",Toast.LENGTH_LONG).show();
    }


    /*
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("fp_ppb.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }*/
}