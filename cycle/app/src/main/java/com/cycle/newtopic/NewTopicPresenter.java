package com.cycle.newtopic;

import com.vdurmont.emoji.EmojiParser;

import com.cycle.data.NetworkTaskScheduler;
import com.cycle.data.OnResponseListener;
import com.cycle.data.task.NewTopicTask;
import com.cycle.data.task.UploadImageTask;

import java.io.InputStream;

/**
 *
 * @author cycle.member
 * @date 2017/10/10
 */

public class NewTopicPresenter implements NewTopicContract.Presenter {

    private NewTopicContract.View mView;

    public NewTopicPresenter(NewTopicContract.View view) {
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void newTopic(String title, String content) {
        mView.startLoading();

        title = EmojiParser.parseToAliases(title);
        content = EmojiParser.parseToAliases(content);

        NetworkTaskScheduler.getInstance().execute(new NewTopicTask(mView.getUrl(),
                title,
                content,
                new OnResponseListener<String>() {
                    @Override
                    public void onSucceed(String data) {
                        mView.stopLoading();
                        mView.onNewTopicSucceed();
                    }

                    @Override
                    public void onFailed(String msg) {
                        mView.stopLoading();
                        mView.onNewTopicFailed(msg);
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
