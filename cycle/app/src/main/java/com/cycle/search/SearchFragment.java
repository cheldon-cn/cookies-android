package com.cycle.search;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.cycle.R;
import com.cycle.base.BaseFragment;
import com.cycle.data.entity.UserProfile;
import com.cycle.util.ConstantUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author cycle.member
 */
public class SearchFragment extends BaseFragment<SearchContract.Presenter> implements SearchContract.View {

    @BindView(R.id.keyword) EditText mKeywordEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, root);

        if (mPresenter == null) {
            mPresenter = new SearchPresenter(this);
        }

        initParams();

        return root;
    }

    @OnClick(R.id.search)
    public void onSearch() {
        Editable rawKeyword = mKeywordEditText.getText();
        if (rawKeyword == null || TextUtils.isEmpty(rawKeyword)) {
            toast(getString(R.string.please_input_user_id));
            return;
        }

        // TODO: 2018/12/19 添加用户名有效性校验

        String keyword = rawKeyword.toString();
        String url = String.format(ConstantUtil.USER_PROFILE_BASE_URL, keyword);
        mPresenter.getUserProfile(url);
    }

    @Override
    public void onGetUserProfileSucceed(UserProfile userProfile) {
        if (getContext() == null) {
            return;
        }
        mListener.openPage(userProfile.getUrl(), null);
    }

    @Override
    public void onGetUserProfileFailed(String msg) {
        if (getContext() == null) {
            return;
        }

        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getTitle() {
        if (!TextUtils.isEmpty(mTitle)) {
            return mTitle;
        } else {
            return getString(R.string.search_user);
        }
    }
}
