package com.example.onlineshopping;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class adapterCart extends RecyclerView.Adapter<adapterCart.ViewHolder> {
    Context context;
    private List<OrdDetails> OrderList = new ArrayList<>();
    private OnItemClickListener listener;
    private OnItemClickListener2 listener2;
    //interface
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    //interface
    public interface OnItemClickListener2{
        void onItemClick2(int position);
    }
    //method
    public void setOnItemClickListener(OnItemClickListener clickListener){
        listener=clickListener;
    }
    //method
    public void setOnItemClickListener2(OnItemClickListener2 clickListener){
        listener2=clickListener;
    }
    public adapterCart(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //pass itemclick

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false),listener,listener2);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          int id=OrderList.get(position).getProid();
          //int idOrder = OrderList.get(position).getOrdid();
          ProductHelper productHelper=new ProductHelper(context);
        OrderDetailsHelper orderDetailsHelper=new OrderDetailsHelper(context);
         product p=productHelper.getProduct(String.valueOf(id));
          holder.Name.setText(p.getName());
          holder.Price.setText("Price: "+String.valueOf(Integer.parseInt(p.getPrice())*OrderList.get(position).getQuantity()));
          holder.quantity.setText("Quantity: "+String.valueOf(OrderList.get(position).getQuantity()));
          holder.img.setImageBitmap(p.getImage());
//          holder.del.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View v) {
//                  orderDetailsHelper.deletefromCart(String.valueOf(idOrder));
//                  //remove from list
//              }
//          });
    }

    @Override
    public int getItemCount() {
        return OrderList.size();
    }

    public void setList(List<OrdDetails> orderList) {
        this.OrderList = orderList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Name, Price,quantity;
        private ImageView img;
        private ImageView del,edit;

        public ViewHolder(@NonNull View itemView,OnItemClickListener listener,OnItemClickListener2 listener2) {
            super(itemView);
            Name = itemView.findViewById(R.id.cart_product_name);
            Price = itemView.findViewById(R.id.cart_product_price);
            quantity=itemView.findViewById(R.id.cart_product_quantity);
            img=itemView.findViewById(R.id.imgCart);
            del = (ImageView) itemView.findViewById(R.id.remove);
            edit = (ImageView) itemView.findViewById(R.id.Edit);
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener2.onItemClick2((getAdapterPosition()));
                }
            });
        }
    }
}