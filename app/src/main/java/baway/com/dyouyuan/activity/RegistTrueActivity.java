package baway.com.dyouyuan.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lljjcoder.citypickerview.widget.CityPicker;

import baway.com.dyouyuan.IApplication;
import baway.com.dyouyuan.R;
import baway.com.dyouyuan.base.AppManager;
import baway.com.dyouyuan.base.IActivity;
import baway.com.dyouyuan.bean.RegisterBean;
import baway.com.dyouyuan.cipher.Md5Utils;
import baway.com.dyouyuan.presenter.RegistTrueActivityPresenter;
import baway.com.dyouyuan.utils.MyToast;
import baway.com.dyouyuan.view.RegistTrueActivityView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//android:inputType="number"
//android:imeOptions="actionDone"


// utils包下的 cityview 可以删除 那就是老师写的麻烦 按照网址依赖，粘贴代码 https://github.com/crazyandcoder/citypicker

// Android 手动显示和隐藏软键盘  http://blog.csdn.net/h7870181/article/details/8332991

//点击回车 自动显示在哪个Editext上
//getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

public class RegistTrueActivity extends IActivity implements RegistTrueActivityView {

    private InputMethodManager imm;

    private RegistTrueActivityPresenter registTrueActivityPresenter;

    @BindView(R.id.registtrueactivity_fanhui)
    ImageView registtrueactivityFanhui;
    @BindView(R.id.registtrueactivity_denglu)
    TextView registtrueactivityDenglu;
    @BindView(R.id.registtrueactivity_etname)
    EditText registtrueactivityEtname;
    @BindView(R.id.registtrueactivity_etpwd)
    EditText registtrueactivityEtpwd;
    @BindView(R.id.registtrueactivity_tvsex)
    TextView registtrueactivityTvsex;
    @BindView(R.id.registtrueactivity_tvage)
    TextView registtrueactivityTvage;
    @BindView(R.id.registtrueactivity_tvlocal)
    TextView registtrueactivityTvlocal;
    @BindView(R.id.registtrueactivity_tvmine)
    EditText registtrueactivityTvmine;
    @BindView(R.id.registtrueactivity_btlogin)
    Button registtrueactivityBtlogin;

