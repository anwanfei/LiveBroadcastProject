package com.anfly.contentresolver;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_insert;
    private Button btn_delete;
    private Button btn_updata;
    private Button btn_query;
    private EditText et_age;
    private EditText et_name;
    private ContentResolver contentResolver;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
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
        }
    }

    private void updata() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "123");
        contentResolver.update(uri, contentValues, "name=?", new String[]{"a"});
    }

    private void query() {
        Cursor corsor = contentResolver.query(uri, null, null, null, null);
        while (corsor.moveToNext()) {
            String name = corsor.getString(corsor.getColumnIndex("name"));
            String age = corsor.getString(corsor.getColumnIndex("age"));
            Log.e("TAG", "MainActivity query():name-" + name + ",age-" + age);
        }
    }

    private void delete() {
        contentResolver.delete(uri, "name=?", new String[]{"anfly"});
    }

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
