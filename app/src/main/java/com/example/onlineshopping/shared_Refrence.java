package com.example.onlineshopping;

import android.content.Context;
import android.content.SharedPreferences;

public class shared_Refrence {
    private SharedPreferences shp;
    public void setRememberMeStatues(Context context,boolean remember)
    {
        if(shp==null)
        {
            shp=context.getSharedPreferences("infoUser",0);//0 ->private
        }
        shp.edit().putBoolean("remember",remember).apply();
    }
    public boolean isRemember(Context context){
        if(shp==null)
        {
            shp=context.getSharedPreferences("infoUser",0);//0 ->private
        }
        return shp.getBoolean("remember",false);//false is defalut if no checked remeber before
    }
    public void setUserdata(String email,String pass){
        shp.edit().putString("email",email).apply();
        shp.edit().putString("pass",pass).apply();
    }
    public String getEmail(){
        return  shp.getString("email","");
    }
    public String getPassword(){
        return  shp.getString("pass","");
    }


}
