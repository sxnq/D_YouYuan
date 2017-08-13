package baway.com.dyouyuan.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import baway.com.dyouyuan.R;
import baway.com.dyouyuan.adapter.SingleAdapter;
import baway.com.dyouyuan.adapter.SingleAdapter2;
import baway.com.dyouyuan.bean.DataBean;
import baway.com.dyouyuan.bean.News;
import baway.com.dyouyuan.core.JNICore;
import baway.com.dyouyuan.core.SortUtils;
import baway.com.dyouyuan.footer.LoadMoreFooterView;
import baway.com.dyouyuan.network.BaseObserver;
import baway.com.dyouyuan.network.RetrofitManager;

//http://qhb.2dyt.com/MyInterface/userAction_selectAllUser.action?pageIndex=1&pageSize=10&user.sign=1 


public class HomeFragment extends Fragment{  //首页

    private IRecyclerView recyclerView;

    int page = 1;

    List<DataBean> list = new ArrayList<>();
    private LoadMoreFooterView loadMoreFooterView;
    private SingleAdapter adapter;
    private SingleAdapter2 adapter2;

    boolean flag = false;
    boolean flag2 = false;
    private LinearLayoutManager linearLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 0:
                    //停止刷新
                    recyclerView.setRefreshing(false);

                    break;

                case 1:
                    //设置刷新是都出现转圈图标
                    loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);

                    break;
            }
        }
    };
    private Map<String, String> map;
    private TextView mTv;
    private HorizontalDividerItemDecoration mDividerItemDecoration;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.radioactivity_home, container, false);

        mTv = (TextView) v.findViewById(R.id.radiogroupactivity_qiehuanyangshi);

        recyclerView = (IRecyclerView) v.findViewById(R.id.radiogroupactivity_recycleview);


        loadMoreFooterView = (LoadMoreFooterView) recyclerView.getLoadMoreFooterView();

        mDividerItemDecoration = new HorizontalDividerItemDecoration.Builder(getActivity())
                .color(Color.RED)
                .build();

        toLinearLayoutManager();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag2) {
                    toStaggeredGridLayoutManager();
                    flag2 = false;
                } else {
                    toLinearLayoutManager();
                    flag2 = true;
                }
            }
        });

    }

    public void toLinearLayoutManager(){
        if(linearLayoutManager == null){
            linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        recyclerView.addItemDecoration(mDividerItemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(linearLayoutManager);
        execute(true);
    }


    public void toStaggeredGridLayoutManager(){
        if(staggeredGridLayoutManager == null){
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        }
        execute2(true);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.removeItemDecoration(mDividerItemDecoration);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {

                list.clear();
                adapter.notifyDataSetChanged();

                handler.sendEmptyMessage(0);

                execute(true);
            }
        });

        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                page++;

                execute(false);

                loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);

                handler.sendEmptyMessageDelayed(1,2000);

            }
        });

    }

    public void initData(final boolean flag) {

        if (flag) {

            adapter = new SingleAdapter(getActivity(), list);

            recyclerView.setIAdapter(adapter);

        } else {

            adapter.notifyDataSetChanged();

        }

    }

    public void initData2(final boolean flag) {

        if (flag) {

            adapter2 = new SingleAdapter2(list);

            recyclerView.setIAdapter(adapter2);

        } else {

            adapter2.notifyDataSetChanged();

        }

    }


    public void execute(final boolean flag) {

        Map map = new HashMap<String, String>();
        map.put("pageIndex", page + "");
        map.put("pageSize", "10");

        String sign = JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));
        map.put("user.sign", sign);

        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_selectAllUser.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {

                System.out.println("xunxun result = " + result);


                News news = JSON.parseObject(result, News.class);

                list.addAll(news.getData());

                initData(flag);
            }

            @Override
            public void onFailed(int code) {

            }
        });
    }

    public void execute2(final boolean flag) {

        Map map = new HashMap<String, String>();
        map.put("pageIndex", page+"");
        map.put("pageSize", "10");

        String sign =  JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map))) ;
        map.put("user.sign",sign);

        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_selectAllUser.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {

                System.out.println("xunxun result = " + result);


                News news = JSON.parseObject(result, News.class);

                list.addAll(news.getData());

                initData2(flag);
            }

            @Override
            public void onFailed(int code) {

            }
        });


    }
}
