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

public class CategoryHelper extends SQLiteOpenHelper{
    private static String name="CategoryDatabase";
    Context context;
    private ByteArrayOutputStream objByteArrayOutputStream;
    private byte[] ImgInByte;

    public CategoryHelper(@androidx.annotation.Nullable android.content.Context context) {
        super(context, name, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Category(cat_Id integer primary key autoincrement,name text,image Blob)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Category");
        onCreate(db);
    }

    public void CreateData(Category c){
        try{
            SQLiteDatabase objSqLiteDatabase = this.getWritableDatabase();
            Bitmap imagetoStore=c.getImg();

            objByteArrayOutputStream=new ByteArrayOutputStream();
            imagetoStore.compress(Bitmap.CompressFormat.JPEG,100,objByteArrayOutputStream);

            ImgInByte=objByteArrayOutputStream.toByteArray();
            ContentValues objContentValues = new ContentValues();

            objContentValues.put("name",c.getName());
            objContentValues.put("image",ImgInByte);


            long check = objSqLiteDatabase.insert("Category",null,objContentValues);
            if(check!=-1){
                //Toast.makeText(context, "added", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(context, "not added", Toast.LENGTH_LONG).show();
            }
            objSqLiteDatabase.close();
        }catch (Exception e){
           Toast.makeText(context, e.getMessage()+"add", Toast.LENGTH_LONG).show();
        }
    }

    public ArrayList<Category> getAllCategory(){
        Category c;
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        ArrayList<Category> categories = new ArrayList<>();
        Cursor cursor=sqLiteDatabase.rawQuery("select * from Category",null);
        try {
            while (cursor.moveToNext()){
                c=new Category();
                c.setId(cursor.getInt(0));
                c.setName(cursor.getString(1));
                byte[] img = cursor.getBlob(2);
                c.setImg(BitmapFactory.decodeByteArray(img,0,img.length));
                categories.add(c);
            }
        }catch (Exception e){
            Toast.makeText(context, e.getMessage()+"get", Toast.LENGTH_LONG).show();
        }
        return categories;
    }


}
