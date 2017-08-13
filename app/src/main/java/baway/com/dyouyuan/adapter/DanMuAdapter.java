package baway.com.dyouyuan.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import baway.com.dyouyuan.R;

/**
 * Created by : Xunqiang
 * 2017/8/8 20:47
 */

public class DanMuAdapter extends BaseAdapter {

        Context context;
    List<String> list;

    public DanMuAdapter(Context context, List<String> list) {

        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();

    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewholder ;

        if (convertView == null ){

            viewholder = new ViewHolder();

            convertView = View.inflate(context, R.layout.fragment_fourth_list, null);


            viewholder.textview = (TextView) convertView.findViewById(R.id.fragment_fourth_listtextview);

            convertView.setTag(viewholder);

        }else{

            viewholder = (ViewHolder) convertView.getTag();

        }


        viewholder.textview.setText(list.get(position));

        return convertView;
    }

    class ViewHolder {

        TextView textview ;
    }
}
