package baway.com.dyouyuan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import baway.com.dyouyuan.Custom.EditTextPassword;
import baway.com.dyouyuan.IApplication;
import baway.com.dyouyuan.R;
import baway.com.dyouyuan.bean.RegisterBean;
import baway.com.dyouyuan.cipher.Md5Utils;
import baway.com.dyouyuan.cipher.aes.JNCryptorUtils;
import baway.com.dyouyuan.cipher.rsa.RsaUtils;
import baway.com.dyouyuan.core.JNICore;
import baway.com.dyouyuan.core.SortUtils;
import baway.com.dyouyuan.network.BaseObserver;
import baway.com.dyouyuan.network.RetrofitManager;
import baway.com.dyouyuan.utils.ClickLongUtils;
import baway.com.dyouyuan.utils.PreferencesUtils;
import baway.com.dyouyuan.utils.ToastUtils;
import baway.com.dyouyuan.utils.keyboard.KeyBoardHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends Activity implements EditTextPassword.DrawableRightListener,KeyBoardHelper.OnKeyBoardStatusChangeListener{


    @BindView(R.id.loginactivity_fanhui)
    ImageView loginactivityFanhui;
    @BindView(R.id.loginactivity_zhuce)
    TextView loginactivityZhuce;
    @BindView(R.id.loginactivity_etphone)
    EditText loginactivityEtphone;
    @BindView(R.id.loginactivity_etpwd)
    EditTextPassword loginactivityEtpwd;
    @BindView(R.id.loginactivity_btlogin)
    Button loginactivityBtlogin;
    @BindView(R.id.loginactivity_tvphone)
    TextView loginactivityTvphone;
    @BindView(R.id.loginactivity_id)
    LinearLayout linearLayout;

    private boolean mIsShow = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //设置输入为密码模式
        loginactivityEtpwd.setInputType(InputType.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        loginactivityEtpwd.setDrawableRightListener(LoginActivity.this);

        //点击回车 自动显示在哪个Editext上
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //点击外部软键盘消失
        setupUI(linearLayout);

        //布局  软键盘
        KeyBoardHelper keyBoardHelper = new KeyBoardHelper(this);
        keyBoardHelper.onCreate();
        keyBoardHelper.setOnKeyBoardStatusChangeListener(this);





    }

    @OnClick({R.id.loginactivity_fanhui, R.id.loginactivity_zhuce, R.id.loginactivity_etpwd, R.id.loginactivity_btlogin, R.id.loginactivity_tvphone})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.loginactivity_fanhui:

                finish();

                break;
            case R.id.loginactivity_zhuce:

                Intent i = new Intent(LoginActivity.this,PhoneRegistActivity.class);
                startActivity(i);

                break;

            case R.id.loginactivity_btlogin:

                denglu();



//                //随机数 rsa 公钥进行加密
//                //  aes 加密  拿着加密后的
//                String random = RsaUtils.getInstance().createRandom();
//                String rsaKey = RsaUtils.getInstance().createRsaSecret(getApplicationContext(), random);
//                String mobile = JNCryptorUtils.getInstance().encryptData("18511085102", getApplicationContext(), random);
//
//                System.out.println("random = " + random);
//                System.out.println("rsaKey = " + rsaKey);
//                System.out.println("mobile = " + mobile);
//
////                ==================================
//                Map map = new HashMap<String,String>();
//                map.put("username","18511085102");
//                map.put("password","18511085102");
//                map.put("timer","11111222222");
//                //排序
//                //        map.put("password","18511085102");
//                //        map.put("timer","11111222222");
//                //map.put("username","18511085102");
//                //拼接
//                //        password=18511085102&timer=11111222222&username=18511085102
//
//
//                //        jni   appkey= fgjkfggkf
//                //        appkey= fgjkfggkf&password=18511085102&timer=11111222222&username=18511085102
//                //        appkey= md5(appkey= fgjkfggkf&= + (password=18511085102&timer=11111222222&username=18511085102)) return  sign
//                //        32位长度的字符串 sign
//                //        map 排序 ascd 排序  拼接成字符串  调用jni 中方法 返回一个sign  传递给服务器
//                map.put("sign","12fffff");

                break;
            case R.id.loginactivity_tvphone:

                Intent i1 = new Intent(LoginActivity.this,PhoneLoginActivity.class);
                startActivity(i1);

                break;
        }
    }

    private void denglu() {

        if (ClickLongUtils.isFastClick()) {
            return ;
        }


        String phone =   loginactivityEtphone.getText().toString().trim() ;
        String password = loginactivityEtpwd.getText().toString().trim();

        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)){



            //生成随机16位字符数
        String randomKey =   RsaUtils.getStringRandom(16);

        //16位随机数和 rsa 加密
        String rsaRandomKey =   RsaUtils.getInstance().createRsaSecret(IApplication.getApplication(),randomKey);

        //给电话号码  和 aes 加密
        String aesPhone =   JNCryptorUtils.getInstance().encryptData(phone,IApplication.getApplication(),randomKey);

        //给服务器传递参数
        Map map = new HashMap<String,String>();
        map.put("user.phone",aesPhone);
        map.put("user.password", Md5Utils.getMD5(password));
        map.put("user.secretkey",rsaRandomKey);


        String sign =  JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map))) ;
        map.put("user.sign",sign);

