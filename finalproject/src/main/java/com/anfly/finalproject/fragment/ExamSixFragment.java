package com.anfly.finalproject.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anfly.finalproject.R;
import com.anfly.finalproject.adapter.ExamSiixAdapter;
import com.anfly.finalproject.bean.BannerBean;
import com.anfly.finalproject.bean.FoodBean;
import com.anfly.finalproject.utils.NetManager;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExamSixFragment extends Fragment {

    private RecyclerView rv_e6;
    private Banner banner_e6;
    String bannerUrl = "https://www.wanandroid.com/banner/json";
    private String foodUrl = "http://www.qubaobei.com/ios/cf/dish_list.php?stage_id=1&limit=20&page=1";
    private ArrayList<FoodBean.DataBean> list;
    private ExamSiixAdapter examSiixAdapter;

    public ExamSixFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_exam_six, container, false);
        initView(inflate);
        initData();
        return inflate;
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String bannerJson = NetManager.getNetData(bannerUrl);
                    String foodJson = NetManager.getNetData(foodUrl);
                    Gson gson = new Gson();
                    final BannerBean bannerBean = gson.fromJson(bannerJson, BannerBean.class);
                    final FoodBean foodBean = gson.fromJson(foodJson, FoodBean.class);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<String> iamges = new ArrayList<>();
                            ArrayList<String> tities = new ArrayList<>();

                            for (int i = 0; i < bannerBean.getData().size(); i++) {
                                iamges.add(bannerBean.getData().get(i).getImagePath());
                                tities.add(bannerBean.getData().get(i).getTitle());
                            }
                            banner_e6
                                    .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                                    .setBannerTitles(tities).setImages(iamges).setImageLoader(new ImageLoader() {
                                @Override
                                public void displayImage(Context context, Object path, ImageView imageView) {
                                    Glide.with(context).load(path).into(imageView);
                                }
                            }).start();

                            list.addAll(foodBean.getData());
                            examSiixAdapter.notifyDataSetChanged();


                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initView(View inflate) {
        rv_e6 = inflate.findViewById(R.id.rv_e6);
        banner_e6 = inflate.findViewById(R.id.banner_e6);
        rv_e6.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        list = new ArrayList<>();
        examSiixAdapter = new ExamSiixAdapter(getActivity(), list);
        rv_e6.setAdapter(examSiixAdapter);

    }
}
