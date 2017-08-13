package baway.com.dyouyuan.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMCallStateChangeListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.EMNoActiveCallException;
import com.hyphenate.exceptions.EMServiceNotReadyException;
import com.hyphenate.media.EMCallSurfaceView;

import baway.com.dyouyuan.IApplication;
import baway.com.dyouyuan.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.superrtc.sdk.VideoView.EMCallViewScaleMode.EMCallViewScaleModeAspectFill;

public class VideoActivity extends Activity {

    @BindView(R.id.surface_big)
    EMCallSurfaceView surfaceBig;
    @BindView(R.id.surface_small)
    EMCallSurfaceView surfaceSmall;
    @BindView(R.id.videoactivity_tv)
    TextView videoactivityTv;
    @BindView(R.id.videoactivity_bt_jieting)
    Button videoactivityBtJieting;
    @BindView(R.id.videoactivity_bt_guaduan)
    Button videoactivityBtGuaduan;
    @BindView(R.id.videoactivity_bt_jujie)
    Button videoactivityBtJujie;
    @BindView(R.id.videoactivity_ivqiehuan)
    ImageView iv_qiehuan;

    private String uid;
    private int type;

    /**
     * @param type    1 拨打视频电话  2 接受视频电话
     * @param uid
     * @param context
     */
    public static void startTelActivity(int type, String uid, Context context) {

        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("uid", uid);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);

        uid = getIntent().getExtras().getString("uid");
        type = getIntent().getExtras().getInt("type");

        //让自己图像 显示在上面
        surfaceSmall.getHolder().setFormat(PixelFormat.TRANSPARENT);
        surfaceSmall.setZOrderOnTop(true);

//      全屏
        surfaceBig.setScaleMode(EMCallViewScaleModeAspectFill);


        if(1 ==type){

            //拨打电话
            videoactivityBtJieting.setVisibility(View.GONE);
            videoactivityBtJujie.setVisibility(View.GONE);
            videoactivityBtGuaduan.setVisibility(View.VISIBLE);

            //拨打视频通话
            try {
                EMClient.getInstance().callManager().makeVideoCall(uid);

                IApplication.callTo();

            } catch (EMServiceNotReadyException e) {
                e.printStackTrace();
            }


        } else {

            //接听电话
            videoactivityBtJieting.setVisibility(View.VISIBLE);
            videoactivityBtJujie.setVisibility(View.VISIBLE);
            videoactivityBtGuaduan.setVisibility(View.GONE);

        }

        EMClient.getInstance().callManager().setSurfaceView(surfaceSmall, surfaceBig);


        connectState();

        iv_qiehuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EMClient.getInstance().callManager().switchCamera();
            }
        });

        surfaceSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int tag= (int) surfaceSmall.getTag();
                if (tag== 1) {
                    EMClient.getInstance().callManager().setSurfaceView(surfaceBig, surfaceSmall);
                    surfaceSmall.setTag(2);
                } else {
                    EMClient.getInstance().callManager().setSurfaceView(surfaceSmall, surfaceBig);

                    surfaceSmall.setTag(1);
                }



            }
        });

    }


    public void connectState(){
        EMClient.getInstance().callManager().addCallStateChangeListener(new EMCallStateChangeListener() {
            @Override
            public void onCallStateChanged(CallState callState, CallError error) {

                Log.e("videoactivityTv ","callState "+callState);
                Log.e("videoactivityTv ","error "+error);

                switch (callState) {
                    case CONNECTING: // 正在连接对方

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                videoactivityTv.setText("正在连接");

                            }
                        });

                        Log.e("videoactivityTv ","正在连接");

                        break;
                    case CONNECTED: // 双方已经建立连接

                        Log.e("videoactivityTv ","双方已经建立连接");

                        break;

                    case ACCEPTED: // 电话接通成功
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                videoactivityTv.setText("通话中");

                            }
                        });

                        Log.e("videoactivityTv ","电话接通成功");

                        break;
                    case DISCONNECTED: // 电话断了

                        finish();

                        Log.e("videoactivityTv ","电话断了");

                        break;
                    case NETWORK_UNSTABLE: //网络不稳定
                        if(error == CallError.ERROR_NO_DATA){
                            //无通话数据
                        }else{
                        }

                        Log.e("videoactivityTv ","网络不稳定");


                        break;
                    case NETWORK_NORMAL: //网络恢复正常

                        Log.e("videoactivityTv ","网络恢复正常");

                        break;
                    default:

                        Log.e("videoactivityTv ","default");

                        break;
                }

            }
        });
    }


    @OnClick({R.id.videoactivity_bt_jieting, R.id.videoactivity_bt_guaduan, R.id.videoactivity_bt_jujie})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.videoactivity_bt_jieting:

                try {
                    EMClient.getInstance().callManager().answerCall();
                } catch (EMNoActiveCallException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.e("videoactivityTv ","接听电话");


                break;
            case R.id.videoactivity_bt_guaduan:

                try {
                    //挂断
                    EMClient.getInstance().callManager().endCall();
                } catch (EMNoActiveCallException e) {
                    e.printStackTrace();
                }
                Log.e("videoactivityTv ","挂断电话");

                finish();


                break;
            case R.id.videoactivity_bt_jujie:

                try {
                    EMClient.getInstance().callManager().rejectCall();
                } catch (EMNoActiveCallException e) {
                    e.printStackTrace();
                }

                Log.e("videoactivityTv ","拒接电话");
                break;
        }
    }
}
