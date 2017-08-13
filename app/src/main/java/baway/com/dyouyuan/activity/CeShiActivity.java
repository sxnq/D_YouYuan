package baway.com.dyouyuan.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import java.io.File;

import baway.com.dyouyuan.R;
import baway.com.dyouyuan.speex.SpeexPlayer;
import baway.com.dyouyuan.speex.SpeexRecorder;
import baway.com.dyouyuan.utils.SDCardUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CeShiActivity extends Activity {

    private String fileName;
    private SpeexRecorder recorderInstance;

    @BindView(R.id.button1)
    Button bt1;
    @BindView(R.id.button2)
    Button bt2;

    @BindView(R.id.button3)
    Button bt3;

    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){

                case 1:

                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ce_shi);
        ButterKnife.bind(this);

        bt1.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String filePath = Environment.getExternalStorageDirectory() + File.separator + SDCardUtils.DLIAO;
                System.out.println("filePath:" + filePath);
                File file = new File(filePath  + "/");
                System.out.println("file:" + file);
                if (!file.exists()) {
                    file.mkdirs();
                }
                fileName = file + File.separator + System.currentTimeMillis() + ".spx";
                System.out.println("保存文件名：＝＝ " + fileName);
                recorderInstance = new SpeexRecorder(fileName, handler);
                Thread th = new Thread(recorderInstance);
                th.start();
                recorderInstance.setRecording(true);            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recorderInstance.setRecording(false);
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpeexPlayer player = new SpeexPlayer(fileName,handler);
                player.startPlay();
            }
        });

    }

//    @OnClick({R.id.button1, R.id.button2})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.button1:
//
//                String filePath = Environment.getExternalStorageDirectory() + File.separator + SDCardUtils.DLIAO;
//                System.out.println("filePath:" + filePath);
//                File file = new File(filePath  + "/");
//                System.out.println("file:" + file);
//                if (!file.exists()) {
//                    file.mkdirs();
//                }
//
//                fileName = file + File.separator + System.currentTimeMillis() + ".spx";
//                System.out.println("保存文件名：＝＝ " + fileName);
//                recorderInstance = new SpeexRecorder(fileName, handler);
//                Thread th = new Thread(recorderInstance);
//                th.start();
//                recorderInstance.setRecording(true);
//
//                break;
//            case R.id.button2:
//
//                recorderInstance.setRecording(false);
//
//                System.out.println("fileName = " + new File(fileName).length());
//
//
//                break;
//
//
//        }
//    }
}
