package com.example.onlineshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class home extends AppCompatActivity {
    OrderDetailsHelper order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        order=new OrderDetailsHelper(getApplicationContext());

        int id=getIntent().getIntExtra("id",1);//id product
        String userid=getIntent().getExtras().getString("userid");

        ProductHelper productHelper=new ProductHelper(getApplicationContext());
        TextView Name = (TextView) findViewById(R.id.NameDesc);
        TextView price = (TextView) findViewById(R.id.priceDesc);
        TextView desc = (TextView) findViewById(R.id.Desc);
        ImageView imageView = (ImageView)findViewById(R.id.imgDesc);
        Button addToCart = (Button)findViewById(R.id.totalPrice);
        Button plus = (Button)findViewById(R.id.p_btn);
        Button minus = (Button)findViewById(R.id.m_btn);
        TextView textNum = (TextView)findViewById(R.id.quantity);

        product p = productHelper.getProduct(String.valueOf(id));
        Name.setText(p.getName());
        price.setText(p.getPrice());
        desc.setText(p.getDescription());
        imageView.setImageBitmap(p.getImage());
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(textNum.getText().toString());
                if(num+1<=Integer.parseInt(p.getQuantity())){
                    textNum.setText(String.valueOf(num+1));
                }
            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(textNum.getText().toString());
                if(num-1>0){
                    textNum.setText(String.valueOf(num-1));
                }
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantity=textNum.getText().toString();
                String userid=getIntent().getExtras().getString("userid");
                OrdDetails o=new OrdDetails(id,Integer.parseInt(quantity),userid);
                order.CreateOrder(o);

                Intent i=new Intent(getApplicationContext(),CartActivity.class);
                i.putExtra("userid",userid);
                startActivity(i);
            }
        });

    }
}