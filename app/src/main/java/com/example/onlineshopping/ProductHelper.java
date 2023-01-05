package com.example.onlineshopping;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ProductHelper extends android.database.sqlite.SQLiteOpenHelper{
    private static String name="ProductDatabase";
    Context context;
    private ByteArrayOutputStream objByteArrayOutputStream;
    private byte[] ImgInByte;
    public ProductHelper(Context context) {
        super(context, name, null, 1);
        this.context = context;
    }

     @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {
         db.execSQL("create table Product(prouductId integer primary key autoincrement,image Blob,name text,price float,quantity integer,descriotion String,countSaled integer,cat_Id integer,FOREIGN key(cat_Id) references Category(cat_Id))");
        }
        @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Product");
        onCreate(db);
    }

    public void CreateData(product p){
        try{
            SQLiteDatabase objSqLiteDatabase = this.getWritableDatabase();
            Bitmap imagetoStore=p.getImage();

            objByteArrayOutputStream=new ByteArrayOutputStream();
            imagetoStore.compress(Bitmap.CompressFormat.JPEG,100,objByteArrayOutputStream);

            ImgInByte=objByteArrayOutputStream.toByteArray();
            ContentValues objContentValues = new ContentValues();

            objContentValues.put("image",ImgInByte);
            objContentValues.put("name",p.getName());
            objContentValues.put("price",p.getPrice());
            objContentValues.put("quantity",p.getQuantity());
            objContentValues.put("cat_Id",p.getCatId());
            objContentValues.put("descriotion",p.getDescription());
            objContentValues.put("countSaled",p.getCountSale());

            long check = objSqLiteDatabase.insert("Product",null,objContentValues);
            if(check!=-1){
                //Toast.makeText(context, "added", Toast.LENGTH_SHORT).show();
            }else{

            }
            objSqLiteDatabase.close();
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<product> getAllProduct(){
        product p;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        ArrayList<product> products = new ArrayList<>();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from Product",null);
        try {

            while (cursor.moveToNext()){
                p=new product();
                p.setId(cursor.getInt(0));
                byte[] img = cursor.getBlob(1);
                p.setImage(BitmapFactory.decodeByteArray(img,0,img.length));
                p.setName(cursor.getString(2));
                p.setPrice(cursor.getString(3));
                p.setQuantity(cursor.getString(4));
                p.setDescription(cursor.getString(5));
                p.setCountSale(cursor.getString(6));
                p.setCatId(cursor.getInt(7));
                products.add(p);
            }
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return products;
    }
//get Product Of specific Category
    public ArrayList<product> getProductOfCat(String id){
        product p;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        ArrayList<product> products = new ArrayList<>();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from Product where cat_Id like ?",new String[]{id});
        try {

            while (cursor.moveToNext()){
                p=new product();
                p.setId(cursor.getInt(0));
                byte[] img = cursor.getBlob(1);
                p.setImage(BitmapFactory.decodeByteArray(img,0,img.length));
                p.setName(cursor.getString(2));
                p.setPrice(cursor.getString(3));
                p.setQuantity(cursor.getString(4));
                p.setDescription(cursor.getString(5));
                p.setCountSale(cursor.getString(6));
                p.setCatId(cursor.getInt(7));
                products.add(p);
            }
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return products;
    }

    public product getProduct(String id){
        product p=null;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from Product where prouductId like ?",new String[]{id});
        try {
            while (cursor.moveToNext()){
                p=new product();
                p.setId(cursor.getInt(0));
                byte[] img = cursor.getBlob(1);
                p.setImage(BitmapFactory.decodeByteArray(img,0,img.length));
                p.setName(cursor.getString(2));
                p.setPrice(cursor.getString(3));
                p.setQuantity(cursor.getString(4));
                p.setDescription(cursor.getString(5));
                p.setCountSale(cursor.getString(6));
                p.setCatId(cursor.getInt(7));
            }
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return p;
    }
    public ArrayList<product> getAllProducOfCategorytHasQuantity(String idCategory){
        product p;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        ArrayList<product> products = new ArrayList<>();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from Product where cat_Id =? and quantity!=?",new String[]{idCategory,"0"});
        try {

            while (cursor.moveToNext()){
                p=new product();
                p.setId(cursor.getInt(0));
                byte[] img = cursor.getBlob(1);
                p.setImage(BitmapFactory.decodeByteArray(img,0,img.length));
                p.setName(cursor.getString(2));
                p.setPrice(cursor.getString(3));
                p.setQuantity(cursor.getString(4));
                p.setDescription(cursor.getString(5));
                p.setCountSale(cursor.getString(6));
                p.setCatId(cursor.getInt(7));
                products.add(p);
            }
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return products;
    }


    public void UpdateQuantity(String id,int newVal) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("quantity",newVal);
        sqLiteDatabase.update("Product",row,"prouductId=?",new String[]{id});
        sqLiteDatabase.close();
    }

    public void UpdateCntSale(String id,int newCntSale) {
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("countSaled",newCntSale);
        sqLiteDatabase.update("Product",row,"prouductId=?",new String[]{id});
        sqLiteDatabase.close();
    }

    public void deleteProduct(String Id){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.delete("Product","prouductId like ?",new String[]{Id});
        sqLiteDatabase.close();
    }

    public void UpdateProduct(product p){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues c = new ContentValues();

        Bitmap imagetoStore=p.getImage();
        objByteArrayOutputStream=new ByteArrayOutputStream();
        imagetoStore.compress(Bitmap.CompressFormat.JPEG,100,objByteArrayOutputStream);
        ImgInByte=objByteArrayOutputStream.toByteArray();
        c.put("name",p.getName());
        c.put("descriotion",p.getDescription());
        c.put("price",p.getPrice());
        c.put("quantity",p.getQuantity());
        c.put("image",ImgInByte);

        sqLiteDatabase.update("Product", c, "prouductId like ?", new String[]{String.valueOf(p.getId())});
        sqLiteDatabase.close();
    }

}
