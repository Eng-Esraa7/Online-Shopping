package com.example.onlineshopping;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdpter extends RecyclerView.Adapter<OrderListAdpter.OrderListHolder> {
    private List<Orders> orders=new ArrayList<>();
    Context context;
    public OrderListAdpter(Context context){
        this.context=context;
    }
    @NonNull
    @Override
    public OrderListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.itemorderlist,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListHolder holder, int position) {
       holder.userName.setText(orders.get(position).getName());
        holder.dateOrder.setText(orders.get(position).getOrderDate());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderListHolder extends RecyclerView.ViewHolder {
        TextView userName ,dateOrder;
        public OrderListHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.userName);
            dateOrder=itemView.findViewById(R.id.dateOrder);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(context, informationOrder.class);
                    Orders order = orders.get(position);
                    intent.putExtra("name", order.getName());//sent to infoOrder
                    intent.putExtra("phone", order.getPhone());
                    intent.putExtra("address", order.getAddress());
                    intent.putExtra("feedback", order.getFeedback());
                    intent.putExtra("rate", order.getRating());
                    context.startActivity(intent);
                }
            });
        }
    }
    public void setOrders(List<Orders>orders){
        this.orders=orders;
        notifyDataSetChanged();
    }
}
