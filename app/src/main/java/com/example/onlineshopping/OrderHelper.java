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

public class OrderHelper extends SQLiteOpenHelper {
    private static String name="CartDatabase";
    Context context;

    public OrderHelper(@androidx.annotation.Nullable android.content.Context context) {
        super(context, name, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Orders(ordId integer primary key autoincrement,name text,phone integer,address text,orderDate text,ordDetailsId integer,feedback text,rate text,FOREIGN key(ordDetailsId) references OrderDetails(ordId))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Orders");
        onCreate(db);
    }

    public void CreateOrder(Orders o){
        try{
            SQLiteDatabase objSqLiteDatabase = this.getWritableDatabase();
            ContentValues objContentValues = new ContentValues();

            objContentValues.put("name",o.getName());
            objContentValues.put("phone",o.getPhone());
            objContentValues.put("address",o.getAddress());
            objContentValues.put("orderDate",o.getOrderDate());
            objContentValues.put("ordDetailsId",o.getOrdDetailsId());
            objContentValues.put("feedback",o.getFeedback());
            objContentValues.put("rate",o.getRating());

            //Toast.makeText(context, o.getName()+" "+o.getPhone()+" "+o.getAddress()+" "+o.getOrderDate()+" "+String.valueOf(o.getOrdDetailsId()), Toast.LENGTH_SHORT).show();
            long check = objSqLiteDatabase.insert("Orders",null,objContentValues);
            if(check!=-1){
                Toast.makeText(context, "Confirmed", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(context, "not added", Toast.LENGTH_LONG).show();
            }
            objSqLiteDatabase.close();
           }catch (Exception e){
            Toast.makeText(context, e.getMessage()+"not add", Toast.LENGTH_LONG).show();
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
                o.setName(cursor.getString(1));
                o.setPhone(cursor.getString(2));
                o.setAddress(cursor.getString(3));
                o.setOrderDate(cursor.getString(4));
                o.setordDetailsId(cursor.getInt(5));
                o.setFeedback(cursor.getString(6));
                o.setRating(cursor.getString(7));
                orders.add(o);
            }
        }catch (Exception e){
            Toast.makeText(context, e.getMessage()+"get", Toast.LENGTH_LONG).show();
        }
        return orders;
    }

    public Orders getOrderByOrderDetails(String idOrderDetails){
        Orders o=null;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from Orders where ordId like ?",new String[]{idOrderDetails});
        try {
            while (cursor.moveToNext()){
                o=new Orders();
                o.setId(cursor.getInt(0));
                o.setName(cursor.getString(1));
                o.setPhone(cursor.getString(2));
                o.setAddress(cursor.getString(3));
                o.setOrderDate(cursor.getString(4));
                o.setordDetailsId(cursor.getInt(5));
                o.setFeedback(cursor.getString(6));
                o.setRating(cursor.getString(7));
            }
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return o;
    }

    public void UpdateOrderAfterReview(Orders o){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues c = new ContentValues();

        c.put("feedback",o.getFeedback());
        c.put("rate",o.getRating());

        sqLiteDatabase.update("Orders", c, "ordId like ?", new String[]{String.valueOf(o.getId())});
        sqLiteDatabase.close();
    }
    //to get allOrder by sepcific date
    public ArrayList<Orders> getOrdersBydate(String date){
        Orders o;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        ArrayList<Orders>orders= new ArrayList<>();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from Orders where orderDate like ?",new String[]{date});
        try {

            while (cursor.moveToNext()){
                o=new Orders();
                o.setId(cursor.getInt(0));
                o.setName(cursor.getString(1));
                o.setPhone(cursor.getString(2));
                o.setAddress(cursor.getString(3));
                o.setOrderDate(cursor.getString(4));
                o.setordDetailsId(cursor.getInt(5));
                o.setFeedback(cursor.getString(6));
                o.setRating(cursor.getString(7));
                orders.add(o);
            }
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return orders;
    }
}