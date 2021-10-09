package com.cycle.notifications;

import com.cycle.data.NetworkTaskScheduler;
import com.cycle.data.OnResponseListener;
import com.cycle.data.entity.ListResult;
import com.cycle.data.entity.Notification;
import com.cycle.data.task.BaseTask;
import com.cycle.data.task.GetNotificationListTask;
import com.cycle.util.UrlUtil;

/**
 * @author cycle.member
 * @date 2018/8/19
 */
public class NotificationsPresenter implements NotificationsContract.Presenter {

    private NotificationsContract.View mView;

    private BaseTask mCurrentTask;

    public NotificationsPresenter(NotificationsContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void getNotificationList() {
        if (mCurrentTask != null) {
            mCurrentTask.cancel();
        }

        mCurrentTask = new GetNotificationListTask(mView.getUrl(),
                new OnResponseListener<ListResult<Notification>>() {
                    @Override
                    public void onSucceed(ListResult<Notification> data) {
                        mView.onGetNotificationListSucceed(data);
                        mCurrentTask = null;
                    }

                    @Override
                    public void onFailed(String msg) {
                        mView.onGetNotificationListFailed(msg);
                        mCurrentTask = null;
                    }
                });

        NetworkTaskScheduler.getInstance().execute(mCurrentTask);
    }

    @Override
    public void getMoreNotification(int page) {
        if (mCurrentTask != null) {
            mCurrentTask.cancel();
        }

        mCurrentTask = new GetNotificationListTask(UrlUtil.appendPage(mView.getUrl(), page),
                new OnResponseListener<ListResult<Notification>>() {
                    @Override
                    public void onSucceed(ListResult<Notification> data) {
                        mView.onGetMoreNotificationSucceed(data);
                        mCurrentTask = null;
                    }

                    @Override
                    public void onFailed(String msg) {
                        mView.onGetMoreNotificationFailed(msg);
                        mCurrentTask = null;
                    }
                });

        NetworkTaskScheduler.getInstance().execute(mCurrentTask);
    }
}
