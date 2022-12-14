package com.example.onlineshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BuyActivity extends AppCompatActivity {
    TextView name,address,phone,date;
    Button confirm;
    OrderHelper orderHelper;
    Orders Order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        //database order,orderDetails,product
        orderHelper=new OrderHelper(getApplicationContext());
        OrderDetailsHelper orderDetailsHelper=new OrderDetailsHelper(getApplicationContext());
        ProductHelper productHelper=new ProductHelper(getApplicationContext());

        //get orderDetails id, user id,product id,quantityOfOrder
        int ordId=getIntent().getIntExtra("orderId",0);
        String userId=getIntent().getExtras().getString("userId");
        int prodId=getIntent().getIntExtra("prodId",0);
        int quantityOfOrder=getIntent().getIntExtra("quantityOfOrder",0);
        //get text
        name=(TextView) findViewById(R.id.shippment_userName);
        address=(TextView) findViewById(R.id.shippment_userHomeAddress);
        phone=(TextView) findViewById(R.id.shippment_userPhoneNumber);
        date=(TextView) findViewById(R.id.shippment_userdate);

        confirm = (Button)findViewById(R.id.confirm_final_order_btn);
        //current date
        Calendar calendar=Calendar.getInstance();
        String currentDate= DateFormat.getDateInstance().format(calendar.getTime());
        date.setText(currentDate);

        //strings of data
        String nametxt=name.getText().toString();
        String phonetxt=phone.getText().toString();
        String addresstxt=address.getText().toString();
//click on confirm
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nametxt==null||phonetxt==null||addresstxt==null){
                    Toast.makeText(BuyActivity.this, "Please Enter All Data", Toast.LENGTH_SHORT).show();
                }else {
                    //create order
                    Order = new Orders(currentDate, nametxt, phonetxt, addresstxt, ordId);
                    orderHelper.CreateOrder(Order);
                    //minus quantity from product
                    product p=new product();
                    p=productHelper.getProduct(String.valueOf(prodId));
                    int newVal=(Integer.parseInt(p.getQuantity()))-quantityOfOrder;
                    productHelper.UpdateQuantity(String.valueOf(prodId),newVal);
                    //delete from cart
                    orderDetailsHelper.SetFinish(userId, String.valueOf(ordId));
                    //go to cart
                    Intent i=new Intent(BuyActivity.this,CartActivity.class);
                    i.putExtra("userid",userId);
                    startActivity(i);
                }
            }
        });

        //map
    Button btn =(Button) findViewById(R.id.button2);
    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Toast.makeText(BuyActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(BuyActivity.this,MapppActivity.class);
            startActivity(i);
        }
    });
    }
}