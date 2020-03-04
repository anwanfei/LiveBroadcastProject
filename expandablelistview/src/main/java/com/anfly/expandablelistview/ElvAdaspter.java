package com.anfly.expandablelistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ElvAdaspter extends BaseExpandableListAdapter {

    private ArrayList<ElvBean.DataBean> list;
    private Context context;
    private final LayoutInflater inflater;

    public ElvAdaspter(ArrayList<ElvBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getChildren().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_group, null);
        }
        TextView tv_name = convertView.findViewById(R.id.tv_name);
        TextView tv_num = convertView.findViewById(R.id.tv_num);

        String name = list.get(groupPosition).getName();
        tv_name.setText(name);
        tv_num.setText((groupPosition + 1) + " / " + list.size());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_child, null);
        }
        TextView tv_name = convertView.findViewById(R.id.tv_name);

        String name = list.get(groupPosition).getChildren().get(childPosition).getName();
        tv_name.setText(name);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
