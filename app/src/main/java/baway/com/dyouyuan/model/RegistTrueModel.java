package baway.com.dyouyuan.model;

import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import baway.com.dyouyuan.IApplication;
import baway.com.dyouyuan.bean.RegisterBean;
import baway.com.dyouyuan.core.JNICore;
import baway.com.dyouyuan.core.SortUtils;
import baway.com.dyouyuan.network.BaseObserver;
import baway.com.dyouyuan.network.RetrofitManager;
import baway.com.dyouyuan.utils.PreferencesUtils;

/**
 * Created by : Xunqiang
 * 2017/7/6 12:04
 */

public class RegistTrueModel implements RegistTrueActivityModelInterface{


    private String jingdu;
    private String weidu;

    public void getData(String phone, String nickname, String sex, String age, String area, String introduce, String password, final RegisterInforFragmentDataListener listener){

        jingdu = PreferencesUtils.getValueByKey(IApplication.getApplication(), "jingdu","0");
        weidu = PreferencesUtils.getValueByKey(IApplication.getApplication(), "weidu","0");

        Map<String,String> map = new HashMap<String,String>();
        map.put("user.phone",phone);
        map.put("user.nickname",nickname);
        map.put("user.password",password);
        map.put("user.gender",sex);
        map.put("user.area",area);
        map.put("user.age",age);
        map.put("user.introduce",introduce);

        if (!jingdu.equals("0") && !weidu.equals("0")){

        map.put("user.lat", jingdu);
        map.put("user.lng", weidu);
        }

        JNICore jniCore = new JNICore();

        String sign =  jniCore.getSign(SortUtils.getMapResult(SortUtils.sortString(map))) ;
        map.put("user.sign",sign);

//        http://169.254.69.102:8080/MyInterface/userAction_add.action
//        http://qhb.2dyt.com/MyInterface/userAction_add.action

        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_add.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {


                Log.e("xunxun",result);

                Gson gson = new Gson();
                RegisterBean registerBean = gson.fromJson(result, RegisterBean.class);

                if(registerBean.getResult_code() == 200){
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"phone",registerBean.getData().getPhone());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"password",registerBean.getData().getPassword());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"yxpassword",registerBean.getData().getYxpassword());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"uid",registerBean.getData().getUserId());
                    PreferencesUtils.addConfigInfo(IApplication.getApplication(),"nickname",registerBean.getData().getNickname());
                }
                listener.onSuccess(registerBean);

            }

            @Override
            public void onFailed(int code) {

                Log.e("registcode",code+"");

                listener.onFailed(code);
            }
        });




    }

}

