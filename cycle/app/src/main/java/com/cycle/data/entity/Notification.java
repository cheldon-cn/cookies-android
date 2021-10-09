package com.cycle.data.entity;

import lombok.Data;

/**
 * @author cycle.member
 * @date 2018/8/19
 */
@Data
public class Notification {
    public static final int TYPE_MENTIONED = 1;
    public static final int TYPE_REPLY = 2;

    private UserProfile user;
    private Topic topic;
    private int type;
    private String content;
}
