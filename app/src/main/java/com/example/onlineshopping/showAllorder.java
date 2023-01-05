package com.example.onlineshopping;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class showAllorder extends AppCompatActivity {
    private RecyclerView recyclerView;
    List<Orders> specificOrders;
    OrderHelper orderHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_allorder);
        OrderListAdpter orderListAdpter=new OrderListAdpter(showAllorder.this);
        EditText datetxt=(EditText) findViewById(R.id.dateOrder);
        Button search_btn=(Button)findViewById(R.id.button);
        orderHelper=new OrderHelper(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerView);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date=datetxt.getText().toString();
                specificOrders=orderHelper.getOrdersBydate(date);
               // specificOrders=orderHelper.getAllOrders();
                orderListAdpter.setOrders(specificOrders);
                recyclerView.setLayoutManager(new LinearLayoutManager(showAllorder.this));
                recyclerView.setAdapter(orderListAdpter);
            }
        });
    }
}