package com.example.onlineshopping;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdminAdapterCategories extends RecyclerView.Adapter<AdminAdapterCategories.ViewHolder> {
    //adapter of category
    Context context;
    private List<Category> CatList = new ArrayList<>();

    public AdminAdapterCategories(Context context)
    {
        this.context = context;
    }
    private adapterCart.OnItemClickListener listener;
    private adapterCart.OnItemClickListener2 listener2;
    //interface delete
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    //interface edit
    public interface OnItemClickListener2{
        void onItemClick2(int position);
    }
    //method delete
    public void setOnItemClickListener(adapterCart.OnItemClickListener clickListener){
        listener=clickListener;
    }
    //method edit
    public void setOnItemClickListener2(adapterCart.OnItemClickListener2 clickListener){
        listener2=clickListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Name.setText(CatList.get(position).getName());
        holder.img.setImageBitmap(CatList.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return CatList.size();
    }

    public void setList(List<Category> CatList) {
        this.CatList = CatList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Name;
        private ImageView img;
        private ImageView del,edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.Name_cat_admin);
            img = itemView.findViewById(R.id.imgCatAdmin);
            del = (ImageView) itemView.findViewById(R.id.delAdmin);
            edit = (ImageView) itemView.findViewById(R.id.EditAdmin);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(context, AdminProductsActivity.class);
                    Category item = CatList.get(position);
                    intent.putExtra("id", item.getId());//sent to products(category id)
                    context.startActivity(intent);
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
