package baway.com.dyouyuan.view;


import java.util.List;

import baway.com.dyouyuan.bean.DataBean;
import baway.com.dyouyuan.bean.News;

public interface FirstFragmentView {

    public void success(News indexBean, long currentime);
    public void failed(List<DataBean> list);
}
