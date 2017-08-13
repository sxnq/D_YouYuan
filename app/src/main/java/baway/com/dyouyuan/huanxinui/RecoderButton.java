package baway.com.dyouyuan.huanxinui;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

import baway.com.dyouyuan.R;


public class RecoderButton extends Button{
    
    //按钮的三个状态
    
    private static final int STATE_NORMAL = 1;//正常
    private static final int STATE_RECODING = 2;//录音状态
    private static final int STATE_CACLE = 3;//取消状态
    
    private int mCurState = STATE_NORMAL;//记录当前按钮状态
    
    private int Y = 50;//限定手指移动的上下宽度

    private DialogManager mDialogManager;
    

    public RecoderButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    
    }
    
    
    
    //捕捉按钮点击事件
    public boolean onTouchEvent(MotionEvent event) {
        
        int x = (int) event.getX();
        int y =(int)event.getY();
        
        switch(event.getAction()){
        
        
        case MotionEvent.ACTION_DOWN:
            
            changeState(STATE_RECODING);//按下按钮，改变按钮状态
            
            break;
        case MotionEvent.ACTION_MOVE:
            
            if(wantCancel(x,y)){ //如果检测到取消，则改变按钮状态为取消
                
            changeState(STATE_CACLE);
            
            }else{
                changeState(STATE_RECODING);
            }
            
            break;
        case MotionEvent.ACTION_UP:
            
            reset();//各种设置复位
            
            break;
            default:
                break;
        }
        
        return super.onTouchEvent(event);
    }



    //复位
    private void reset() {
        
        mCurState = STATE_NORMAL;
        changeState(STATE_NORMAL);
        
    }



    //检查手指移动范围，从而确定用户是否想取消录音
    private boolean wantCancel(int x, int y) {
        
        if(x<0||x>getWidth()){
            
            return true;
        }
        
        if(y<0||y>getHeight()+Y){
            return true;
        }
        return false;
    }


    
    //改变状态，包括按钮等操作
    private void changeState(int state) {
        
        if(mCurState != state){
            
            mCurState = state;
            
        }
        
        switch(mCurState){
              
        case STATE_NORMAL:
            
            setText(R.string.btn_normal);
            
            break;
        case STATE_RECODING:
            
            setText(R.string.btn_recoding);
            
            break;
        case STATE_CACLE:
            
            setText(R.string.btn_cancel);
            
            break;
            default:
                break;
        
        }
        
    }
    
    

}