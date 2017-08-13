package baway.com.dyouyuan.view;

/**
 * Created by : Xunqiang
 * 2017/7/5 18:34
 */

public interface PhoneRegistActivityView {

    /**
     * 1 表示手机号码为空
     * 2 手机号码 不合法
     * 3.验证码不是四位
     */
    public void phoneError(int type);

    // 显示倒计时
    public void showTimer();

    //下一个页面
    public void toNextPage();

}
