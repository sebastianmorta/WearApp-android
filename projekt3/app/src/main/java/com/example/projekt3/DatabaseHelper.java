package com.example.projekt3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;
import com.example.projekt3.Tables.*;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Clothes.db";
//    public static final String TABLE_NAME = "Clothes.table";
//    public static final String COL_1 = "ID";
//    public static final String COL_2 = "URL";
//    public static final String COL_3 = "Clothes.table"



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        ("create table " + TABLE_NAME + "  (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, URL STRING)");
        final String CREATE_TABLE_GEAR = "CREATE TABLE " +
            Gear.TABLE_NAME + " (" +
            Gear._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Gear.COLUMN_COLOR + " TEXT, " +
            Gear.COLUMN_TYPE + " TEXT NOT NULL, " +
            Gear.COLUMN_URL + " TEXT " +
            ");" ;
        db.execSQL(CREATE_TABLE_GEAR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Gear.TABLE_NAME);
        onCreate(db);
    }
}
