package com.example.onlineshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    List<OrdDetails> OrderList;
    adapterCart adapter;
    ProductHelper p;
    OrderDetailsHelper orderDetailsHelper;
    Button total;
    private RecyclerView RecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        String userId=getIntent().getExtras().getString("userid");
        OrderList = new ArrayList<>();
        orderDetailsHelper =new OrderDetailsHelper(getApplicationContext());
        OrderList = orderDetailsHelper.getOrderByUser(userId);

        RecyclerView = findViewById(R.id.cart_list_recyclerView);
        adapter = new adapterCart(this);
        adapter.setList(OrderList);
        RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecyclerView.setAdapter(adapter);

        setTotal();

        adapter.setOnItemClickListener(new adapterCart.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                int idOrder = OrderList.get(position).getOrdid();
                OrderList.remove(position);
                setTotal();
                orderDetailsHelper.deletefromCart(String.valueOf(idOrder));
                adapter.notifyItemRemoved(position);
            }
        });

        adapter.setOnItemClickListener2(new adapterCart.OnItemClickListener2() {
            @Override
            public void onItemClick2(int position) {
                Toast.makeText(CartActivity.this, "Edit", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void setTotal(){
        total=(Button) findViewById(R.id.totalPrice);
        total.setText("total price: "+String.valueOf(orderDetailsHelper.totalPrice(OrderList)));
    }
}