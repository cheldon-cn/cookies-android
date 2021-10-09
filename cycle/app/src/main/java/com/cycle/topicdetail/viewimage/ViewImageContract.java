package com.cycle.topicdetail.viewimage;

import com.bm.library.PhotoView;

import com.cycle.base.BasePresenter;
import com.cycle.base.BaseView;

/**
 *
 * @author cycle.member
 * @date 2017/11/18
 */

public interface ViewImageContract {
    interface Presenter extends BasePresenter {
        /**
         * 保存图片
         * @param photoViewTemp 展示图片的控件
         */
        void saveImage(PhotoView photoViewTemp);
    }

    interface View extends BaseView<Presenter> {
        /**
         * 保存图片成功
         */
        void onSaveImageSucceed();

        /**
         * 保存图片失败
         * @param msg 失败提示信息
         */
        void onSaveImageFailed(String msg);
    }
}
