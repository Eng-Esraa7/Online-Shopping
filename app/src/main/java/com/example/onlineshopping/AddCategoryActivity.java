package com.example.onlineshopping;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddCategoryActivity extends AppCompatActivity {
    private static final int IMAGE_REQUEST=100;
    private Uri imageFilePath;
    private Bitmap bitmapImg=null;
    ImageView img;
    Button addCat;
    EditText name;
    Category category;
    CategoryHelper categoryHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        name=(EditText) findViewById(R.id.category_nameAdmin);
        img=(ImageView) findViewById(R.id.Edit_select_category_image);
        addCat=(Button)findViewById(R.id.add_new_product_btn);
        categoryHelper=new CategoryHelper(getApplicationContext());
        //choose img
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImg(v);
            }
        });

        //add btn
        addCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmapImg!=null&&!name.getText().toString().equals("")) {
                    category=new Category(name.getText().toString(),bitmapImg);
                    categoryHelper.CreateData(category);
                    Intent i=new Intent(AddCategoryActivity.this,AdminCategoriesActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(AddCategoryActivity.this, "Please Enter All Data!"+category.getImg()+" "+category.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void chooseImg(View img){
        try {
            Intent i=new Intent();
            i.setType(("image/*"));
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(i,IMAGE_REQUEST);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null & data.getData() != null) {
                imageFilePath = data.getData();
                bitmapImg = MediaStore.Images.Media.getBitmap(getContentResolver(), imageFilePath);
                img.setImageBitmap(bitmapImg);
            }
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}