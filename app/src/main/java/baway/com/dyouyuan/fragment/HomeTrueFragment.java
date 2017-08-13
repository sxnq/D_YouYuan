package baway.com.dyouyuan.fragment;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aspsine.irecyclerview.IRecyclerView;
import com.aspsine.irecyclerview.OnLoadMoreListener;
import com.aspsine.irecyclerview.OnRefreshListener;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import baway.com.dyouyuan.IApplication;
import baway.com.dyouyuan.R;
import baway.com.dyouyuan.activity.XiangQingActivity;
import baway.com.dyouyuan.adapter.IndexAdapter;
import baway.com.dyouyuan.bean.DataBean;
import baway.com.dyouyuan.bean.News;
import baway.com.dyouyuan.footer.LoadMoreFooterView;
import baway.com.dyouyuan.jiekou.OnItemClickListener;
import baway.com.dyouyuan.presenter.FirstFragmentPresenter;
import baway.com.dyouyuan.utils.NetUtil;
import baway.com.dyouyuan.view.FirstFragmentView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

//http://qhb.2dyt.com/MyInterface/userAction_selectAllUser.action?pageIndex=1&pageSize=10&user.sign= 


public class HomeTrueFragment extends Fragment implements FirstFragmentView ,OnItemClickListener{  //首页

    IndexAdapter adapter;

    News indexBean;
    private IRecyclerView recycleviewIndexfragment;
    private LoadMoreFooterView loadMoreFooterView;
    FragmentManager manager;

    List<DataBean> list = new ArrayList<>();

    @BindView(R.id.pub_title_title)
    TextView pubTitleTitle;

//    @BindView(R.id.recycleview_index)
//    RecyclerView recycleviewIndexfragment;
//    @BindView(R.id.springview_indexfragment)
//    SpringView springviewIndexfragment;

    Unbinder unbinder;

    private LinearLayoutManager linearLayoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private HorizontalDividerItemDecoration horizontalDividerItemDecoration;
    private FirstFragmentPresenter firstFragmentPresenter;


    int page = 1;
    public long userId;

    //    Handler handler = new Handler(){
//
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 1:
//
//                    recycleviewIndexfragment.setRefreshing(false);//刷新
//
//                    page = 1;
//
//                    firstFragmentPresenter.getData(page);
//
//                    break;
//                case 2:
//
//                    loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);//加载更多
//
//                    page++;
//
//                    firstFragmentPresenter.getData(page);
//
//
//                    break;
//            }
//        }
//    } ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_first, container, false);
        unbinder = ButterKnife.bind(this, view);


        recycleviewIndexfragment = (IRecyclerView) view.findViewById(R.id.recycleview_index);

        firstFragmentPresenter = new FirstFragmentPresenter(this);

        initView(view);

        return view;
    }

    //    pubTitleRightbtn.tag
    //     1 切换成 瀑布流 2 切换成 线性布局
    private void initView(View view) {

        pubTitleTitle.setVisibility(View.GONE);
        pubTitleTitle.setVisibility(View.VISIBLE);
        pubTitleTitle.setTag(1);
        pubTitleTitle.setText("切换模式");

        boolean networkAvailable = NetUtil.isNetworkAvailable(getActivity());

        if (networkAvailable == false){

                List<DataBean> dataBeen = IApplication.daoSession.getDataBeanDao().loadAll();

            Log.e("11111",dataBeen.size()+"");

            if (dataBeen.size() > 0){

            list = dataBeen;

            }


        }



        pubTitleTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tag = (int) v.getTag() ;
                if(tag == 1){
                    pubTitleTitle.setTag(2);
                    toStaggeredGridLayoutManager();

                } else {
                    pubTitleTitle.setTag(1);
                    toLinearLayoutManager();

                }
            }
        });

        horizontalDividerItemDecoration = new HorizontalDividerItemDecoration.Builder(getActivity()).build();


        loadMoreFooterView = (LoadMoreFooterView) recycleviewIndexfragment.getLoadMoreFooterView();

        adapter = new IndexAdapter(getActivity());

        recycleviewIndexfragment.setIAdapter(adapter);

        adapter.setOnItemClickListener(this);

        toLinearLayoutManager();


        long l = System.currentTimeMillis();
        firstFragmentPresenter.getData(l);


        recycleviewIndexfragment.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {

                recycleviewIndexfragment.setRefreshing(true);

                recycleviewIndexfragment.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        recycleviewIndexfragment.setRefreshing(false);//刷新

                        page=1;

                        if (NetUtil.isNetworkAvailable(getActivity()) ==true){

                        long l = System.currentTimeMillis();

//                        IApplication.daoSession.getDataBeanDao().deleteAll();

                        firstFragmentPresenter.getData(l);

                        }else{

                            firstFragmentPresenter.getDataShuJuKu();

                        }


                    }
                },1000);


