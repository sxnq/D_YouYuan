package baway.com.dyouyuan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import baway.com.dyouyuan.R;
import baway.com.dyouyuan.bean.DataBean;
import baway.com.dyouyuan.bean.News;
import baway.com.dyouyuan.jiekou.OnItemClickListener;
import baway.com.dyouyuan.utils.DeviceUtils;
import baway.com.dyouyuan.utils.DisanceUtils;
import baway.com.dyouyuan.utils.PointToDistance;
import baway.com.dyouyuan.utils.PreferencesUtils;
import butterknife.BindView;
import butterknife.ButterKnife;


public class IndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DataBean> list;
    private Context context;
    private int tag = 1; // 1 先行布局 2 瀑布流
    private int itemWidth ;
    private String s;

    private View oneView;
    private View twoView;

    private OnItemClickListener<DataBean> mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<DataBean> listener) {
        this.mOnItemClickListener = listener;
    }

    public IndexAdapter(Context context) {

        this.context = context;
        //当前屏幕 的宽度 除以3
        itemWidth = DeviceUtils.getDisplayInfomation(context).x / 3 ;
    }

    public void setData(News bean,int page) {

        if (list == null) {

            list = new ArrayList<DataBean>();

//            list = IApplication.daoSession.getDataBeanDao().loadAll();
        }
        if(page == 1){
            list .clear();
        }
        list.addAll(bean.getData());
        notifyDataSetChanged();
    }
    /**
     * 2 瀑布流
     * 1 线性布局
     *
     * @param type
     */
    public void dataChange(int type) {
        this.tag = type;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == 0) {

            oneView = LayoutInflater.from(context).inflate(R.layout.radiogroupactivity_single_item, parent, false);
            final VerticalViewHolder verticalViewHolder = new VerticalViewHolder(oneView);



            return verticalViewHolder;

        } else {

            twoView = LayoutInflater.from(context).inflate(R.layout.radioactivitypubuliumasonry_item, parent, false);
            final PinterestViewHolder pinterestViewHolder = new PinterestViewHolder(twoView);


            return pinterestViewHolder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof VerticalViewHolder) {

            //列表的形式展示
            VerticalViewHolder verticalViewHolder = (VerticalViewHolder) holder;

            verticalViewHolder.indexfragmentNickname.setText(list.get(position).getNickname());

            verticalViewHolder.indexfragmentAgesex.setText(list.get(position).getIntroduce());

            String lat =  PreferencesUtils.getValueByKey(context, "jingdu","");
            String lng = PreferencesUtils.getValueByKey(context,"weidu","");

            Glide.with(context).load(list.get(position).getImagePath()).error(R.mipmap.ic_launcher).into(verticalViewHolder.indexfragmentFace);

            double olat = list.get(position).getLat();
            double olng = list.get(position).getLng() ;


            if(!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lng) && olat != 0.0 && olng != 0.0){

                double dlat = Double.valueOf(lat);
                double dlng = Double.valueOf(lng);

                //计算两点间距离
                double distanceFromTwoPoints = PointToDistance.getDistanceFromTwoPoints
                        (dlat, dlng, olat, olng);

                //DPoint dPoint = new DPoint(dlat,dlng);
//                DPoint oPoint = new DPoint(olat,olng);

                //计算两点之间的距离
//                float dis =  CoordinateConverter.calculateLineDistance(dPoint,oPoint);

                s = DisanceUtils.standedDistance((float) distanceFromTwoPoints);

                verticalViewHolder.indexfragmentAgesex.setText(list.get(position).getAge() + "岁 , " + list.get(position).getGender() + " , " + s);

                verticalViewHolder.indexfragmentintro.setText(list.get(position).getIntroduce());

                verticalViewHolder.indexfragmentNickname.setText(list.get(position).getNickname());

            } else {

                verticalViewHolder.indexfragmentAgesex.setText(list.get(position).getAge() + "岁 , " + list.get(position).getGender());
                verticalViewHolder.indexfragmentintro.setText(list.get(position).getIntroduce());
            }

            verticalViewHolder.indexfragmentNickname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.e("zzzzz",list.size()+"");
                    Log.e("zzzzz",position+"");
                    final DataBean image = list.get(position);
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(position, image, v,s);
                    }
                }
            });

        } else {

            PinterestViewHolder staggeredViewHolder = (PinterestViewHolder) holder;

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) staggeredViewHolder.indexfragmentStagger.getLayoutParams() ;

            float scale =  (float) itemWidth / (float) list.get(position).getPicWidth()  ;
            params.width = itemWidth;
            params.height = (int)( (float)scale * (float)list.get(position).getPicHeight()) ;

            System.out.println("params.scale = " + scale);
            System.out.println("params.width = " + params.width + " " + list.get(position).getPicWidth());
            System.out.println("params.height = " + params.height + "  " + list.get(position).getPicHeight());

            staggeredViewHolder.indexfragmentStagger.setLayoutParams(params);

            //            params.width = itemWidth



            Glide.with(context).load(list.get(position).getImagePath()).error(R.mipmap.ic_launcher).into(staggeredViewHolder.indexfragmentStagger);

            String lat =  PreferencesUtils.getValueByKey(context, "jingdu","");
            String lng = PreferencesUtils.getValueByKey(context,"weidu","");


            double olat = list.get(position).getLat();
            double olng = list.get(position).getLng() ;


            if(!TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lng) && olat != 0.0 && olng != 0.0) {

                double dlat = Double.valueOf(lat);
                double dlng = Double.valueOf(lng);

                //计算两点间距离
                double distanceFromTwoPoints = PointToDistance.getDistanceFromTwoPoints(dlat, dlng, olat, olng);

                staggeredViewHolder.tv.setText(DisanceUtils.standedDistance((float)distanceFromTwoPoints)+"");
            }

            twoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                     DataBean image = list.get(position);
                    Log.e("zzz",position+"");
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(position, image, v,s);
                    }
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (tag == 1) {
            return 0;
        } else {
            return 1;
        }
    }

    static class VerticalViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView2)
        TextView indexfragmentNickname;
        @BindView(R.id.textView3)
        TextView indexfragmentAgesex;
        @BindView(R.id.textView5)
        TextView indexfragmentintro;
        @BindView(R.id.radiogroupactivity_iv)
        ImageView indexfragmentFace;



        public VerticalViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class PinterestViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.masonry_item_img)
        ImageView indexfragmentStagger;

        @BindView(R.id.masonry_item_title)
        TextView tv;

        public PinterestViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
