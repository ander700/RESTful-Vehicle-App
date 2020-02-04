package com.example.vehicleapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class LoginActivity extends AppCompatActivity {
    String URL = "http://10.0.2.2:8080/processLogin";
    private EditText userNameEdit;
    private EditText passwordEdit;
    private EditText apiEdit;
    public String userName = null;
    public String password = null;
    public String apiKey = null;
    final HashMap<String,String>loginPostValues = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userNameEdit = findViewById(R.id.userNameEdit);
        passwordEdit = findViewById(R.id.passwordEdit);
        apiEdit = findViewById(R.id.apiKeyEdit);
    }


    public void loginStart(View v){
        loginAsync login = new loginAsync();
        login.execute();
    }

    public class loginAsync extends AsyncTask<Void,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            userName = userNameEdit.getText().toString();
            password = passwordEdit.getText().toString();
            apiKey = apiEdit.getText().toString();
            loginPostValues.put("userName",userName);
            loginPostValues.put("password",password);
            loginPostValues.put("apiKey",apiKey);
            String loginSuc = null;
            URL url;
            try{
                url = new URL(URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writ = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                writ.write(getPostDataString(loginPostValues));
                writ.flush();
                writ.close();
                os.close();
                int responseCode = conn.getResponseCode();
                System.out.println("login response code" + responseCode);
                if (responseCode == HttpsURLConnection.HTTP_OK){
                    loginSuc = "success";
                }
                else{
                    loginSuc = "incorrect";
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }


            return loginSuc;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println(s + "loginSuc");
            loginTrue(s);

        }

        public void loginTrue(String login){
            if(login.equals("success")){
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("apiKey",apiKey);
                startActivity(intent);
            }
            else{
                Toast.makeText(LoginActivity.this,"Login details are incorrect",Toast.LENGTH_LONG).show();
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

}
