package com.anfly.fragmentr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_a, container, false);
//        MainActivity activity = (MainActivity) getActivity();
//        activity.getMsgFromFramgent("我是来自fragment的数据");

//        Bundle bundle = getArguments();
//        String a = bundle.getString("a");
//        Log.e("TAG", "AFragment onCreateView()来自activity的数据：" + a);
        return view;
    }
}
