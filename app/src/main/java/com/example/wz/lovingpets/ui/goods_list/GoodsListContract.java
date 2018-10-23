package com.example.wz.lovingpets.ui.goods_list;

import com.example.wz.lovingpets.base.BasePresenter;
import com.example.wz.lovingpets.base.BaseView;

public interface GoodsListContract {
    interface View extends BaseView{

    }

    interface Presenter extends BasePresenter{
        void getGoods(String family);
    }
}
