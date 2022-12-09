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

public class adapterCat extends RecyclerView.Adapter<adapterCat.ViewHolder> {
    Context context;
    String userid;
    private List<Category> CatList = new ArrayList<>();

    public adapterCat(Context context,String userid)
    {
        this.userid=userid;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_list,parent,false);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.Name_cat);
            img = itemView.findViewById(R.id.imgCat);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent = new Intent(context, GirlsActivity.class);
                    Category item = CatList.get(position);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("userid",userid);
                    context.startActivity(intent);
                }
            });
        }

    }
}