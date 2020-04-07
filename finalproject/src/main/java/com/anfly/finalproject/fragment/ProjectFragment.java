package com.anfly.finalproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.anfly.finalproject.R;
import com.anfly.finalproject.adapter.ProjectTabAdapter;
import com.anfly.finalproject.bean.TabBean;
import com.anfly.finalproject.utils.NetManager;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectFragment extends Fragment {

    private String tabUrl = "https://www.wanandroid.com/project/tree/json";
    private ViewPager vp;
    private TabLayout tab;
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;

    public ProjectFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String netData = NetManager.getNetData(tabUrl);
                final TabBean tabBean = new Gson().fromJson(netData, TabBean.class);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<TabBean.DataBean> data = tabBean.getData();
                        for (int i = 0; i < data.size(); i++) {
                            fragments.add(new ProjectPubFragment(data.get(i).getId()));
                            titles.add(data.get(i).getName());
                        }
                        ProjectTabAdapter projectTabAdapter = new ProjectTabAdapter(getChildFragmentManager(), fragments, titles);
                        vp.setAdapter(projectTabAdapter);
                        tab.setupWithViewPager(vp);
                    }
                });
            }
        }).start();
    }

    private void initView(View view) {
        vp = view.findViewById(R.id.vp);
        tab = view.findViewById(R.id.tab);
        fragments = new ArrayList<>();
        titles = new ArrayList<>();

    }
}
