package com.example.onlineshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

public class informationOrder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_order);
        String Name=getIntent().getExtras().getString("name");
        String Phone=getIntent().getExtras().getString("phone");
        String Address=getIntent().getExtras().getString("address");
        String Feedback=getIntent().getExtras().getString("feedback");
        String Rate=getIntent().getExtras().getString("rate");
        TextView name=(TextView) findViewById(R.id.customerName);
        name.setText(Name);
        TextView phone=(TextView) findViewById(R.id.phone);
        phone.setText(Phone);
        TextView address=(TextView) findViewById(R.id.address);
        address.setText(Address);
        TextView feedBack=(TextView) findViewById(R.id.feedback);
        feedBack.setText(Feedback);
        RatingBar rate=(RatingBar) findViewById(R.id.ratingBar);
        rate.setRating(Float.parseFloat(Rate));
    }
}