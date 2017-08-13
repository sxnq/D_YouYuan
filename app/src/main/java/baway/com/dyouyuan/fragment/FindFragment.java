package baway.com.dyouyuan.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import baway.com.dyouyuan.IApplication;
import baway.com.dyouyuan.R;
import baway.com.dyouyuan.adapter.XinXianShiAdapter;
import baway.com.dyouyuan.bean.XinXianBean;
import baway.com.dyouyuan.core.JNICore;
import baway.com.dyouyuan.core.SortUtils;
import baway.com.dyouyuan.footer.LoadMoreFooterView;
import baway.com.dyouyuan.header.ClassicRefreshHeaderView;
import baway.com.dyouyuan.network.BaseObserver;
import baway.com.dyouyuan.network.RetrofitManager;


public class FindFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener {

    private IRecyclerView recycleviewIndexfragment;
    private List<XinXianBean.ListBean> cricleList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager;
    private boolean isFirst = true;


    private XinXianShiAdapter adapter;
    private HorizontalDividerItemDecoration horizontalDividerItemDecoration;
    private LoadMoreFooterView loadMoreFooterView;
    private ClassicRefreshHeaderView classicRefreshHeaderView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, null);

        adapter = new XinXianShiAdapter(IApplication.application);
        recycleviewIndexfragment = (IRecyclerView) view.findViewById(R.id.xinxianshi_recyclerView);
        initView();

        recycleviewIndexfragment.post(new Runnable() {
            @Override
            public void run() {
                recycleviewIndexfragment.setRefreshing(true);
            }
        });
        return view;
    }

    private void initView() {

        horizontalDividerItemDecoration = new HorizontalDividerItemDecoration.Builder(getActivity()).build();
        toLinearLayoutManager();
    }

    private void toLinearLayoutManager() {

        if (linearLayoutManager == null) {
            linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }

        //添加分割线
        recycleviewIndexfragment.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(IApplication.application)
                        .color(Color.YELLOW)
                        .build());

        recycleviewIndexfragment.setLayoutManager(linearLayoutManager);

        classicRefreshHeaderView = (ClassicRefreshHeaderView) recycleviewIndexfragment.getRefreshHeaderView();
        loadMoreFooterView = (LoadMoreFooterView) recycleviewIndexfragment.getLoadMoreFooterView();

        recycleviewIndexfragment.setOnRefreshListener(this);
        recycleviewIndexfragment.setOnLoadMoreListener(this);

        recycleviewIndexfragment.setIAdapter(adapter);
//        recycleviewIndexfragment.removeItemDecoration(horizontalDividerItemDecoration);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(System.currentTimeMillis() + "");
    }

    private void initData(String time) {

        Map map = new HashMap<String, String>();

        map.put("dynamicinfor.dynamicTime", time);

        String sign = JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));
        map.put("user.sign", sign);

        RetrofitManager.post("http://qhb.2dyt.com/MyInterface/userAction_selectFriends.action", map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {

                XinXianBean cricleBean = JSON.parseObject(result, XinXianBean.class);

                if (cricleBean.getResult_code() == 200 && cricleBean.getList().size() != 0) {

                    cricleList.addAll(cricleBean.getList());
                    adapter.setList(cricleList);
                    adapter.notifyDataSetChanged();
                    recycleviewIndexfragment.setRefreshing(false);
                    loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);

                } else {


                }
            }

            @Override
            public void onFailed(int code) {

            }
        });

    }

    @Override
    public void onLoadMore() {

        isFirst = false;

        String currentime = cricleList.get(cricleList.size() - 1).getDynamicTime();
        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);
        initData(currentime);
    }

    @Override
    public void onRefresh() {
        isFirst = true;
        cricleList.clear();
        initData(System.currentTimeMillis() + "");
    }

}
