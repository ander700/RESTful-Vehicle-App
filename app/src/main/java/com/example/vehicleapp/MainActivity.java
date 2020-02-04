package com.example.vehicleapp;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> vehArray = new ArrayList<String>();
    public String apiKey = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        apiKey = intent.getStringExtra("apiKey");
        System.out.println(apiKey + " vurrent api key honety");


    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.front,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Button confirmApi = findViewById(R.id.confirmApi);
        final EditText apiKeyInput = findViewById(R.id.apiInput);
        int id = item.getItemId();
        if(id == R.id.apiKeyButt){
            apiKeyInput.setVisibility(View.VISIBLE);
            confirmApi.setVisibility(View.VISIBLE);
            confirmApi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    apiKey = apiKeyInput.getText().toString();
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    public void startPost(View v){
        if(apiKey.equals("")){
            Toast.makeText(MainActivity.this,"To perform interactions with the server, an API key is required",Toast.LENGTH_LONG).show();
        }
        else{
            Intent intent = new Intent(this,PostActivity.class);
            intent.putExtra("choice",apiKey);
            intent.putExtra("apiKey",apiKey);
            startActivity(intent);
        }

    }

    public void showVehiclesDefault(View v){
        if(apiKey.equals("")){
            Toast.makeText(this,"To perform interactions with the server, an API key is required",Toast.LENGTH_LONG).show();
        }
        else{
            String no = "1";
            Intent intent = new Intent(this,ViewVehiclesActivity.class);
            intent.putExtra("choice",no);
            intent.putExtra("apiKey",apiKey);
            startActivity(intent);
        }

    }


public void showVehiclesWeird(View v){
        if(apiKey.equals("")){
            Toast.makeText(this,"To perform interactions with the server, an API key is required",Toast.LENGTH_LONG).show();
        }
        else{
            String yes = "0";
            Intent intent = new Intent(this,ViewVehiclesActivity.class);
            intent.putExtra("apiKey",apiKey);
            intent.putExtra("choice",yes);
            startActivity(intent);
        }

}

    public void getConnection(View v){
        getConnectionAsync task = new getConnectionAsync(this);
        task.execute("http://10.0.2.2:8080/list");
    }

    public class getConnectionAsync extends AsyncTask<String,Void,String>{
        private WeakReference<MainActivity> activityWeakReference;
        getConnectionAsync(MainActivity activity){
            activityWeakReference = new WeakReference<MainActivity>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity activity= activityWeakReference.get();
            if(activity == null || activity.isFinishing()){
                return;
            }
            Toast.makeText(MainActivity.this,"Acquiring connection...",Toast.LENGTH_LONG).show();

        }
        @Override
       protected String doInBackground(String... strings) {

            StringBuilder newSb = new StringBuilder();
            try {


                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bin = new BufferedReader(new InputStreamReader(in));

                String inputLine;
                while ((inputLine = bin.readLine()) != null) {
                    newSb.append(inputLine);
                }

                urlConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newSb.toString();

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MainActivity activity= activityWeakReference.get();
            if(activity == null || activity.isFinishing()){
                return;
            }
           System.out.println(s);
            Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();

        }
   }

}
