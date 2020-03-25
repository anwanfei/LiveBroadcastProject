package com.anfly.viewpagerr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class VpFragment extends Fragment {
    private TextView tv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vp, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        tv = view.findViewById(R.id.tv);

        Bundle bundle = getArguments();
        String key = bundle.getString("key");
        tv.setText(key);
    }
}
