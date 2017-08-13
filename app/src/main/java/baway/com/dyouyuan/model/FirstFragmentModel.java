package baway.com.dyouyuan.model;

import baway.com.dyouyuan.bean.News;


public interface FirstFragmentModel {


    public void getData(long currentime, DataListener dataListener);




    public interface DataListener{
        public void onSuccess(News indexBean, long currentime);
        public void onFailed(int code, long currentime);
    }

}
