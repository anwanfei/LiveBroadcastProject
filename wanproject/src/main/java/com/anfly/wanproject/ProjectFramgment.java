package com.anfly.wanproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ProjectFramgment extends Fragment {
    private ViewPager vp;
    private TabLayout tab;
    private String tabUrl = "https://www.wanandroid.com/project/tree/json";
    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(tabUrl);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    int responseCode = con.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = con.getInputStream();
                        String s = streamToString(inputStream);
                        final TabBean tabBean = new Gson().fromJson(s, TabBean.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < tabBean.getData().size(); i++) {
                                    fragments.add(new PublicFragment(tabBean.getData().get(i).getId()));
                                    titles.add(tabBean.getData().get(i).getName());
                                }

                                WANAdapter adapter = new WANAdapter(getChildFragmentManager(), fragments, titles);
                                vp.setAdapter(adapter);
                                tab.setupWithViewPager(vp);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

    private void initView(View view) {
        vp = view.findViewById(R.id.vp);
        tab = view.findViewById(R.id.tab);
        fragments = new ArrayList<>();
        titles = new ArrayList<>();

    }
}
