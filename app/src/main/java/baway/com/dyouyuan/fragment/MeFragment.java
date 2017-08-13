package baway.com.dyouyuan.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import baway.com.dyouyuan.IApplication;
import baway.com.dyouyuan.R;
import baway.com.dyouyuan.activity.LoginActivity;
import baway.com.dyouyuan.adapter.GuanZhongAdapter;
import baway.com.dyouyuan.bean.GuanZhongBean;
import baway.com.dyouyuan.bean.ZhuboBean;
import baway.com.dyouyuan.network.BaseObserver;
import baway.com.dyouyuan.network.RetrofitManager;
import baway.com.dyouyuan.utils.PreferencesUtils;
import baway.com.dyouyuan.zhibo.PLVideoViewActivity;
import baway.com.dyouyuan.zhibo.SWCameraStreamingActivity;

/**
 * 直播
 */

public class MeFragment extends Fragment {

    private Button bt_zhubo;
    private Button bt_guanzhong;
    private Button bt_denglu;
    private String zhuboBeanUrl;
    private ListView listview;
    private Gson gson;
    private List<GuanZhongBean.ListBean> list;
    private GuanZhongAdapter guanZhongAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fourth, container, false);
        bt_zhubo = (Button) view.findViewById(R.id.fragment_foutrth_btnzhubo);
        bt_guanzhong = (Button) view.findViewById(R.id.fragment_foutrth_guanzhong);
        bt_denglu = (Button) view.findViewById(R.id.fragment_foutrth_denglu);
        listview = (ListView) view.findViewById(R.id.fragment_foutrth_listview);

        gson = new Gson();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Boolean second = PreferencesUtils.getValueByKey(getActivity(), "second", false);

        if (second) {

            bt_denglu.setVisibility(View.GONE);
            bt_zhubo.setVisibility(View.VISIBLE);
            bt_guanzhong.setVisibility(View.VISIBLE);

            //没登录
        } else {

            bt_denglu.setVisibility(View.VISIBLE);
            bt_zhubo.setVisibility(View.INVISIBLE);
            bt_guanzhong.setVisibility(View.INVISIBLE);
        }

//主播
        bt_zhubo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> map = new HashMap<>();

                RetrofitManager.get("http://qhb.2dyt.com/MyInterface/userAction_live.action?user.sign=1&live.type=1", map, new BaseObserver() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("login result 主播 = " + result);

                        ZhuboBean zhuboBean = gson.fromJson(result, ZhuboBean.class);

                        System.out.println("login result 主播 code= " +  zhuboBean.getResult_code()+"");


                        if (zhuboBean != null && zhuboBean.getResult_code() == 200) {

                            zhuboBeanUrl = zhuboBean.getUrl();

                            Intent intent = new Intent(IApplication.application, SWCameraStreamingActivity.class);

                            intent.putExtra("stream_json_str", zhuboBeanUrl);

                            getActivity().startActivity(intent);

                        } else {

                        }
                    }

                    @Override
                    public void onFailed(int code) {
                    }
                });

            }
        });

        //观众
        bt_guanzhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, String> map = new HashMap<>();

                RetrofitManager.get("http://qhb.2dyt.com/MyInterface/userAction_live.action?user.sign=1&live.type=2", map, new BaseObserver() {
                    @Override
                    public void onSuccess(String result) {

                        System.out.println("login result = 观众 " + result);

                        GuanZhongBean guanZhongBean = gson.fromJson(result, GuanZhongBean.class);

                        if (guanZhongBean != null && guanZhongBean.getResult_code() == 200) {

                            list = guanZhongBean.getList();

                            guanZhongAdapter = new GuanZhongAdapter(IApplication.application,list);

                            listview.setVisibility(View.VISIBLE);


                            listview.setAdapter(guanZhongAdapter);
                            guanZhongAdapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onFailed(int code) {
                    }
                });

            }
        });

        //登陆
        bt_denglu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(IApplication.application, LoginActivity.class);
                startActivity(intent);
            }
        });


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String playUrl = list.get(position).getPlayUrl();

                Intent intent = new Intent(IApplication.application, PLVideoViewActivity.class);

                intent.putExtra("videoPath",playUrl);

                getActivity().startActivity(intent);
            }
        });
    }

}
