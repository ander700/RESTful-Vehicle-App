package com.example.vehicleapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class PostActivity extends AppCompatActivity {
    String URL = "http://10.0.2.2:8080/add_vehicle_json";
    public String model,make,licenseNumber,colour,transmission,fuelType,bodyStyle,condition,notes;
    public String year,price,numberDoors,mileage,engineSize;
    public int conYear,conPrice,conNumberDoors,conMileage,conEngineSize;
    final HashMap<String,String>postValues = new HashMap<>();
    //instantiate editTextInputs
    public EditText vehicleNotes,vehicleCondition,vehicleBodyStyle,vehicleEngineSize,vehicleFuelType,vehicleMileage,vehicleTransmission,vehicleNumberDoors,vehicleColour,vehiclePrice,vehicleLicense,vehicleModel,vehicleYear,vehicleMake;
    public String apiKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Intent intent = getIntent();
        apiKey = intent.getStringExtra("apiKey");
        vehicleNotes = findViewById(R.id.vehicleNotesEdit);
        vehicleCondition = findViewById(R.id.vehicleConditionEdit);
        vehicleBodyStyle = findViewById(R.id.vehicleBodyStyleEdit);
        vehicleEngineSize = findViewById(R.id.vehicleEngineSizeEdit);
         vehicleFuelType = findViewById(R.id.vehicleFuelType);
         vehicleMileage = findViewById(R.id.vehicleMileageEdit);
         vehicleTransmission = findViewById(R.id.vehicleTransmissionEdit);
         vehicleNumberDoors = findViewById(R.id.vehicleNumberDoorsEdit);
         vehicleColour = findViewById(R.id.vehicleColourEdit);
         vehiclePrice = findViewById(R.id.vehiclePriceEdit);
         vehicleLicense = findViewById(R.id.vehicleLicenseEdit);
         vehicleModel = findViewById(R.id.vehicleModelEd);
         vehicleYear = findViewById(R.id.vehicleYearEdit);
         vehicleMake = findViewById(R.id.vehicleMakeEdit);

    }

    public void confirmDecision(View v){

        Button btn = findViewById(R.id.inputPostDataButton);


        if (vehicleNotes.equals("") || vehicleCondition.equals("") || vehicleBodyStyle.equals("") || vehicleEngineSize.equals("") || vehicleFuelType.equals("") || vehicleMileage.equals("")
                || vehicleTransmission.equals("") || vehicleNumberDoors.equals("") || vehicleColour.equals("") || vehiclePrice.equals("") || vehicleLicense.equals("") || vehicleModel.equals("")
                || vehicleYear.equals("") || vehicleMake.equals(""))
        {
            Toast.makeText(PostActivity.this,"All fields must be entered",Toast.LENGTH_SHORT).show();
        }
        else{
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder altdial = new AlertDialog.Builder(PostActivity.this);
                    altdial.setMessage("Are you sure you want to upload this vehicle?").setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    postConfirmed post = new postConfirmed();
                                    post.getPostData();
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
            });
        }







    }
public class postConfirmed {
    public void getPostData() {



        //strings
        model = vehicleModel.getText().toString();
        make = vehicleMake.getText().toString();
        licenseNumber = vehicleLicense.getText().toString();
        colour = vehicleColour.getText().toString();
        transmission = vehicleTransmission.getText().toString();
        fuelType = vehicleFuelType.getText().toString();
        bodyStyle = vehicleBodyStyle.getText().toString();
        condition = vehicleCondition.getText().toString();
        notes = vehicleNotes.getText().toString();
        //ints
        year = (vehicleYear.getText().toString());
        price = (vehiclePrice.getText().toString());
        numberDoors = (vehicleNumberDoors.getText().toString());
        mileage = (vehicleMileage.getText().toString());
        engineSize = (vehicleEngineSize.getText().toString());


        //ints
        conYear = Integer.parseInt(vehicleYear.getText().toString());
        conPrice = Integer.parseInt(vehiclePrice.getText().toString());
        conNumberDoors = Integer.parseInt(vehicleNumberDoors.getText().toString());
        conMileage = Integer.parseInt(vehicleMileage.getText().toString());
        conEngineSize = Integer.parseInt(vehicleEngineSize.getText().toString());

        //postData(model,make,licenseNumber,colour,transmission,fuelType,bodyStyle,condition,notes,year,price,numberDoors,mileage,engineSize,URL);
        asyncPost();
    }
}
    public void asyncPost(){
        postDataAsync post = new postDataAsync();
        post.execute();
    }

    public class postDataAsync extends AsyncTask<Void,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String corApi = null;
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
                    Toast.makeText(PostActivity.this,"Vehicle Saved !",Toast.LENGTH_LONG).show();
                    String line;
                    corApi = "correct";
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    while((line= br.readLine())!=null){
                        response+=line;

                    }
                }
                else{
                    Toast.makeText(PostActivity.this,"Error on saving vehicle ",Toast.LENGTH_LONG).show();
                    response="";
                    corApi = "false";
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            return corApi;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            showCor(s);

        }

    }

    public void showCor(String ApiBool){
        System.out.println(ApiBool);
        if (ApiBool.equals("correct")) {
            Toast.makeText(PostActivity.this,"Vehicle inserted into database",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(PostActivity.this,"API Key was incorrect",Toast.LENGTH_SHORT).show();

        }
    }


    private String getPostDataString(HashMap<String,String>params)throws UnsupportedEncodingException{
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
