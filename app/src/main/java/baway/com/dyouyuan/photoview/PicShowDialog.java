package baway.com.dyouyuan.photoview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

import baway.com.dyouyuan.R;
import baway.com.dyouyuan.bean.UserInfoBean;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PicShowDialog extends Dialog {
    private Context context;
    private View view;
    private List<UserInfoBean.DataBean.PhotolistBean> imageInfos;//接收传入集合
    private MyViewPager vp;
    private List<View> views = new ArrayList<View>();//图片上圆点集合
    private LayoutAnimationController lac;
    private LinearLayout ll_point;
    private ViewPagerAdapter pageAdapter;
    private int position;//点击位置
    private LinearLayout.LayoutParams paramsL = new LinearLayout.LayoutParams(10, 10);
    // 图片缓存 默认 等
    private DisplayImageOptions optionsImag = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.mipmap.ic_launcher)
            .showImageOnFail(R.mipmap.ic_launcher).cacheInMemory(true).cacheOnDisk(true)
            .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY)
            .bitmapConfig(Bitmap.Config.RGB_565).build();

    public PicShowDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }


    public PicShowDialog(Context context, List<UserInfoBean.DataBean.PhotolistBean> imageInfos, int position) {
        this(context, R.style.Pic_Dialog);
        this.imageInfos = imageInfos;
        this.position = position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dialog_pic);
        getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        getWindow().setLayout(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        vp = (MyViewPager) findViewById(R.id.vp);
        ll_point = (LinearLayout) findViewById(R.id.ll_point);
//        init();
        initMyPageAdapter();
//        vp.setAdapter(new ViewPagerAdapter());
        vp.setCurrentItem(position);
        //滑动监听设置图片滑动时，相应点跟着变化
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if (views.size() != 0 && views.get(position) != null) {

                    for (int i = 0; i < views.size(); i++) {
                        if (i == position) {
                            views.get(i).setBackgroundResource(R.drawable.point_focus2);
                        } else {
                            views.get(i).setBackgroundResource(R.drawable.point_normal2);
                        }
                    }

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }
    /***
     * 初始化viewpager适配器
     */

    private void initMyPageAdapter() {
        initPoint();
        if (pageAdapter == null) {
            pageAdapter = new ViewPagerAdapter();
            if (vp != null) {
                vp.setAdapter(pageAdapter);
            }

        } else {
            pageAdapter.notifyDataSetChanged();
        }
    }

//初始化圆点
    private void initPoint() {
        views.clear();
        ll_point.removeAllViews();
        if (imageInfos.size()==1){
            ll_point.setVisibility(View.GONE);
        }else {
            for (int i = 0; i < imageInfos.size(); i++) {
                View view = new View(context);
                paramsL.setMargins(dip2px(context, 5), dip2px(context, 2), 0, dip2px(context, 5));
                view.setLayoutParams(paramsL);
                if (i == position) {
                    view.setBackgroundResource(R.drawable.point_focus2);
                } else {
                    view.setBackgroundResource(R.drawable.point_normal2);
                }

                views.add(view);
                ll_point.addView(view);
            }
        }

    }

//viewpager适配器
    private class ViewPagerAdapter extends PagerAdapter {


        @Override
        public int getCount() {
            return imageInfos.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view =View.inflate(context, R.layout.item_pic_show, null);
            PhotoView photoView = (PhotoView) view.findViewById(R.id.pic_pv);

//这里使用Imageloader加载框架，加载网络图片，图片地址给的是网络图片。
             ImageLoader.getInstance().displayImage(imageInfos.get(position).getImagePath(),photoView,optionsImag);
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float x, float y) {
                    dismiss();
                }
            });


                    ((ViewPager) container).addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }
    }
        private   int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}