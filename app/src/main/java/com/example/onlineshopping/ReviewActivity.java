package com.example.onlineshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ReviewActivity extends AppCompatActivity {

    RatingBar rate;
    EditText feedback;
    Button submit;
    OrderHelper orderHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        rate = (RatingBar) findViewById(R.id.ratingBar);
        feedback = (EditText) findViewById(R.id.Feedback);
        submit = (Button) findViewById(R.id.submit);
        orderHelper = new OrderHelper(this);

        int ordId = getIntent().getIntExtra("ordDetailsId", 0);
        String UserId = getIntent().getExtras().getString("userid");

        //get order
        Orders order = orderHelper.getOrderByOrderDetails(String.valueOf(ordId));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(rate.getRating()).equals("0.0")) {
                    Toast.makeText(ReviewActivity.this, "please Rating Order!", Toast.LENGTH_SHORT).show();
                } else {
                    order.setFeedback(feedback.getText().toString());
                    order.setRating(String.valueOf(rate.getRating()));
                    //update in db
                    orderHelper.UpdateOrderAfterReview(order);
                    Intent i = new Intent(ReviewActivity.this, CartActivity.class);
                    i.putExtra("userid", UserId);
                    startActivity(i);
                    finish();
                }
            }
        });
    }

}