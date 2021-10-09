package com.cycle.topicdetail;

import com.vdurmont.emoji.EmojiParser;

import com.cycle.App;
import com.cycle.data.NetworkTaskScheduler;
import com.cycle.data.OnResponseListener;
import com.cycle.data.entity.TopicDetail;
import com.cycle.data.task.CommentTask;
import com.cycle.data.task.FavouriteTask;
import com.cycle.data.task.FollowUserTask;
import com.cycle.data.task.GetTopicDetailTask;
import com.cycle.data.task.UploadImageTask;
import com.cycle.data.task.VoteCommentTask;
import com.cycle.util.ConstantUtil;
import com.cycle.util.PrefsUtil;
import com.cycle.util.UrlUtil;

import java.io.InputStream;

/**
 *
 * @author cycle.member
 * @date 2017/9/17
 */

public class TopicDetailPresenter implements TopicDetailContract.Presenter {

    private TopicDetailContract.View mView;

    private boolean mCommentsDesc;

    public TopicDetailPresenter(TopicDetailContract.View view) {
        mView = view;
        mView.setPresenter(this);
        mCommentsDesc = PrefsUtil.getBoolean(App.getInstance(), ConstantUtil.KEY_COMMENTS_ORDER_DESC, false);
    }

    @Override
    public void getTopicDetail() {
        mView.startLoading();
        String url = mCommentsDesc ? getUrl() : UrlUtil.appendPage(getUrl(), 1);
        NetworkTaskScheduler.getInstance().execute(new GetTopicDetailTask(url, new OnResponseListener<TopicDetail>() {
            @Override
            public void onSucceed(TopicDetail data) {
                mView.stopLoading();
                mView.onGetTopicDetailSucceed(data);
            }

            @Override
            public void onFailed(String msg) {
                mView.stopLoading();
                mView.onGetTopicDetailFailed(msg);
            }
        }));
    }

    @Override
    public void getMoreComments(int page) {
        mView.startLoading();
        NetworkTaskScheduler.getInstance().execute(new GetTopicDetailTask(UrlUtil.appendPage(getUrl(), page), new OnResponseListener<TopicDetail>() {
            @Override
            public void onSucceed(TopicDetail data) {
                mView.stopLoading();
                mView.onGetMoreCommentsSucceed(data);
            }

            @Override
            public void onFailed(String msg) {
                mView.stopLoading();
                mView.onGetMoreCommentsFailed(msg);
            }
        }));
    }

    @Override
    public void comment(String content) {
        mView.startLoading();
        content = EmojiParser.parseToAliases(content);
        NetworkTaskScheduler.getInstance().execute(new CommentTask(getUrl(), content, new OnResponseListener<String>() {
            @Override
            public void onSucceed(String data) {
                mView.stopLoading();
                mView.onCommentSucceed();
            }

            @Override
            public void onFailed(String msg) {
                mView.stopLoading();
                mView.onCommentFailed(msg);
            }
        }));
    }

    @Override
    public void favorite() {
        String topicId = UrlUtil.getTid(getUrl());
        String favouriteUrl = ConstantUtil.FAVORITE_URL + "?topic_id=" + topicId;

        NetworkTaskScheduler.getInstance().execute(new FavouriteTask(favouriteUrl, new OnResponseListener<String>() {
            @Override
            public void onSucceed(String data) {
                mView.onFavoriteSucceed();
            }

            @Override
            public void onFailed(String msg) {
                mView.onFavoriteFail(msg);
            }
        }));
    }

    @Override
    public void unfavorite() {
        String topicId = UrlUtil.getTid(getUrl());
        String favouriteUrl = ConstantUtil.UN_FAVORITE_URL + "?topic_id=" + topicId;

        NetworkTaskScheduler.getInstance().execute(new FavouriteTask(favouriteUrl, new OnResponseListener<String>() {
            @Override
            public void onSucceed(String data) {
                mView.onUnfavoriteSucceed();
            }

            @Override
            public void onFailed(String msg) {
                mView.onUnfavoriteFailed(msg);
            }
        }));
    }

    @Override
    public void voteComment(String url, final OnResponseListener<Boolean> listener) {
        mView.startLoading();
        NetworkTaskScheduler.getInstance().execute(new VoteCommentTask(url, new OnResponseListener<Boolean>() {
            @Override
            public void onSucceed(Boolean value) {
                mView.stopLoading();
                listener.onSucceed(value);
                if (value) {
                    mView.onVoteCommentSucceed();
                } else {
                    mView.onVoteCommentFailed("您已经点过赞了");
                }
            }

            @Override
            public void onFailed(String msg) {
                mView.stopLoading();
                listener.onFailed(msg);
                mView.onVoteCommentFailed(msg);
            }
        }));
    }

    private String getUrl() {
        return mView.getUrl().replaceAll("#reply\\d+", "");
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
    public void uploadImage(InputStream inputStream) {
        mView.startLoading();

        NetworkTaskScheduler.getInstance().execute(new UploadImageTask(inputStream, new OnResponseListener<String>() {
            @Override
            public void onSucceed(String url) {
                mView.stopLoading();
                mView.onUploadImageSucceed(url);
            }

            @Override
            public void onFailed(String msg) {
                mView.stopLoading();
                mView.onUploadImageFailed(msg);
            }
        }));
    }
}
