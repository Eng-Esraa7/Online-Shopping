package com.example.onlineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdminProductsActivity extends AppCompatActivity {
    private RecyclerView RecyclerView;
    List<product> productList;
    adapter adapter1;
    ProductHelper productHelper;
    ImageView add,back;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

            case R.id.home: Intent i = new Intent(AdminProductsActivity.this, AdminCategoriesActivity.class);
                i.putExtra("Name", getIntent().getExtras().getString("Name") );
                i.putExtra("userid",getIntent().getExtras().getString("userid"));
                startActivity(i);
                finish();
                return true;

            case R.id.logout: Intent i2 = new Intent(AdminProductsActivity.this, Login.class);
                startActivity(i2);
                finish();
                return true;
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_products);

        int id = getIntent().getIntExtra("id",0);//id category
        productList = new ArrayList<>();
        add = (ImageView)findViewById(R.id.Button_Add_products);
        back = (ImageView)findViewById(R.id.Back_categories);

//database pf product
        productHelper = new ProductHelper(getApplicationContext());
//recycle view
        RecyclerView = findViewById(R.id.admin_all_products_list_recyclerView);
        if(productHelper.getAllProduct().size()==0) {//initial data when install
            Toast.makeText(this, "Not Found Products", Toast.LENGTH_SHORT).show();
        }
        productList=productHelper.getProductOfCat(String.valueOf(id));

//adapter
        adapter1 = new adapter(this,"");


        adapter1.setList(productList);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.setAdapter(adapter1);
        Button chart_btn=(Button) findViewById(R.id.toGOchartAdmin);
        chart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AdminProductsActivity.this,chart.class);
                i.putExtra("categoryId",String.valueOf(id));
                startActivity(i);
            }
        });

        //add product btn
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminProductsActivity.this,AddProductActivity.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });

        //back to categories

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminProductsActivity.this,AdminCategoriesActivity.class);
                startActivity(i);
            }
        });
    }

}

