package baway.com.dyouyuan.huanxinui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aspsine.irecyclerview.IRecyclerView;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseEmojicon;
import com.hyphenate.easeui.domain.EaseEmojiconGroupEntity;
import com.hyphenate.easeui.model.EaseDefaultEmojiconDatas;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenu;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenuBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

import baway.com.dyouyuan.IApplication;
import baway.com.dyouyuan.R;
import baway.com.dyouyuan.activity.VideoActivity;
import baway.com.dyouyuan.adapter.MsgAdapter;
import baway.com.dyouyuan.speex.SpeexPlayer;
import baway.com.dyouyuan.speex.SpeexRecorder;
import baway.com.dyouyuan.utils.Constants;
import baway.com.dyouyuan.utils.ImageResizeUtils;
import baway.com.dyouyuan.utils.PreferencesUtils;
import baway.com.dyouyuan.utils.SDCardUtils;
import baway.com.dyouyuan.utils.keyboard.KeyBoardHelper;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static baway.com.dyouyuan.utils.ImageResizeUtils.copyStream;


//  hyphenate_SDK3.0 文档  http://www.easemob.com/apidoc/android/chat3.0/index.html

//android:theme="@android:style/Theme.Dialog" 将一个Activity显示为能话框模式


//实现 获取键盘高度类的接口
@RuntimePermissions
public class ChatActivity extends Activity implements KeyBoardHelper.OnKeyBoardStatusChangeListener{

    @BindView(R.id.chatactivity_recycle)
    IRecyclerView chatactivityRecycle;
    @BindView(R.id.chatactivity_tvtitle)
    TextView tv_title;

    @BindView(R.id.chatactivity_et)
    EditText chatactivityEt;

    @BindView(R.id.chatactivity_bt1)
    Button chatactivityBt1;

    @BindView(R.id.chatactivity_bt2)
    Button chatactivityBt2;

    @BindView(R.id.chatactivity_bt3)
    Button chatactivityBt3;

    @BindView(R.id.chatactivity_bt33)
    Button chatactivityBt33;

    @BindView(R.id.chatactivity_ll)
    LinearLayout chatactivityLl;

    @BindView(R.id.chatactivity_shipin)
    LinearLayout chatactivityLl_shipin;

    @BindView(R.id.chatactivity_shipin_iv)
    ImageView chatactivity_shipin_iv;
    @BindView(R.id.chatactivity_paizhao)
    ImageView chatactivity_paizhao;
    @BindView(R.id.chatactivity_xiangce)
    ImageView chatactivity_xiangce;

    @BindView(R.id.chatactivity_llid)
    LinearLayout chatactivity_llid;

    @BindView(R.id.voice_btn1)
    Button button_voice;


    private int jianpanHight;

    private String id;
    private String name;
    private EMMessageListener msgListener;

    private List<EMMessage> mMsgList = new ArrayList<EMMessage>();

    private MsgAdapter mMsgAdapter;


    EaseEmojiconMenuBase emojiconMenu;

    private String fileName;
    private SpeexRecorder recorderInstance;
    private String touxiang;

    private AnimationDrawable ad;
    private Timer timer;

    static final int INTENTFORCAMERA = 1 ;
    static final int INTENTFORPHOTO = 2 ;

    private int width;
    private int height;

    private static final int STATE_NORMAL = 1;// 默认的状态
    private static final int STATE_RECORDING = 2;// 正在录音
    private static final int STATE_WANT_TO_CANCEL = 3;// 希望取消

    private int mCurrentState = STATE_NORMAL; // 当前的状态
    private boolean isRecording = false;// 已经开始录音

    private static final int DISTANCE_Y_CANCEL = 50;


    // 是否触发longClick
    private boolean mReady;

    private static final int MSG_AUDIO_PREPARED = 0x110;
    private static final int MSG_VOICE_CHANGED = 0x111;
    private static final int MSG_DIALOG_DIMISS = 0x112;


    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private DialogManager dialogManager = new DialogManager(ChatActivity.this);


    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case 1:
                    mMsgAdapter.notifyDataSetChanged();
                    chatactivityRecycle.scrollToPosition(mMsgList.size() - 1);

