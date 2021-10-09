package com.cycle.data.entity;

/**
 *
 * @author cycle.member
 * @date 2017/10/6
 */

public class Reply {
    private Topic topic;
    private String content;

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
