package com.cycle.topiclist;

import com.cycle.base.BasePresenter;
import com.cycle.base.BaseView;
import com.cycle.data.entity.ListResult;
import com.cycle.data.entity.Topic;

/**
 *
 * @author cycle.member
 * @date 2017/9/16
 */

public interface TopicListContract {
    interface Presenter extends BasePresenter {
        /**
         * 获取主题列表
         */
        void getTopicList();

        /**
         * 获取更多主题
         * @param page 第几页
         */
        void getMoreTopic(int page);

        /**
         * 获取分页数
         * @return 单面数量
         */
        int getPagination();

        /**
         * 设置分页数
         * @param pagination 单面数量
         */
        void setPagination(int pagination);
    }

    interface View extends BaseView<Presenter> {
        /**
         * 获取主题列表成功
         * @param topicList 主题列表对象
         */
        void onGetTopicListSucceed(ListResult<Topic> topicList);

        /**
         * 获取主题列表失败
         * @param msg 失败提示信息
         */
        void onGetTopicListFailed(String msg);

        /**
         * 获取更多主题成功
         * @param topicList 主题列表对象
         */
        void onGetMoreTopicSucceed(ListResult<Topic> topicList);

        /**
         * 获取更多主题失败
         * @param msg 失败提示信息
         */
        void onGetMoreTopicFailed(String msg);
    }
}
