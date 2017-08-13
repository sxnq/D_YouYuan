package baway.com.dyouyuan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hyphenate.easeui.EaseConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import baway.com.dyouyuan.IApplication;
import baway.com.dyouyuan.MainActivity;
import baway.com.dyouyuan.R;
import baway.com.dyouyuan.adapter.SecondFragmentAdapter;
import baway.com.dyouyuan.bean.AllFriendBean;
import baway.com.dyouyuan.core.JNICore;
import baway.com.dyouyuan.core.SortUtils;
import baway.com.dyouyuan.huanxinui.ChatActivity;
import baway.com.dyouyuan.network.BaseObserver;
import baway.com.dyouyuan.network.RetrofitManager;
import baway.com.dyouyuan.utils.PreferencesUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

//http://blog.csdn.net/lmj623565791/article/details/38238749

public class FeedFragment extends Fragment {  //好友

    @BindView(R.id.findfragment_button)
    Button findfragmentButton;
    Unbinder unbinder;

    static boolean flag; //true 刷新


    //定义下拉刷新列表控件
    private PullToRefreshListView userDefinedList_pull_refresh_list;

    private List<AllFriendBean.DataBean> data;
    private List<AllFriendBean.DataBean> datazong;
    private SecondFragmentAdapter secondFragmentAdapter;
    private AllFriendBean allFriendBean;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_second, container, false);
        unbinder = ButterKnife.bind(this, view);

        Boolean second = PreferencesUtils.getValueByKey(getActivity(), "second", false);

        userDefinedList_pull_refresh_list = (PullToRefreshListView) view.findViewById(R.id.userDefinedList_pull_refresh_list);

        secondFragmentAdapter = new SecondFragmentAdapter();


        Log.e("second", second + "");

        if (second) {


            findfragmentButton.setVisibility(View.GONE);


            //没登录
        } else {

            userDefinedList_pull_refresh_list.setVisibility(View.GONE);
        }

        userDefinedList_pull_refresh_list.setMode(PullToRefreshBase.Mode.BOTH);

        datazong = new ArrayList<>();


        return view;
    }



    private void initdata(long currenttime) {

        Map map = new HashMap<String, String>();

        map.put("user.currenttimer", currenttime + "");

        String sign1 = JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));
        map.put("user.sign", sign1);


        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_selectAllUserAndFriend.action", map, new BaseObserver() {
                    @Override
                    public void onSuccess(String result) {


                        Log.e("feedfragment", result);

                        Gson gson = new Gson();

                        allFriendBean = gson.fromJson(result, AllFriendBean.class);

                        if (allFriendBean != null && allFriendBean.getResult_code() == 200) {

                            data = allFriendBean.getData();
                            datazong.addAll(data);


                            Log.e("feedfragment", data.size()+"");

                            secondFragmentAdapter.setData(allFriendBean,flag);
                        }
                    }

                    @Override
                    public void onFailed(int code) {

                    }
                }
        );
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        long l = System.currentTimeMillis();
        initdata(l);

        userDefinedList_pull_refresh_list.setAdapter(secondFragmentAdapter);

        userDefinedList_pull_refresh_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            //下拉刷新
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                userDefinedList_pull_refresh_list.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        userDefinedList_pull_refresh_list.onRefreshComplete();

                        flag = true;

                        long lxialashuaxin = System.currentTimeMillis();

                       initdata(lxialashuaxin);

                    }
                },2000);

            }

            //上拉加载
            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                userDefinedList_pull_refresh_list.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        flag = false;

                        if (allFriendBean.getData().size() != 0 && allFriendBean!=null) {

                            System.out.println("refreshView = " + allFriendBean.getData().size());

                            long createtime = datazong.get(datazong.size()-1).getRelationtime();
                            Log.e("createtime","===="+createtime);
                            initdata(createtime);

                        }

                        userDefinedList_pull_refresh_list.onRefreshComplete();

                    }
                },2000);


            }
        });



        //长按点击事件
//        userDefinedList_pull_refresh_list.getRefreshableView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, final View view,
//                                           final int position-1, long id) {
//
//
//
//                Intent i = new Intent(getActivity(), ChatActivity.class);
//                startActivity(i);
//
//                return false;
//            }
//        });

        userDefinedList_pull_refresh_list.getRefreshableView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                
                Intent i = new Intent(getActivity(), ChatActivity.class);
                i.putExtra(EaseConstant.EXTRA_USER_ID,datazong.get(position-1).getUserId()+"");
                i.putExtra(EaseConstant.EXTRA_USER_name,datazong.get(position-1).getNickname());
                i.putExtra(EaseConstant.EXTRA_USER_touxiang,datazong.get(position-1).getImagePath());

                                startActivity(i);

            }
        });

    }


    @OnClick(R.id.findfragment_button)
    public void onViewClicked() {

        PreferencesUtils.addConfigInfo(IApplication.getApplication(),"second",false);

        Intent i = new Intent(getActivity(), MainActivity.class);
        startActivity(i);
    }
}