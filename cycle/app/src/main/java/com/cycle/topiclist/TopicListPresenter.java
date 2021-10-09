package com.cycle.topiclist;

import com.cycle.data.NetworkTaskScheduler;
import com.cycle.data.OnResponseListener;
import com.cycle.data.entity.ListResult;
import com.cycle.data.entity.Topic;
import com.cycle.data.task.BaseTask;
import com.cycle.data.task.GetTopicListTask;
import com.cycle.util.UrlUtil;


/**
 *
 * @author cycle.member
 * @date 2017/9/16
 */

public class TopicListPresenter implements TopicListContract.Presenter {

    private TopicListContract.View mView;

    private BaseTask mCurrentTask;

    private int mPagination;

    public TopicListPresenter(TopicListContract.View view) {
        mView = view;
        view.setPresenter(this);
    }

    @Override
    public void getTopicList() {
        if (mCurrentTask != null) {
            mCurrentTask.cancel();
        }

        mCurrentTask = new GetTopicListTask(mView.getUrl(),
                new OnResponseListener<ListResult<Topic>>() {
                    @Override
                    public void onSucceed(ListResult<Topic> data) {
                        mView.onGetTopicListSucceed(data);
                        mCurrentTask = null;
                    }

                    @Override
                    public void onFailed(String msg) {
                        mView.onGetTopicListFailed(msg);
                        mCurrentTask = null;
                    }
                });

        NetworkTaskScheduler.getInstance().execute(mCurrentTask);
    }

    @Override
    public void getMoreTopic(int page) {
        if (mCurrentTask != null) {
            mCurrentTask.cancel();
        }

        mCurrentTask = new GetTopicListTask(UrlUtil.appendPage(mView.getUrl(), page),
                new OnResponseListener<ListResult<Topic>>() {
                    @Override
                    public void onSucceed(ListResult<Topic> data) {
                        mView.onGetMoreTopicSucceed(data);
                        mCurrentTask = null;
                    }

                    @Override
                    public void onFailed(String msg) {
                        mView.onGetMoreTopicFailed(msg);
                        mCurrentTask = null;
                    }
                });

        NetworkTaskScheduler.getInstance().execute(mCurrentTask);
    }

    @Override
    public int getPagination() {
        return mPagination;
    }

    @Override
    public void setPagination(int pagination) {
        mPagination = pagination;
    }
}
