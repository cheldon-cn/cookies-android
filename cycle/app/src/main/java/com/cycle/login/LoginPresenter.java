package com.cycle.login;

import com.cycle.data.NetworkTaskScheduler;
import com.cycle.data.OnResponseListener;
import com.cycle.data.task.LoginTask;

/**
 *
 * @author cycle.member
 * @date 2017/9/16
 */

public class LoginPresenter implements LoginContract.Presenter {

    LoginContract.View mView;

    public LoginPresenter(LoginContract.View view) {
        mView = view;
        view.setPresenter(this);
    }

    @Override
    public void login(String email, String password) {
        mView.startLoading();
        NetworkTaskScheduler.getInstance().execute(new LoginTask(email, password, new OnResponseListener<String>() {
            @Override
            public void onSucceed(String data) {
                mView.stopLoading();
                mView.onLoginSucceed(data);
            }

            @Override
            public void onFailed(String msg) {
                mView.stopLoading();
                mView.onLoginFailed(msg);
            }
        }));
    }
}
