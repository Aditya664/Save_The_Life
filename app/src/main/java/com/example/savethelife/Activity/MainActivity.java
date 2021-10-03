package com.example.savethelife.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.savethelife.DataModels.RequestDataModel;
import com.example.savethelife.Adapter.RequestAdapter;
import com.example.savethelife.R;

import java.util.ArrayList;
import java.util.List;

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
        RequestDataModel requestDataModel = new RequestDataModel("blood ba nk","https://post.medicalnewstoday.com/wp-content/uploads/sites/3/2020/02/320717_1100-1100x628.jpg");
        requestDataModels.add(requestDataModel);
        requestDataModels.add(requestDataModel);
        requestDataModels.add(requestDataModel);
        requestDataModels.add(requestDataModel);
  }

}