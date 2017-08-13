//package baway.com.dyouyuan.activity;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.design.widget.TabLayout;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//
//import baway.com.dyouyuan.R;
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//import static baway.com.dyouyuan.R.id.registactivity_ivboy;
//
//public class RegistActivity extends Activity {
//
//    int initPosition = 1; //默认位置
//    @BindView(R.id.registactivity_fanhui)
//    ImageView registactivityFanhui;
//    @BindView(R.id.registactivity_denglu)
//    TextView registactivityDenglu;
//    @BindView(registactivity_ivboy)
//    RadioButton registactivityIvboy;
//    @BindView(R.id.registactivity_ivgirl)
//    RadioButton registactivityIvgirl;
//    @BindView(R.id.registactivity_btlogin)
//    Button registactivityBtlogin;
//    @BindView(R.id.tablayout)
//    TabLayout tablayout;
//    @BindView(R.id.registactivity_rggroup)
//    RadioGroup rg;
//
//    private View tabView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_regist);
//        ButterKnife.bind(this);
//
//        //tab可滚动
//        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//
//
////        //tab的字体选择器,默认黑色,选择时红色
////        tablayout.setTabTextColors(Color.BLACK, Color.BLUE);
//
////        //tab的下划线颜色,默认是粉红色
//        tablayout.setSelectedTabIndicatorColor(Color.WHITE);
//        for (int i = 18; i < 50; i++) {
//            //添加tab
////            tablayout.addTab(tablayout.newTab().setText(i+"岁"));
//
////            TextView tv = new TextView(this);
////            tv.setText(i+"岁");
////            tv.setTextColor(Color.BLACK);
////            tablayout.addTab(tablayout.newTab().setCustomView(tv));
//
//
//            //3.支持添加View
//            View tabView =  View.inflate(RegistActivity.this, R.layout.regist_view_tab, null);
//            TextView tv = ((TextView) tabView.findViewById(R.id.tv_title));
//            tv.setText(i+"岁");
//            tablayout.addTab(tablayout.newTab().setCustomView(tv));
//
//        }
//
//        //设置tab的点击监听器
//        tablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                //将默认位置选中为false
//                isSelected(tablayout.getTabAt(initPosition), false);
//                //选中当前位置
//                isSelected(tab, true);
//
//               TextView tv = (TextView) tab.getCustomView();
//               tv.setTextColor(Color.BLUE);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                //tab未选中
//                isSelected(tab, false);
//                TextView tv = (TextView) tab.getCustomView();
//                tv.setTextColor(Color.GRAY);
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//                //tab重新选中
//                isSelected(tab,true);
//            }
//        });
//        //进来默认选中位置第3个item
//        initPosition = 2;
//        isSelected(tablayout.getTabAt(initPosition), true);
//
//    }
//
//        /**
//         * 设置选中的tab是否带缩放效果
//         * @param tab
//         * @param isSelected
//         */
//        private void isSelected(TabLayout.Tab tab, boolean isSelected) {
//            View view = tab.getCustomView();
//            if (null != view) {
//                view.setScaleX(isSelected ? 1.3f : 1.0f);
//                view.setScaleY(isSelected ? 1.3f : 1.0f);
//                TextView tv = (TextView) tab.getCustomView();
//                tv.setTextColor(Color.BLUE);
//            }else{
//
//                TextView tv = (TextView) tab.getCustomView();
//                tv.setTextColor(Color.GRAY);
//            }
//        }
//
//
//
//
//
//    @OnClick({R.id.registactivity_fanhui, R.id.registactivity_denglu, registactivity_ivboy, R.id.registactivity_ivgirl, R.id.registactivity_btlogin})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.registactivity_fanhui:
//
//                finish();
//
//                break;
//            case R.id.registactivity_denglu:
//
//                Intent i = new Intent(RegistActivity.this,LoginActivity.class);
//                startActivity(i);
//
//                break;
//            case registactivity_ivboy:
//
//
//
//                break;
//            case R.id.registactivity_ivgirl:
//                break;
//            case R.id.registactivity_btlogin:
//
//                if (rg.getCheckedRadioButtonId() == registactivityIvboy.getId() || rg.getCheckedRadioButtonId() == registactivityIvgirl.getId()){
//
//                    Intent i1 = new Intent(RegistActivity.this,PhoneRegistActivity.class);
//                    startActivity(i1);
//                }else{
//
//                    return;
//                }
//
//                break;
//        }
//    }
//}
