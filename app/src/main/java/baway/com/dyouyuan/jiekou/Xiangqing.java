package baway.com.dyouyuan.jiekou;

import baway.com.dyouyuan.bean.AddFriendsBean;
import baway.com.dyouyuan.bean.UserInfoBean;

/**
 * Created by : Xunqiang
 * 2017/7/12 18:00
 */

public interface Xiangqing {

    public void onSuccess(UserInfoBean userInfoBean);
    public void onSuccessAddFriend(AddFriendsBean addFriendsBean);
    public void onFail();


}
