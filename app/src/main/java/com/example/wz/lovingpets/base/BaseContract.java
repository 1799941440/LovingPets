package com.example.wz.lovingpets.base;

public interface BaseContract {
    interface BaseView<P extends BasePresenter> {
        void setPresenter(P presenter);
        boolean isActive();
    }

    interface BasePresenter<V> {

        void attachMVPView(V view);

        void detachMVPView();

    }

}