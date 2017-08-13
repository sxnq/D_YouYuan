package baway.com.dyouyuan.utils;

import com.hyphenate.chat.EMMessage;

import java.util.List;

import baway.com.dyouyuan.IApplication;
import baway.com.dyouyuan.bean.DataBean;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class FirstFragmentDaoUtils {

    //增加
    public static void insert(final List<DataBean> list) {

        Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Long> e) throws Exception {


                IApplication.daoSession.getDataBeanDao().insertOrReplaceInTx(list);


            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {

                    }
                });
    }

    //增加消息记录
    public static void insertMes(final List<EMMessage>list) {

        Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Long> e) throws Exception {


//                IApplication.daoSession.getMsgDao().insertOrReplaceInTx(list);


            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {

                    }
                });
    }

}