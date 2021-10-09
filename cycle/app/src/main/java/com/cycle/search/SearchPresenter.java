package com.cycle.search;

import com.cycle.data.NetworkTaskScheduler;
import com.cycle.data.OnResponseListener;
import com.cycle.data.entity.UserProfile;
import com.cycle.data.task.GetUserProfileTask;

/**
 *
 * @author Lenovo
 * @date 2017/9/29
 */

public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View mView;

    public SearchPresenter(SearchContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getUserProfile(String url) {
        mView.startLoading();
        NetworkTaskScheduler.getInstance().execute(new GetUserProfileTask(url, new OnResponseListener<UserProfile>() {
            @Override
            public void onSucceed(UserProfile data) {
                mView.stopLoading();
                mView.onGetUserProfileSucceed(data);
            }

            @Override
            public void onFailed(String msg) {
                mView.stopLoading();
                mView.onGetUserProfileFailed(msg);
            }
        }));
    }
}
