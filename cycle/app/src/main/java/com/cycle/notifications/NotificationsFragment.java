package com.cycle.notifications;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cycle.App;
import com.cycle.R;
import com.cycle.base.BaseFragment;
import com.cycle.data.entity.ListResult;
import com.cycle.data.entity.Notification;
import com.cycle.util.ConstantUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author cycle.member
 * @date 2018/8/18
 */
public class NotificationsFragment extends BaseFragment<NotificationsContract.Presenter> implements NotificationsContract.View, SwipeRefreshLayout.OnRefreshListener {

    private NotificationsAdapter mAdapter;
    private boolean mLoadable = false;
    int pastVisibleItems, visibleItemCount, totalItemCount;

    @BindView(R.id.list) RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout) SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.empty) SwipeRefreshLayout mEmptyLayout;

    private boolean mFirstFetchFinished = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        initParams();

        ButterKnife.bind(this, root);

        if (mPresenter == null) {
            mPresenter = new NotificationsPresenter(this);
        }

        initViews();

        if (!mAdapter.isFilled()) {
            mPresenter.getNotificationList();
        }

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!mFirstFetchFinished && mListener != null) {
            mListener.startLoading();
        }
    }

    private void initViews() {
        Context context = getContext();
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, 1);
            }
        });
        if (mAdapter == null) {
            mAdapter = new NotificationsAdapter(mListener);
        }
        mRecyclerView.setAdapter(mAdapter);

        // ref https://stackoverflow.com/questions/26543131/how-to-implement-endless-list-with-recyclerview
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //check for scroll down
                if (dy > 0) {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (mLoadable) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            mLoadable = false;
                            if (totalItemCount >= ConstantUtil.NOTIFICATIONS_PER_PAGE && totalItemCount <= ConstantUtil.MAX_TOPICS) {
                                mPresenter.getMoreNotification(totalItemCount / ConstantUtil.NOTIFICATIONS_PER_PAGE + 1);
                            } else {
                                Toast.makeText(getActivity(), "1024", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });

        initSwipeLayout(mRefreshLayout);
        initSwipeLayout(mEmptyLayout);

        handleEmptyList();
    }

    private void initSwipeLayout(SwipeRefreshLayout swipeRefreshLayout) {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.main);
    }

    private void handleEmptyList() {
        if (mAdapter.getItemCount() == 0) {
            mEmptyLayout.setVisibility(View.VISIBLE);
            mRefreshLayout.setVisibility(View.GONE);
        } else {
            mEmptyLayout.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public String getTitle() {
        return getString(R.string.notifications);
    }

    @Override
    public void onGetNotificationListSucceed(ListResult<Notification> notificationList) {

        finishRefresh();

        if (getContext() == null) {
            return;
        }

        // 获取消息提醒列表成功，则可以消除小红点了
        App.getInstance().mGlobal.hasNotifications.setValue(false);

        mLoadable = notificationList.isHasMore();

        mAdapter.setData(notificationList.getData());

        handleEmptyList();
    }

    @Override
    public void onGetNotificationListFailed(String msg) {
        finishRefresh();

        if (getContext() == null) {
            return;
        }

        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

        handleEmptyList();
    }

    @Override
    public void onGetMoreNotificationSucceed(ListResult<Notification> notificationList) {
        if (getContext() == null) {
            return;
        }

        mLoadable = notificationList.isHasMore();

        mAdapter.addData(notificationList.getData());
    }

    @Override
    public void onGetMoreNotificationFailed(String msg) {
        if (getContext() == null) {
            return;
        }

        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        if (!mFirstFetchFinished) {
            return;
        }

        mPresenter.getNotificationList();
    }

    private void finishRefresh() {

        if (mListener != null && mListener.isLoading()) {
            mListener.stopLoading();
        }

        mRefreshLayout.setRefreshing(false);
        mEmptyLayout.setRefreshing(false);
        if (!mFirstFetchFinished) {
            mFirstFetchFinished = true;
        }
    }
}
