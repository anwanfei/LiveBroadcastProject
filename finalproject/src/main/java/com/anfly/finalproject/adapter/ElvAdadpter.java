package com.anfly.finalproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.anfly.finalproject.R;
import com.anfly.finalproject.bean.ElvBean;

import java.util.ArrayList;

public class ElvAdadpter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<ElvBean.DataBean> list;
    private final LayoutInflater inflater;

    public ElvAdadpter(Context context, ArrayList<ElvBean.DataBean> list) {
        this.context = context;
        this.list = list;
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
        convertView = inflater.inflate(R.layout.item_elv, parent, false);
        ElvBean.DataBean dataBean = list.get(groupPosition);
        TextView tv_title = convertView.findViewById(R.id.tv_title);
        tv_title.setText(dataBean.getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.item_elv, parent, false);
        ElvBean.DataBean.ChildrenBean childrenBean = list.get(groupPosition).getChildren().get(childPosition);
        TextView tv_title = convertView.findViewById(R.id.tv_title);
        tv_title.setText(childrenBean.getName());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