    @BindView(R.id.registactivity_id)
    LinearLayout linearLayout;

    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_true);
        ButterKnife.bind(this);

        Intent i = getIntent();
        phone = i.getStringExtra("phone");


        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        // 关闭软键盘 如果 显示 则隐藏  同理
        //        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        //强制隐藏键盘
        imm.hideSoftInputFromWindow(registtrueactivityTvmine.getWindowToken(), 0);
        //        强制显示键盘
        //        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);

        //隐藏键盘
        //        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        //        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        //        boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开
        registTrueActivityPresenter = new RegistTrueActivityPresenter(this);

        //点击回车 自动显示在哪个Editext上
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setupUI(linearLayout);

    }

    //    首先我们要先定义一个隐藏软键盘的工具类方法：　
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    //    我们只要找到根布局，然后让根布局自动找到其子组件，再递归注册监听器即可
    public void setupUI(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(RegistTrueActivity.this);  //Main.this是我的activity名
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }


    @OnClick({R.id.registtrueactivity_fanhui, R.id.registtrueactivity_denglu, R.id.registtrueactivity_tvsex, R.id.registtrueactivity_tvage, R.id.registtrueactivity_tvlocal, R.id.registtrueactivity_btlogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.registactivity_id:

                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                break;

            case R.id.registtrueactivity_fanhui:

                finish();

                break;
            case R.id.registtrueactivity_denglu:

                Intent i1 = new Intent(RegistTrueActivity.this, LoginActivity.class);
                startActivity(i1);

                break;
            case R.id.registtrueactivity_tvsex:
                showSexChooseDialog();

                break;
            case R.id.registtrueactivity_tvage:
                showAgeDialog();
                break;
            case R.id.registtrueactivity_tvlocal:

                CityPicker cityPicker = new CityPicker.Builder(RegistTrueActivity.this)
                        .textSize(20)
                        .title("地址选择")
                        .backgroundPop(0xa0000000)
                        .titleBackgroundColor("#234Dfa")
                        .titleTextColor("#000000")
                        .backgroundPop(0xa0000000)
                        .confirTextColor("#000000")
                        .cancelTextColor("#000000")
                        .province("江苏省")
                        .city("常州市")
                        .district("天宁区")
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(true)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(7)
                        .itemPadding(10)
                        .onlyShowProvinceAndCity(false)
                        .build();
                cityPicker.show();

                //监听方法，获取选择结果
                cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        //省份
                        String province = citySelected[0];
                        //城市
                        String city = citySelected[1];
                        //区县（如果设定了两级联动，那么该项返回空）
                        String district = citySelected[2];
                        //邮编
                        String code = citySelected[3];

                        registtrueactivityTvlocal.setText(province + "-" + city + "-" + district);
                        registtrueactivityTvlocal.setTextSize(13);
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(RegistTrueActivity.this, "已取消", Toast.LENGTH_LONG).show();
                    }
                });

                break;

            case R.id.registtrueactivity_btlogin:

                toData();

                break;
        }
    }

    private String[] sexArry = new String[]{"女", "男"};
    AlertDialog.Builder builder;

    private void showSexChooseDialog() {
        builder = new AlertDialog.Builder(RegistTrueActivity.this);
        builder.setTitle("请选择性别");
        builder.setItems(sexArry, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                registtrueactivityTvsex.setText(sexArry[which]);
            }
        });
        builder.show();
    }

    private void showAgeDialog() {
        final String[] ages = getResources().getStringArray(R.array.age);
        builder = new AlertDialog.Builder(RegistTrueActivity.this);
        builder.setTitle("请选择年龄");
        builder.setItems(ages, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                registtrueactivityTvage.setText(ages[which]);
            }
        });
        builder.show();

    }


    /**

        if (ClickLongUtils.isFastClick()) {
            return ;
        }
     * 判断所有的参数 非空
     * 注册 添加 草稿功能
     */
    private void toData() {

        registTrueActivityPresenter.vaildInfor(phone, registtrueactivityEtname.getText().toString().trim(), registtrueactivityTvsex.getText().toString().trim()
                                                , registtrueactivityTvage.getText().toString().trim(), registtrueactivityTvlocal.getText().toString().trim()
                                                , registtrueactivityTvmine.getText().toString().trim(), Md5Utils.getMD5(registtrueactivityEtpwd.getText().toString().trim()));

//        /**
//         * 防止抖动点击
//         *
//         * 给谁设置点击事件
//         * throttleFirst  （在多少时间内点击   时间单位）
//         *

//        这个方法要写在onCreate方法里 因为看源码 点击一次才会注册点击事件，写在这会有Bug要点击两次
//         */
//        RxView.clicks(registtrueactivityBtlogin).throttleFirst(1, TimeUnit.SECONDS)
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(@NonNull Object o) throws Exception {
//
//                        registTrueActivityPresenter.vaildInfor(phone, registtrueactivityEtname.getText().toString().trim(), registtrueactivityTvsex.getText().toString().trim()
//                                , registtrueactivityTvage.getText().toString().trim(), registtrueactivityTvlocal.getText().toString().trim()
//                                , registtrueactivityTvmine.getText().toString().trim(), Md5Utils.getMD5(registtrueactivityEtpwd.getText().toString().trim()));
//
//                    }
//                });


        //可以实现 监听 edittext 内容变化
        //        TextWatcher textWatcher = new TextWatcher() {
        //            @Override
        //            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //
        //            }
        //
        //            @Override
        //            public void onTextChanged(CharSequence s, int start, int before, int count) {
        //
        //            }
        //
        //            @Override
        //            public void afterTextChanged(Editable s) {
        //
        //            }
        //        } ;
        //
        //
        //        registtrueactivityEtname.addTextChangedListener(textWatcher);


        //        RxTextView.afterTextChangeEvents(new TextView(getActivity())).subscribe(new Consumer<TextViewAfterTextChangeEvent>() {
        //            @Override
        //            public void accept(@NonNull TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) throws Exception {
        ////                textViewAfterTextChangeEvent.view().getText().
        //            }
        //        })


    }


    @Override
    public void registerSuccess(RegisterBean registerBean) {


        //跳到上传形象照页面

        if (registerBean.getResult_code() == 200) {

            Intent i = new Intent(RegistTrueActivity.this, UpPhoneLoadActivity.class);
            startActivity(i);

        AppManager.getAppManager().finishActivity(RegistTrueActivity.class);
        AppManager.getAppManager().finishActivity(PhoneRegistActivity.class);


        } else {
            MyToast.makeText(IApplication.getApplication(), registerBean.getResult_message(), Toast.LENGTH_SHORT);
        }


    }

    @Override
    public void registerFailed(int code) {

        // 给一个用户友好的提示
        MyToast.makeText(IApplication.getApplication(), code + "", Toast.LENGTH_SHORT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        AppManager.getAppManager().finishActivity(this);
    }
}
