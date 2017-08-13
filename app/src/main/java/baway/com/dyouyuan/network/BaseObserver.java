package baway.com.dyouyuan.network;


import com.socks.library.KLog;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


public abstract  class BaseObserver implements Observer<String> {

    @Override
    public void onSubscribe(@NonNull Disposable d) {


    }

    @Override
    public void onNext(@NonNull String s) {


        KLog.i(s);

        onSuccess(s);

    }

    @Override
    public void onError(@NonNull Throwable e) {
        e.printStackTrace() ;
        System.out.println();
        KLog.i(e);

        onFailed(0);

    }

    @Override
    public void onComplete() {

    }


    public abstract void onSuccess(String result);
    public abstract void onFailed(int code);

}