//                handler.sendEmptyMessageDelayed(1,2000);
            }
        });


        recycleviewIndexfragment.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                loadMoreFooterView.setStatus(LoadMoreFooterView.Status.LOADING);

                recycleviewIndexfragment.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        loadMoreFooterView.setStatus(LoadMoreFooterView.Status.GONE);

                        page=2;

                        if (NetUtil.isNetworkAvailable(getActivity()) ==true) {
                            long currentime = indexBean.getData().get(indexBean.getData().size() - 1).getLasttime();

                            //                        IApplication.daoSession.getDataBeanDao().deleteAll();

                            firstFragmentPresenter.getData(currentime);
                        }else{

                            firstFragmentPresenter.getDataShuJuKu();

                        }
                    }
                },1000);


//                handler.sendEmptyMessageDelayed(2,2000);

            }
        });
    }


        //        springviewIndexfragment.setHeader(new DefaultHeader(getActivity()));
//        springviewIndexfragment.setFooter(new DefaultFooter(getActivity()));
//
//        springviewIndexfragment.setListener(new SpringView.OnFreshListener() {
//            @Override
//            public void onRefresh() {
//
//                page = 1 ;
//                firstFragmentPresenter.getData(page);
//                springviewIndexfragment.onFinishFreshAndLoad();
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        springviewIndexfragment.onFinishFreshAndLoad();
//                    }
//                }, 1000);
//            }
//
//            @Override
//            public void onLoadmore() {
//
//                firstFragmentPresenter.getData(++page);
//
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        springviewIndexfragment.onFinishFreshAndLoad();
//                    }
//                }, 1000);
//            }
//        });


    public void toLinearLayoutManager(){

        if(linearLayoutManager == null){
            linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }
        adapter.dataChange(1);

        recycleviewIndexfragment.setLayoutManager(linearLayoutManager);
        recycleviewIndexfragment.setIAdapter(adapter);
        recycleviewIndexfragment.addItemDecoration(horizontalDividerItemDecoration);

    }

    public void toStaggeredGridLayoutManager(){
        if(staggeredGridLayoutManager == null){
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        }
        adapter.dataChange(2);
        recycleviewIndexfragment.setLayoutManager(staggeredGridLayoutManager);
        recycleviewIndexfragment.setIAdapter(adapter);
        recycleviewIndexfragment.removeItemDecoration(horizontalDividerItemDecoration);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void success(News indexBean,long currentime) {

        this.indexBean = indexBean;

        List<DataBean> data = indexBean.getData();

        list.addAll(data);

        adapter.setData(indexBean,page);

                Log.e("pagesuccess",page+"");

    }


    @Override
    public void failed(List<DataBean> list2) {

        list = IApplication.daoSession.getDataBeanDao().loadAll();
        News news = new News();
        news.setData(list);

        adapter.setData(news,page);

        Log.e("pagefail",page+"");

    }

//    //点击事件
//    @Override
//    public void OnItemClickListener(int code) {
//
//        Intent i = new Intent(IApplication.application,XiangQingActivity.class);
//        i.putExtra("user_id",code);
//        startActivity(i);
//    }

    @Override
    public void onItemClick(int position, Object o, View v,String s) {

        Intent i = new Intent(IApplication.application,XiangQingActivity.class);
        DataBean db =(DataBean) o;
        userId = db.getUserId();
        Log.e("userid","userid"+ userId);
        i.putExtra("user_id", userId +"");
        i.putExtra("location",s);

        startActivity(i);
    }
}