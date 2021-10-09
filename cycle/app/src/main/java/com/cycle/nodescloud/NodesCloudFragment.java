package com.cycle.nodescloud;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cycle.R;
import com.cycle.base.BaseFragment;
import com.cycle.data.entity.Node;
import com.cycle.data.entity.NodeCategory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author cycle.member
 */
public class NodesCloudFragment extends BaseFragment<NodesCloudContract.Presenter>
        implements NodesCloudContract.View, OnNodeClickListener {

    @BindView(R.id.nodes_cloud) RecyclerView mNodesCloudListView;

    private NodesCloudAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_nodes_cloud, container, false);

        ButterKnife.bind(this, root);

        if (mPresenter == null) {
            mPresenter = new NodesCloudPresenter(this);
        }

        initParams();

        initViews();

        if (mAdapter.getItemCount() == 0) {
            mPresenter.getNodesCloud();
        }

        return root;
    }

    private void initViews() {
        if (mAdapter == null) {
            mAdapter = new NodesCloudAdapter(mListener, this);
        }
        mNodesCloudListView.setAdapter(mAdapter);
    }

    @Override
    public void onGetNodesCloudSucceed(List<NodeCategory> nodesCloud) {
        if (getContext() == null) {
            return;
        }

        mAdapter.setData(nodesCloud);
    }

    @Override
    public String getTitle() {
        if (TextUtils.isEmpty(mTitle)) {
            return getString(R.string.nodes_list);
        } else {
            return mTitle;
        }
    }

    @Override
    public void onGetNodesCloudFailed(String msg) {
        if (getContext() == null) {
            return;
        }

        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNodeClick(Node node) {
        if (mListener != null) {
            mListener.openPage(node.getUrl(), node.getTitle());
        }
    }
}
