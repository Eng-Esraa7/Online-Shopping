package com.example.onlineshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
public class GirlsActivity extends AppCompatActivity {
    private RecyclerView RecyclerView;
    List<product> productList;
    adapter adapter1;
    ProductHelper productHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girls);
        int id = getIntent().getIntExtra("id",1);
        String userid=getIntent().getExtras().getString("userid");
        productList = new ArrayList<>();
//database
        productHelper = new ProductHelper(getApplicationContext());
//recycle view
        RecyclerView = findViewById(R.id.resycleView1);
        if(productHelper.getAllProduct().size()==0) {
            data();
            storeProduct();
        }
        productList=productHelper.getProductOfCat(String.valueOf(id));

//adapter
        adapter1 = new adapter(this,userid);


        adapter1.setList(productList);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.setAdapter(adapter1);
    }
        public void data(){
        Bitmap[] images ={
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.gd2),
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.gd3),
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.gd1),
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.bch1),
                BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.bch3),
        };
            String [] name={
                    "Dress Black",
                    "Dress Green",
                    "Dress Black",
                    "spyderman",
                    "boy child"
            };

            String [] price={
                    "30000",
                    "20000",
                    "15000",
                    "1555",
                    "545",
            };
            int [] catId={
                    1,1,1,3,3,3
            };
            String [] quantity={
                    "10",
                    "100",
                    "20",
                    "50",
                    "60",
            };
            String [] desc={
                    "this is dress black very nice",
                    "this is dress green very nice",
                    "this is dress black very nice",
                    "this is boy spiderman",
                    "this is children clothes",
            };

            for(int i=0;i<5;i++){
                productList.add(new product(name[i],price[i],quantity[i],images[i],catId[i],desc[i]));
            }

        }

    public void storeProduct(){
        try {
            for(int i=0;i<5;i++){
                productHelper.CreateData(productList.get(i));
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    }

