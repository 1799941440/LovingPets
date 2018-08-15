package com.example.wz.lovingpets.ui.register;

import com.example.wz.lovingpets.base.BasePresenter;
import com.example.wz.lovingpets.base.BaseView;
import com.example.wz.lovingpets.entity.User;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface RegisterContract {

    interface View extends BaseView<Presenter> {

        void registerSuccess(User user, boolean success);
    }

    interface Presenter extends BasePresenter {

        void register(String username, String password);
    }
}
