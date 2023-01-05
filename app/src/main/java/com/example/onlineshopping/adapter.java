package com.example.onlineshopping;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.ProductsViewHolder> {
    Context context;
    String userid;
    private List<product> productList = new ArrayList<>();
//Adapter of display product
    public adapter(Context context,String userid) {//get user id to sent in details activity
        this.userid=userid;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        holder.Name.setText(productList.get(position).getName());
        holder.Price.setText("price: "+productList.get(position).getPrice());
        holder.img.setImageBitmap(productList.get(position).getImage());
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }

    public List<product> setList(List<product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
        return productList;
    }
    public void setfilterproducts(ArrayList<product> productList) {
        this.productList = productList;
        notifyDataSetChanged();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {
        private TextView Name, Price;
        private ImageView img;
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.Name_cat);
            Price = itemView.findViewById(R.id.price_product);
            img=itemView.findViewById(R.id.imgCat);
            //admin
            if (userid.equals("")) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        product item = productList.get(position);
                        Intent intent = new Intent(context, AdminMaintainProductsActivity.class);
                        intent.putExtra("idProduct", item.getId());//sent to desc product id
                        context.startActivity(intent);
                    }
                });
            }else {//user
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        product item = productList.get(position);
                        Intent intent = new Intent(context, home.class);//desc
                        intent.putExtra("id", item.getId());//sent to desc product id
                        intent.putExtra("userid", userid);//sent to desc user id
                        context.startActivity(intent);
                    }
                });
            }
        }
    }
}