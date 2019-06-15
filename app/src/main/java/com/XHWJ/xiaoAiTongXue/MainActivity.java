package com.XHWJ.xiaoAiTongXue;

import android.Manifest;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.XHWJ.xiaoAiTongXue.base.BaseActivity;
import com.XHWJ.xiaoAiTongXue.baseadapter.BaseRecyclerViewAdapter;
import com.XHWJ.xiaoAiTongXue.utils.LogUtils;
import com.XHWJ.xiaoAiTongXue.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private String[] permission;//权限
    private ProgressBar progressBar2;
    private ProgressBar progressBar3;
    private ProgressBar progressBar4;
    private ProgressBar progressBar5;
    private ImageView image_btn;
    private ImageView image_yidong;
    //    private DrawLineChart drawline;
    private BaseRecyclerViewAdapter adapter;
    private int number;
    private int x = 30;
    private int y = 600;
    private ChartView chart;
    private TextView textView;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        SeekBar seekBar = findViewById(R.id.seekBar);
        chart = findViewById(R.id.chart_view);
        textView = findViewById(R.id.text);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                chart.setDotPosition(progress);
                textView.setText("成长值已达到" + progress + "点");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
//        initView();
//        requestPermission(permission, 1);
    }

    private void initView() {
//        number = 0;
//        progressBar2 = findViewById(R.id.progressBar2);
//        progressBar3 = findViewById(R.id.progressBar3);
//        progressBar4 = findViewById(R.id.progressBar4);
//        progressBar5 = findViewById(R.id.progressBar5);
//        image_btn = findViewById(R.id.image_btn);
//        image_yidong = findViewById(R.id.image_yidong);
////        drawline = findViewById(R.id.drawline);
//        image_btn.setOnClickListener(this);
//        permission = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//                , Manifest.permission.ACCESS_FINE_LOCATION
//                , Manifest.permission.ACCESS_COARSE_LOCATION};
    }

    @Override
    protected void load() {

    }


    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.image_btn:
//                number += 30;
//                ToastUtils.showShort(context, "成长值加30");
//                if (number >= 60) {
//                    progressBar2.setProgressDrawable(getResources().getDrawable(R.drawable.my_progressbar));
//                }
//                if (number >= 140) {
//                    progressBar3.setProgressDrawable(getResources().getDrawable(R.drawable.my_progressbar));
//                }
//                if (number >= 300) {
//                    progressBar4.setProgressDrawable(getResources().getDrawable(R.drawable.my_progressbar));
//                }
//                if (number >= 600) {
//                    progressBar5.setProgressDrawable(getResources().getDrawable(R.drawable.my_progressbar));
//                }
//                if (number >= 30) {
//                    image_yidong.setVisibility(View.VISIBLE);
//                }
////                setXy();
////                x += 43.5;
////                y -= 29.5;
////                if (y <= 20) {
////                    y = 20;
////                }
////                if (x >= 900) {
////                    x = 900;
////                }
////                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) image_yidong.getLayoutParams();
////                layoutParams.setMargins(x, y, 0, 0);
////                image_yidong.setLayoutParams(layoutParams);
////                LogUtils.e(TAG, "X==" + x + "Y==" + y);
//                break;
//        }
    }
}

