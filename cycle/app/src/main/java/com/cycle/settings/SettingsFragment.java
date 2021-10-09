package com.cycle.settings;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.cycle.App;
import com.cycle.R;
import com.cycle.base.BaseFragment;
import com.cycle.util.ConstantUtil;
import com.cycle.util.PrefsUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * @author cycle.member
 * @date 2018/12/23
 */
public class SettingsFragment extends BaseFragment {

    @BindView(R.id.comments_order_desc) SwitchCompat mCommentsOrderDescSwitch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        ButterKnife.bind(this, root);

        initParams();

        initViews();

        return root;
    }

    private void initViews() {
        boolean commentsOrderDesc = PrefsUtil.getBoolean(App.getInstance(), ConstantUtil.KEY_COMMENTS_ORDER_DESC, false);
        mCommentsOrderDescSwitch.setChecked(commentsOrderDesc);
    }

    @Override
    public String getTitle() {
        return getString(R.string.settings);
    }

    @OnCheckedChanged(R.id.comments_order_desc)
    public void onCommentsOrderDescChanged(CompoundButton button, boolean value) {
        PrefsUtil.putBoolean(App.getInstance(), ConstantUtil.KEY_COMMENTS_ORDER_DESC, value);
    }
}
