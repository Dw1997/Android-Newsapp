package com.example.graduatedesign.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreTools {
    static final String PRENAME = "dw_savedata";

    public static String getname(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        return sp.getString("Name", "null");
    }

    public static void setName(Context context,String name) {
        SharedPreferences sp = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("Name", name);
        ed.commit();
    }

    public static String getPass(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        return sp.getString("Pass", "null");
    }
    public static void setPass(Context context,String pass) {
        SharedPreferences sp = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("Pass", pass);
        ed.commit();
    }

    public static void setMail(Context context,String mail) {
        SharedPreferences sp = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("Mail", mail);
        ed.commit();
    }

    public static String getMail(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        return sp.getString("Mail", "null");
    }

    public static void setType(Context context,String type) {
        SharedPreferences sp = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("Type", type);
        ed.commit();
    }

    public static String getType(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PRENAME, Context.MODE_PRIVATE);
        return sp.getString("Type", "null");
    }


    public static void setPhone(Context context,String phone){
        SharedPreferences sp = context.getSharedPreferences(PRENAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("Phone",phone);
        ed.commit();
    }

    public static String getPhone(Context context){
        SharedPreferences sp = context.getSharedPreferences(PRENAME,Context.MODE_PRIVATE);
        return sp.getString("Phone",null);
    }

}
