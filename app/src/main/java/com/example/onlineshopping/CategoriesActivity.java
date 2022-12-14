package com.example.onlineshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {
    private androidx.recyclerview.widget.RecyclerView RecyclerView;
    List<Category> CartList;
    adapterCat adapter;
    CategoryHelper categoryHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        //get username
        String Name = getIntent().getExtras().getString("Name");
        //get userid (email)
        String userid = getIntent().getExtras().getString("userid");
        TextView view = (TextView)findViewById(R.id.textView4);
        //set text with name of user
        view.setText("Hi "+Name);
        CartList = new ArrayList<>();
//database categry
        categoryHelper = new CategoryHelper(getApplicationContext());
//recycle view of category
        RecyclerView = findViewById(R.id.recycleView2);
        //put date in create app first time
        if(categoryHelper.getAllCategory().size()==0) {
            data();
            storeCategory();
        }
        CartList=categoryHelper.getAllCategory();

//adapter
        adapter = new adapterCat(this,userid);
        adapter.setList(CartList);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.setAdapter(adapter);


    }
    public void data(){
        Bitmap[] images ={
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.g1),
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.p1),
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.ch1),
        };
        String [] name={
                "Girls",
                "Boys",
                "Children",
        };
        int [] id={
                1,2,3
        };

        for(int i=0;i<3;i++){
            CartList.add(new Category(name[i],images[i]));
        }

    }
    public void storeCategory(){
        try {
            for(int i=0;i<3;i++){
                categoryHelper.CreateData(CartList.get(i));
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage()+"  main", Toast.LENGTH_LONG).show();
        }
    }
};