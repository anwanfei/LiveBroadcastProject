package com.anfly.contentresolverr;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_insert;
    private ContentResolver contentResolver;
    private Uri uri;
    private Button btn_sms;
    private Button btn_photo;
    private Button btn_audio;
    private Button btn_video;
    private Button btn_contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermision();
        initView();
    }

    private void initPermision() {
        PermissionsUtil.requestPermission(this, new PermissionListener() {
                    @Override
                    public void permissionGranted(@NonNull String[] permission) {

                    }

                    @Override
                    public void permissionDenied(@NonNull String[] permission) {

                    }
                }, Manifest.permission.READ_SMS, Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_CONTACTS);
    }

    private void initView() {
        btn_insert = (Button) findViewById(R.id.btn_insert);

        btn_insert.setOnClickListener(this);

        contentResolver = getContentResolver();
        uri = Uri.parse("content://com.anfly.contentproviderr.ClContentProvider/cl");
        btn_sms = (Button) findViewById(R.id.btn_sms);
        btn_sms.setOnClickListener(this);
        btn_photo = (Button) findViewById(R.id.btn_photo);
        btn_photo.setOnClickListener(this);
        btn_audio = (Button) findViewById(R.id.btn_audio);
        btn_audio.setOnClickListener(this);
        btn_video = (Button) findViewById(R.id.btn_video);
        btn_video.setOnClickListener(this);
        btn_contacts = (Button) findViewById(R.id.btn_contacts);
        btn_contacts.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert:
                insert();
                break;
            case R.id.btn_sms:
                sms();
                break;
            case R.id.btn_photo:
                photo();
                break;
            case R.id.btn_audio:
                audio();
                break;
            case R.id.btn_video:
                video();
                break;
            case R.id.btn_contacts:
                contact();
                break;
        }
    }

    private void contact() {
        ArrayList<ContactBean> list = new ArrayList<>();
        Cursor query = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null, null);
        while (query.moveToNext()) {
            String num = query.getString(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            String name = query.getString(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            ContactBean contactBean = new ContactBean(name, num);
            list.add(contactBean);
        }
    }

    private void video() {
        Cursor query = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null, null);
        while (query.moveToNext()) {
            String name = query.getString(query.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
            String data = query.getString(query.getColumnIndex(MediaStore.Video.Media.DATA));
            Log.e("TAG", name + ":" + data);
        }
    }

    private void audio() {
        Cursor query = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null, null);
        while (query.moveToNext()) {
            String name = query.getString(query.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            String data = query.getString(query.getColumnIndex(MediaStore.Audio.Media.DATA));
            Log.e("TAG", name + ":" + data);
        }
    }

    private void photo() {
        Cursor query = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null, null);
        while (query.moveToNext()) {
            String data = query.getString(query.getColumnIndex(MediaStore.Images.Media.DATA));
            String name = query.getString(query.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));

            Log.e("TAG", name + ":" + data);
        }
    }

    private void sms() {
        Cursor cursor = contentResolver.query(Telephony.Sms.CONTENT_URI, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String address = cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS));
            String body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
            String date = cursor.getString(cursor.getColumnIndex(Telephony.Sms.DATE));

            Log.e("TAG", address + " : " + date + " : " + body);
        }

    }

    private void insert() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "中级");
        contentValues.put("age", "1908");
        contentResolver.insert(uri, contentValues);
    }
}
