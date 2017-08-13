package baway.com.dyouyuan.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;

import java.util.ArrayList;
import java.util.List;

import baway.com.dyouyuan.IApplication;
import baway.com.dyouyuan.MainActivity;
import baway.com.dyouyuan.R;
import baway.com.dyouyuan.fragment.FeedFragment;
import baway.com.dyouyuan.fragment.FindFragment;
import baway.com.dyouyuan.fragment.HomeTrueFragment;
import baway.com.dyouyuan.fragment.MeFragment;
import baway.com.dyouyuan.utils.PreferencesUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RadioGroupActivity extends FragmentActivity{

    private CallReceiver callReceiver;

    @BindView(R.id.tab_radiobutton_home)
    RadioButton tabRadiobuttonHome;
    @BindView(R.id.tab_radiobutton_find)
    RadioButton tabRadiobuttonFind;
    @BindView(R.id.tab_radiobutton_feed)
    RadioButton tabRadiobuttonFeed;
    @BindView(R.id.tab_radiobutton_me)
    RadioButton tabRadiobuttonMe;
    @BindView(R.id.tab_radiogroup)
    RadioGroup tabRadiogroup;

    HomeTrueFragment homeFragment = null;
    FeedFragment feedFragment = null;
    FindFragment findFragment = null;
    MeFragment meFragment = null;

        //创建集合 存入Fragment
        private List<Fragment> listfragment = new ArrayList<>();
        //定义下标
        private int selectIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_group);

        ButterKnife.bind(this);

        //创建Fragment
        createFragment(savedInstanceState);

        //显示Fragment
        showFragment(0);

        //RadioGroup监听事件
        tabRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.tab_radiobutton_home:

                        selectIndex =0;

                        break;

                    case R.id.tab_radiobutton_find:

                        selectIndex =1;


                        Boolean second = PreferencesUtils.getValueByKey(RadioGroupActivity.this, "second", false);
if (second == false){
    Log.e("second",second+"");
}else{
    Log.e("second",second+"");
}


                        break;

                    case R.id.tab_radiobutton_feed:

                        selectIndex =2;

                        break;

                    case R.id.tab_radiobutton_me:

                        selectIndex =3;

                        break;
                }

                showFragment(selectIndex);

            }
        });

incoming();
    }


    public void incoming() {
        callReceiver = new CallReceiver();
        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
        registerReceiver(callReceiver, callFilter);
    }

    private class CallReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 拨打方username
            String from = intent.getStringExtra("from");
            // call type
            String type = intent.getStringExtra("type");
            //跳转到通话页面

            IApplication.ring();
            VideoActivity.startTelActivity(2,from,RadioGroupActivity.this);

        }
    }

    @OnClick({R.id.tab_radiobutton_home, R.id.tab_radiobutton_find, R.id.tab_radiobutton_feed, R.id.tab_radiobutton_me})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tab_radiobutton_home:
                break;
            case R.id.tab_radiobutton_find:
                break;
            case R.id.tab_radiobutton_feed:
                break;
            case R.id.tab_radiobutton_me:
                break;
        }
    }


    //创建Fragment
    private void createFragment(Bundle savedInstanceState) {

        //        HomeFragment homeFragment = new HomeFragment();
        //        FeedFragment feedFragment = new FeedFragment();
        //        MeFragment meFragment = new MeFragment();
        //        FindFragment findFragment = new FindFragment();
        //
        //        listfragment.add(homeFragment);
        //        listfragment.add(feedFragment);
        //        listfragment.add(meFragment);
        //        listfragment.add(findFragment);



        if (savedInstanceState == null) {

            homeFragment = new HomeTrueFragment();
            feedFragment = new FeedFragment();
            findFragment = new FindFragment();
            meFragment = new MeFragment();

        }else{

            homeFragment = (HomeTrueFragment) getSupportFragmentManager().findFragmentByTag("homeFragment");
            feedFragment = (FeedFragment) getSupportFragmentManager().findFragmentByTag("feedFragment");
            findFragment = (FindFragment) getSupportFragmentManager().findFragmentByTag("findFragment");
            meFragment = (MeFragment) getSupportFragmentManager().findFragmentByTag("meFragment");


            if (homeFragment == null){

                homeFragment = new HomeTrueFragment();
            }

            if (feedFragment == null){

                feedFragment = new FeedFragment();
            }


            if (findFragment == null){

                findFragment = new FindFragment();
            }

            if (meFragment == null){

                meFragment = new MeFragment();
            }
        }

        listfragment.add(homeFragment);
        listfragment.add(feedFragment);
        listfragment.add(findFragment);
        listfragment.add(meFragment);


    }

    //临时取值
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        System.out.println(savedInstanceState.getString("a"));
    }


    //将Fragment加进FrameLayout 根据下标判断从集合中加哪个Fragment进去
    private void showFragment(int position) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();


        //展示和隐藏
        //如果刚开始没有Fragment加进了fragmentManager
        if (!listfragment.get(position).isAdded()) {

            //            transaction.add(R.id.container, listfragment.get(position));
            //防止Frgament错乱 --- 获取每个Fragment的值 作为标签
            transaction.add(R.id.container, listfragment.get(position),
                    listfragment.get(position).getClass().getSimpleName());

        }

        for (int i = 0; i < listfragment.size(); i++) {

            if (position == i) {

                transaction.show(listfragment.get(i));

            } else {

                transaction.hide(listfragment.get(i));
            }

        }

        transaction.commit();

    }


    private long clickTime=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (SystemClock.uptimeMillis() - clickTime <= 1500) {
                //如果两次的时间差＜1s，就不执行操作

                Intent i = new Intent(RadioGroupActivity.this, MainActivity.class);
                startActivity(i);
                finish();

            } else {
                //当前系统时间的毫秒值
                clickTime = SystemClock.uptimeMillis();
                Toast.makeText(RadioGroupActivity.this, "再次点击去注册和登录", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}