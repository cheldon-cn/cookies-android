package com.cycle.userprofile.replies;

import com.cycle.data.NetworkTaskScheduler;
import com.cycle.data.OnResponseListener;
import com.cycle.data.entity.ListResult;
import com.cycle.data.entity.Reply;
import com.cycle.data.task.GetReplyListTask;
import com.cycle.util.UrlUtil;

/**
 *
 * @author cycle.member
 * @date 2017/9/16
 */

public class ReplyListPresenter implements ReplyListContract.Presenter {

    private ReplyListContract.View mView;

    public ReplyListPresenter(ReplyListContract.View view) {
        mView = view;
        view.setPresenter(this);
    }

    @Override
    public void getReplyList() {
        NetworkTaskScheduler.getInstance().execute(new GetReplyListTask(mView.getUrl(),
                new OnResponseListener<ListResult<Reply>>() {
                    @Override
                    public void onSucceed(ListResult<Reply> data) {
                        mView.onGetReplyListSucceed(data);
                    }

                    @Override
                    public void onFailed(String msg) {
                        mView.onGetReplyListFailed(msg);
                    }
                }));
    }

    @Override
    public void getMoreReply(int page) {
        NetworkTaskScheduler.getInstance().execute(new GetReplyListTask(UrlUtil.appendPage(mView.getUrl(), page),
                new OnResponseListener<ListResult<Reply>>() {
                    @Override
                    public void onSucceed(ListResult<Reply> data) {
                        mView.onGetMoreReplySucceed(data);
                    }

                    @Override
                    public void onFailed(String msg) {
                        mView.onGetMoreReplyFailed(msg);
                    }
                }));
    }
}
