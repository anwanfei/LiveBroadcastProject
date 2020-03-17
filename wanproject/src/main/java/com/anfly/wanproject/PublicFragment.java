package com.anfly.wanproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PublicFragment extends Fragment implements OnRefreshLoadMoreListener {

    private int id;
    private RecyclerView rv;
    private ArrayList<PublicBean.DataBean.DatasBean> list;
    private PublicAdapter adapter;
    private String pubUrl = "https://www.wanandroid.com/project/list/";
    private SmartRefreshLayout srl;
    private int page = 1;

    public PublicFragment(int id) {
        this.id = id;
    }

    public PublicFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_public, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(pubUrl + page + "/json?cid=" + id);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    int responseCode = con.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = con.getInputStream();
                        String json = streamToString(inputStream);
                        final PublicBean publicBean = new Gson().fromJson(json, PublicBean.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                list.addAll(publicBean.getData().getDatas());
                                adapter.notifyDataSetChanged();
                                srl.finishLoadMore();
                                srl.finishRefresh();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initView(View view) {
        rv = view.findViewById(R.id.rv);
        srl = view.findViewById(R.id.srl);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        adapter = new PublicAdapter(getActivity(), list);
        rv.setAdapter(adapter);

        srl.setOnRefreshLoadMoreListener(this);
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

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        initData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        if (list != null && list.size() > 0) {
            list.clear();
            initData();
        }
    }
}
