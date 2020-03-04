package com.anfly.banner;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * banner使用
 * 1.添加依赖、网络权限
 * 2.创建布局
 * 3.获取网数据
 * 4.banner设置图片集合并调用start()方法
 */
public class MainActivity extends AppCompatActivity {

    private Banner banner;
    String bannerUrl = "https://www.wanandroid.com/banner/json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(bannerUrl);
                    InputStream inputStream = url.openConnection().getInputStream();
                    final BannerBean bannerBean = new Gson().fromJson(streamToString(inputStream), BannerBean.class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayList<String> images = new ArrayList<>();
                            ArrayList<String> titles = new ArrayList<>();
                            for (int i = 0; i < bannerBean.getData().size(); i++) {
                                images.add(bannerBean.getData().get(i).getImagePath());
                                titles.add(bannerBean.getData().get(i).getTitle());
                            }
                            banner
                                    .setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE)
                                    .setImages(images)//
                                    .setBannerAnimation(Transformer.DepthPage)
                                    .setBannerTitles(titles)//直接添加无效，必须设置BannerStyle
                                    .setImageLoader(new GlideImageLoader())//
                                    .start();//
                        }
                    });

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

    private void initView() {
        banner = (Banner) findViewById(R.id.banner);
    }


}
