package com.example.onlineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    //Cart to user
    List<OrdDetails> OrderList;
    adapterCart adapter;
    OrderDetailsHelper orderDetailsHelper;
    Button total;
    private RecyclerView RecyclerView;
    ImageView back;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){

            case R.id.home: Intent i = new Intent(CartActivity.this, CategoriesActivity.class);
                i.putExtra("Name", getIntent().getExtras().getString("Name") );
                i.putExtra("userid",getIntent().getExtras().getString("userid"));
                startActivity(i);
                finish();
                return true;
            case R.id.Cart: Intent ii = new Intent(CartActivity.this, CartActivity.class);
                ii.putExtra("userid",getIntent().getExtras().getString("userid"));//sent user id
                startActivity(ii);
                return true;
            case R.id.logout: Intent i2 = new Intent(CartActivity.this, Login.class);
                startActivity(i2);
                finish();
                return true;
        }
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        String userId=getIntent().getExtras().getString("userid");//get user id
        //create list of order in cart
        OrderList = new ArrayList<>();

        back = (ImageView)findViewById(R.id.Back_p);
        //object of database to cart
        orderDetailsHelper =new OrderDetailsHelper(getApplicationContext());
        //get products in cart specific user not finish(buy)
        OrderList = orderDetailsHelper.getOrderByUser(userId,String.valueOf(0));
        //set RecyclerView
        RecyclerView = findViewById(R.id.cart_list_recyclerView);
        adapter = new adapterCart(this);
        adapter.setList(OrderList);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.setAdapter(adapter);

        //calc total in cart
        setTotal();

        //delete item from cart
        adapter.setOnItemClickListener(new adapterCart.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int idOrder = OrderList.get(position).getOrdid();
                //remove from list & adapter
                OrderList.remove(position);
                //calc total again
                setTotal();
                //delete from database cart
                orderDetailsHelper.deletefromCart(String.valueOf(idOrder));
                //notify change in adapter
                adapter.notifyItemRemoved(position);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CartActivity.this,ProductsActivity.class);
                startActivity(i);
            }
        });

        //click on edit item
        adapter.setOnItemClickListener2(new adapterCart.OnItemClickListener2() {
            @Override
            public void onItemClick2(int position) {
                //Toast.makeText(CartActivity.this, "Edit", Toast.LENGTH_SHORT).show();
                //get orderid & productid
                int idOrder = OrderList.get(position).getOrdid();
                int prodId=OrderList.get(position).getProid();
                //delete from cart
                orderDetailsHelper.deletefromCart(String.valueOf(idOrder));
                //go to desc of product
                Intent i=new Intent(CartActivity.this,home.class);
                i.putExtra("id",prodId);
                i.putExtra("userid",userId);
                startActivity(i);

            }
        });

    }
    //calc total of products in cart
    public void setTotal(){
        total=(Button) findViewById(R.id.totalPrice);
        total.setText("total price: "+String.valueOf(orderDetailsHelper.totalPrice(OrderList)));
    }

}