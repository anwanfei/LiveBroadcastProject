package com.anfly.livebroadcastproject;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * 二级列表步骤
 * 1.创建布局并找id
 * 2.获取数据（死数据、网络数据）
 * 3.创建视图
 * 4.创建适配器（*****）
 * 5.给二级列表设置适配器
 */
public class MainActivity extends AppCompatActivity {

    private ExpandableListView elv_elv;
    private ArrayList<String> groupList;
    private ArrayList<ArrayList<String>> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initListener();
    }

    private void initListener() {
        //父项的点击事件
        elv_elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Toast.makeText(MainActivity.this, groupList.get(groupPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //子项的点击事件
        elv_elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(MainActivity.this, itemList.get(groupPosition).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void initData() {
        //父项集合
        groupList = new ArrayList<>();
        groupList.add("我的好友");
        groupList.add("我的家人");
        groupList.add("黑名单");

        //子项集合
        itemList = new ArrayList<>();

        ArrayList<String> itemListone = new ArrayList<>();
        itemListone.add("小明");
        itemListone.add("小红");
        itemListone.add("小黑");

        ArrayList<String> itemListTwo = new ArrayList<>();
        itemListTwo.add("大妹");
        itemListTwo.add("二妹");
        itemListTwo.add("三妹");

        ArrayList<String> itemListThree = new ArrayList<>();
        itemListThree.add("狗蛋");
        itemListThree.add("黑夜");
        itemListThree.add("黑狗");

        itemList.add(itemListone);
        itemList.add(itemListThree);
        itemList.add(itemListTwo);
    }

    private void initView() {
        elv_elv = (ExpandableListView) findViewById(R.id.elv_elv);
        elv_elv.setAdapter(new ElvAdapter(this, groupList, itemList));
    }
}
