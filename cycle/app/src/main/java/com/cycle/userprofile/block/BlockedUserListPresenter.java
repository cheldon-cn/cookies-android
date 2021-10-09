package com.cycle.userprofile.block;

import com.cycle.data.NetworkTaskScheduler;
import com.cycle.data.OnResponseListener;
import com.cycle.data.entity.UserProfile;
import com.cycle.data.task.GetBlockedUserListTask;

import java.util.List;

/**
 * @author cycle.member
 * @date 2019-07-21
 */
public class BlockedUserListPresenter implements BlockedUserListContract.Presenter {

    private BlockedUserListContract.View mView;

    public BlockedUserListPresenter(BlockedUserListContract.View view) {
        mView = view;
        view.setPresenter(this);
    }

    @Override
    public void getBlockedUserList() {
        NetworkTaskScheduler.getInstance().execute(new GetBlockedUserListTask(new OnResponseListener<List<UserProfile>>() {
            @Override
            public void onSucceed(List<UserProfile> data) {
                mView.onGetBlockedUserListSucceed(data);
            }

            @Override
            public void onFailed(String msg) {
                mView.onGetBlockedUserListFailed(msg);
            }
        }));
    }
}
