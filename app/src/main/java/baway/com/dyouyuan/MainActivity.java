package baway.com.dyouyuan;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import baway.com.dyouyuan.activity.LoginActivity;
import baway.com.dyouyuan.activity.PhoneRegistActivity;
import baway.com.dyouyuan.base.IActivity;
import baway.com.dyouyuan.utils.MyToast;
import baway.com.dyouyuan.utils.PreferencesUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//添加个产品   ---  在开发者后台，点进去选择第二个

public class MainActivity extends IActivity {

    private LocationManager locationManager;

    @BindView(R.id.mainactivity_btlogin)
    Button mainactivityBtlogin;
    @BindView(R.id.mainactivity_btregist)
    Button mainactivityBtregist;
    private String locationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏

        //获取经纬度
        getLocation();

//        int [] a = new int[0];
//        a[3] = 1;


//        Intent i = new Intent(MainActivity.this, CeShiActivity.class);
//        startActivity(i);
    }

    private void getLocation() {

        //1.获取位置管理器
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //2.获取位置提供器，GPS或是NetWork
        List<String> providers = locationManager.getProviders(true);
        if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是网络定位
            locationProvider = LocationManager.NETWORK_PROVIDER;

        } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS定位
            locationProvider = LocationManager.GPS_PROVIDER;
        } else {
            Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            return;
        }

        //3.获取上次的位置，一般第一次运行，此值为null
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location!=null){
            showLocation(location);
        }else{
            // 监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
            locationManager.requestLocationUpdates(locationProvider, 0, 0,mListener);
        }
    }

    private void showLocation(Location location){
        String address = "纬度："+location.getLatitude()+"  经度："+location.getLongitude();

                MyToast.makeText(MainActivity.this,address,1);

        PreferencesUtils.addConfigInfo(IApplication.getApplication(),"weidu",location.getLongitude()+"");
        PreferencesUtils.addConfigInfo(IApplication.getApplication(),"jingdu",location.getLatitude()+"");

    }

    LocationListener mListener = new LocationListener() {
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
        @Override
        public void onProviderEnabled(String provider) {
        }
        @Override
        public void onProviderDisabled(String provider) {
        }
        // 如果位置发生变化，重新显示
        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }
    };



    @OnClick({R.id.mainactivity_btlogin, R.id.mainactivity_btregist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mainactivity_btlogin:

                Intent i1 = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i1);

                break;

            case R.id.mainactivity_btregist:

                Intent i2 = new Intent(MainActivity.this, PhoneRegistActivity.class);
                startActivity(i2);

                break;
        }
    }


    private long clickTime=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - clickTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再次点击退出",  Toast.LENGTH_SHORT).show();
            clickTime = System.currentTimeMillis();

        } else {

            this.finish();
            System.exit(0);
        }
    }
}
