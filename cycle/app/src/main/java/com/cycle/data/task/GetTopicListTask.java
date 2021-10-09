package com.cycle.data.task;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.cycle.data.OnResponseListener;
import com.cycle.data.entity.ListResult;
import com.cycle.data.entity.Meta;
import com.cycle.data.entity.Node;
import com.cycle.data.entity.Topic;
import com.cycle.data.entity.User;
import com.cycle.util.ConstantUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cycle.member
 * @date 2017/9/16
 */

public class GetTopicListTask extends BaseTask<ListResult<Topic>> implements Runnable {

    private String mUrl;

    public GetTopicListTask(String url, OnResponseListener<ListResult<Topic>> listener) {
        super(listener);
        mUrl = url;
    }

    @Override
    public void run() {
        List<Topic> topics = new ArrayList<>();

        boolean succeed = false;
        boolean hasMore = false;
        try {
            Document doc = get(mUrl);

            tryFixAuthStatus(doc);

            Elements elements = doc.select("div.topic-item");

            for (Element element : elements) {
                Topic topic = createTopicFromElement(element);
                topics.add(topic);
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
            ListResult<Topic> topicList = new ListResult<>();
            topicList.setData(topics);
            topicList.setHasMore(hasMore);
            successOnUI(topicList);
        } else {
            failedOnUI("获取主题列表失败");
        }
    }

    public static Topic createTopicFromElement(Element element) {
        Topic topic = new Topic();

        Element titleElement = element.select("h3.title").select("a").first();
        if (titleElement != null) {
            // 主题列表页
            topic.setTitle(titleElement.text());
        } else {
            // 主题详情页
            titleElement = element.select("h3.title").first();
            if (titleElement != null) {
                topic.setTitle(titleElement.text());
            }
        }
        topic.setUrl(titleElement.absUrl("href"));

        topic.setAvatar(element.select("img.avatar").attr("src"));

        Element metaElement = element.select("div.meta").first();
        Meta meta = createMetaFromElement(metaElement);

        topic.setMeta(meta);

        Elements countElements = element.select("div.count");
        if (!countElements.isEmpty()) {
            topic.setCount(Integer.valueOf(countElements.first().select("a").first().text()));
        }

        return topic;
    }

    private static Meta createMetaFromElement(Element element) {
        Meta meta = new Meta();

        Element nodeElement = element.select("span.node").select("a").first();
        Node node = new Node();
        node.setTitle(nodeElement.text());
        node.setUrl(nodeElement.absUrl("href"));

        meta.setNode(node);

        Element userElement = element.select("span.username").select("a").first();
        User user = new User();
        user.setUsername(userElement.text());
        user.setUrl(userElement.absUrl("href"));

        meta.setAuthor(user);

        // 主题列表页
        Elements lastTouchedElement = element.select("span.last-touched");
        if (lastTouchedElement.isEmpty()) {
            // 主题详情页
            lastTouchedElement = element.select("span.last-reply-time");
        }
        meta.setLastTouched(lastTouchedElement.text());

        Elements createdTimeElement = element.select("span.created-time");
        meta.setCreatedTime(createdTimeElement.text());

        Element lastReplyUserElement = element.select("span.last-reply-username").select("a").first();
        if (lastReplyUserElement != null) {
            User lastReplyUser = new User();
            lastReplyUser.setUsername(lastReplyUserElement.select("strong").text());
            lastReplyUser.setUrl(lastReplyUserElement.absUrl("href"));

            meta.setLastReplyUser(lastReplyUser);
        }

        return meta;
    }
}
