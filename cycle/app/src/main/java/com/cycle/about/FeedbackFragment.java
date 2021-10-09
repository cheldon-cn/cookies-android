package com.cycle.about;

import com.cycle.R;
import com.cycle.topicdetail.viewimage.ViewImageFragment;

/**
 * @author cycle.member
 * @date 2019-07-20
 */
public class FeedbackFragment extends ViewImageFragment {
    @Override
    protected void loadImage() {
        mImageView.setImageResource(R.drawable.qrcode_asset);
    }
}
