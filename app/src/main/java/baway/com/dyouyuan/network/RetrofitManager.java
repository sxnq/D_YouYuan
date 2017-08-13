package baway.com.dyouyuan.network;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import baway.com.dyouyuan.IApplication;
import baway.com.dyouyuan.network.cookie.CookiesManager;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RetrofitManager {


    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .cookieJar(new CookiesManager(IApplication.getApplication()))
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20,TimeUnit.SECONDS)
            .writeTimeout(20,TimeUnit.SECONDS)
            .addNetworkInterceptor(new LoggingInterceptor())
            .build();


    private   static ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://qhb.2dyt.com")
//            .baseUrl("http://169.254.69.102:8080")  //满满服务器
//    http://169.254.69.102:8080/MyInterface/userAction_uploadImage.action
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            //把 以前的 call 转化成 Observable
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(ApiService.class);





    public  static void get(String url,Observer<String> observer){
         apiService.get(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }




    public static void get(String url, Map<String,String> map, Observer<String> observer){

         apiService.get(url,map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(observer);
    }





    public static void post(String url,Map<String,String> map, Observer<String> observer){
          apiService.post(url,map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(observer);
    }

//
//    public static void uploadPhoto(MultipartBody multipartBody, Observer<String> observer){
//        apiService.uploadPhoto(multipartBody)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
//    }


    public static void uploadPhoto(MultipartBody multipartBody, Map<String,String> map, Observer<String> observer){
        apiService.uploadPhoto(multipartBody,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void uploadPhotoAlbum(MultipartBody multipartBody, Map<String,String> map, Observer<String> observer){
        apiService.uploadPhotoAlbum(multipartBody,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }


}
