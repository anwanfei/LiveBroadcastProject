package com.anfly.menur.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.anfly.menur.R;
import com.anfly.menur.adapter.LayoutManagetAdapter;
import com.anfly.menur.bean.SmartGirlBean;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MutiLayoutFragment extends Fragment implements OnRefreshLoadMoreListener {

    private RecyclerView rv_muti;
    private int page = 19;
    private String sgUrl = "https://gank.io/api/data/%E7%A6%8F%E5%88%A9/20/";
    private ArrayList<SmartGirlBean.ResultsBean> list;
    //    private MultiAdapter adapter;
    private LayoutManagetAdapter adapter;
    private SmartRefreshLayout srl;

    public MutiLayoutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View inflate = inflater.inflate(R.layout.fragment_muti_layout, container, false);
        initView(inflate);
        initData();
        return inflate;
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(sgUrl + page);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    int responseCode = con.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = con.getInputStream();
                        String json = streamToString(inputStream);
                        final SmartGirlBean smartGirlBean = new Gson().fromJson(json, SmartGirlBean.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                list.addAll(smartGirlBean.getResults());
                                adapter.notifyDataSetChanged();
                                srl.finishRefresh();
                                srl.finishLoadMore();
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

    private void initView(View inflate) {
        rv_muti = inflate.findViewById(R.id.rv_muti);
        srl = inflate.findViewById(R.id.srl);

        rv_muti.setLayoutManager(new LinearLayoutManager(getActivity()));
        list = new ArrayList<>();
//        adapter = new MultiAdapter(getActivity(), list);
        adapter = new LayoutManagetAdapter(getActivity(), list);
        rv_muti.setAdapter(adapter);
        adapter.setOnItemClickListener(new LayoutManagetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(), list.get(position).getCreatedAt(), Toast.LENGTH_SHORT).show();
            }
        });

        srl.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        //代码添加
        menu.add(0, 0, 0, "线性布局");
        menu.add(0, 1, 0, "网格布局");
        menu.add(0, 2, 0, "瀑布流");
        //menu添加
        inflater.inflate(R.menu.munu_nv, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                rv_muti.setLayoutManager(new LinearLayoutManager(getActivity()));
                break;
            case 1:
                rv_muti.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                break;
            case 2:
                rv_muti.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                break;
            case R.id.item_music:
                Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_contacts:
                Toast.makeText(getActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        initData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 19;
        if (list != null && list.size() > 0) {
            list.clear();
            initData();
        }
    }
}
