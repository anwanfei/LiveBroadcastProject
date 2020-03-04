package com.anfly.exam8;


import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionFragment extends Fragment {

    private RecyclerView rv;
    private int position;
    private DbHelperr dbHelperr;
    private ArrayList<String> list;
    private CollectionAdapter adapter;
    private LinearLayout ll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_collection, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        rv = inflate.findViewById(R.id.rv);
        ll=inflate.findViewById(R.id.ll);

        rv.setLayoutManager(new LinearLayoutManager(getActivity()));

        dbHelperr = new DbHelperr(getActivity());

        list = dbHelperr.query();
        if (list.size() <= 0) {
            for (int i = 0; i < 20; i++) {
                dbHelperr.insert("网络课堂" + i);
            }
        }

        adapter = new CollectionAdapter(getActivity(), list);
        rv.setAdapter(adapter);

        adapter.setLongClickItemListener(new CollectionAdapter.OnLongClickItemListener() {
            @Override
            public void onLongClickItem(int position1) {
                position = position1;
            }
        });

        registerForContextMenu(rv);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, 0, 0, "插入");
        menu.add(0, 1, 0, "删除");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                showPop();
                break;
            case 1:
                dbHelperr.delete(list.get(position));
                list.remove(position);
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void showPop() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop, null);
        final EditText et = view.findViewById(R.id.et);
        Button btn_insert = view.findViewById(R.id.btn_insert);
        final PopupWindow pop = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        pop.setFocusable(true);

        pop.showAtLocation(ll, Gravity.CENTER, 0, 0);

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                String s = et.getText().toString();
                dbHelperr.insert(s);
                list.add(s);
                adapter.notifyDataSetChanged();
            }
        });

    }
}
