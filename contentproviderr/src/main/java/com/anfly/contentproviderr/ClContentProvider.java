package com.anfly.contentproviderr;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class ClContentProvider extends ContentProvider {

    private DbHelpper dbHelpper;

    public ClContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelpper.getWritableDatabase();
        return db.delete("cl", selection, selectionArgs);
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        Log.e("TAG", "name:" + values.getAsString("name"));
        Log.e("TAG", "age:" + values.getAsString("age"));

        SQLiteDatabase db = dbHelpper.getWritableDatabase();
        db.insert("cl", null, values);
        return null;
    }

    @Override
    public boolean onCreate() {
        dbHelpper = new DbHelpper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelpper.getWritableDatabase();
        Cursor cursor = db.query("cl", projection, selection, selectionArgs, null, null, sortOrder);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbHelpper.getWritableDatabase();
        int cl = db.update("cl", values, selection, selectionArgs);
        return cl;
    }
}
