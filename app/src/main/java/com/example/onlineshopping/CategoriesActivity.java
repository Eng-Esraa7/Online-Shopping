package com.example.onlineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

            case R.id.home: Intent i = new Intent(CategoriesActivity.this, CategoriesActivity.class);
                i.putExtra("Name", getIntent().getExtras().getString("Name") );
                i.putExtra("userid",getIntent().getExtras().getString("userid"));
                startActivity(i);
                finish();
                return true;
            case R.id.Cart: Intent ii = new Intent(CategoriesActivity.this, CartActivity.class);
                ii.putExtra("userid",getIntent().getExtras().getString("userid"));//sent user id
                startActivity(ii);
                return true;
            case R.id.logout: Intent i2 = new Intent(CategoriesActivity.this, Login.class);
                startActivity(i2);
                finish();
                return true;
        }
        return false;
    }
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

//menu

    }
    public void data(){
        Bitmap[] images ={
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.dress_category),
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.boys),
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.girls),
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.jb2),
        };
        String [] name={
                "Women",
                "Boys Children",
                "Girls Children",
                "Men",
        };
        int [] id={
                1,2,3,4
        };

        for(int i=0;i<4;i++){
            CartList.add(new Category(name[i],images[i]));
        }

    }
    public void storeCategory(){
        try {
            for(int i=0;i<4;i++){
                categoryHelper.CreateData(CartList.get(i));
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage()+"  main", Toast.LENGTH_LONG).show();
        }
    }
};