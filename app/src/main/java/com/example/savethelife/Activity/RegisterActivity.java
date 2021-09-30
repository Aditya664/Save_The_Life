package com.example.savethelife.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.savethelife.R;

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
                showMsg(name+"\n"+city+"\n"+blood_group+"\n"+password+"\n"+number+"\n");
            }
        });
    }
    private void showMsg(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }
}