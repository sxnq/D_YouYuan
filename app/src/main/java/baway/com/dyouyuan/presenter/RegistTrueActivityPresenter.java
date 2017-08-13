package baway.com.dyouyuan.presenter;

import baway.com.dyouyuan.bean.RegisterBean;
import baway.com.dyouyuan.model.RegistTrueModel;
import baway.com.dyouyuan.view.RegistTrueActivityView;

/**
 * Created by : Xunqiang
 * 2017/7/6 12:02
 */

public class RegistTrueActivityPresenter {

    private RegistTrueModel registTrueModel ;
    RegistTrueActivityView registTrueActivityView;

    public RegistTrueActivityPresenter( RegistTrueActivityView registTrueActivityView){
        registTrueModel = new RegistTrueModel();
        this.registTrueActivityView = registTrueActivityView;
    }

    public void vaildInfor(String phone, String nickname, String sex, String age, String area, String introduce, String password){


        //非空判断

        registTrueModel.getData(phone, nickname, sex, age, area, introduce, password, new RegistTrueModel.RegisterInforFragmentDataListener() {
            @Override
            public void onSuccess(RegisterBean registerBean) {


                registTrueActivityView.registerSuccess(registerBean);

            }

            @Override
            public void onFailed(int code) {

                registTrueActivityView.registerFailed(code);
            }
        });

    }
}
