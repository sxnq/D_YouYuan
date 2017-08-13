package baway.com.dyouyuan.view;

import baway.com.dyouyuan.bean.AddFriendsBean;
import baway.com.dyouyuan.bean.UserInfoBean;

/**
 * Created by : Xunqiang
 * 2017/7/12 17:53
 */

public interface XiangQiangActivityView {

    public void onRegisterSuccess(UserInfoBean userInfoBean);
    public void onRegisterSuccessAddFriend(AddFriendsBean addFriendsBean);
    public void onRegisterFailed();

}
