package baway.com.dyouyuan.view;

import baway.com.dyouyuan.bean.RegisterBean;

/**
 * Created by : Xunqiang
 * 2017/7/5 18:34
 */

public interface RegistTrueActivityView {
    public void registerSuccess(RegisterBean registerBean);
    public void registerFailed(int code);

}
