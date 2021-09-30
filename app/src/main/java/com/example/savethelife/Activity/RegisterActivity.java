package com.example.savethelife.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.savethelife.R;
import com.example.savethelife.Utils.VolleySingleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText Name,City,Bloodgrp,Pass,mbl;
    private Button subBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Name = findViewById(R.id.name);
        City = findViewById(R.id.city);
        Bloodgrp = findViewById(R.id.blood_group);
        Pass = findViewById(R.id.password);
        mbl = findViewById(R.id.number);
        subBtn = findViewById(R.id.reg_btn);
        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,city,blood_group,password,number;
                name = Name.getText().toString();
                city = City.getText().toString();
                blood_group = Bloodgrp.getText().toString();
                password = Pass.getText().toString();
                number = mbl.getText().toString();
                if(isValid(name,city,blood_group,password,number)){
                    register(name,city,blood_group,password,number);
                }
            }
        });
    }
    private void register(final String name,final String  city,final String blood_group,final String password,final String number){
       StringRequest stringRequest = new StringRequest(Method.POST, EndPoints.register_url, new Listener<String>() {

           @Override
           public void onResponse(String response) {
               if(response.equals("Success")){
                   PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
                           .putString("city", city).apply();
                   Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                   RegisterActivity.this.finish();
               }else{
                   Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
               }
           }
       }, new ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(RegisterActivity.this, "Something went wrong:(", Toast.LENGTH_SHORT).show();
               Log.d("VOLLEY",error.getMessage());
           }
       }){
           protected Map<String,String> getParams() throws AuthFailureError {
               Map<String,String> params = new HashMap<>();
               params.put("name",name);
               params.put("city",city);
               params.put("blood_group",blood_group);
               params.put("password",password);
               params.put("number",number);

               return params;
           }
       };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
        private boolean isValid(String name, String city, String blood_group, String number, String password){
        List<String> valid_blood_group = new ArrayList<>();
        valid_blood_group.add("A+");
        valid_blood_group.add("A-");
        valid_blood_group.add("B+");
        valid_blood_group.add("B-");
        valid_blood_group.add("AB+");
        valid_blood_group.add("AB-");
        valid_blood_group.add("O+");
        if(name.isEmpty()){
            showMessage("Name is empty");
            return false;
        }else if(city.isEmpty()){
            showMessage("City is empty");
            return false;
        }else if(!valid_blood_group.contains(blood_group)){
            showMessage(" Blood group Choose from "+valid_blood_group);
            return false;
        }else if(number.length() != 10){
            showMessage("number is invalid");
            return false;
        }
        return true;

    }
    private void showMessage(String msg){
        Toast.makeText(this, msg , Toast.LENGTH_SHORT).show();
    }
}