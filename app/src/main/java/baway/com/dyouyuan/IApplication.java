package baway.com.dyouyuan;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.mob.MobApplication;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.qiniu.pili.droid.streaming.StreamingEnv;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import baway.com.dyouyuan.dao.DaoMaster;
import baway.com.dyouyuan.dao.DaoSession;
import baway.com.dyouyuan.utils.PreferencesUtils;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;


/**
 * 环信集成 http://docs.easemob.com/im/200androidclientintegration/30androidsdkbasics
 *
 */

public class IApplication extends MobApplication {

    public static IApplication application ;

    private final static String dbName = "test_db.db";
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;
    public static DaoSession daoSession;


    static {

        System.loadLibrary("speex");
    }



    @Override
    public void onCreate() {
        super.onCreate();

//        x.Ext.init(this);
        application = this;

        //初始化  图片加载框架
        Fresco.initialize(this);

        //腾讯 Bugly
        CrashReport.initCrashReport(getApplicationContext(), "525faf7128", false);

        //数据库
        openHelper = new DaoMaster.DevOpenHelper(this, dbName);
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDatabase());
        //通过这个对象操作数据库
        daoSession = daoMaster.newSession();
        initImageLoader();


//        注：如果你的 APP 中有第三方的服务启动，请在初始化 SDK（EMClient.getInstance().init(applicationContext, options)）方法的前面添加以下相关代码（
//        相应代码也可参考 Demo 的 application），使用 EaseUI 库的就不用理会这个。
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null ||!processAppName.equalsIgnoreCase(this.getPackageName())) {
            Log.e(TAG, "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }

        initEM();

        //七牛直播
        StreamingEnv.init(getApplicationContext());
    }


//    如何获取processAppName请参考以下方法。
private String getAppName(int pID) {
    String processName = null;
    ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
    List l = am.getRunningAppProcesses();
    Iterator i = l.iterator();
    PackageManager pm = this.getPackageManager();
    while (i.hasNext()) {
        ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
        try {
            if (info.pid == pID) {
                processName = info.processName;
                return processName;
            }
        } catch (Exception e) {
            // Log.d("Process", "Error>> :"+ e.toString());
        }
    }
    return processName;
}

    private void initImageLoader(){


        try {

            File cacheDir = StorageUtils.getOwnCacheDirectory(this, "/SD");
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                    .threadPriority(Thread.NORM_PRIORITY - 2)
                    .denyCacheImageMultipleSizesInMemory()
                    .threadPoolSize(3)//线程池内加载的数量
                    .discCacheFileNameGenerator(new Md5FileNameGenerator())//diskCache() and diskCacheFileNameGenerator()调用相互重叠
                    .tasksProcessingOrder(QueueProcessingType.LIFO)
                    .memoryCache(new LruMemoryCache((int) (6 * 1024 * 1024)))
                    .diskCache(new UnlimitedDiskCache(cacheDir))
                    .imageDownloader(new BaseImageDownloader(this,5 * 1000, 30 * 1000))//设置超时时间
                    .build();
            ImageLoader.getInstance().init(config);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static IApplication getApplication(){
        if(application == null){
            application = getApplication() ;
        }
        return application;
    }


    public void initEM(){

        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证

        options.setAcceptInvitationAlways(false);

//        SDK 中自动登录属性默认是 true 打开的，如果不需要自动登录，在初始化 SDK 初始化的时候
        // 调用options.setAutoLogin(false);设置为 false 关闭。
//
//        自动登录在以下几种情况下会被取消：
//
//        用户调用了 SDK 的登出动作；
//        用户在别的设备上更改了密码，导致此设备上自动登录失败；
//        用户的账号被从服务器端删除；

        //退出登录
//        EMClient.getInstance().logout(true);


        //初始化
//        EMClient.getInstance().init(this, options);

        EaseUI.getInstance().init(this, options);

        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

//
//        //加载so 文件成功失败回调
//        ReLinker.loadLibrary(this,"",new  ReLinker.LoadListener()){
//
//
//        }




    }

    //登陆环信  在登陆界面调用
    public void emLogin(){

        final String userName = PreferencesUtils.getValueByKey(this,"uid",0)+"";
        final String password = PreferencesUtils.getValueByKey(this,"yxpassword","0");

        EMClient.getInstance().login(userName,password,new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");

                Log.d("main---userName",userName);
                Log.d("main---password",password);
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", code+"登录聊天服务器失败！");
            }
        });
    }

    //视频通话
    public static void ring(){
        SoundPool soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC,0);

        soundPool.load(application, R.raw.avchat_ring,1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId,1, 1, 0, 0, 1);
            }
        });

    }


    public static void callTo(){
        SoundPool soundPool = new SoundPool(3, AudioManager.STREAM_MUSIC,0);

        soundPool.load(application, R.raw.avchat_connecting,1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(sampleId,1, 1, 0, 0, 1);
            }
        });


    }



}
