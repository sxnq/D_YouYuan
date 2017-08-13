package baway.com.dyouyuan.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import baway.com.dyouyuan.IApplication;
import baway.com.dyouyuan.R;
import baway.com.dyouyuan.bean.AllFriendBean;

/**
 * Created by : Xunqiang
 * 2017/7/14 20:13
 */

public class SecondFragmentAdapter extends BaseAdapter{

    private static List<AllFriendBean.DataBean> data;


    public void setData(AllFriendBean allFriendBean,boolean flag){

        if (data == null) {
            data = new ArrayList<>();
        }

        if (flag) {         //true表示刷新

            data.clear();

        }

        data.addAll(allFriendBean.getData());

        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        A a = null;

        if (convertView == null){

            a = new A ();

            convertView = View.inflate(IApplication.application, R.layout.fragment_second_list,null);

            a.iv = (ImageView) convertView.findViewById(R.id.fragment_second_list_iv);
            a.tv = (TextView) convertView.findViewById(R.id.fragment_second_list_tv1);

            convertView.setTag(a);

        }else{

            a = (A) convertView.getTag();
        }

        Glide.with(IApplication.application).load(data.get(position).getImagePath()).error(R.mipmap.ic_launcher).into(a.iv);
        a.tv.setText(data.get(position).getNickname());


        return convertView;
    }



    class A {

        ImageView iv;
        TextView tv;
    }
}
