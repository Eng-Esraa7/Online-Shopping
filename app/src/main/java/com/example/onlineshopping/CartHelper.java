package com.example.onlineshopping;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CartHelper extends SQLiteOpenHelper {
    private static String name="CartDatabase";
    SQLiteDatabase Database;
    Context context;
    private ByteArrayOutputStream objByteArrayOutputStream;
    //private byte[] ImgInByte;

    public CartHelper(@androidx.annotation.Nullable android.content.Context context) {
        super(context, name, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Orders(ord_Id integer primary key autoincrement,ordate text,addres text,userId integer,FOREIGN key(userId) references User(user_id))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Orders");
        onCreate(db);
    }

    public void OrdersData(Orders o){
        try{
            SQLiteDatabase objSqLiteDatabase = this.getWritableDatabase();
            objByteArrayOutputStream=new ByteArrayOutputStream();
            ContentValues objContentValues = new ContentValues();

            objContentValues.put("addres",o.getAddress());
            objContentValues.put("ordate",o.getOrderDate());


            long check = objSqLiteDatabase.insert("Orders",null,objContentValues);
            if(check!=-1){
                Toast.makeText(context, "added", Toast.LENGTH_LONG).show();
            }else{

            }
            objSqLiteDatabase.close();
        }catch (Exception e){
            // Toast.makeText(context, e.getMessage()+"add", Toast.LENGTH_LONG).show();
        }
    }

    public ArrayList<Orders> getAllOrders(){
        Orders o;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        ArrayList<Orders> orders = new ArrayList<>();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from Orders",null);
        try {
            while (cursor.moveToNext()){
                o=new Orders();
                o.setId(cursor.getInt(0));
                o.setOrderDate(cursor.getString(1));
                o.setAddress(cursor.getString(2));
                o.setUserid(cursor.getInt(4));
                orders.add(o);
            }
        }catch (Exception e){
            Toast.makeText(context, e.getMessage()+"get", Toast.LENGTH_LONG).show();
        }
        return orders;
    }

}