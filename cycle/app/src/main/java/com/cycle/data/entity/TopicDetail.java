package com.cycle.data.entity;

import java.util.Map;

/**
 *
 * @author cycle.member
 * @date 2017/9/17
 */

public class TopicDetail {

    private Favorite favorite;
    private Topic topic;
    private String content;
    private int commentsCount;
    private Map<Integer, Comment> comments;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<Integer, Comment> getComments() {
        return comments;
    }

    public void setComments(Map<Integer, Comment> comments) {
        this.comments = comments;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
    public Favorite getFavorite() {
        return favorite;
    }

    public void setFavorite(Favorite favorite) {
        this.favorite = favorite;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }
}
