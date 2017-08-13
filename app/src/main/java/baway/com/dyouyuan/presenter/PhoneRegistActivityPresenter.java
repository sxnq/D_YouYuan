package baway.com.dyouyuan.presenter;

import android.text.TextUtils;

import baway.com.dyouyuan.model.PhoneRegistActivityModel;
import baway.com.dyouyuan.utils.PhoneCheckUtils;
import baway.com.dyouyuan.view.PhoneRegistActivityView;

/**
 * Created by : Xunqiang
 * 2017/7/5 18:41
 */

public class PhoneRegistActivityPresenter {

    public PhoneRegistActivityModel phoneRegistActivityModel ;
    PhoneRegistActivityView phoneRegistActivityView;

    public PhoneRegistActivityPresenter(PhoneRegistActivityView phoneRegistActivityView) {
        phoneRegistActivityModel = new PhoneRegistActivityModel();
        this.phoneRegistActivityView = phoneRegistActivityView;
    }

    public void getVerificationCode(String phone){


        if(TextUtils.isEmpty(phone)){

            phoneRegistActivityView.phoneError(1);
            return;
        }



        if(!PhoneCheckUtils.isChinaPhoneLegal(phone)){
            phoneRegistActivityView.phoneError(2);
            return;
        }

        phoneRegistActivityModel.getVerificationCode(phone);


        phoneRegistActivityView.showTimer();

    }



    public void nextStep(String phone,String sms){

        if(TextUtils.isEmpty(phone)){

            phoneRegistActivityView.phoneError(1);
            return;
        }



        if(!PhoneCheckUtils.isChinaPhoneLegal(phone)){
            phoneRegistActivityView.phoneError(2);
            return;
        }

        //判断验证码是否合法  验证码是4为数字 \\d{4} sms 非空

if (PhoneCheckUtils.four(sms)){
    phoneRegistActivityView.toNextPage();
}else{
    phoneRegistActivityView.phoneError(3);
    return;
}
    }



}
