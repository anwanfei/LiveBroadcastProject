package com.anfly.exam8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelperr extends SQLiteOpenHelper {
    public DbHelperr(Context context) {
        super(context, "eaxm8", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table exam(name varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    protected void insert(String name) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        db.insert("exam", null, contentValues);
        db.close();
    }

    protected ArrayList<String> query() {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query("exam", null, null, null, null, null, null);

        ArrayList<String> lists = new ArrayList<>();

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            lists.add(name);
        }
        db.close();
        return lists;
    }

    protected void updata(String name, String newName) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", newName);
        db.update("exam", contentValues, "name=?", new String[]{name});
        db.close();
    }

    public void delete(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("exam", "name=?", new String[]{name});
        db.close();
    }
}
