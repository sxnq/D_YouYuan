package baway.com.dyouyuan.zhibo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoView;

import java.util.ArrayList;
import java.util.List;

import baway.com.dyouyuan.IApplication;
import baway.com.dyouyuan.R;
import baway.com.dyouyuan.adapter.DanMuAdapter;
import baway.com.dyouyuan.utils.ButtonUtils;
import baway.com.dyouyuan.utils.keyboard.KeyBoardHelper;

/**
 * This is a demo activity of PLVideoView
 */
public class PLVideoViewActivity extends Activity implements KeyBoardHelper.OnKeyBoardStatusChangeListener{

    private int jianpanHight;

    private LinearLayout linearLayout;
    private LinearLayout linearLayoutFirst;

    TextView textViewFirst;
    TextView textViewXXX;
    private int width;
    int position = 0 ;

    private static final String TAG = PLVideoViewActivity.class.getSimpleName();

    private static final int MESSAGE_ID_RECONNECTING = 0x01;

    private MediaController mMediaController;
    private PLVideoView mVideoView;
    private Toast mToast = null;
    private String mVideoPath = null;
    private int mDisplayAspectRatio = PLVideoView.ASPECT_RATIO_FIT_PARENT;
    private boolean mIsActivityPaused = true;
    private View mLoadingView;
    private View mCoverView = null;
    private int mIsLiveStreaming = 1;
    private ListView listview;
    private Button bt_send;
    private EditText et_shuru;

    private void setOptions(int codecType) {
        AVOptions options = new AVOptions();

        // the unit of timeout is ms
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
        options.setInteger(AVOptions.KEY_PROBESIZE, 128 * 1024);
        // Some optimization with buffering mechanism when be set to 1
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, mIsLiveStreaming);
        if (mIsLiveStreaming == 1) {
            options.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
        }

        // 1 -> hw codec enable, 0 -> disable [recommended]
        options.setInteger(AVOptions.KEY_MEDIACODEC, codecType);

