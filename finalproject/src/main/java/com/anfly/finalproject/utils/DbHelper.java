package com.anfly.finalproject.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, "final.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table f(name varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("f", null, contentValues);
    }

    public void delete(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("f", "name=?", new String[]{name});
    }

    public ArrayList query() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query("f", null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            list.add(name);
        }
        return list;
    }

    public void updata(String newName, String oldName) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", newName);
        SQLiteDatabase db = getWritableDatabase();
        db.update("f", contentValues, "name=?", new String[]{oldName});
    }
}
