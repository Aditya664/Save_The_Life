package com.example.savethelife.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import android.util.Log;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.android.volley.Response.ErrorListener;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.androidnetworking.common.Method;
import com.example.savethelife.DataModels.RequestDataModel;
import com.example.savethelife.Adapter.RequestAdapter;
import com.example.savethelife.R;
import com.example.savethelife.Utils.EndPoints;
import com.example.savethelife.Utils.VolleySingleton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
private RecyclerView recyclerView;
    private List<RequestDataModel> requestDataModels;
    private RequestAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestDataModels = new ArrayList<>();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView makereqbtn = findViewById(R.id.make_request_button);
        makereqbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MakeRequest.class));
            }
        });


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.search_button){
                    //open new page
                    startActivity(new Intent(MainActivity.this,SearchActivity.class));
                }

                return false;
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        LayoutManager layoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RequestAdapter(requestDataModels,this);
        recyclerView.setAdapter(adapter);
        pop();

    }

  private void pop(){
      final String city = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
              .getString("city", "no_city");
      StringRequest stringRequest = new StringRequest(
              Method.POST, EndPoints.get_requests, new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
              Gson gson = new Gson();
              Type type = new TypeToken<List<RequestDataModel>>() {
              }.getType();
              List<RequestDataModel> dataModels = gson.fromJson(response, type);
              requestDataModels.addAll(dataModels);
              adapter.notifyDataSetChanged();
          }
      }, new ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {
              Toast.makeText(MainActivity.this, "Something went wrong:(", Toast.LENGTH_SHORT).show();
              Log.d("VOLLEY", Objects.requireNonNull(error.getMessage()));
          }
      }) {
          @Override
          protected Map<String, String> getParams() throws AuthFailureError {
              Map<String, String> params = new HashMap<>();
              params.put("city", city);
              return params;
          }
      };
      VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
  }


}