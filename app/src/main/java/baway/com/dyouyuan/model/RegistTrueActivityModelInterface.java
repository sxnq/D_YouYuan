package baway.com.dyouyuan.model;

import baway.com.dyouyuan.bean.RegisterBean;

/**
 * Created by : Xunqiang
 * 2017/7/6 12:07
 */

public interface RegistTrueActivityModelInterface {

    public void getData(String phone, String nickname, String sex, String age, String area, String introduce, String password, RegisterInforFragmentDataListener listener);


    public interface RegisterInforFragmentDataListener {

        public void onSuccess(RegisterBean registerBean);

        public void onFailed(int code);

    }
}
