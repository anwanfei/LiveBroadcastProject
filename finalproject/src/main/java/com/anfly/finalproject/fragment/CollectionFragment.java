package com.anfly.finalproject.fragment;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anfly.finalproject.R;
import com.anfly.finalproject.adapter.CollectionAdapter;
import com.anfly.finalproject.utils.DbHelper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionFragment extends Fragment {

    private RecyclerView rv_collection;
    private DbHelper dbHelper;
    private int position;
    private ArrayList<String> list;
    private CollectionAdapter adapter;
    private ConstraintLayout cl_collection;

    public CollectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_collection, container, false);
        initView(inflate);
        return inflate;
    }

    private void initView(View inflate) {
        dbHelper = new DbHelper(getActivity());

        list = dbHelper.query();

        if (list.size() <= 0) {
            for (int i = 0; i < 20; i++) {
                dbHelper.insert("anfly" + i);
            }
        }

        rv_collection = inflate.findViewById(R.id.rv_collection);
        cl_collection = inflate.findViewById(R.id.cl_collection);
        rv_collection.setLayoutManager(new LinearLayoutManager(getActivity()));

        rv_collection.addItemDecoration(new DividerItemDecoration(getActivity(), RecyclerView.VERTICAL));

        adapter = new CollectionAdapter(list, getActivity());
        rv_collection.setAdapter(adapter);

        adapter.setOnItemLongClickListener(new CollectionAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int position1) {
                position = position1;
            }
        });
        registerForContextMenu(rv_collection);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, 0, 0, "插入");
        menu.add(0, 1, 0, "删除");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                pop();
                break;
            case 1:
                dbHelper.delete(list.get(position));
                list.remove(position);
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void pop() {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.pop_layout_collection, null);

        Button btn_insert = view.findViewById(R.id.btn_insert);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);
        final EditText et_content = view.findViewById(R.id.et_content);

        final PopupWindow pop = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        pop.setFocusable(true);
        pop.showAtLocation(cl_collection, Gravity.CENTER, 0, 0);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
            }
        });
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = et_content.getText().toString();
                dbHelper.insert(s);
                list.add(s);
                adapter.notifyDataSetChanged();
                pop.dismiss();
            }
        });
    }

}
