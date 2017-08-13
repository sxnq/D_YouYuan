package baway.com.dyouyuan.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import baway.com.dyouyuan.R;
import baway.com.dyouyuan.bean.DataBean;


public class SingleAdapter2 extends RecyclerView.Adapter<SingleAdapter2.MasonryView>{

    private List<DataBean> products;


    public SingleAdapter2(List<DataBean> list) {
        products=list;
    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.radioactivitypubuliumasonry_item, viewGroup, false);
        return new MasonryView(view);
    }

    @Override
    public void onBindViewHolder(MasonryView masonryView, int position) {

        masonryView.textView.setText(products.get(position).getArea());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class MasonryView extends  RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public MasonryView(View itemView){
            super(itemView);

            textView= (TextView) itemView.findViewById(R.id.masonry_item_title);
        }

    }

}