        // whether start play automatically after prepared, default value is 1
        options.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);

        mVideoView.setAVOptions(options);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pl_video_view);

        listview = (ListView) findViewById(R.id.cameraPreview_listview);
        bt_send = (Button) findViewById(R.id.PLVideoViewActivity_btnsend);
        et_shuru = (EditText) findViewById(R.id.cameraPreview_et);

        et_shuru.setFocusable(true);
        et_shuru.setFocusableInTouchMode(true);
        et_shuru.requestFocus();

        mVideoView = (PLVideoView) findViewById(R.id.VideoView);

        mCoverView = (ImageView) findViewById(R.id.CoverView);
        mVideoView.setCoverView(mCoverView);
        mLoadingView = findViewById(R.id.LoadingView);
        mVideoView.setBufferingIndicator(mLoadingView);
        mLoadingView.setVisibility(View.VISIBLE);

        //拉流的地址
        mVideoPath = getIntent().getStringExtra("videoPath");
        mIsLiveStreaming = getIntent().getIntExtra("liveStreaming", 1);

        // 1 -> hw codec enable, 0 -> disable [recommended]
        int codec = getIntent().getIntExtra("mediaCodec", AVOptions.MEDIA_CODEC_SW_DECODE);
        setOptions(codec);

        // Set some listeners
        mVideoView.setOnInfoListener(mOnInfoListener);
        mVideoView.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
        mVideoView.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        mVideoView.setOnCompletionListener(mOnCompletionListener);
        mVideoView.setOnSeekCompleteListener(mOnSeekCompleteListener);
        mVideoView.setOnErrorListener(mOnErrorListener);

        mVideoView.setVideoPath(mVideoPath);

        // You can also use a custom `MediaController` widget
        mMediaController = new MediaController(this, false, mIsLiveStreaming == 1);
        mVideoView.setMediaController(mMediaController);


        linearLayout = (LinearLayout) findViewById(R.id.b_view);
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 20; i++) {
                    list.add("hahhhhhhhhhhhhh" + i);
                }
                DanMuAdapter adapter = new DanMuAdapter(IApplication.getApplication(), list);
                listview.setAdapter(adapter);

        et_shuru.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {

                            keyPop();

                            return false;
                        }
                    });


        KeyBoardHelper keyBoardHelper = new KeyBoardHelper(this);

        keyBoardHelper.onCreate();

        keyBoardHelper.setOnKeyBoardStatusChangeListener(this);



        linearLayoutFirst = (LinearLayout) findViewById(R.id.firstlinearlayout);

        textViewFirst = (TextView) findViewById(R.id.first_paoche_view);
        textViewXXX = (TextView) findViewById(R.id.xxx);


        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();


        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!ButtonUtils.isFastDoubleClick(R.id.PLVideoViewActivity_btnsend)) {
                    //写你相关操作即可


                //弹幕移动
                textViewFirst.setVisibility(View.VISIBLE);
                float sw =  (float)( - width +  textViewFirst.getWidth()+dip2px(PLVideoViewActivity.this,100)) ;
                float curTranslationX = textViewFirst.getTranslationX()+textViewFirst.getWidth();

                System.out.println("sw = " + sw);
                System.out.println("curTranslationX = " + curTranslationX);
                System.out.println("textViewFirst = " + textViewFirst.getWidth());

                ObjectAnimator animator = ObjectAnimator.ofFloat(linearLayoutFirst, "translationX", curTranslationX,sw );
                animator.setDuration(3000);

                animator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        textViewXXX.setText("X 1");
                        ObjectAnimator scaleAnimator = ObjectAnimator.ofFloat(textViewXXX, "xxx", 1.0f,2.0f,1.0f);
                        scaleAnimator.setDuration(500);
                        scaleAnimator.setRepeatCount(10);
                        scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                float cVal = (Float) animation.getAnimatedValue();
                                textViewXXX.setScaleX(cVal);
                                textViewXXX.setScaleY(cVal);



                            }
                        });
                        scaleAnimator.start();
                        scaleAnimator.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                textViewXXX.setText("");

                                textViewFirst.setVisibility(View.GONE);
                                textViewFirst.setTranslationX(0);
                                position = 0 ;

                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {
                                System.out.println("position = " + position);
                                position ++ ;

                                textViewXXX.setText("X "+ position);

                            }
                        });


                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {



                    }
                });
                animator.start();

                }

            }
        });
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    private void keyPop(){
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)linearLayout.getLayoutParams() ;
        params.setMargins(0,0,0,558);
        params.gravity = Gravity.BOTTOM;
        linearLayout.setLayoutParams(params);
    }

    private void keyClose(){
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)linearLayout.getLayoutParams() ;
        params.setMargins(0,0,0,40);
        params.gravity = Gravity.BOTTOM;
        linearLayout.setLayoutParams(params);
    }

    @Override
    public void OnKeyBoardPop(int keyBoardheight) {
        System.out.println("OnKeyBoardPop keyBoardheight = " + keyBoardheight);
    }

    @Override
    public void OnKeyBoardClose(int oldKeyBoardheight) {
        System.out.println("OnKeyBoardClose keyBoardheight = " + oldKeyBoardheight);
        keyClose();


    }


    //动态改变软键盘顶出
    public void setKeyBoardModelPan() {

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    //动态改变软键盘自动调整
    public void setKeyBoardModelResize() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    //隐藏键盘
    public void hideKeyBoard() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_shuru.getWindowToken(), 0);
    }

    //显示键盘
    public void showKeyBoard() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(et_shuru, InputMethodManager.SHOW_FORCED);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsActivityPaused = false;
        mVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mToast = null;
        mIsActivityPaused = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
    }

    public void onClickSwitchScreen(View v) {
        mDisplayAspectRatio = (mDisplayAspectRatio + 1) % 5;
        mVideoView.setDisplayAspectRatio(mDisplayAspectRatio);
        switch (mVideoView.getDisplayAspectRatio()) {
            case PLVideoView.ASPECT_RATIO_ORIGIN:
                showToastTips("Origin mode");
                break;
            case PLVideoView.ASPECT_RATIO_FIT_PARENT:
                showToastTips("Fit parent !");
                break;
            case PLVideoView.ASPECT_RATIO_PAVED_PARENT:
                showToastTips("Paved parent !");
                break;
            case PLVideoView.ASPECT_RATIO_16_9:
                showToastTips("16 : 9 !");
                break;
            case PLVideoView.ASPECT_RATIO_4_3:
                showToastTips("4 : 3 !");
                break;
            default:
                break;
        }
    }

    private PLMediaPlayer.OnInfoListener mOnInfoListener = new PLMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(PLMediaPlayer plMediaPlayer, int what, int extra) {
            Log.d(TAG, "onInfo: " + what + ", " + extra);
            return false;
        }
    };

    private PLMediaPlayer.OnErrorListener mOnErrorListener = new PLMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(PLMediaPlayer plMediaPlayer, int errorCode) {
            boolean isNeedReconnect = false;
            Log.e(TAG, "Error happened, errorCode = " + errorCode);
            switch (errorCode) {
                case PLMediaPlayer.ERROR_CODE_INVALID_URI:
                    showToastTips("Invalid URL !");
                    break;
                case PLMediaPlayer.ERROR_CODE_404_NOT_FOUND:
                    showToastTips("404 resource not found !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_REFUSED:
                    showToastTips("Connection refused !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_TIMEOUT:
                    showToastTips("Connection timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_EMPTY_PLAYLIST:
                    showToastTips("Empty playlist !");
                    break;
                case PLMediaPlayer.ERROR_CODE_STREAM_DISCONNECTED:
                    showToastTips("Stream disconnected !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_IO_ERROR:
                    showToastTips("Network IO Error !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_UNAUTHORIZED:
                    showToastTips("Unauthorized Error !");
                    break;
                case PLMediaPlayer.ERROR_CODE_PREPARE_TIMEOUT:
                    showToastTips("Prepare timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_READ_FRAME_TIMEOUT:
                    showToastTips("Read frame timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_HW_DECODE_FAILURE:
                    setOptions(AVOptions.MEDIA_CODEC_SW_DECODE);
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.MEDIA_ERROR_UNKNOWN:
                    break;
                default:
                    showToastTips("unknown error !");
                    break;
            }
            // Todo pls handle the error status here, reconnect or call finish()
            if (isNeedReconnect) {
                sendReconnectMessage();
            } else {
                finish();
            }
            // Return true means the error has been handled
            // If return false, then `onCompletion` will be called
            return true;
        }
    };

    private PLMediaPlayer.OnCompletionListener mOnCompletionListener = new PLMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(PLMediaPlayer plMediaPlayer) {
            Log.d(TAG, "Play Completed !");
            showToastTips("Play Completed !");
            finish();
        }
    };

    private PLMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new PLMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(PLMediaPlayer plMediaPlayer, int precent) {
            Log.d(TAG, "onBufferingUpdate: " + precent);
        }
    };

    private PLMediaPlayer.OnSeekCompleteListener mOnSeekCompleteListener = new PLMediaPlayer.OnSeekCompleteListener() {
        @Override
        public void onSeekComplete(PLMediaPlayer plMediaPlayer) {
            Log.d(TAG, "onSeekComplete !");
        }
    };

    private PLMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener = new PLMediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(PLMediaPlayer plMediaPlayer, int width, int height, int videoSar, int videoDen) {
            Log.d(TAG, "onVideoSizeChanged: width = " + width + ", height = " + height + ", sar = " + videoSar + ", den = " + videoDen);
        }
    };

    private void showToastTips(final String tips) {
        if (mIsActivityPaused) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(PLVideoViewActivity.this, tips, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }

    protected Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what != MESSAGE_ID_RECONNECTING) {
                return;
            }
//            if (mIsActivityPaused || !Utils.isLiveStreamingAvailable()) {
//                finish();
//                return;
//            }
//            if (!Utils.isNetworkAvailable(PLVideoViewActivity.this)) {
//                sendReconnectMessage();
//                return;
//            }
            mVideoView.setVideoPath(mVideoPath);
            mVideoView.start();
        }
    };

    private void sendReconnectMessage() {
        showToastTips("正在重连...");
        mLoadingView.setVisibility(View.VISIBLE);
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_ID_RECONNECTING), 500);
    }
}
