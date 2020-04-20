package com.anfly.finalproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.anfly.finalproject.R;
import com.anfly.finalproject.adapter.ElvAdadpter;
import com.anfly.finalproject.bean.ElvBean;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ElvFragment extends Fragment {
    private String elvUrl = "https://www.wanandroid.com/tree/json";
    private ExpandableListView elv;
    private ArrayList<ElvBean.DataBean> list;
    private ElvAdadpter adadpter;

    public ElvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_elv, container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initListener() {
        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getActivity(), list.get(groupPosition).getChildren().get(childPosition).getName(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            initData();
        } else {
            if (list != null && list.size() > 0) {
                list.clear();
            }
        }
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String netData = getNetData(elvUrl);
                final ElvBean elvBean = new Gson().fromJson(netData, ElvBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<ElvBean.DataBean> data = elvBean.getData();
                        if (data != null) {
                            list.addAll(data);
                            adadpter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }).start();
    }

    private void initView(View view) {
        elv = view.findViewById(R.id.elv);
        list = new ArrayList<>();
        adadpter = new ElvAdadpter(getActivity(), list);
        elv.setAdapter(adadpter);
    }

    private String getNetData(String netUrl) {
        try {
            URL url = new URL(netUrl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = con.getInputStream();
                return streamToString(inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private String streamToString(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int length = -1;
        byte[] bytes = new byte[1024];
        try {
            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
            }

            outputStream.close();
            String result = outputStream.toString();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
