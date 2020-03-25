package com.anfly.tablayoutr;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class CollectionFragment extends Fragment {

    public CollectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("TAG", "CollectionFragment onCreateView()");
        View inflate = inflater.inflate(R.layout.fragment_collection, container, false);

        return inflate;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
//            initData();
        } else {
//            if (list != null && list.size > 0) {
//                list.clear();
//            }
        }
    }
}
