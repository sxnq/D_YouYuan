package baway.com.dyouyuan.model;

import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import baway.com.dyouyuan.bean.UserInfoBean;
import baway.com.dyouyuan.core.JNICore;
import baway.com.dyouyuan.core.SortUtils;
import baway.com.dyouyuan.jiekou.Xiangqing;
import baway.com.dyouyuan.network.BaseObserver;
import baway.com.dyouyuan.network.RetrofitManager;

/**
 * Created by : Xunqiang
 * 2017/7/12 17:54
 */

public class XiangQingModel {

    Xiangqing xiangqing ;

    public XiangQingModel(Xiangqing xiangqing) {
        this.xiangqing = xiangqing;
    }

    public void getshuju(String user_id) {

        Map map = new HashMap<String,String>();
        map.put("user.userId",user_id);


        JNICore jniCore = new JNICore();
        String sign =  jniCore.getSign(SortUtils.getMapResult(SortUtils.sortString(map))) ;
        map.put("user.sign",sign);


        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_selectUserById.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {


                Log.e("xunxungetshuju",result);

                Gson gson = new Gson();
                UserInfoBean userInfoBean = gson.fromJson(result, UserInfoBean.class);

                if (userInfoBean!=null && userInfoBean.getResult_code() == 200){

                    Log.e("xunxun123getshuju",userInfoBean.toString());

                    xiangqing.onSuccess(userInfoBean);
                }else {
                    xiangqing.onFail();

                }


            }

            @Override
            public void onFailed(int code) {
                xiangqing.onFail();
            }
        });


    }

    //加好友
    public void getshujuAddFriend(String user_id) {

        Map map = new HashMap<String,String>();
        map.put("relationship.friendId",user_id);


        JNICore jniCore = new JNICore();
        String sign =  jniCore.getSign(SortUtils.getMapResult(SortUtils.sortString(map))) ;
        map.put("user.sign",sign);


        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_addFriends.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {


                Log.e("xunxungetshujuAddFriend",result);

//                Gson gson = new Gson();
//
//                AddFriendsBean addFriendsBean = gson.fromJson(result, AddFriendsBean.class);
//
//                if (addFriendsBean!=null && addFriendsBean.getResult_code() == 200){
//
//                    Log.e("xunxun123addfriend",addFriendsBean.toString());
//
//                    xiangqing.onSuccessAddFriend(addFriendsBean);
//                }else {
//                    xiangqing.onFail();
//
//                }
            }

            @Override
            public void onFailed(int code) {
                xiangqing.onFail();
            }
        });
    }

}
