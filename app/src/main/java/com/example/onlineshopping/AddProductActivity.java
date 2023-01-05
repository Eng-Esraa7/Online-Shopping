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

public class AddProductActivity extends AppCompatActivity {
    ProductHelper productHelper;
    product p;
    private static final int IMAGE_REQUEST=100;
    private Uri imageFilePath;
    private Bitmap bitmapImg=null;
    ImageView img;
    Button addProduct;
    EditText name,desc,price,quantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        int idCategoey = getIntent().getIntExtra("id",0);
        productHelper=new ProductHelper(getApplicationContext());

        name=(EditText) findViewById(R.id.product_name);
        desc=(EditText) findViewById(R.id.product_description);
        price=(EditText) findViewById(R.id.product_price);
        quantity=(EditText) findViewById(R.id.product_Quantity);

        img=(ImageView) findViewById(R.id.select_product_image);
        addProduct=(Button)findViewById(R.id.add_new_product_btn);
        //choose img
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImg(v);
            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmapImg!=null&&!name.getText().toString().equals("")&&!desc.getText().toString().equals("")&&
                        !price.getText().toString().equals("")&&!quantity.getText().toString().equals("")){
                    p=new product(name.getText().toString(),price.getText().toString(),
                            quantity.getText().toString(),bitmapImg,idCategoey,desc.getText().toString());
                    productHelper.CreateData(p);
                    Intent i=new Intent(AddProductActivity.this,AdminProductsActivity.class);
                    i.putExtra("id",idCategoey);
                    startActivity(i);
                    finish();
                }else{
                    Toast.makeText(AddProductActivity.this, "Please Enter All Data!", Toast.LENGTH_SHORT).show();
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