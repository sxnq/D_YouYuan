package baway.com.dyouyuan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import baway.com.dyouyuan.R;
import baway.com.dyouyuan.base.IActivity;
import baway.com.dyouyuan.presenter.PhoneRegistActivityPresenter;
import baway.com.dyouyuan.utils.MyToast;
import baway.com.dyouyuan.view.PhoneRegistActivityView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


// Mob官网 短信验证 http://wiki.mob.com/sdk-sms-android-3-0-0/

public class PhoneRegistActivity extends IActivity implements PhoneRegistActivityView{

    @BindView(R.id.phoneregistactivity_fanhui)
    ImageView phoneregistactivityFanhui;
    @BindView(R.id.phoneregistactivity_etphone)
    EditText phoneregistactivityEtphone;
    @BindView(R.id.phoneregistactivity_yanzhengma)
    EditText phoneregistactivityYanzhengma;
    @BindView(R.id.phoneregistactivity_bthuoquyanzhengma)
    Button phoneregistactivityBthuoquyanzhengma;
    @BindView(R.id.phoneregistactivity_btregist)
    Button phoneregistactivityBtregist;
    private Unbinder bind;

    private PhoneRegistActivityPresenter phoneRegistActivityPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_regist);
        bind = ButterKnife.bind(this);

        SMSSDK.initSDK(PhoneRegistActivity.this,"1f3017ab69eb2","3bbf8da2fffc2732c84e84bc96606e4c");

        // 创建EventHandler对象
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable)data;
                } else {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        // 处理你自己的逻辑
                    }
                }
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);

        phoneRegistActivityPresenter = new PhoneRegistActivityPresenter(this);

        //点击回车 自动显示在哪个Editext上
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    EventHandler eventHandler;

    @OnClick({R.id.phoneregistactivity_fanhui, R.id.phoneregistactivity_bthuoquyanzhengma, R.id.phoneregistactivity_btregist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.phoneregistactivity_fanhui:
                finish();
                break;

            case R.id.phoneregistactivity_bthuoquyanzhengma:

                phoneRegistActivityPresenter.getVerificationCode(phoneregistactivityEtphone.getText().toString().trim());

                phoneRegistActivityPresenter.nextStep(phoneregistactivityEtphone.getText().toString().trim(),phoneregistactivityYanzhengma.getText().toString().trim());

                break;

            case R.id.phoneregistactivity_btregist:


                phoneRegistActivityPresenter.nextStep(phoneregistactivityEtphone.getText().toString().trim(),phoneregistactivityYanzhengma.getText().toString().trim());


                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();

        if (eventHandler!=null){

            SMSSDK.unregisterEventHandler(eventHandler);
        }
    }

    @Override
    public void phoneError(int type) {


        switch (type){
            case 1:
                MyToast.makeText(PhoneRegistActivity.this,"手机号码不能为空", Toast.LENGTH_SHORT);

                break;
            case 2:
                MyToast.makeText(PhoneRegistActivity.this,"手机格式不正确",Toast.LENGTH_SHORT);
                break;

            case 3:
                MyToast.makeText(PhoneRegistActivity.this,"验证码不是四位",Toast.LENGTH_SHORT);
                break;

            case 4:
                MyToast.makeText(PhoneRegistActivity.this,"请输入验证码",Toast.LENGTH_SHORT);
                break;
        }


    }

    @Override
    public void showTimer() {

        phoneregistactivityBthuoquyanzhengma.setClickable(false);

        Observable.interval(0,1,TimeUnit.SECONDS)
                .take(30)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@io.reactivex.annotations.NonNull Long aLong) throws Exception {
                        return 29 - aLong;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {



                        //                        d.dispose();

                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {

                        System.out.println("aLong = " + aLong);
                        phoneregistactivityBthuoquyanzhengma.setText(aLong+" S ");

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        phoneregistactivityBthuoquyanzhengma.setClickable(true);
                        phoneregistactivityBthuoquyanzhengma.setText(getText(R.string.register_sms));


                    }
                });




    }

    @Override
    public void toNextPage() {

        Intent i = new Intent(PhoneRegistActivity.this,RegistTrueActivity.class);
        i.putExtra("phone",phoneregistactivityEtphone.getText().toString().trim());
        startActivity(i);
//        finish();
    }
}
