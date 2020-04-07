package com.anfly.popupwindowr;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_show1;
    private TextView tv_address;
    private ConstraintLayout cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_show1 = (Button) findViewById(R.id.btn_show1);
        tv_address = (TextView) findViewById(R.id.tv_address);

        btn_show1.setOnClickListener(this);
        cl = (ConstraintLayout) findViewById(R.id.cl);
        cl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_show1:
                pop1();
                break;
        }
    }

    private void pop1() {
        View view = LayoutInflater.from(this).inflate(R.layout.pop1, null);
        PopupWindow pop = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setFocusable(true);
        pop.setBackgroundDrawable(new ColorDrawable());
        backgroundAlpha(0.5f);
        pop.setOutsideTouchable(true);
        pop.setAnimationStyle(R.style.pop_animation);
        pop.showAsDropDown(tv_address, 20, 20);
//        pop.showAtLocation(cl, Gravity.BOTTOM, 0, btn_show1.getHeight());

        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    private void backgroundAlpha(float alpha) {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha = alpha;
        window.setAttributes(attributes);
    }
}
