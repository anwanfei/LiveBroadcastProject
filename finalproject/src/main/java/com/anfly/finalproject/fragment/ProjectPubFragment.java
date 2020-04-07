package com.anfly.finalproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anfly.finalproject.R;
import com.anfly.finalproject.activity.WebViewActivity;
import com.anfly.finalproject.adapter.ProjectPubAdapter;
import com.anfly.finalproject.bean.PublicBean;
import com.anfly.finalproject.utils.NetManager;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectPubFragment extends Fragment implements OnRefreshLoadMoreListener {
    private String pubUrl = "https://www.wanandroid.com/project/list/";
    private int id;
    private RecyclerView rv;
    private SmartRefreshLayout srl;
    private ArrayList<PublicBean.DataBean.DatasBean> list;
    private ProjectPubAdapter adapter;
    private int page = 1;

    public ProjectPubFragment(int id) {
        this.id = id;
    }

    public ProjectPubFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_project_pub, container, false);
        initView(inflate);
        initData();
        return inflate;
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String netData = NetManager.getNetData(pubUrl + page + "/json?cid=" + id);
                final PublicBean publicBean = new Gson().fromJson(netData, PublicBean.class);
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
        }).start();
    }

    private void initView(final View inflate) {
        rv = inflate.findViewById(R.id.rv);
        srl = inflate.findViewById(R.id.srl);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
        adapter = new ProjectPubAdapter(getActivity(), list);
        rv.setAdapter(adapter);

        srl.setOnRefreshLoadMoreListener(this);

        adapter.setOnItemClickListener(new ProjectPubAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("link",list.get(position).getLink());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        initData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        if (list.size() > 0) {
            list.clear();
            initData();
        }
    }
}
