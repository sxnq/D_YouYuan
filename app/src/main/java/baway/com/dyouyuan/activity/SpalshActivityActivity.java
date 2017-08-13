package baway.com.dyouyuan.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import baway.com.dyouyuan.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 帧动画 --- 在res 下创建 drawable 文件 animation-list
 */

public class SpalshActivityActivity extends Activity {

    @BindView(R.id.activityspalsh_iv)
    ImageView activityspalshIv;
    private AnimationDrawable ad;

    private Timer timer;
    // 设置倒计时时间
    private int num = 3;

    // API 不兼容声明
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh_activity);
        ButterKnife.bind(this);

        // 创建帧动画对象
        ad = new AnimationDrawable();

        // 添加一帧图片，并制定间隔时间 很麻烦 所以在xml 文件中配置

        // 根据本地图片 返回drawable 对象

        ad = (AnimationDrawable) getResources().getDrawable(R.drawable.zdh);

        // 把 动画形式的 drawable 设置 给 ImageView
        activityspalshIv.setBackground(ad);


        // 创建计时器
        timer = new Timer();
        // 创建时间任务类
        TimerTask task = new TimerTask() {

            @Override
            public void run() {

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        if (num >= 0) {

                            ad.start();

                            num--;

//                            SystemClock.sleep(1000);

                        } else if (num < 0) {

                            timer.cancel();

                            Intent i = new Intent(SpalshActivityActivity.this, RadioGroupActivity.class);
                            startActivity(i);
                            finish();

                        }

                    }
                });

            }
        };

        timer.schedule(task, 1000, 1000);
    }
}
