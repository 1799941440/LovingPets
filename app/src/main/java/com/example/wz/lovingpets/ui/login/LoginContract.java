package com.example.wz.lovingpets.ui.login;

import com.example.wz.lovingpets.base.BaseContract;
import com.example.wz.lovingpets.db.UserDao;
import com.example.wz.lovingpets.entity.User;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface LoginContract {

    interface LoginView extends BaseContract.BaseView<LoginPresenter> {

        void loginSuccess(User user, boolean success);
        void showLoginAnim();
        void showTip(String s);
        UserDao getUserDao();
    }

    interface Presenter extends BaseContract.BasePresenter<LoginView> {

        void login(String username, String password);

    }
}
