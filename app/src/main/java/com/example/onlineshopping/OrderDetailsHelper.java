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
import java.util.List;

public class OrderDetailsHelper extends SQLiteOpenHelper {
    private static String name="OrderDetails";
    SQLiteDatabase Database;
    Context context;
    private ByteArrayOutputStream objByteArrayOutputStream;
    //private byte[] ImgInByte;

    public OrderDetailsHelper(@androidx.annotation.Nullable android.content.Context context) {
        super(context, name, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table OrderDetails(ordId integer primary key autoincrement,proId integer ,quantity integer,userId text,FOREIGN key(proId) references Product(prouductId))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists OrderDetails");
        onCreate(db);
    }

    public void CreateOrder(OrdDetails o){
        try{
            SQLiteDatabase objSqLiteDatabase = this.getWritableDatabase();
            objByteArrayOutputStream=new ByteArrayOutputStream();
            ContentValues objContentValues = new ContentValues();
            objContentValues.put("quantity",o.getQuantity());
            objContentValues.put("proId",o.getProid());
            objContentValues.put("userId",o.getUserId());

            long check = objSqLiteDatabase.insert("OrderDetails",null,objContentValues);
            if(check!=-1){
                Toast.makeText(context, "added", Toast.LENGTH_LONG).show();
            }
            objSqLiteDatabase.close();
        }catch (Exception e){
            Toast.makeText(context, e.getMessage()+"add", Toast.LENGTH_LONG).show();
        }
    }

    public ArrayList<OrdDetails> getAllOrders(){
        OrdDetails o;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        ArrayList<OrdDetails> orders = new ArrayList<>();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from OrderDetails",null);
        try {
            while (cursor.moveToNext()){
                o=new OrdDetails();
                o.setOrdid(cursor.getInt(0));
                o.setProid(cursor.getInt(1));
                o.setQuantity(cursor.getInt(2));
                o.setUserId(cursor.getString(3));

                orders.add(o);
                return orders;
            }
        }catch (Exception e){
            Toast.makeText(context, e.getMessage()+"get", Toast.LENGTH_LONG).show();
        }
        return null;
    }

    public ArrayList<OrdDetails> getOrderByUser(String userId){
        OrdDetails o;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        ArrayList<OrdDetails> orders = new ArrayList<>();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from OrderDetails where userId like ?",new String[]{userId});
        try {
            while (cursor.moveToNext()){
                o=new OrdDetails();
                o.setOrdid(cursor.getInt(0));
                o.setProid(cursor.getInt(1));
                o.setQuantity(cursor.getInt(2));
                o.setUserId(cursor.getString(3));
                orders.add(o);
            }
        }catch (Exception e){
            Toast.makeText(context, e.getMessage()+"get", Toast.LENGTH_LONG).show();
        }
        totalPrice(orders);
        return orders;
    }

    public void deletefromCart(String OrderId){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        sqLiteDatabase.delete("OrderDetails","ordId like ?",new String[]{OrderId});
        sqLiteDatabase.close();
    }

    public void updateCart(String orderId,String quantity){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("quantity",quantity);
        sqLiteDatabase.update("OrderDetails",row,"ordId like ?",new String[]{orderId});
        sqLiteDatabase.close();
    }

    public float totalPrice(List<OrdDetails> orders){
        float Totalprice=0;
        ProductHelper productHelper=new ProductHelper(context);
        product p;
        for (int i=0;i<orders.size();i++){
            p=productHelper.getProduct(String.valueOf(orders.get(i).getProid()));
            Totalprice+=Float.valueOf(p.getPrice())*orders.get(i).getQuantity();
        }
        return  Totalprice;
    }
}