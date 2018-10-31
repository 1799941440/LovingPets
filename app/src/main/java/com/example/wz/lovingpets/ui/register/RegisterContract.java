package com.example.wz.lovingpets.ui.register;

import com.example.wz.lovingpets.base.BaseContract;
import com.example.wz.lovingpets.entity.User;
import com.example.wz.lovingpets.ui.login.LoginContract;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface RegisterContract {

    interface View extends BaseContract.BaseView<RegisterPresenter> {

        void registerSuccess(User user, boolean success);
    }

    interface Presenter extends BaseContract.BasePresenter<RegisterContract.View> {

        void register(String username, String password);
    }
}
