package com.example.vehicleapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class ViewMoreDetailsActivity extends AppCompatActivity {
    String URL = "http://10.0.2.2:8080/delete_vehicle_json";

    public int conYear,conPrice,conNumberDoors,conMileage,conEngineSize;
    final HashMap<String,String> postValues = new HashMap<>();
    public String model,make,licenseNumber,colour,transmission,fuelType,bodyStyle,condition,notes,year,numberDoors,mileage,engineSize,price;

    TextView modelText,yearText,makeText,priceText,licenseText,colourText,numberDoorsText,transmissionText,mileageText,fuelTypeText,engineSizeText,bodyStyleText,conditionText,notesText;
    public String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_more_details);
        populateList();

    }

    public void populateList(){
        Intent intent = getIntent();
        modelText = findViewById(R.id.vehicleModelView);
        yearText = findViewById(R.id.vehicleYearView);
        makeText = findViewById(R.id.vehicleMakeView);
        priceText = findViewById(R.id.vehiclePriceView);
        licenseText = findViewById(R.id.vehicleLicenseView);
        colourText = findViewById(R.id.vehicleColourView);
        numberDoorsText = findViewById(R.id.vehicleNumberDoorsView);
        transmissionText = findViewById(R.id.vehicleTransmissionView);
        mileageText = findViewById(R.id.vehicleMileageView);
        fuelTypeText = findViewById(R.id.vehicleFuelTypeView);
        engineSizeText = findViewById(R.id.vehicleEngineSizeView);
        bodyStyleText = findViewById(R.id.vehicleBodyStyleView);
        conditionText = findViewById(R.id.vehicleConditionsView);
        notesText = findViewById(R.id.vehicleNotesView);

        model = intent.getStringExtra("model");
        year = intent.getStringExtra("year");
        make = intent.getStringExtra("make");
        price = intent.getStringExtra("price");
        licenseNumber = intent.getStringExtra("licenseNumber");
        colour = intent.getStringExtra("colour");
        numberDoors = intent.getStringExtra("numberDoors");
        transmission = intent.getStringExtra("transmission");
        mileage = intent.getStringExtra("mileage");
        fuelType = intent.getStringExtra("fuelType");
        engineSize = intent.getStringExtra("engineSize");
        bodyStyle = intent.getStringExtra("bodyStyle");
        condition = intent.getStringExtra("condition");
        notes = intent.getStringExtra("notes");
        apiKey = intent.getStringExtra("apiKey");

        System.out.println(model + year + "***********************");

        modelText.setText(model);
        yearText.setText(year);
        makeText.setText(make);
        priceText.setText(price);
        licenseText.setText(licenseNumber);
        colourText.setText(colour);
        numberDoorsText.setText(numberDoors);
        transmissionText.setText(transmission);
        mileageText.setText(mileage);
        fuelTypeText.setText(fuelType);
        engineSizeText.setText(engineSize);
        bodyStyleText.setText(bodyStyle);
        conditionText.setText(condition);
        notesText.setText(notes);


        conYear = Integer.parseInt(year);
        conPrice = Integer.parseInt(price);
        conNumberDoors = Integer.parseInt(numberDoors);
        conMileage = Integer.parseInt(mileage);
        conEngineSize = Integer.parseInt(engineSize);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.viewvehicle,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.deleteVehicle){
            AlertDialog.Builder altdial = new AlertDialog.Builder(ViewMoreDetailsActivity.this);
            altdial.setMessage("Are you sure you want to delete this vehicle?").setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            executeDelete();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = altdial.create();
            alert.setTitle("Dialog Header");
            alert.show();
        }
        else if(id == R.id.editButtton){
           sendUpdate();
        }
        else if(id == R.id.vehicleLogo){

            Intent homeIntent = new Intent(ViewMoreDetailsActivity.this,MainActivity.class);
            startActivity(homeIntent);

        }
        return super.onOptionsItemSelected(item);
    }

    public void sendUpdate(){

        Bundle vExtras  = new Bundle();
        Intent intent = new Intent(ViewMoreDetailsActivity.this,UpdateActivity.class);

        vExtras.putString("model",model);
        vExtras.putString("year",year);
        vExtras.putString("make",make);
        vExtras.putString("price",price);
        vExtras.putString("licenseNumber",licenseNumber);
        vExtras.putString("colour",colour);
        vExtras.putString("numberDoors",numberDoors);
        vExtras.putString("transmission",transmission);
        vExtras.putString("mileage",mileage);
        vExtras.putString("fuelType",fuelType);
        vExtras.putString("engineSize",engineSize);
        vExtras.putString("bodyStyle",bodyStyle);
        vExtras.putString("condition",condition);
        vExtras.putString("notes",notes);
        vExtras.putString("apiKey",apiKey);
        intent.putExtras(vExtras);

        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        populateList();
    }

    public void executeDelete(){
        deleteDataAsync delete = new deleteDataAsync();
        delete.execute();
    }

    public class deleteDataAsync extends AsyncTask<Void,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            Gson gson = new Gson();
            Vehicles veh = new Vehicles(model,conYear,make,conPrice,licenseNumber,colour,conNumberDoors,transmission,conMileage,fuelType,conEngineSize,bodyStyle,condition,notes,1);
            String vehicleJson = gson.toJson(veh);
            postValues.put("json",vehicleJson);
            postValues.put("apiKey",apiKey);

            String response ="";
            URL url;
            try {
                url = new URL(URL);

                //create connection
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writ = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

                writ.write(getPostDataString(postValues));

                writ.flush();
                writ.close();
                os.close();

                int responseCode = conn.getResponseCode();
                System.out.println("response " + responseCode);
                if (responseCode == HttpsURLConnection.HTTP_OK){
                    Toast.makeText(ViewMoreDetailsActivity.this,"Vehicle Deleted !",Toast.LENGTH_LONG).show();
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while((line= br.readLine())!=null){
                        response+=line;
                    }
                }
                else{
                    Toast.makeText(ViewMoreDetailsActivity.this,"Error on deleting vehicle ",Toast.LENGTH_LONG).show();
                    response="";
                }

            }
            catch(Exception e){
                e.printStackTrace();
            }

            return response;
        }



        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }

    }

    private String getPostDataString(HashMap<String,String>params)throws UnsupportedEncodingException {
        StringBuilder res = new StringBuilder();

        boolean first = true;
        for(Map.Entry<String,String>entry:params.entrySet()){
            if(first) {
                first = false;
            }
            else {
                res.append("&");
            }
            res.append(URLEncoder.encode(entry.getKey(),"UTF-8"));
            res.append("=");
            res.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
        }
        return res.toString();
    }


}