                    break;

                case 3:

                    mMsgAdapter.notifyDataSetChanged();
                    chatactivityRecycle.scrollToPosition(mMsgList.size() - 1);
                    chatactivity_xiangce.setBackgroundResource(R.drawable.xiangce);
                    break;

                case 2:

                    dialogManager.dimissDialog();

                    break;


                case 250:

                    Log.e("Message,fenbei", msg.obj + "");

                    final  double db = (double) msg.obj;

                    System.out.println("Message,fenbei ,Thread.currentThread() = " + Thread.currentThread());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            int level = 0;

                            if (db<30){
                                level = 1;
                            }else  if(db<40 && db>30){
                                level = 2;
                            }else  if(db<50 && db>40){
                                level = 3;
                            }else  if(db<60 && db>50){
                                level = 4;
                            }else  if(db<70 && db>60){
                                level = 5;
                            }else  if(db<80 && db>70){
                                level = 6;
                            }else  if(db>80){
                                level = 7;
                            }

                            dialogManager.updateVoiceLevel(level);

                        }
                    });
                    break;
            }
        }
    };
    private long anxiaTime;
    private long taiqiTime;
    private long chaTime;
    private File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//状态栏
        //        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏

        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        id = intent.getStringExtra(EaseConstant.EXTRA_USER_ID);
        name = intent.getStringExtra(EaseConstant.EXTRA_USER_name);
        touxiang = intent.getStringExtra(EaseConstant.EXTRA_USER_touxiang);

        tv_title.setText(name + "   " + id);


        setKeyBoardModelResize();

        //获取键盘高度
        KeyBoardHelper keyBoardHelper = new KeyBoardHelper(this);
        keyBoardHelper.onCreate();
        keyBoardHelper.setOnKeyBoardStatusChangeListener(this);


        //在登录界面获取键盘高度
        jianpanHight = PreferencesUtils.getValueByKey(this, "jianpan", 0);

        Log.e("jianpanHight", jianpanHight + "");

        //设置加号布局高度
        android.view.ViewGroup.LayoutParams lp = chatactivityLl_shipin.getLayoutParams();
        lp.height = jianpanHight;


        initEmoje(null);

        chatactivityEt.setOnTouchListener(new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if (mMsgList != null && mMsgList.size() != 0 && chatactivityRecycle != null) {
                    mMsgAdapter.notifyDataSetChanged();
                    chatactivityRecycle.scrollToPosition(mMsgList.size() - 1);

                }

                //如果点击edittext的时候底部view是显示的
                if (chatactivityLl.getVisibility() == View.VISIBLE) {

                    Drawable drawable = ChatActivity.this.getDrawable(R.drawable.ease_chatting_setmode_xiaolian_btn);

                    chatactivityBt1.setBackground(drawable);

                    chatactivityBt1.setTag(1);

                    chatactivityBt2.setTag(1);

                    setKeyBoardModelPan();

                } else if (chatactivityLl_shipin.getVisibility() == View.VISIBLE) {

                    Drawable drawable = ChatActivity.this.getDrawable(R.drawable.ease_chatting_setmode_xiaolian_btn);

                    chatactivityBt1.setBackground(drawable);

                    chatactivityBt1.setTag(1);

                    chatactivityBt2.setTag(1);

                    setKeyBoardModelPan();

                } else {

                    setKeyBoardModelResize();

                }

                return false;
            }
        });


        //1.表情 2.键盘
        chatactivityBt1.setTag(1);


        chatactivityEt.addTextChangedListener(watcher);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        chatactivityRecycle.setLayoutManager(layoutManager);


        initListener();

        //接收消息
        receiveMessage();

        //适配器
        initAdapter();

        //点击外部让消失
        chatactivityRecycle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                hideKeyBoard();
                chatactivityLl.setVisibility(View.GONE);
                chatactivityLl_shipin.setVisibility(View.GONE);

                return false;
            }
        });


        chatactivityBt2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                setKeyBoardModelPan();


                int tag = (int) chatactivityBt2.getTag();

                if (tag == 1) {


                    chatactivityBt1.setTag(1);

                    chatactivityBt3.setTag(1);//1代表输入  2代表录音

                    Drawable drawable = ChatActivity.this.getDrawable(R.drawable.ease_chatting_setmode_xiaolian_btn);

                    chatactivityBt1.setBackground(drawable);

                    chatactivityEt.requestFocus(); //请求获取焦点

                    hideKeyBoard();

                    chatactivityLl_shipin.setVisibility(View.VISIBLE);

                    chatactivityLl.setVisibility(View.GONE);

                    chatactivityBt2.setTag(2);

                    button_voice.setVisibility(View.GONE);

                    chatactivityEt.setVisibility(View.VISIBLE);

                    Drawable drawable2 = ChatActivity.this.getDrawable(R.drawable.ease_chatting_setmode_voice_btn);

                    chatactivityBt3.setBackground(drawable2);


                    chatactivity_shipin_iv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //发起视频聊天
                            VideoActivity.startTelActivity(1, id + "", IApplication.application);

                        }
                    });

                    //拍照
                    chatactivity_paizhao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            toCheckPermissionCamera();

                        }
                    });

                    //相册
                    chatactivity_xiangce.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            toPhoto();
                        }
                    });

                } else {

                    chatactivityBt1.setTag(1);

                    chatactivityBt2.setTag(1);//1代表输入  2代表录音

                    Drawable drawable = ChatActivity.this.getDrawable(R.drawable.ease_chatting_setmode_xiaolian_btn);

                    chatactivityBt1.setBackground(drawable);

                    chatactivityBt2.setTag(1);

                    button_voice.setVisibility(View.GONE);

                    chatactivityEt.setVisibility(View.VISIBLE);

                    Drawable drawable2 = ChatActivity.this.getDrawable(R.drawable.ease_chatting_setmode_voice_btn);

                    chatactivityBt3.setBackground(drawable2);

                    showKeyBoard();


                }
            }
        });

        //发送点击事件
        chatactivityBt33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = chatactivityEt.getText().toString();

                if (!TextUtils.isEmpty(content)) {
                    sendTextMessage();
                }


                chatactivityEt.setText("");
            }
        });


        chatactivityBt3.setTag(1);//1代表输入  2代表录音


        chatactivityBt3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                int tag1 = (int) chatactivityBt3.getTag();

                if (tag1 == 1) {

                    chatactivityBt1.setTag(1);

                    chatactivityLl_shipin.setVisibility(View.GONE);

                    //语音
                    Drawable drawable = ChatActivity.this.getDrawable(R.drawable.ease_chatting_setmode_keyboard_btn);

                    chatactivityBt3.setBackground(drawable);

                    chatactivityBt3.setTag(2);

                    button_voice.setVisibility(View.VISIBLE);

                    chatactivityEt.setVisibility(View.GONE);

                    hideKeyBoard();

                    chatactivityLl.setVisibility(View.GONE);

                    Drawable drawable1 = ChatActivity.this.getDrawable(R.drawable.ease_chatting_setmode_xiaolian_btn);

                    chatactivityBt1.setBackground(drawable1);

                    Drawable drawable2 = ChatActivity.this.getDrawable(R.drawable.ease_chatting_setmode_xiaolian_btn);

                    chatactivityBt1.setBackground(drawable2);

                    chatactivityBt2.setTag(1);

                    setKeyBoardModelResize();

                } else {

                    chatactivityBt1.setTag(1);

                    chatactivityBt2.setTag(1);

                    //输入
                    Drawable drawable = ChatActivity.this.getDrawable(R.drawable.ease_chatting_setmode_voice_btn);

                    chatactivityBt3.setBackground(drawable);

                    chatactivityBt3.setTag(1);//1代表输入  2代表录音

                    button_voice.setVisibility(View.GONE);

                    chatactivityEt.setVisibility(View.VISIBLE);

                    chatactivityEt.requestFocus(); //请求获取焦点

                    showKeyBoard();

                    Drawable drawable2 = ChatActivity.this.getDrawable(R.drawable.ease_chatting_setmode_xiaolian_btn);

                    chatactivityBt1.setBackground(drawable2);

                    chatactivityLl_shipin.setVisibility(View.GONE);

                }
            }
        });

        chatactivityBt2.setTag(1);


        //发送语音 按下抬起 显示弹窗
        button_voice.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                int x = (int) event.getX();// 获得x轴坐标
                int y = (int) event.getY();// 获得y轴坐标

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:

                        changeState(STATE_RECORDING);

                        dialogManager.showRecordingDialog();

                        anxiaTime = System.currentTimeMillis();

                        String filePath = Environment.getExternalStorageDirectory() + File.separator + SDCardUtils.DLIAO;
                        System.out.println("filePath:" + filePath);
                        File file = new File(filePath + "/");
                        System.out.println("file:" + file);
                        if (!file.exists()) {
                            file.mkdirs();
                        }

                        fileName = file + File.separator + System.currentTimeMillis() + ".spx";
                        System.out.println("保存文件名：＝＝ " + fileName);
                        recorderInstance = new SpeexRecorder(fileName, handler);
                        Thread th = new Thread(recorderInstance);
                        th.start();
                        recorderInstance.setRecording(true);

                        mReady = true;

                        break;


                    case MotionEvent.ACTION_UP:

                        taiqiTime = System.currentTimeMillis();
                        recorderInstance.setRecording(false);

                        chaTime = taiqiTime - anxiaTime;

                        if (chaTime <= 1000) {

                            dialogManager.tooShort();

                            handler.sendEmptyMessageDelayed(2, 1000);// 延迟显示对话框

                        } else {

                            dialogManager.dimissDialog();

                            System.out.println("fileName = " + new File(fileName).length());

                            if (mCurrentState != STATE_WANT_TO_CANCEL) {

                                sendVoiceMessage();
                            }
                        }
                        reset();
                        break;

                    case MotionEvent.ACTION_MOVE:


                        if (isRecording) {
                            // 如果想要取消，根据x,y的坐标看是否需要取消
                            if (wantToCancle(x, y)) {
                                changeState(STATE_WANT_TO_CANCEL);
                            } else {
                                changeState(STATE_RECORDING);
                            }
                        }

                        break;
                }


                return false;
            }


        });


        //从服务器获取数据
        String myid = PreferencesUtils.getValueByKey(ChatActivity.this, "uid", "");

        EMConversation conversation = EMClient.getInstance().chatManager().getConversation(id);
        //获取此会话的所有消息
        if (conversation != null) {

            List<EMMessage> messages = conversation.getAllMessages();

            mMsgList.addAll(messages);
            handler.sendEmptyMessage(1);
        }
    }


