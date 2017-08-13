package baway.com.dyouyuan.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import baway.com.dyouyuan.R;
import baway.com.dyouyuan.bean.DataBean;

public class SingleAdapter extends RecyclerView.Adapter<SingleAdapter.MyViewHolder> {


    Context context;
    List<DataBean> list;

    public SingleAdapter(Context context,  List<DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHolder iViewHolder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.radiogroupactivity_single_item, parent, false));

        return iViewHolder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv.setText(list.get(position).getArea());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
//            tv = (TextView) itemView.findViewById(R.id.radiogroupactivity_tv1);
        }
    }
}