//        http://169.254.69.102:8080/MyInterface/userAction_add.action
        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_login.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {

                System.out.println("xunxun result = " + result);


                Gson gson = new Gson();
                RegisterBean registerBean = gson.fromJson(result, RegisterBean.class);

                System.out.println("xunxun result code= " + registerBean.getResult_code());

                if (registerBean.getResult_code() == 200 && registerBean.getData()!=null){

                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"second",true);

                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"phone",registerBean.getData().getPhone());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"password",registerBean.getData().getPassword());

                   //这两个参数用于登录环信
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"yxpassword",registerBean.getData().getYxpassword());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"uid",registerBean.getData().getUserId());

                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"nickname",registerBean.getData().getNickname());


                    Toast.makeText(LoginActivity.this, "登陆成功，即将跳转" , Toast.LENGTH_SHORT).show();
                    //登陆环信
                    IApplication.getApplication().emLogin();


                Intent i = new Intent(LoginActivity.this,RadioGroupActivity.class);
                startActivity(i);

                finish();

                }


//                AppManager.getAppManager().finishActivity(LoginActivity.class);

            }


            @Override
            public void onFailed(int code) {

            }
        });
    }

    }

    @Override
    public void onDrawableRightClick() {

        if (mIsShow) {
            loginactivityEtpwd.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.show_password_icon, 0) ;
            loginactivityEtpwd.setInputType(InputType.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            loginactivityEtpwd.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.close_password_icon, 0) ;
            loginactivityEtpwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        mIsShow = !mIsShow ;
    }

    //    首先我们要先定义一个隐藏软键盘的工具类方法：　
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    //    我们只要找到根布局，然后让根布局自动找到其子组件，再递归注册监听器即可
    public void setupUI(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {
            view.setOnTouchListener( new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(LoginActivity.this);  //Main.this是我的activity名
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


    //当前页面布局大小发生改变接口返回
    @Override
    public void OnKeyBoardPop(int keyBoardheight) {

        //在登录界面获取键盘高度
        PreferencesUtils.addConfigInfo(this,"jianpan",keyBoardheight);
        ToastUtils.showToast(this,"键盘高度已经存入"+keyBoardheight);


        Log.e("keyBoardheight",keyBoardheight+"");

    }

    @Override
    public void OnKeyBoardClose(int oldKeyBoardheight) {
        Log.e("oldKeyBoardheight",oldKeyBoardheight+"");

    }
}