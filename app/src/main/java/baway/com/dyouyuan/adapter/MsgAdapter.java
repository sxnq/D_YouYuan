package baway.com.dyouyuan.adapter;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.utils.EaseImageUtils;
import com.hyphenate.easeui.utils.EaseSmileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import baway.com.dyouyuan.IApplication;
import baway.com.dyouyuan.R;
import baway.com.dyouyuan.photoview.PicShowLiaoTianDialog;

public class MsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int MESSAGE_TYPE_SENT_TXT = 0;  //发
    private static final int MESSAGE_TYPE_RECV_TXT = 1;

    private static final int MESSAGE_TYPE_SENT_IMAGE = 2;
    private static final int MESSAGE_TYPE_RECV_IMAGE = 3;

    private static final int MESSAGE_TYPE_SENT_LOCATION = 4;
    private static final int MESSAGE_TYPE_RECV_LOCATION = 5;

    private static final int MESSAGE_TYPE_SENT_VOICE = 6;
    private static final int MESSAGE_TYPE_RECV_VOICE = 7;

    private static final int MESSAGE_TYPE_SENT_VIDEO = 8;
    private static final int MESSAGE_TYPE_RECV_VIDEO = 9;

    private static final int MESSAGE_TYPE_SENT_FILE = 10;
    private static final int MESSAGE_TYPE_RECV_FILE = 11;

    private static final int MESSAGE_TYPE_SENT_EXPRESSION = 12;
    private static final int MESSAGE_TYPE_RECV_EXPRESSION = 13;


    private List<EMMessage> mMsgList;
    private String touxiang;
    private String name;
    private voicViewHolder voiceholder;
private  Context context;

    public MsgAdapter(List<EMMessage> mMsgList, String touxiang, String name, Context context){
        this.mMsgList = mMsgList;
        this.touxiang = touxiang;
        this.name = name;
      this.context = context;
    }

    private AnimationDrawable animationDrawable;
    private Timer timer;
    // 设置倒计时时间
    private int num = 0;

    private List<String> imgPath = new ArrayList<>();

//    private ArrayList<String> imageList = new ArrayList<String>();
//    public void setImageList(ArrayList<String> imageList) {
//        this.imageList = imageList;
//    }
//
//    private HashMap<Integer, Integer> imagePosition = new HashMap<Integer, Integer>();
//    public void setImagePosition(HashMap<Integer, Integer> imagePosition) {
//        this.imagePosition = imagePosition;
//    }


    //右面 我说的
    private ChatLinter chatlinter;

    public void setChatlinter(ChatLinter chatlinter) {
        this.chatlinter = chatlinter;
    }

    public ChatLinter setChatlinter() {
        return chatlinter;
    }

    public interface ChatLinter{

        void chatlinter(EMMessage emMessage);
    }

//    //左面 她说的
    private ChatLinterzuo chatlinterzuo;

    public void setChatLinterzuo(ChatLinterzuo chatlinterzuo) {
        this.chatlinterzuo = chatlinterzuo;
    }

    public ChatLinterzuo setChatLinterzuo() {
        return chatlinterzuo;
    }

    public interface ChatLinterzuo{

        void ChatLinterzuo(EMMessage emMessage);
    }


    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){

                case 1:

                    voiceholder.iv_send_voic_tubiao.setImageResource(R.drawable.v_anim3);

                    break;


            }
        }
    };


    public int getItemViewType(int position) {

        EMMessage message = mMsgList.get(position);

        if (message == null) {
            return -1;
        }

        //        direct   消息谁发出

        if (message.getType() == EMMessage.Type.TXT) {

            if (message.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false)) {
                return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_EXPRESSION : MESSAGE_TYPE_SENT_EXPRESSION;
            }
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_TXT : MESSAGE_TYPE_SENT_TXT;
        }
        if (message.getType() == EMMessage.Type.IMAGE) {
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_IMAGE : MESSAGE_TYPE_SENT_IMAGE;

        }
        if (message.getType() == EMMessage.Type.LOCATION) {
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_LOCATION : MESSAGE_TYPE_SENT_LOCATION;
        }
        if (message.getType() == EMMessage.Type.VOICE) {
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE : MESSAGE_TYPE_SENT_VOICE;
        }
        if (message.getType() == EMMessage.Type.VIDEO) {
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO : MESSAGE_TYPE_SENT_VIDEO;
        }
        if (message.getType() == EMMessage.Type.FILE) {
            return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_FILE : MESSAGE_TYPE_SENT_FILE;
        }
        return -1;    // invalid
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //文本和表情
        if (viewType == 0 || viewType == 1){

            View view = View.inflate(parent.getContext(), R.layout.recyclerview_item, null);
            textViewHolder textHolder = new textViewHolder(view);

            return textHolder;
    }

        //语音
        if (viewType == 6 || viewType == 7){

            View view = View.inflate(parent.getContext(), R.layout.recyclerview_item_voice, null);
            voiceholder = new voicViewHolder(view);

            return voiceholder;
        }

        //图片
        if (viewType == 2 || viewType == 3){

            View view = View.inflate(parent.getContext(), R.layout.recyclerview_item_photo, null);
            photoViewHolder photoholder = new photoViewHolder(view);

            return photoholder;
        }

    return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

