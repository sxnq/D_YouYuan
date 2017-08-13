package baway.com.dyouyuan.presenter;


import java.util.ArrayList;
import java.util.List;

import baway.com.dyouyuan.IApplication;
import baway.com.dyouyuan.bean.DataBean;
import baway.com.dyouyuan.bean.News;
import baway.com.dyouyuan.model.FirstFragmentModel;
import baway.com.dyouyuan.model.FirstFragmentModelImpl;
import baway.com.dyouyuan.view.FirstFragmentView;

public class FirstFragmentPresenter  {

    List<DataBean> list = new ArrayList<>();

    private FirstFragmentModel firstFragmentModel ;
    FirstFragmentView firstFragmentView;

    public FirstFragmentPresenter(FirstFragmentView firstFragmentView){
        firstFragmentModel = new FirstFragmentModelImpl();
        this.firstFragmentView = firstFragmentView;
    }

    public void getData(long currentime){

        firstFragmentModel.getData(currentime, new FirstFragmentModel.DataListener() {
            @Override
            public void onSuccess(News indexBean, long currentime) {
                firstFragmentView.success(indexBean,currentime);
            }

            @Override
            public void onFailed(int code,long currentime) {
                firstFragmentView.failed(list);
            }
        });

    }


    public List<DataBean> getDataShuJuKu() {

        List<DataBean> list = IApplication.daoSession.getDataBeanDao().queryBuilder().list();
return  list;
    }
    }
