package com.cycle.data.task;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.cycle.data.OnResponseListener;
import com.cycle.data.entity.ListResult;
import com.cycle.data.entity.Reply;
import com.cycle.data.entity.Topic;
import com.cycle.util.ConstantUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cycle.member
 * @date 2017/10/6
 */

public class GetReplyListTask extends BaseTask<ListResult<Reply>> {

    private String mUrl;

    public GetReplyListTask(String url, OnResponseListener<ListResult<Reply>> listener) {
        super(listener);
        mUrl = url;
    }

    @Override
    public void run() {
        List<Reply> replies = new ArrayList<>();

        boolean succeed = false;
        boolean hasMore = false;

        try {
            Document doc = get(mUrl);

            Elements elements = doc.select("div.reply-item");

            for (Element element : elements) {
                Reply reply = new Reply();

                Elements topicElements = element.select("span.title a");
                if (topicElements.isEmpty()) {
                    continue;
                }

                Topic topic = new Topic();
                topic.setTitle(topicElements.first().text());
                topic.setUrl(topicElements.first().absUrl("href"));

                reply.setTopic(topic);

                Elements contentElements = element.select("div.content");
                if (contentElements.isEmpty()) {
                    continue;
                }

                reply.setContent(contentElements.outerHtml());

                replies.add(reply);
            }

            succeed = true;

            Elements paginationElements = doc.select("ul.pagination");
            if (!paginationElements.isEmpty()) {
                Elements disabledElements = paginationElements.select("li.disabled");
                if (disabledElements.isEmpty()) {
                    hasMore = true;
                } else if (disabledElements.last() != null) {
                    Elements disableLinkElements = disabledElements.last().select("a");
                    if (!ConstantUtil.NEXT_PAGE.equals(disableLinkElements.text())) {
                        hasMore = true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (succeed) {
            ListResult<Reply> replyList = new ListResult<>();
            replyList.setData(replies);
            replyList.setHasMore(hasMore);
            successOnUI(replyList);
        } else {
            failedOnUI("获取回复列表失败");
        }
    }
}
