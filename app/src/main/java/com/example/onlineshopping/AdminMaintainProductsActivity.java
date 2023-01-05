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

public class AdminMaintainProductsActivity extends AppCompatActivity {

    private static final int IMAGE_REQUEST=100;
    private Uri imageFilePath;
    private Bitmap bitmapImg=null;
    ImageView img;
    Button Edit,del;
    EditText name,desc,price,quantity;
    product p;
    ProductHelper productHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_maintain_products);
        name=(EditText) findViewById(R.id.product_name_maintain);
        desc=(EditText) findViewById(R.id.product_description_maintain);
        price=(EditText) findViewById(R.id.product_price_maintain);
        quantity=(EditText) findViewById(R.id.product_quantity_maintain);
        img=(ImageView) findViewById(R.id.product_image_maintain);
        Edit= (Button)findViewById(R.id.apply_changes_btn);
        del= (Button)findViewById(R.id.delete_product_btn);
        productHelper = new ProductHelper(getApplicationContext());
        p=new product();
        //get id product
        int id=getIntent().getIntExtra("idProduct",0);
        String idProduct = String.valueOf(id);
        //get product
        //Toast.makeText(this, idProduct, Toast.LENGTH_SHORT).show();
        p = productHelper.getProduct(idProduct);
        //set fields
        name.setText(p.getName());
        desc.setText(p.getDescription());
        price.setText(p.getPrice());
        quantity.setText(p.getQuantity());
        img.setImageBitmap(p.getImage());
        //choose img
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImg(v);
            }
        });

        //click edit
        Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("")||desc.getText().toString().equals("")||
                        price.getText().toString().equals("")||quantity.getText().toString().equals("")){
                    Toast.makeText(AdminMaintainProductsActivity.this, "please Enter All Data!", Toast.LENGTH_SHORT).show();

                }else{
                //set data
                p.setName(name.getText().toString());
                p.setDescription(desc.getText().toString());
                p.setPrice(price.getText().toString());
                p.setQuantity(quantity.getText().toString());
                //set img if updated
                if(bitmapImg!=null) {
                    p.setImage(bitmapImg);
                }
                //update category in db
                productHelper.UpdateProduct(p);
                Intent i=new Intent(AdminMaintainProductsActivity.this,AdminProductsActivity.class);
                i.putExtra("id",p.getCatId());//send category id
                startActivity(i);
                    finish();
            }}
        });

        //click delete
        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //update category in db
                productHelper.deleteProduct(String.valueOf(p.getId()));
                Intent i=new Intent(AdminMaintainProductsActivity.this,AdminProductsActivity.class);
                i.putExtra("id",p.getCatId());
                startActivity(i);
                finish();
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