package baway.com.dyouyuan.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;

import java.util.List;

import baway.com.dyouyuan.IApplication;
import baway.com.dyouyuan.MainActivity;
import baway.com.dyouyuan.R;
import baway.com.dyouyuan.bean.AddFriendsBean;
import baway.com.dyouyuan.bean.UserInfoBean;
import baway.com.dyouyuan.network.cookie.CookiesManager;
import baway.com.dyouyuan.photoview.PicShowDialog;
import baway.com.dyouyuan.presenter.XiangQiangPresenter;
import baway.com.dyouyuan.utils.PreferencesUtils;
import baway.com.dyouyuan.view.XiangQiangActivityView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XiangQingActivity extends Activity implements XiangQiangActivityView,View.OnClickListener{

    @BindView(R.id.xiangqing_touxiang)
    ImageView xiangqingTouxiang;
    @BindView(R.id.xiangqing_zuoshangjiao)
    ImageView xiangqingZuoshangjiao;
    @BindView(R.id.xiangqing_youshangjiao)
    ImageView xiangqingYoushangjiao;
    @BindView(R.id.xiangqing_name)
    TextView xiangqingName;
    @BindView(R.id.xiangqing_age)
    TextView xiangqingAge;
    @BindView(R.id.xiangqing_guangzhu)
    RadioButton xiangqingGuangzhu;
    @BindView(R.id.xiangqing_weixinqq)
    RadioButton xiangqingWeixinqq;

    @BindView(R.id.xiangqing_addfriend)
    Button bt1 ;

    @BindView(R.id.xiangqing_tuichu)
    Button bt2 ;



    @BindView(R.id.id_gallery)
    LinearLayout linearLayout;

    private LayoutInflater mInflater;

    private XiangQiangPresenter xiangQiangPresenter;

    private String location;

    private int itemWidth ;
    private ImageView img;
    private List<UserInfoBean.DataBean.PhotolistBean> photolist;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiang_qing);
        ButterKnife.bind(this);


        user_id = getIntent().getStringExtra("user_id");

        location = getIntent().getStringExtra("location");


        xiangQiangPresenter = new XiangQiangPresenter(this);
        xiangQiangPresenter.modelgetShuju(user_id);

        mInflater = LayoutInflater.from(this);
    }

    @OnClick({R.id.xiangqing_zuoshangjiao, R.id.xiangqing_youshangjiao, R.id.xiangqing_guangzhu, R.id.xiangqing_weixinqq,R.id.xiangqing_addfriend,R.id.xiangqing_tuichu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.xiangqing_zuoshangjiao:

                finish();

                break;
            case R.id.xiangqing_youshangjiao:
                break;

            case R.id.xiangqing_guangzhu:
                break;
            case R.id.xiangqing_weixinqq:
                break;


            //添加好友
            case R.id.xiangqing_addfriend:

                Boolean second = PreferencesUtils.getValueByKey(XiangQingActivity.this, "second", false);

                if (second){   //登陆了

                xiangQiangPresenter.modelgetShujuAddFriend(user_id);

                Toast.makeText(XiangQingActivity.this, "已经添加", Toast.LENGTH_SHORT).show();

                }else{


                    Toast.makeText(XiangQingActivity.this, "请点击两次返回去登陆", Toast.LENGTH_SHORT).show();
                }


                break;


            //退出登录
            case R.id.xiangqing_tuichu:


                new CookiesManager(IApplication.application).removeAllCookie();

                //退出登录  环信
                EMClient.getInstance().logout(true);


                Intent i = new Intent(IApplication.application, MainActivity.class);
                startActivity(i);

                finish();

                break;
        }
    }

    @Override
    public void onRegisterSuccess(UserInfoBean userInfoBean) {

       final UserInfoBean.DataBean data = userInfoBean.getData();

        xiangqingName.setText(data.getNickname());
        xiangqingAge.setText(data.getArea()+" , "+location);


        Glide.with(XiangQingActivity.this).load(data.getImagePath()).error(R.mipmap.ic_launcher).into(xiangqingTouxiang);

        photolist = data.getPhotolist();

        if (photolist.size()!=0){

        for (int i = 0; i < photolist.size() ; i++) {

            img = new ImageView(this);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(400,400);
            params.setMargins(100,100,100,100);
            img.setLayoutParams(params);

            Glide.with(XiangQingActivity.this).load(photolist.get(i).getImagePath()).error(R.mipmap.ic_launcher).into(img);

            linearLayout.addView(img);


            img.setOnClickListener(this);

        }


        }
    }

    @Override
    public void onRegisterSuccessAddFriend(AddFriendsBean addFriendsBean) {

        Log.e("addFriendsBean",addFriendsBean+"");

        Toast.makeText(XiangQingActivity.this, "添加成功", Toast.LENGTH_SHORT).show();

    }


    //子条目点击事件
    @Override
    public void onClick(View v) {

        Log.e("11111",photolist.size()+"");

        //点击位置及对象传入dialog
        PicShowDialog dialog=new PicShowDialog(XiangQingActivity.this,photolist,1);
        dialog.show();
    }




    @Override
    public void onRegisterFailed() {

    }




}
