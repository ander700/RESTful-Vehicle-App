package com.example.vehicleapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class UpdateActivity extends AppCompatActivity {
    public String model,make,licenseNumber,colour,transmission,fuelType,bodyStyle,condition,notes,year,numberDoors,mileage,engineSize,price;
    public String Nmodel,Nmake,NlicenseNumber,Ncolour,Ntransmission,NfuelType,NbodyStyle,Ncondition,Nnotes,Nyear,NnumberDoors,Nmileage,NengineSize,Nprice;
    public EditText modelText,yearText,makeText,priceText,licenseText,colourText,numberDoorsText,transmissionText,mileageText,fuelTypeText,engineSizeText,bodyStyleText,conditionText,notesText;
    public int conYear,conPrice,conNumberDoors,conMileage,conEngineSize;
    String URL = "http://10.0.2.2:8080/update_vehicle_json";
    final HashMap<String,String> postValues = new HashMap<>();
    public String apiKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent = getIntent();

        modelText = findViewById(R.id.vehicleModelEd2);
        yearText = findViewById(R.id.vehicleYearEdit2);
        makeText = findViewById(R.id.vehicleMakeEdit2);
        priceText = findViewById(R.id.vehiclePriceEdit2);
        licenseText = findViewById(R.id.vehicleLicenseEdit2);
        colourText = findViewById(R.id.vehicleColourEdit2);
        numberDoorsText = findViewById(R.id.vehicleNumberDoorsEdit2);
        transmissionText = findViewById(R.id.vehicleTransmissionEdit2);
        mileageText = findViewById(R.id.vehicleMileageEdit2);
        fuelTypeText = findViewById(R.id.vehicleFuelType2);
        engineSizeText = findViewById(R.id.vehicleEngineSizeEdit2);
        bodyStyleText = findViewById(R.id.vehicleBodyStyleEdit2);
        conditionText = findViewById(R.id.vehicleConditionEdit2);
        notesText = findViewById(R.id.vehicleNotesEdit2);



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
        getMenuInflater().inflate(R.menu.updatemenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.sendVehicle){
            AlertDialog.Builder altdial = new AlertDialog.Builder(UpdateActivity.this);
            altdial.setMessage("Are you sure you want to update this vehicle?").setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startAsyncUpdate();
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
        else if(id == R.id.vehicleLogo){

            Intent homeIntent = new Intent(UpdateActivity.this,MainActivity.class);
            startActivity(homeIntent);

        }

        return super.onOptionsItemSelected(item);
    }


    public void startAsyncUpdate()
    {
        updateAsyncTask update = new updateAsyncTask();
        update.execute();
    }


    public class updateAsyncTask extends AsyncTask<Void,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {

            modelText = findViewById(R.id.vehicleModelEd2);
            yearText = findViewById(R.id.vehicleYearEdit2);
            makeText = findViewById(R.id.vehicleMakeEdit2);
            priceText = findViewById(R.id.vehiclePriceEdit2);
            licenseText = findViewById(R.id.vehicleLicenseEdit2);
            colourText = findViewById(R.id.vehicleColourEdit2);
            numberDoorsText = findViewById(R.id.vehicleNumberDoorsEdit2);
            transmissionText = findViewById(R.id.vehicleTransmissionEdit2);
            mileageText = findViewById(R.id.vehicleMileageEdit2);
            fuelTypeText = findViewById(R.id.vehicleFuelType2);
            engineSizeText = findViewById(R.id.vehicleEngineSizeEdit2);
            bodyStyleText = findViewById(R.id.vehicleBodyStyleEdit2);
            conditionText = findViewById(R.id.vehicleConditionEdit2);
            notesText = findViewById(R.id.vehicleNotesEdit2);
            model = modelText.getText().toString();
            make = makeText.getText().toString();
            licenseNumber = licenseText.getText().toString();
            colour = colourText.getText().toString();
            transmission = transmissionText.getText().toString();
            fuelType = fuelTypeText.getText().toString();
            bodyStyle = bodyStyleText.getText().toString();
            condition = conditionText.getText().toString();
            notes = notesText.getText().toString();
            //ints
            year = (yearText.getText().toString());
            price = (priceText.getText().toString());
            numberDoors = (numberDoorsText.getText().toString());
            mileage = (mileageText.getText().toString());
            engineSize = (engineSizeText.getText().toString());
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
                conn.setRequestMethod("PUT");
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
                    Toast.makeText(UpdateActivity.this,"Vehicle Deleted !",Toast.LENGTH_LONG).show();
                    String line;
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while((line= br.readLine())!=null){
                        response+=line;
                    }
                }
                else{
                    Toast.makeText(UpdateActivity.this,"Error on updating vehicle ",Toast.LENGTH_LONG).show();
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
