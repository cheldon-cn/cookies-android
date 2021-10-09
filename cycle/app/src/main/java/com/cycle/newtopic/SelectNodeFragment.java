package com.cycle.newtopic;

import android.text.TextUtils;

import com.cycle.R;
import com.cycle.data.entity.Node;
import com.cycle.nodescloud.NodesCloudFragment;
import com.cycle.router.annotations.FinishWhenCovered;
import com.cycle.util.ConstantUtil;
import com.cycle.util.UrlUtil;

/**
 *
 * @author cycle.member
 * @date 2017/11/18
 */

@FinishWhenCovered
public class SelectNodeFragment extends NodesCloudFragment {
    @Override
    public void onNodeClick(Node node) {
        String nodeCode = UrlUtil.getNodeCode(node.getUrl());
        if (mListener != null && !TextUtils.isEmpty(nodeCode)) {
            mListener.openPage(String.format(ConstantUtil.NEW_TOPIC_BASE_URL, nodeCode), getString(R.string.new_topic));
        }
    }
}
