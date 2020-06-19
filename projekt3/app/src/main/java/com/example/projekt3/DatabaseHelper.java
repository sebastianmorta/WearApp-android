package com.example.projekt3;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projekt3.Tables.*;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Clothes.db";
//    public static final String TABLE_NAME = "Clothes.table";
//    public static final String COL_1 = "ID";
//    public static final String COL_2 = "URL";
//    public static final String COL_3 = "Clothes.table"
    private Context context;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 5);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        ("create table " + TABLE_NAME + "  (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, URL STRING)");
        final String CREATE_TABLE = "CREATE TABLE " +
            Gear.TABLE_NAME + " (" +
            Gear._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Gear.TABLE_NAME + " TEXT, " +
            Gear.COLUMN_COLOR + " TEXT, " +
            Gear.COLUMN_TYPE + " TEXT NOT NULL, " +
            Gear.COLUMN_URL + " TEXT " +
            ");" ;
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Gear.TABLE_NAME);
        onCreate(db);
    }

    void add_gear(String title, String color, String type, String url)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Gear.COLUMN_NAME, title);
        cv.put(Gear.COLUMN_COLOR, color);
        cv.put(Gear.COLUMN_TYPE, type);
        cv.put(Gear.COLUMN_URL, url);
        long result = db.insert(Gear.TABLE_NAME, null, cv);
        if (result == -1) Toast.makeText(context, "fail", Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
    }
}
