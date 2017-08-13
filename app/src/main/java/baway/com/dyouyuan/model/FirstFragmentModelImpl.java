package baway.com.dyouyuan.model;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import baway.com.dyouyuan.IApplication;
import baway.com.dyouyuan.bean.DataBean;
import baway.com.dyouyuan.bean.News;
import baway.com.dyouyuan.core.JNICore;
import baway.com.dyouyuan.core.SortUtils;
import baway.com.dyouyuan.network.BaseObserver;
import baway.com.dyouyuan.network.RetrofitManager;
import baway.com.dyouyuan.utils.Constants;
import baway.com.dyouyuan.utils.FirstFragmentDaoUtils;
import baway.com.dyouyuan.utils.ToastUtils;


public class FirstFragmentModelImpl implements FirstFragmentModel {

//    System.currentTimeMillis()

    @Override
    public void getData(final long currentime, final DataListener listener) {

        Map<String,String> map = new HashMap<String, String>();
        map.put("user.currenttimer",  currentime+ "");

        String sign = JNICore.getSign(SortUtils.getMapResult(SortUtils.sortString(map)));
        map.put("user.sign", sign);

        RetrofitManager.post(Constants.ALL_USER, map, new BaseObserver() {
            @Override
            public void onSuccess(String result) {

                try {
                    Gson gson = new Gson();
                    News indexBean =   gson.fromJson(result, News.class);

                    if (indexBean.getResult_code() == 200 && indexBean.getData().size()!=0){

                        List<DataBean> data = indexBean.getData();

                        FirstFragmentDaoUtils.insert(data);

                        ToastUtils.showToast(IApplication.application, "插入成功" );

                    listener.onSuccess(indexBean,currentime);

                    }else{

                        ToastUtils.showToast(IApplication.application, "插入失败" );
                       // listener.onFailed(2333,currentime);
                    }



//                    FirstFragmentDaoUtils.insert(indexBean.getData());


                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailed(int code) {
                listener.onFailed(code,currentime);
            }
        });


    }
}
