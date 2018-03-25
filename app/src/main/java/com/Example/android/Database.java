package com.Example.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by USER on 24/03/2018.
 */

public class Database extends SQLiteOpenHelper {
    //deklarasi variabel yang akan digunakan
    Context cntx;
    SQLiteDatabase db;

    public static final String db_Name = "habbit_db";
    public static final String table_Name = "daftart_habbitt";
    public static final String kolom_ToDo = "todo";
    public static final String kolom_Diskripsi = "description";
    public static final String kolom_Priority = "priority";

    //konstruktor
    public Database(Context context) {
        super(context, db_Name, null, 1);
        this.cntx = context;
        db = this.getWritableDatabase();
        db.execSQL("create table if not exists "+ table_Name +" (todo varchar(35) primary key, description varchar(50), priority varchar(3))");
    }

    //ketika database dibuat
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists "+ table_Name +" (todo varchar(35) primary key, description varchar(50), priority varchar(3))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+ table_Name);
        onCreate(sqLiteDatabase);
    }

    public boolean inputdata(AddData list) {
        //mencocokkan kolom beserta nilainya
        ContentValues val = new ContentValues();
        val.put(kolom_ToDo, list.getTodo());
        val.put(kolom_Diskripsi, list.getDesc());
        val.put(kolom_Priority, list.getPrior());
        long hasil = db.insert(table_Name, null, val);
        if (hasil==-1) {
            return false;
        }else {
            return true;
        }
    }

    //method untuk menghapus data pada database
    public boolean removedata(String ToDo) {
        return db.delete(table_Name, kolom_ToDo +"=\""+ToDo+"\"", null)>0;
    }

 // method untuk mengakses dan melihat database yang ada
    public void readdata(ArrayList<AddData> daftar){
        Cursor cursor = this.getReadableDatabase().rawQuery("select todo, description, priority from "+ table_Name, null);
        while (cursor.moveToNext()){
            daftar.add(new AddData(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
        }
    }
}


