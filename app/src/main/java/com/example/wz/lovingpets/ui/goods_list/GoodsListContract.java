package com.example.wz.lovingpets.ui.goods_list;

import com.example.wz.lovingpets.base.BaseContract;

public interface GoodsListContract {
    interface View extends BaseContract.BaseView{

    }

    interface Presenter extends BaseContract.BasePresenter{
        void getGoods(String family);
    }
}
