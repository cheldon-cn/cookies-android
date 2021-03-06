package com.cycle.base;

/**
 *
 * @author cycle.member
 * @date 2017/9/16
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
    String getUrl();

    void startLoading();
    void stopLoading();
}
