package com.anfly.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class BFragment extends Fragment {

    private String msg;

    public BFragment(String msg) {
        this.msg = msg;
    }

    public BFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_b, container, false);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

        Bundle bundle = getArguments();
        if (bundle != null) {
            String k = bundle.getString("k");

            Log.e("TAG", k);
        }

        return view;
    }

}
