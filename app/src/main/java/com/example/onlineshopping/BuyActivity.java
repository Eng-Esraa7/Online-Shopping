package com.example.onlineshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

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

        //get from map intent
        name.setText(getIntent().getExtras().getString("name",""));
        phone.setText(getIntent().getExtras().getString("phone",""));
        address.setText(getIntent().getExtras().getString("address",""));

//click on confirm
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nametxt=name.getText().toString();
                String phonetxt=phone.getText().toString();
                String addresstxt=address.getText().toString();
                if(nametxt.equals("")||phonetxt.equals("")||addresstxt.equals("")){
                    Toast.makeText(BuyActivity.this, "Please Enter All Data", Toast.LENGTH_SHORT).show();
                }else{
                    //minus quantity from product
                    product p;
                    p=productHelper.getProduct(String.valueOf(prodId));
                    if(p.getQuantity().equals("0")){
                        Toast.makeText(BuyActivity.this, "Not Available in Stoke", Toast.LENGTH_SHORT).show();
                    }else {
                        //create order
                        Order = new Orders(currentDate, nametxt, phonetxt, addresstxt, ordId,"","");
                        orderHelper.CreateOrder(Order);
                        //
                        int newVal = (Integer.parseInt(p.getQuantity())) - quantityOfOrder;
                        productHelper.UpdateQuantity(String.valueOf(prodId), newVal);
                        int NewvalCnt = Integer.parseInt(p.getCountSale())+quantityOfOrder;
                        productHelper.UpdateCntSale(String.valueOf(prodId), NewvalCnt);
                        //delete from cart
                        orderDetailsHelper.SetFinish(userId, String.valueOf(ordId));
                        //go to review
                        Intent i = new Intent(BuyActivity.this, ReviewActivity.class);
                        i.putExtra("ordDetailsId", ordId);
                        i.putExtra("userid",userId);
                        startActivity(i);
                        finish();
                    }
                }
            }
        });

        //map
    Button btn =(Button) findViewById(R.id.submit);
    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Toast.makeText(BuyActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            Intent i=new Intent(BuyActivity.this,MapsActivity2.class);
            i.putExtra("prodId",prodId);
            i.putExtra("name",name.getText().toString());
            i.putExtra("phone",phone.getText().toString());
            i.putExtra("userId",userId);
            i.putExtra("orderId",ordId);
            i.putExtra("quantityOfOrder",quantityOfOrder);
            startActivity(i);
        }
    });
    }
}