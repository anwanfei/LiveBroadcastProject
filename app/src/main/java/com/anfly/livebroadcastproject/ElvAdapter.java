package com.anfly.livebroadcastproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ElvAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<String> groupList;
    private ArrayList<ArrayList<String>> itemList;
    private final LayoutInflater inflater;

    public ElvAdapter(Context context, ArrayList<String> groupList, ArrayList<ArrayList<String>> itemList) {
        this.context = context;
        this.groupList = groupList;
        this.itemList = itemList;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 获取父项个数
     *
     * @return
     */
    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    /**
     * 获取对应父项的子项个数
     *
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return itemList.get(groupPosition).size();
    }

    /**
     * 获取父项
     *
     * @param groupPosition
     * @return
     */
    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    /**
     * 获取子项
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return itemList.get(groupPosition).get(childPosition);
    }

    /**
     * 获取父项id
     *
     * @param groupPosition
     * @return
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * 获取子项id
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * 获取父项视图并赋值
     *
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_group, null);
        }
        TextView tv_name = convertView.findViewById(R.id.tv_name);
        TextView tv_num = convertView.findViewById(R.id.tv_num);

        String name = groupList.get(groupPosition);
        tv_name.setText(name);
        tv_num.setText((groupPosition + 1) + " / " + groupList.size());

        return convertView;
    }

    /**
     * 获取子项视图并赋值
     *
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_item, null);
        }

        TextView tv_name = convertView.findViewById(R.id.tv_name);

        String name = itemList.get(groupPosition).get(childPosition);

        tv_name.setText(name);

        return convertView;
    }

    /**
     * 子项点击事件，如果为true才有效，否则无效
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
