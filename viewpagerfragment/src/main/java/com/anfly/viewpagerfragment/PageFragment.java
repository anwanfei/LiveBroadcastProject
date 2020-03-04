package com.anfly.viewpagerfragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment {

    private String msg;
    private TextView tv_page_name;

    public PageFragment(String msg) {
        this.msg = msg;
    }

    public PageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        tv_page_name = view.findViewById(R.id.tv_page_name);
        tv_page_name.setText(msg);
        return view;
    }

}
