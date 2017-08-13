package baway.com.dyouyuan.jiekou;

import android.view.View;

public interface OnItemClickListener<T> {

    void onItemClick(int position, T t, View v,String s);
}