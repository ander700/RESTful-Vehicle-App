package com.example.vehicleapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ViewVehiclesActivity extends AppCompatActivity {
    public MainActivity main = new MainActivity();
    public String model,make,licenseNumber,colour,transmission,fuelType,bodyStyle,condition,notes;
    public int year,price,numberDoors,mileage,engineSize;
    public String apiKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vehicles);
        Intent intent = getIntent();
        String choice = intent.getStringExtra("choice");
        apiKey = intent.getStringExtra("apiKey");

        if(choice.equals("0")){
            Toast choiceToast = Toast.makeText(getApplicationContext(),"Please select a vehicle to be edited",Toast.LENGTH_LONG);
            choiceToast.setGravity(0,10,40);
            choiceToast.show();
            getConnectionAsync task = new getConnectionAsync(this);
            task.execute("http://10.0.2.2:8080/list");
        }
        else{
            getConnectionAsync task = new getConnectionAsync(this);
            task.execute("http://10.0.2.2:8080/list");
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        getConnectionAsync task = new getConnectionAsync(this);
        task.execute("http://10.0.2.2:8080/list");
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.vehicleLogo){
            Intent homeIntent = new Intent(ViewVehiclesActivity.this,MainActivity.class);
            startActivity(homeIntent);
        }
        return super.onOptionsItemSelected(item);
    }
    //async task to retrieve vehicle data
    public class getConnectionAsync extends AsyncTask<String,Void,String> {
        private WeakReference<ViewVehiclesActivity> activityWeakReference;
        getConnectionAsync(ViewVehiclesActivity activity){
            activityWeakReference = new WeakReference<ViewVehiclesActivity>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ViewVehiclesActivity activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing()){
                return;
            }


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
            ViewVehiclesActivity activity= activityWeakReference.get();
            if(activity == null || activity.isFinishing()){
                return;
            }
            System.out.println(s);
            displayVehicles(s);
        }
    }//async task end


    public void displayVehicles(String vehicles){
        ListView listVehicles = findViewById(R.id.listViewAllVehicles);
        try {
            // declare a new json array and pass it the string response from the server
            // this will convert the string into a JSON array which we can then iterate
            // over using a loop
            final JSONArray vehicleJsonArray = new JSONArray(vehicles);
            ArrayList<String> vehicleNames = new ArrayList<String>();
            final ArrayList<Vehicles> allVehicles = new ArrayList<>();

            // use a for loop to iterate over the JSON array
            for (int i=0; i < vehicleJsonArray.length(); i++)
            {

                int posNumber = i;

                model = vehicleJsonArray.getJSONObject(i).get("model").toString();
                year = Integer.parseInt(vehicleJsonArray.getJSONObject(i).get("year").toString());
                make = vehicleJsonArray.getJSONObject(i).get("make").toString();
                price = Integer.parseInt(vehicleJsonArray.getJSONObject(i).get("price").toString());
                licenseNumber = vehicleJsonArray.getJSONObject(i).get("licenseNumber").toString();
                colour = vehicleJsonArray.getJSONObject(i).get("colour").toString();
                numberDoors = Integer.parseInt(vehicleJsonArray.getJSONObject(i).get("numberDoors").toString());
                transmission = vehicleJsonArray.getJSONObject(i).get("transmission").toString();
                mileage = Integer.parseInt(vehicleJsonArray.getJSONObject(i).get("mileage").toString());
                fuelType = vehicleJsonArray.getJSONObject(i).get("fuelType").toString();
                engineSize = Integer.parseInt(vehicleJsonArray.getJSONObject(i).get("engineSize").toString());
                bodyStyle = vehicleJsonArray.getJSONObject(i).get("bodyStyle").toString();
                condition = vehicleJsonArray.getJSONObject(i).get("condition").toString();
                notes = vehicleJsonArray.getJSONObject(i).get("notes").toString();
                Vehicles v = new Vehicles(model,year,make,price,licenseNumber,colour,numberDoors,transmission,mileage,fuelType,engineSize,bodyStyle,condition,notes,i);
                String modelLicenseYear = vehicleJsonArray.getJSONObject(i).get("model").toString() + " (" + year + ")" +"\n(" + vehicleJsonArray.getJSONObject(i).get("licenseNumber").toString()+")";
                allVehicles.add(v);
                vehicleNames.add(modelLicenseYear);
            }
            ArrayAdapter vehicleAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,vehicleNames);
            listVehicles.setAdapter(vehicleAdapter);

            listVehicles.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    for(Vehicles v : allVehicles){
                        if(v.getPosNum()==(position)){
                            //System.out.println(v);

                            String moreModel = v.getModel();
                            int moreYear = v.getYear();
                            String moreMake = v.getMake();
                            int morePrice = v.getPrice();
                            String moreLicense = v.getLicenseNumber();
                            String moreColour = v.getColour();
                            int moreDoors = v.getNumberDoors();
                            String moreTransmission = v.getTransmission();
                            int moreMileage = v.getMileage();
                            String moreFuel = v.getFuelType();
                            int moreEngine = v.getEngineSize();
                            String moreBody = v.getBodyStyle();
                            String moreCondition= v.getCondition();
                            String moreNotes = v.getNotes();



                            moreDetails more = new moreDetails();
                           more.showActivity(moreModel,moreYear,moreMake,morePrice,moreLicense,moreColour,moreDoors,moreTransmission,moreMileage,moreFuel,moreEngine,moreBody,moreCondition,moreNotes);

                        }
                    }

                    return false;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public class moreDetails implements Serializable{

        public void showActivity(String model, int year, String make, int price, String licenseNumber, String colour, int numberDoors, String transmission, int mileage, String fuelType, int engineSize, String bodyStyle, String condition, String notes){
            Bundle vExtras  = new Bundle();

            Intent intent = new Intent(ViewVehiclesActivity.this,ViewMoreDetailsActivity.class);

            String convYear,convPrice,convDoors,convMileage,convEngine;

            StringBuilder sbYear = new StringBuilder();
            sbYear.append(year);
            convYear = sbYear.toString();

            StringBuilder sbPrice = new StringBuilder();
            sbPrice.append(price);
            convPrice = sbPrice.toString();

            StringBuilder sbDoors = new StringBuilder();
            sbDoors.append(numberDoors);
            convDoors = sbDoors.toString();

            StringBuilder sbMile = new StringBuilder();
            sbMile.append(mileage);
            convMileage = sbMile.toString();

            StringBuilder sbEngine = new StringBuilder();
            sbEngine.append(engineSize);
            convEngine = sbEngine.toString();



           vExtras.putString("model",model);
           vExtras.putString("year",convYear);
           vExtras.putString("make",make);
           vExtras.putString("price",convPrice);
           vExtras.putString("licenseNumber",licenseNumber);
           vExtras.putString("colour",colour);
           vExtras.putString("numberDoors",convDoors);
           vExtras.putString("transmission",transmission);
           vExtras.putString("mileage",convMileage);
           vExtras.putString("fuelType",fuelType);
           vExtras.putString("engineSize",convEngine);
           vExtras.putString("bodyStyle",bodyStyle);
           vExtras.putString("condition",condition);
           vExtras.putString("notes",notes);
           vExtras.putString("apiKey",apiKey);
           intent.putExtras(vExtras);

            startActivity(intent);
        }

    }



}
