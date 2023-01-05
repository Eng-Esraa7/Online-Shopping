package com.example.onlineshopping;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class adapterCart extends RecyclerView.Adapter<adapterCart.ViewHolder> {
    //adapter of display cart
    Context context;
    private List<OrdDetails> OrderList = new ArrayList<>();
    private OnItemClickListener listener;
    private OnItemClickListener2 listener2;
    //interface delete
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    //interface edit
    public interface OnItemClickListener2{
        void onItemClick2(int position);
    }
    //method delete
    public void setOnItemClickListener(OnItemClickListener clickListener){
        listener=clickListener;
    }
    //method edit
    public void setOnItemClickListener2(OnItemClickListener2 clickListener){
        listener2=clickListener;
    }
    //set context
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
        //get product id
          int id=OrderList.get(position).getProid();
          //object of database product
          ProductHelper productHelper=new ProductHelper(context);
          //object of database OrderDetails
          OrderDetailsHelper orderDetailsHelper=new OrderDetailsHelper(context);
          //get product has product id
          product p=productHelper.getProduct(String.valueOf(id));
          holder.Name.setText(p.getName());
          holder.Price.setText("Price: "+String.valueOf(Integer.parseInt(p.getPrice())*OrderList.get(position).getQuantity()));
          holder.quantity.setText("Quantity: "+String.valueOf(OrderList.get(position).getQuantity()));
          holder.img.setImageBitmap(p.getImage());
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
        Button buy;

        public ViewHolder(@NonNull View itemView,OnItemClickListener listener,OnItemClickListener2 listener2) {
            super(itemView);
            Name = itemView.findViewById(R.id.Name_cat_admin);
            Price = itemView.findViewById(R.id.cart_product_price);
            quantity=itemView.findViewById(R.id.cart_product_quantity);
            img=itemView.findViewById(R.id.imgCatAdmin);

            del = (ImageView) itemView.findViewById(R.id.delAdmin);
            edit = (ImageView) itemView.findViewById(R.id.EditAdmin);
            buy = (Button) itemView.findViewById(R.id.Buy);
            //click on buy
            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context,BuyActivity.class);
                    //sent order id , userid, product id,quantity of order
                    i.putExtra("orderId",OrderList.get(getAdapterPosition()).getOrdid());
                    i.putExtra("userId",OrderList.get(getAdapterPosition()).getUserId());
                    i.putExtra("prodId",OrderList.get(getAdapterPosition()).getProid());
                    i.putExtra("quantityOfOrder",OrderList.get(getAdapterPosition()).getQuantity());
                    context.startActivity(i);
                }
            });
            //click on delete
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getAdapterPosition());
                }
            });
            //click on edit
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener2.onItemClick2((getAdapterPosition()));
                }
            });
        }
    }
}