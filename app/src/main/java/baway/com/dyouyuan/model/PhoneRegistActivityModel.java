package baway.com.dyouyuan.model;

import cn.smssdk.SMSSDK;

/**
 * Created by : Xunqiang
 * 2017/7/5 18:44
 */

public class PhoneRegistActivityModel implements PhoneRegistActivityModelInterface{
    @Override

    public void getVerificationCode(String phone){

        SMSSDK.getVerificationCode("86", phone);

    }
}
