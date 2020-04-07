package com.anfly.contentresolver;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Telephony;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_insert;
    private Button btn_delete;
    private Button btn_updata;
    private Button btn_query;
    private EditText et_age;
    private EditText et_name;
    private ContentResolver contentResolver;
    private Uri uri;
    private Button btn_contacts;
    private Button btn_sms;
    private Button btn_photo;
    private Button btn_audio;
    private Button btn_vedio;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermission();
        initView();

    }

    private void initPermission() {
        PermissionsUtil.requestPermission(this, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permission) {

            }

            @Override
            public void permissionDenied(@NonNull String[] permission) {

            }
        }, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CONTACTS, Manifest.permission.READ_SMS, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    private void initView() {

        contentResolver = getContentResolver();
        uri = Uri.parse("content://com.anfly.contentprovider.CpContentprovider/cp");

        btn_insert = (Button) findViewById(R.id.btn_insert);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_updata = (Button) findViewById(R.id.btn_updata);
        btn_query = (Button) findViewById(R.id.btn_query);
        et_age = (EditText) findViewById(R.id.et_age);
        et_name = (EditText) findViewById(R.id.et_name);

        btn_insert.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_updata.setOnClickListener(this);
        btn_query.setOnClickListener(this);


        btn_contacts = (Button) findViewById(R.id.btn_contacts);
        btn_contacts.setOnClickListener(this);
        btn_sms = (Button) findViewById(R.id.btn_sms);
        btn_sms.setOnClickListener(this);
        btn_photo = (Button) findViewById(R.id.btn_photo);
        btn_photo.setOnClickListener(this);
        btn_audio = (Button) findViewById(R.id.btn_audio);
        btn_audio.setOnClickListener(this);
        btn_vedio = (Button) findViewById(R.id.btn_vedio);
        btn_vedio.setOnClickListener(this);
        rv = (RecyclerView) findViewById(R.id.rv);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_insert:
                insert();
                break;
            case R.id.btn_delete:
                delete();
                break;
            case R.id.btn_updata:
                updata();
                break;
            case R.id.btn_query:
                query();
                break;
            case R.id.btn_contacts:
                getContacts();
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
            case R.id.btn_vedio:
                video();
                break;
        }
    }

    /**
     * 读取视频
     */
    private void video() {
        Cursor cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
            String data = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
            Log.e("TAG", name + ":" + data);
        }
    }

    /**
     * 读取音频
     */
    private void audio() {
        Cursor cursor = contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            Log.e("TAG", name + ":" + data.endsWith("mp3"));
        }
    }

        /**
         * 读取图片
         */
        private void photo() {
            Cursor query = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
            while (query.moveToNext()) {
                String name = query.getString(query.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));
                String data = query.getString(query.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
                Log.e("TAG", name + ":" + data);
            }
        }

    /**
     * 读取短信
     */
    private void sms() {
        Cursor cursor = contentResolver.query(Telephony.Sms.CONTENT_URI, null, null, null, null);
        while (cursor.moveToNext()) {
            String num = cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS));
            String content = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
            String date = cursor.getString(cursor.getColumnIndex(Telephony.Sms.DATE));

            Log.e("TAG", date + "-" + num + "-" + content);
        }
    }

    /**
     * 获取通信录
     */
    private void getContacts() {
        final ArrayList<ContactsBean> list = new ArrayList<>();

        Cursor query = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (query.moveToNext()) {
            String name = query.getString(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String num = query.getString(query.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            Log.e("TAG", "name:" + name.substring(0, 1) + ",num:" + num.substring(0, 2) + "*********");
            ContactsBean contactsBean = new ContactsBean(name.substring(0, 1), num.substring(0, 2) + "*********");
            list.add(contactsBean);
        }
        rv.setLayoutManager(new LinearLayoutManager(this));

        ContactsAdapter adapter = new ContactsAdapter(this, list);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new ContactsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                callPhone(list.get(position));
            }
        });
    }

    private void callPhone(ContactsBean contactsBean) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + contactsBean.getNum()));
        startActivity(intent);
    }

    /**
     * 更新数据
     */
    private void updata() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "123");
        contentResolver.update(uri, contentValues, "name=?", new String[]{"a"});
    }

    /**
     * 查询数据
     */
    private void query() {
        Cursor corsor = contentResolver.query(uri, null, null, null, null);
        ArrayList<String> list = new ArrayList<>();
        while (corsor.moveToNext()) {
            String name = corsor.getString(corsor.getColumnIndex("name"));
            String age = corsor.getString(corsor.getColumnIndex("age"));
            Log.e("TAG", "MainActivity query():name-" + name + ",age-" + age);
        }
    }

    /**
     * 删除数据
     */
    private void delete() {
        contentResolver.delete(uri, "name=?", new String[]{"anfly"});
    }

    /**
     * 插入数据
     */
    private void insert() {
        // validate
        String age = et_age.getText().toString().trim();
        if (TextUtils.isEmpty(age)) {
            Toast.makeText(this, "年龄", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "姓名", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("age", age);
        contentResolver.insert(uri, contentValues);
    }
}
