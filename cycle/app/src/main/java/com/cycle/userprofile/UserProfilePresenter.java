package com.cycle.userprofile;

import com.cycle.data.NetworkTaskScheduler;
import com.cycle.data.OnResponseListener;
import com.cycle.data.entity.UserProfile;
import com.cycle.data.task.BlockUserTask;
import com.cycle.data.task.FollowUserTask;
import com.cycle.data.task.GetUserProfileTask;
import com.cycle.util.ConstantUtil;

/**
 *
 * @author Lenovo
 * @date 2017/9/29
 */

public class UserProfilePresenter implements UserProfileContract.Presenter {

    private UserProfileContract.View mView;

    public UserProfilePresenter(UserProfileContract.View view) {
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


    @Override
    public void followUser(String username) {
        mView.startLoading();

        String url = String.format(ConstantUtil.FOLLOW_USER_BASE_URL, username);

        NetworkTaskScheduler.getInstance().execute(new FollowUserTask(url, new OnResponseListener<Boolean>() {
            @Override
            public void onSucceed(Boolean data) {
                mView.stopLoading();
                if (data) {
                    mView.onFollowUserSucceed();
                } else {
                    mView.onUnfollowUserSucceed();
                }
            }

            @Override
            public void onFailed(String msg) {
                mView.stopLoading();
                mView.onFollowUserFailed(msg);
            }
        }));
    }

    @Override
    public void unfollowUser(String username) {
        mView.startLoading();

        String url = String.format(ConstantUtil.FOLLOW_USER_BASE_URL, username);

        NetworkTaskScheduler.getInstance().execute(new FollowUserTask(url, new OnResponseListener<Boolean>() {
            @Override
            public void onSucceed(Boolean data) {
                mView.stopLoading();
                if (!data) {
                    mView.onUnfollowUserSucceed();
                } else {
                    mView.onFollowUserSucceed();
                }
            }

            @Override
            public void onFailed(String msg) {
                mView.stopLoading();
                mView.onUnfollowUserFailed(msg);
            }
        }));
    }

    @Override
    public void blockUser(UserProfile profile) {
        mView.startLoading();

        NetworkTaskScheduler.getInstance().execute(new BlockUserTask(profile, BlockUserTask.TYPE_BLOCK, new OnResponseListener<Boolean>() {
            @Override
            public void onSucceed(Boolean data) {
                mView.stopLoading();
                if (data) {
                    mView.onBlockUserSucceed();
                } else {
                    mView.onBlockUserFailed("屏蔽失败");
                }
            }

            @Override
            public void onFailed(String msg) {
                mView.stopLoading();
                mView.onUnblockUserFailed(msg);
            }
        }));
    }

    @Override
    public void unblockUser(UserProfile profile) {
        mView.startLoading();

        NetworkTaskScheduler.getInstance().execute(new BlockUserTask(profile, BlockUserTask.TYPE_UNBLOCK, new OnResponseListener<Boolean>() {
            @Override
            public void onSucceed(Boolean data) {
                mView.stopLoading();
                if (data) {
                    mView.onUnblockUserSucceed();;
                } else {
                    mView.onUnblockUserFailed("解除屏蔽失败");
                }
            }

            @Override
            public void onFailed(String msg) {
                mView.stopLoading();
                mView.onUnblockUserFailed(msg);
            }
        }));
    }
}