//    =================================================oncreate()=============================================================

    public String LocalPhotoName;
    public String createLocalPhotoName() {
        LocalPhotoName = System.currentTimeMillis() + "liaotianpaizhao.jpg";
        return  LocalPhotoName ;
    }

    /**
     * 打开相册
     */
    public void toPhoto(){
        try {
            createLocalPhotoName();
            Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
            getAlbum.setType("image/*");
            startActivityForResult(getAlbum, INTENTFORPHOTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void toCheckPermissionCamera(){

        ChatActivityPermissionsDispatcher.camerWithCheck(this);

    }

    //2. --- 该方法需要哪些权限，当用户授予了权限之后，会调用使用此注解
    @NeedsPermission(Manifest.permission.CAMERA)
    //调起系统相机
    public void camer() {

        try {
            Intent intentNow = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intentNow.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(SDCardUtils.getMyFaceFile(createLocalPhotoName())));
            startActivityForResult(intentNow, INTENTFORCAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //3. --- 如果用户不授予某权限时调用
    @OnPermissionDenied(Manifest.permission.CAMERA)
    public void onDenied() {

        Toast.makeText(this, "onDenied --- 用户不授予某权限", Toast.LENGTH_SHORT).show();
    }


    //4.--- 如果用户选择了让设备“不再询问”
    @OnNeverAskAgain(Manifest.permission.CAMERA)
    public void onNeverAskAgain() {
        Toast.makeText(this, "onNeverAskAgain --- 用户选择了让设备“不再询问", Toast.LENGTH_SHORT).show();

        //应用信息界面
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        startActivity(localIntent);
    }

    //5.--- 注释一个方法解释为什么需要这个(个)权限并提示用户判断是否允许
    @OnShowRationale(Manifest.permission.CAMERA)
    public void showRationaleForCamera(final PermissionRequest request) {

        new AlertDialog.Builder(this).setTitle("是否请求授权")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 请求授权
                        request.proceed();

                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                request.cancel();
            }
        }).create().show();

    }

    //请求授权之后的回调方法

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        ChatActivityPermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case INTENTFORCAMERA:
                //                相机
                try {

                    Log.e("11111111","111111111");

                    //file 就是拍照完 得到的原始照片
                    File file = new File(SDCardUtils.photoCacheDir, LocalPhotoName);
                    Bitmap bitmap = ImageResizeUtils.resizeImage(file.getAbsolutePath(),Constants.RESIZE_PIC);

                    FileOutputStream fos = new FileOutputStream(file.getAbsolutePath());
                    if (bitmap != null) {
                        if (bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos)) {
                            fos.close();
                            fos.flush();
                        }
                        if (!bitmap.isRecycled()) {
                            //通知系统 回收bitmap
                            bitmap.isRecycled();
                        }

                        width = bitmap.getWidth();

                        height = bitmap.getHeight();

                    }

                    //imagePath为图片本地路径，false为不发送原图（默认超过100k的图片会压缩后发给对方），需要发送原图传true
                    final EMMessage imageSendMessage = EMMessage.createImageSendMessage(file.getAbsolutePath(), false, id);
                    EMClient.getInstance().chatManager().sendMessage(imageSendMessage);

                    imageSendMessage.setMessageStatusCallback(new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            System.out.println("onSuccess() = " + "发送相机消息成功!");

                            mMsgList.add(imageSendMessage);
                            handler.sendEmptyMessage(1);
                        }

                        @Override
                        public void onError(int i, String s) {
                            System.out.println("onSuccess() = " + "发送相机消息失败!"+s);
                        }

                        @Override
                        public void onProgress(int i, String s) {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

          case INTENTFORPHOTO:

            try {
                // 必须这样处理，不然在4.4.2手机上会出问题
                Uri originalUri = data.getData();
                File f = null;
                if (originalUri != null) {
                    f = new File(SDCardUtils.photoCacheDir, LocalPhotoName);
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor actualimagecursor = this.getContentResolver().query(originalUri, proj, null, null, null);
                    if (null == actualimagecursor) {
                        if (originalUri.toString().startsWith("file:")) {
                            File file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                            if (!file.exists()) {
                                //地址包含中文编码的地址做utf-8编码
                                originalUri = Uri.parse(URLDecoder.decode(originalUri.toString(), "UTF-8"));
                                file = new File(originalUri.toString().substring(7, originalUri.toString().length()));
                            }
                            FileInputStream inputStream = new FileInputStream(file);
                            FileOutputStream outputStream = new FileOutputStream(f);
                            copyStream(inputStream, outputStream);
                        }
                    } else {
                        // 系统图库
                        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        actualimagecursor.moveToFirst();
                        String img_path = actualimagecursor.getString(actual_image_column_index);
                        if (img_path == null) {
                            InputStream inputStream = this.getContentResolver().openInputStream(originalUri);
                            FileOutputStream outputStream = new FileOutputStream(f);
                            copyStream(inputStream, outputStream);
                        } else {
                            file = new File(img_path);
                            FileInputStream inputStream = new FileInputStream(file);
                            FileOutputStream outputStream = new FileOutputStream(f);
                            copyStream(inputStream, outputStream);
                        }

                    }
                    Bitmap bitmap = ImageResizeUtils.resizeImage(f.getAbsolutePath(), Constants.RESIZE_PIC);
                    FileOutputStream fos = new FileOutputStream(f.getAbsolutePath());
                    if (bitmap != null) {
                        if (bitmap.compress(Bitmap.CompressFormat.JPEG, 85, fos)) {
                            fos.close();
                            fos.flush();
                        }
                        if (!bitmap.isRecycled()) {
                            bitmap.isRecycled();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

            }

              Bitmap bm = null;
              Uri originalUri =null;

            // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口

            ContentResolver resolver = getContentResolver();


            try {
               originalUri = data.getData(); // 获得图片的uri

                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);

//                chatactivity_xiangce.setImageBitmap(ThumbnailUtils.extractThumbnail(bm, 100, 1800));  //使用系统的一个工具类，参数列表为 Bitmap Width,Height  这里使用压缩后显示，否则在华为手机上ImageView 没有显示
                // 显得到bitmap图片
                // imageView.setImageBitmap(bm);

                String[] proj = {MediaStore.Images.Media.DATA};

                // 好像是android多媒体数据库的封装接口，具体的看Android文档
                Cursor cursor = managedQuery(originalUri, proj, null, null, null);

                // 按我个人理解 这个是获得用户选择的图片的索引值
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                cursor.moveToFirst();
                // 最后根据索引值获取图片路径
                String path = cursor.getString(column_index);

                //imagePath为图片本地路径，false为不发送原图（默认超过100k的图片会压缩后发给对方），需要发送原图传true
                final EMMessage imageSendMessage = EMMessage.createImageSendMessage(path, false, id);
                EMClient.getInstance().chatManager().sendMessage(imageSendMessage);

                imageSendMessage.setMessageStatusCallback(new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        System.out.println("onSuccess() = " + "发送相册消息成功!");

                        mMsgList.add(imageSendMessage);
                        handler.sendEmptyMessage(3);


                    }

                    @Override
                    public void onError(int i, String s) {
                        System.out.println("onSuccess() = " + "发送相册消息失败!"+s);
                    }

                    @Override
                    public void onProgress(int i, String s) {

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        }
    }

    /**
     * 恢复状态及标志位
     */
    private void reset() {
        isRecording = false;
        mReady = false;
        changeState(STATE_NORMAL);
    }
    //判断 手 和 按钮位置
    private boolean wantToCancle(int x, int y) {
        if (x < 0 || x > button_voice.getWidth()) { // 超过按钮的宽度
            return true;
        }
        // 超过按钮的高度
        if (y < -DISTANCE_Y_CANCEL || y > button_voice.getHeight() + DISTANCE_Y_CANCEL) {
            return true;
        }

        return false;
    }

    /**
     * 语音长按钮状态改变
     */
    private void changeState(int state) {
        if (mCurrentState != state) {
            mCurrentState = state;
            switch (state) {
                case STATE_NORMAL:
                    button_voice.setBackgroundResource(R.drawable.btn_recorder_normal);
                    button_voice.setText(R.string.btn_normal);
                    break;

                case STATE_RECORDING:
                    isRecording = true;
                    button_voice.setBackgroundResource(R.drawable.btn_recorder_recording);
                    button_voice.setText(R.string.btn_recoding);
                    if (isRecording) {
                        dialogManager.recording();
                    }
                    break;

                case STATE_WANT_TO_CANCEL:
                    button_voice.setBackgroundResource(R.drawable.btn_recorder_recording);
                    button_voice.setText(R.string.btn_cancel);

                    dialogManager.wantToCancel();
                    break;
            }
        }
    }


    //发送语音
    private void sendVoiceMessage() {

        chatactivityBt2.setVisibility(View.VISIBLE);

        chatactivityBt33.setVisibility(View.GONE);


        //filePath为语音文件路径，length为录音时间(秒)
        final EMMessage message = EMMessage.createVoiceSendMessage(fileName, (int) chaTime, id);

        //获取语音详细信息
        EMVoiceMessageBody voiceBody = (EMVoiceMessageBody) message.getBody();
        //加入语音文件名
        voiceBody.setFileName(fileName);

        EMClient.getInstance().chatManager().sendMessage(message);

        message.setMessageStatusCallback(new EMCallBack() {
            @Override
            public void onSuccess() {
                System.out.println("onSuccess() = " + "发送语音消息成功!");

                mMsgList.add(message);
                handler.sendEmptyMessage(1);


            }

            @Override
            public void onError(int i, String s) {
                System.out.println("onSuccess() = " + "发送语音消息失败!");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }


    //editext 输入监听事件
    private TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            if (s.length() > 0) {//字符大于0

                chatactivityBt2.setVisibility(View.GONE);

                chatactivityBt33.setVisibility(View.VISIBLE);


            } else {

                chatactivityBt2.setVisibility(View.VISIBLE);

                chatactivityBt33.setVisibility(View.GONE);

            }


        }
    };

//表情
    private void initListener() {

        emojiconMenu.setEmojiconMenuListener(new EaseEmojiconMenuBase.EaseEmojiconMenuListener()

        {
            @Override
            public void onExpressionClicked(EaseEmojicon emojicon) {
                if (emojicon.getType() != EaseEmojicon.Type.BIG_EXPRESSION) {
                    if (emojicon.getEmojiText() != null) {
                        chatactivityEt.append(EaseSmileUtils.getSmiledText(ChatActivity.this, emojicon.getEmojiText()));
                    }
                }
            }

            @Override
            public void onDeleteImageClicked() {
                if (!TextUtils.isEmpty(chatactivityEt.getText())) {
                    KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
                    chatactivityEt.dispatchKeyEvent(event);
                }
            }
        });
    }


    //加表情
    private void initEmoje(List<EaseEmojiconGroupEntity> emojiconGroupList) {

        if (emojiconMenu == null) {
            emojiconMenu = (EaseEmojiconMenu) View.inflate(ChatActivity.this, com.hyphenate.easeui.R.layout.ease_layout_emojicon_menu, null);
            if (emojiconGroupList == null) {
                emojiconGroupList = new ArrayList<EaseEmojiconGroupEntity>();
                emojiconGroupList.add(new EaseEmojiconGroupEntity(com.hyphenate.easeui.R.drawable.ee_1, Arrays.asList(EaseDefaultEmojiconDatas.getData())));
            }
            ((EaseEmojiconMenu) emojiconMenu).init(emojiconGroupList);
        }
        chatactivityLl.addView(emojiconMenu);
    }


    private void initAdapter() {

        mMsgAdapter = new MsgAdapter(mMsgList, touxiang, name,ChatActivity.this);
        chatactivityRecycle.setAdapter(mMsgAdapter);
        mMsgAdapter.notifyDataSetChanged();

        //我说的
        mMsgAdapter.setChatlinter(new MsgAdapter.ChatLinter() {
            @Override
            public void chatlinter(EMMessage emMessage) {

                EMVoiceMessageBody embody = (EMVoiceMessageBody) emMessage.getBody();
                String fileName = embody.getFileName();

                SpeexPlayer speexPlayer = new SpeexPlayer(fileName, handler);
                speexPlayer.startPlay();

            }
        });


        //左面 她说的
        mMsgAdapter.setChatLinterzuo(new MsgAdapter.ChatLinterzuo() {

            @Override
            public void ChatLinterzuo(EMMessage emMessage) {

                //获取语音详细信息
                EMVoiceMessageBody voiceBody = (EMVoiceMessageBody) emMessage.getBody();
                SpeexPlayer speexPlayer2 = new SpeexPlayer(voiceBody.getLocalUrl(), handler);
                speexPlayer2.startPlay();

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.chatactivity_bt1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.chatactivity_bt1:

                setKeyBoardModelPan();

                int tag = (int) chatactivityBt1.getTag();

                if (tag == 1) {//表情

                    chatactivityBt2.setTag(1);

                    chatactivityBt3.setTag(1);//1代表输入  2代表录音

                    Drawable drawable = this.getDrawable(R.drawable.ease_chatting_setmode_keyboard_btn);

                    chatactivityBt1.setBackground(drawable);

                    chatactivityEt.requestFocus(); //请求获取焦点

                    hideKeyBoard();

                    chatactivityLl.setVisibility(View.VISIBLE);

                    chatactivityBt1.setTag(2);

                    button_voice.setVisibility(View.GONE);

                    chatactivityEt.setVisibility(View.VISIBLE);

                    Drawable drawable2 = ChatActivity.this.getDrawable(R.drawable.ease_chatting_setmode_voice_btn);

                    chatactivityBt3.setBackground(drawable2);

                    chatactivityLl_shipin.setVisibility(View.GONE);

                } else {//键盘

                    chatactivityBt2.setTag(1);

                    chatactivityBt3.setTag(1);//1代表输入  2代表录音

                    Drawable drawable = this.getDrawable(R.drawable.ease_chatting_setmode_xiaolian_btn);

                    chatactivityBt1.setBackground(drawable);

                    chatactivityBt1.setTag(1);

                    button_voice.setVisibility(View.GONE);

                    chatactivityEt.setVisibility(View.VISIBLE);

                    Drawable drawable2 = ChatActivity.this.getDrawable(R.drawable.ease_chatting_setmode_voice_btn);

                    chatactivityBt3.setBackground(drawable2);

                    chatactivityLl_shipin.setVisibility(View.GONE);

                    showKeyBoard();

                }


                break;

        }
    }

    //键盘弹起
    @Override
    public void OnKeyBoardPop(int keyBoardheight) {

    }

    @Override
    public void OnKeyBoardClose(int oldKeyBoardheight) {

    }

    //隐藏键盘
    public void hideKeyBoard() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(chatactivityEt.getWindowToken(), 0);
    }

    //显示键盘
    public void showKeyBoard() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(chatactivityEt, InputMethodManager.SHOW_FORCED);
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


    public void sendTextMessage() {

        //        EMClient.getInstance().isConnected()  true 表示自己和服务器是链接状态  false 表示自己是断开状态


        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此

        String a = chatactivityEt.getText().toString().trim();

        if (!TextUtils.isEmpty(a)) {


            final EMMessage message = EMMessage.createTxtSendMessage(chatactivityEt.getText().toString().trim(), id);


            System.out.println("chatActivity i = " + message.getFrom() + "  " + message.getTo() + "  " + message.getBody().toString() + " " + message.getMsgId() + " " + message.getMsgTime());


            //发送消息
            EMClient.getInstance().chatManager().sendMessage(message);

            // 当前消息的状态
            message.setMessageStatusCallback(new EMCallBack() {
                @Override
                public void onSuccess() {
                    System.out.println("chatActivity i ok= " + message.getFrom() + "  " + message.getTo() + "  " + message.getBody().toString() + " " + message.getMsgId() + " " + message.getMsgTime());

                    mMsgList.add(message);

                    handler.sendEmptyMessage(1);
                }


                @Override
                public void onError(int i, String s) {
                    Log.e("chatActivity onError", "发送错误");
                }

                @Override
                public void onProgress(int i, String s) {
                    Log.e("chatActivity onError", "发送中");
                }
            });


            //      系统系统数据库的api

            //                           EMConversation emConversation =  EMClient.getInstance().chatManager().getConversation(id);
            //                           emConversation.appendMessage(message);
            //                           PreferencesUtils.addConfigInfo(IApplication.getApplication(),"chatuserid",id);
        }
    }

    //接收消息
    public void receiveMessage() {

        //收到消息
        //收到透传消息
        //收到已读回执
        //收到已送达回执
        //消息状态变动
        msgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                //收到消息
                System.out.println("onMessageReceived messages = " + messages.get(0).getBody().toString());

                for (int i = 0; i < messages.size(); i++) {


                    if (messages.get(i).getFrom().equals(id)) {

                        mMsgList.add(messages.get(i));
                    }
                }

                handler.sendEmptyMessage(1);

            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
                System.out.println("onCmdMessageReceived messages = " + messages);

            }

            @Override
            public void onMessageRead(List<EMMessage> messages) {
                //收到已读回执
                System.out.println("onMessageRead messages = " + messages);

            }

            @Override
            public void onMessageDelivered(List<EMMessage> message) {
                //收到已送达回执
                System.out.println("onMessageDelivered messages = " + message);

            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
                System.out.println("onMessageChanged messages = " + message);

            }
        };

        EMClient.getInstance().chatManager().addMessageListener(msgListener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMsgList.clear();

        //        记得在不需要的时候移除listener，如在activity的onDestroy()时
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            System.out.println("chatTitle = onBack KEYCODE_BACK");
            if (chatactivityLl.getVisibility() == View.VISIBLE) {
                chatactivityLl.setVisibility(View.GONE);
                chatactivityBt1.setTag(1);

                return false;
            } else {
                return super.onKeyDown(keyCode, event);
            }

        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


}
