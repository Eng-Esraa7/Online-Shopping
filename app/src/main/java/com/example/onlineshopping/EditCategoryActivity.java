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

public class EditCategoryActivity extends AppCompatActivity {
    private static final int IMAGE_REQUEST=100;
    private Uri imageFilePath;
    private Bitmap bitmapImg=null;
    ImageView img;
    Button EditCat;
    EditText name;
    Category category;
    CategoryHelper categoryHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);
        name=(EditText) findViewById(R.id.Edit_category_nameAdmin);
        img=(ImageView) findViewById(R.id.Edit_select_category_image);
        EditCat = (Button)findViewById(R.id.edit_new_cat_btn);

        categoryHelper = new CategoryHelper(getApplicationContext());
        //get id category
        String idCategory=getIntent().getExtras().getString("idCategory");
        //get category
        category = categoryHelper.getCategory(idCategory);
        //set fields
        name.setText(category.getName());
        img.setImageBitmap(category.getImg());
        //choose img
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImg(v);
            }
        });
        //click edit
        EditCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("")){
                    Toast.makeText(EditCategoryActivity.this, "Please Enter Name Of Category", Toast.LENGTH_SHORT).show();
                }else {
                    //set name
                    category.setName(name.getText().toString());
                    //set img if updated
                    if (bitmapImg != null) {
                        category.setImg(bitmapImg);
                    }
                    //update category in db
                    categoryHelper.UpdateCategory(idCategory, category.getName(), category.getImg());
                    Intent i = new Intent(EditCategoryActivity.this, AdminCategoriesActivity.class);
                    startActivity(i);
                    finish();
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