//文本
        if (holder instanceof textViewHolder) {

            textViewHolder textViewHolder = (textViewHolder) holder;

            EMMessage emMessage = mMsgList.get(position);

            EMTextMessageBody txtBody = (EMTextMessageBody) emMessage.getBody();
            Spannable span = EaseSmileUtils.getSmiledText(IApplication.application, txtBody.getMessage());

            textViewHolder.iv_receive_touxiang.setImageURI(touxiang);
            textViewHolder.tv_receive_name.setText(name);


            switch (holder.getItemViewType()) {

                case MESSAGE_TYPE_RECV_TXT:

                    textViewHolder.linearLayout_receive.setVisibility(View.VISIBLE);
                    textViewHolder.linearLayout_send.setVisibility(View.GONE);

                    // 设置内容
                    textViewHolder.tv_receive.setText(span, TextView.BufferType.SPANNABLE);

                    break;

                case MESSAGE_TYPE_SENT_TXT:

                    textViewHolder.linearLayout_send.setVisibility(View.VISIBLE);
                    textViewHolder.linearLayout_receive.setVisibility(View.GONE);


                    textViewHolder.tv_send.setText(span, TextView.BufferType.SPANNABLE);

                    break;

            }

            //语音
        }else if (holder instanceof voicViewHolder){

            final voicViewHolder voiceholder = (voicViewHolder) holder;

            EMMessage emMessage = mMsgList.get(position);

            voiceholder.iv_receive_touxiang_voice.setImageURI(touxiang);
            voiceholder.tv_receive_name_voice.setText(name);

            EMVoiceMessageBody getbodys = (EMVoiceMessageBody) emMessage.getBody();
            final int lengths =  getbodys.getLength();
            voiceholder.tv_timelength.setText(lengths/1000 + "\"");// +"\""是为了转换成秒
            voiceholder.tv_timelength_you.setText(lengths/1000 + "\"");// +"\""是为了转换成秒

            num = lengths/1000;

            switch (holder.getItemViewType()) {

                case MESSAGE_TYPE_RECV_VOICE:  //接收

                    voiceholder.linearLayout_receive_voice.setVisibility(View.VISIBLE);
                    voiceholder.linearLayout_send_voice.setVisibility(View.GONE);


                    RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams)voiceholder.iv_receive_voic_iv.getLayoutParams();

                    int i = lengths / 20;

                    if (i>400){
                        i = 400;
                    }


                    params.width=i;//设置当前控件布局的高度

                    voiceholder.iv_receive_voic_iv.setLayoutParams(params);//将设置好的布局参数应用到控件中
                   //将设置好的布局参数应用到控件中


                    voiceholder.iv_receive_voic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            chatlinterzuo.ChatLinterzuo(mMsgList.get(position));
                        }
                    });


                    break;

                case MESSAGE_TYPE_SENT_VOICE:

                    voiceholder.linearLayout_receive_voice.setVisibility(View.GONE);
                    voiceholder.linearLayout_send_voice.setVisibility(View.VISIBLE);

                    LinearLayout.LayoutParams params1= (LinearLayout.LayoutParams)voiceholder.iv_send_voic.getLayoutParams();

                    int i1 = lengths / 20;

                    if (i1>400){
                        i1 = 400;
                    }

                    params1.width=i1;//设置当前控件布局的高度



                    Log.e("11111",params1.width+"");

                    voiceholder.iv_send_voic.setLayoutParams(params1);//将设置好的布局参数应用到控件中


                    voiceholder.iv_send_voic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            chatlinter.chatlinter(mMsgList.get(position));

                            voiceholder.iv_send_voic_tubiao.setImageResource(R.drawable.voice_you);
                            animationDrawable = (AnimationDrawable)  voiceholder.iv_send_voic_tubiao.getDrawable();


                            // 创建计时器
                            timer = new Timer();
                            // 创建时间任务类
                            TimerTask task = new TimerTask() {

                                @Override
                                public void run() {

                                            if (num > 0) {

                                                animationDrawable.start();

                                                num--;

                                            } else if (num == 0) {

                                                timer.cancel();

                                                animationDrawable.stop();

                                                handler.sendEmptyMessage(1);

                                            }
                                }
                            };

                            timer.schedule(task, 0,1000);

                            num = lengths/1000;
                        }
                    });

                    break;
            }


            //图片
            }else if (holder instanceof photoViewHolder){

            final photoViewHolder photoHolder = (photoViewHolder) holder;

            EMMessage emMessage = mMsgList.get(position);

            photoHolder.iv_zuo_touxiang_photo.setImageURI(touxiang);
            photoHolder.tv_zuo_name_photo.setText(name);

            switch (holder.getItemViewType()) {

                case MESSAGE_TYPE_SENT_IMAGE:  //发送


                    photoHolder.linearLayout_zuo_photo.setVisibility(View.GONE);
                    photoHolder.linearLayout_you_photo.setVisibility(View.VISIBLE);

                    EMImageMessageBody imgBody = (EMImageMessageBody) emMessage.getBody();

                    // thumbPath 本地缩略图地址
                    // yuantuv 本地原图地址
                    String thumbPath = imgBody.thumbnailLocalPath();
                    String yuantu = imgBody.getLocalUrl();

                    Log.e("BenDi fasongthumbPath",thumbPath+"");
                    Log.e("BenDi fasongyuantu",yuantu+"");


                    imgPath.add(imgBody.getRemoteUrl());

                    Log.e("BenDi fasongimgBody.getRemoteUrl()",imgBody.getRemoteUrl()+"");


                    if (!new File(thumbPath).exists()) {

                        thumbPath = EaseImageUtils.getThumbnailImagePath(imgBody.getLocalUrl());
                    }

//                    String remoteUrl = imgBody.getRemoteUrl();

                    Glide.with(IApplication.application).load(thumbPath).into(photoHolder.iv_photo_you_photo);

                    photoHolder.iv_photo_you_photo.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            Log.e("BenDi iv_photo_you_photo","发送点击事件");


                            //点击位置及对象传入dialog
                            PicShowLiaoTianDialog dialog=new PicShowLiaoTianDialog(context,imgPath,0);
                            dialog.show();


//                            Intent intent = new Intent(IApplication.application, ImageViewActivity.class);
//                            intent.putStringArrayListExtra("images", imageList);
//                            intent.putExtra("clickedIndex", imagePosition.get(position));
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            IApplication.application.startActivity(intent);
                        }
                    });
                    break;

                case MESSAGE_TYPE_RECV_IMAGE:  //接收


                    photoHolder.linearLayout_zuo_photo.setVisibility(View.VISIBLE);
                    photoHolder.linearLayout_you_photo.setVisibility(View.GONE);

                    EMImageMessageBody imgBodyshou = (EMImageMessageBody) emMessage.getBody();



                    // thumbPath 本地缩略图地址
                    // yuantuv 本地原图地址
                    String thumbPathshou = imgBodyshou.thumbnailLocalPath();
                    String yuantushou = imgBodyshou.getLocalUrl();


                    Log.e("BenDi jieshouthumbPath",thumbPathshou+"");
                    Log.e("BenDi jieshouyuantu",yuantushou+"");
                    Log.e("BenDi jieshouimgBody.getRemoteUrl()",imgBodyshou.getRemoteUrl()+"");

                    imgPath.add(imgBodyshou.getRemoteUrl());

                    if (!new File(thumbPathshou).exists()) {

                        thumbPathshou = EaseImageUtils.getThumbnailImagePath(imgBodyshou.getLocalUrl());
                    }

                    //            String remoteUrl = imgBody.getRemoteUrl();

                    Glide.with(IApplication.application).load(thumbPathshou).into(photoHolder.iv_photo_zuo_photo);

                    photoHolder.iv_photo_zuo_photo.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            Log.e("BenDi iv_photo_you_photo","接收点击事件");

                            //点击位置及对象传入dialog
                            PicShowLiaoTianDialog dialog=new PicShowLiaoTianDialog(context,imgPath,0);
                            dialog.show();

                        }
                    });
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList == null ? 0 : mMsgList.size();
    }

    static class textViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_receive;
        private TextView tv_send;
        private TextView tv_time;

        private LinearLayout linearLayout_receive;
        private LinearLayout linearLayout_send;
        private  SimpleDraweeView iv_receive_touxiang;
        private  TextView tv_receive_name;


        textViewHolder(View itemView) {
            super(itemView);

            linearLayout_receive = (LinearLayout) itemView.findViewById(R.id.left_receive);
            linearLayout_send = (LinearLayout) itemView.findViewById(R.id.right_send);

            tv_time = (TextView) itemView.findViewById(R.id.chat_time);

            iv_receive_touxiang = (SimpleDraweeView) itemView.findViewById(R.id.left_touxiang);
            tv_receive_name = (TextView) itemView.findViewById(R.id.left_name);

            tv_receive = (TextView) itemView.findViewById(R.id.tv_receive);
            tv_send = (TextView) itemView.findViewById(R.id.tv_send);
        }
    }

    static class voicViewHolder extends RecyclerView.ViewHolder {


        private  LinearLayout linearLayout_receive_voice;
        private  RelativeLayout iv_receive_voic;
        private  TextView tv_timelength;
        private  TextView tv_timelength_you;
        private  LinearLayout linearLayout_send_voice;
        private  RelativeLayout iv_send_voic;
        private  SimpleDraweeView iv_receive_touxiang_voice;
        private  TextView tv_receive_name_voice;
        private  TextView voice_tv_time;
        private  ImageView iv_receive_voic_iv;
        private final ImageView iv_send_voic_tubiao;

        voicViewHolder(View itemView) {
            super(itemView);

            linearLayout_receive_voice =  (LinearLayout) itemView.findViewById(R.id.voice_zuo_receive);
            iv_receive_voic = (RelativeLayout) itemView.findViewById(R.id.voice_zuo_voice);
            iv_receive_voic_iv = (ImageView) itemView.findViewById(R.id.voice_zuo_voice_view);

            tv_timelength = (TextView) itemView.findViewById(R.id.voice_zuo_length);
            tv_timelength_you = (TextView) itemView.findViewById(R.id.voice_you_length);

            voice_tv_time = (TextView) itemView.findViewById(R.id.voice_chat_time);

            iv_receive_touxiang_voice = (SimpleDraweeView) itemView.findViewById(R.id.voice_receive_touxiang);
            tv_receive_name_voice = (TextView) itemView.findViewById(R.id.voice_receive_name);

            linearLayout_send_voice = (LinearLayout) itemView.findViewById(R.id.voice_you_send);
            iv_send_voic = (RelativeLayout) itemView.findViewById(R.id.voice_you_voices);
            iv_send_voic_tubiao = (ImageView) itemView.findViewById(R.id.id_recorder_anim);
        }
    }


    static class photoViewHolder extends RecyclerView.ViewHolder {


//        private  BubbleImageView iv_photo_you_photo;
        private ImageView iv_photo_you_photo;
        private  LinearLayout linearLayout_you_photo;
//        private  BubbleImageView iv_photo_zuo_photo;
        private  ImageView iv_photo_zuo_photo;
        private  LinearLayout linearLayout_zuo_photo;

        private  SimpleDraweeView iv_zuo_touxiang_photo;
        private  TextView tv_zuo_name_photo;

        public photoViewHolder(View itemView) {
            super(itemView);

            //我
            iv_photo_you_photo = (ImageView)itemView.findViewById(R.id.photo_you_photo);
            linearLayout_you_photo = (LinearLayout) itemView.findViewById(R.id.photo_you_send);

            //收
            linearLayout_zuo_photo = (LinearLayout) itemView.findViewById(R.id.photo_zuo_receive);
            iv_photo_zuo_photo = (ImageView) itemView.findViewById(R.id.photo_zuo_photo);
            iv_zuo_touxiang_photo = (SimpleDraweeView) itemView.findViewById(R.id.photo_receive_touxiang);
            tv_zuo_name_photo = (TextView) itemView.findViewById(R.id.photo_receive_name);
        }
    }




}