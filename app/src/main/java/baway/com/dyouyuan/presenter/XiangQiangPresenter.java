package baway.com.dyouyuan.presenter;

import baway.com.dyouyuan.bean.AddFriendsBean;
import baway.com.dyouyuan.bean.UserInfoBean;
import baway.com.dyouyuan.jiekou.Xiangqing;
import baway.com.dyouyuan.model.XiangQingModel;
import baway.com.dyouyuan.view.XiangQiangActivityView;


public class XiangQiangPresenter implements Xiangqing{


    private XiangQingModel xiangQingModel ;
    XiangQiangActivityView xiangQiangActivityView;

    public XiangQiangPresenter(XiangQiangActivityView xiangQiangActivityView){
        xiangQingModel = new XiangQingModel(this);
        this.xiangQiangActivityView = xiangQiangActivityView;
    }


    public void modelgetShuju(String user_id){


        xiangQingModel.getshuju(user_id);

    }

    public void modelgetShujuAddFriend(String user_id){

        xiangQingModel.getshujuAddFriend(user_id);
    }


    //成功失败
    @Override
    public void onSuccess(UserInfoBean userInfoBean) {

        xiangQiangActivityView.onRegisterSuccess(userInfoBean);

    }

    //添加好友成功
    @Override
    public void onSuccessAddFriend(AddFriendsBean addFriendsBean) {
        xiangQiangActivityView.onRegisterSuccessAddFriend(addFriendsBean);
    }

    @Override
    public void onFail() {

    }
}
