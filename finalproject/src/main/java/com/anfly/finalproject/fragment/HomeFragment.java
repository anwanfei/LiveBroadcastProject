package com.anfly.finalproject.fragment;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anfly.finalproject.R;
import com.anfly.finalproject.activity.CollectionActivity;
import com.anfly.finalproject.adapter.HomeAdapter;
import com.anfly.finalproject.bean.BannerBean;
import com.anfly.finalproject.bean.FoodBean;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements HomeAdapter.OnItemClickListener {

    String bannerUrl = "https://www.wanandroid.com/banner/json";
    private String foodUrl = "http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=1";
    private RecyclerView rv;
    private SmartRefreshLayout srl;
    private ArrayList<BannerBean.DataBean> banners;
    private ArrayList<FoodBean.DataBean> list;
    private HomeAdapter adapter;
    private ConstraintLayout cl_home;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        initView(inflate);
        initData();
        return inflate;
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String bannerJson = getNetData(bannerUrl);
                    String foodJson = getNetData(foodUrl);
                    Gson gson = new Gson();
                    final BannerBean bannerBean = gson.fromJson(bannerJson, BannerBean.class);
                    final FoodBean foodBean = gson.fromJson(foodJson, FoodBean.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            banners.addAll(bannerBean.getData());
                            list.addAll(foodBean.getData());
                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
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

    private void initView(View inflate) {
        rv = inflate.findViewById(R.id.rv);
        srl = inflate.findViewById(R.id.srl);
        cl_home = inflate.findViewById(R.id.cl_home);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        banners = new ArrayList<>();
        list = new ArrayList<>();
        adapter = new HomeAdapter(getActivity(), list, banners);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        showPop();
    }

    private void showPop() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_layout, null);

        Button btn_confirm = view.findViewById(R.id.btn_confirm);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.showAtLocation(cl_home, Gravity.CENTER, 0, 0);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                notification();
            }
        });
    }

    private void notification() {
        NotificationManager nm = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "1";
        String channelName = "final";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            nm.createNotificationChannel(channel);
        }

        Intent intent = new Intent(getActivity(), CollectionActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 200, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification nofication = new NotificationCompat.Builder(getActivity(), channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("最后的项目")
                .setContentText("可能最后一次了")
                .setContentIntent(pendingIntent)
                .build();

        nm.notify(100, nofication);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home_options, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_collection:
                Intent intent = new Intent(getActivity(), CollectionActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
