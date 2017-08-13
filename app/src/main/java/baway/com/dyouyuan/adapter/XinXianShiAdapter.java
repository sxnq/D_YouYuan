package baway.com.dyouyuan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import baway.com.dyouyuan.R;
import baway.com.dyouyuan.bean.XinXianBean;
import baway.com.dyouyuan.customview.NineGridTestLayout;

/**
 * Created by : Xunqiang
 * 2017/8/3 18:37
 */

public class XinXianShiAdapter extends RecyclerView.Adapter<XinXianShiAdapter.ViewHolder> {

    private Context mContext;
    private List<XinXianBean.ListBean> listzong;
    protected LayoutInflater inflater;
    private List<String> imgList;

    public XinXianShiAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    public void setList(List<XinXianBean.ListBean> list) {
        listzong = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View convertView = inflater.inflate(R.layout.item_bbs_nine_grid, parent, false);
        ViewHolder viewHolder = new ViewHolder(convertView);
        return viewHolder;
    }

        @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

            imgList = new ArrayList<>();

        if (listzong.get(position).getType() == 2){

            String[] img  = listzong.get(position).getPic().split("\\|");

            for (int i = 0 ; i <img.length ; i++ ) {
                imgList.add(img[i]);
            }

            Log.e("imgList",imgList.size()+"");

            holder.layout.setVisibility(View.VISIBLE);
            holder.layout.setUrlList(imgList);

        }else{

            holder.layout.setVisibility(View.GONE);
        }

        holder.textView.setText(listzong.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return getListSize(listzong);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        NineGridTestLayout layout;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = (NineGridTestLayout) itemView.findViewById(R.id.layout_nine_grid);
            textView = (TextView) itemView.findViewById(R.id.xinxian_tv);
        }
    }

    private int getListSize(List<XinXianBean.ListBean> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        return list.size();
    }
}
