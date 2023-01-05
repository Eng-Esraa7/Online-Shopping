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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdminCategoriesActivity extends AppCompatActivity {
    private RecyclerView RecyclerView;
    List<Category> categories;
    AdminAdapterCategories adapter;
    CategoryHelper categoryHelper;
    Button addCatbtn;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

            case R.id.home: Intent i = new Intent(AdminCategoriesActivity.this, AdminCategoriesActivity.class);
                i.putExtra("Name", getIntent().getExtras().getString("Name") );
                i.putExtra("userid",getIntent().getExtras().getString("userid"));
                startActivity(i);
                finish();
                return true;

            case R.id.logout: Intent i2 = new Intent(AdminCategoriesActivity.this, Login.class);
                startActivity(i2);
                finish();
                return true;
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_categories);
        addCatbtn=(Button)findViewById(R.id.Add_cat_admin);
        categories = new ArrayList<>();
//database categry
        categoryHelper = new CategoryHelper(getApplicationContext());
//recycle view of category
        RecyclerView = findViewById(R.id.admin_all_categories_list_recyclerView);
        //put date in create app first time
        if(categoryHelper.getAllCategory().size()==0) {
            Toast.makeText(AdminCategoriesActivity.this,"Not Founnd Catetgory",Toast.LENGTH_SHORT).show();
        }
        categories=categoryHelper.getAllCategory();

//adapter
        adapter = new AdminAdapterCategories(this);
        adapter.setList(categories);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.setAdapter(adapter);

        //add category
        addCatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AdminCategoriesActivity.this,AddCategoryActivity.class);
                startActivity(i);
            }
        });

        //delete category
        adapter.setOnItemClickListener(new adapterCart.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int idcategory = categories.get(position).getId();
                //remove from list & adapter
                categories.remove(position);
                //delete from database cart
                categoryHelper.deletefromCategory(String.valueOf(idcategory));
                //notify change in adapter
                adapter.notifyItemRemoved(position);
            }
        });

        //click on edit item
        adapter.setOnItemClickListener2(new adapterCart.OnItemClickListener2() {
            @Override
            public void onItemClick2(int position) {
                int idCategory = categories.get(position).getId();
                String name = categories.get(position).getName();
                Bitmap img = categories.get(position).getImg();

                //go to Edit categories
                Intent i=new Intent(AdminCategoriesActivity.this,EditCategoryActivity.class);
                i.putExtra("idCategory",String.valueOf(idCategory));
                startActivity(i);
            }
        });
        Button showOrder=(Button) findViewById(R.id.showOrder);
        showOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AdminCategoriesActivity.this,showAllorder.class);
                startActivity(i);
            }
        });
    }
};