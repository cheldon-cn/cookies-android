package com.cycle.nodescloud;

import com.cycle.data.entity.Node;

/**
 *
 * @author cycle.member
 * @date 2017/11/18
 */

public interface OnNodeClickListener {
    /**
     * 节点被点击
     * @param node 节点
     */
    void onNodeClick(Node node);
}
