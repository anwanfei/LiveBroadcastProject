package com.anfly.popupwindow;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private ConstraintLayout cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        button = (Button) findViewById(R.id.button);
        cl = (ConstraintLayout) findViewById(R.id.cl);

        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                showPop();
                break;
        }
    }

    private void showPop() {

        View view = LayoutInflater.from(this).inflate(R.layout.pop_one, null);

        PopupWindow pop = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

//        pop.showAsDropDown(button,0,20, Gravity.CENTER_HORIZONTAL);


        //击范围外关闭PopupWindow
        pop.setBackgroundDrawable(new ColorDrawable());
//        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setOutsideTouchable(true);

        //全屏阴影
        setBackGroundAlpha(0.5f);

        //设置pop进出场动画
        pop.setAnimationStyle(R.style.popAnimation);

        //设置聚焦
        pop.setFocusable(true);

        pop.showAsDropDown(button);
//        pop.showAtLocation(cl, Gravity.BOTTOM, 0, 0);

        //设置pop消失监听
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackGroundAlpha(1f);
            }
        });
    }

    /**
     * 设置透明度
     *
     * @param alpha
     */
    private void setBackGroundAlpha(float alpha) {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha = alpha;
        window.setAttributes(attributes);
    }
}
