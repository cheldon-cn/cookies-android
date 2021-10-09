package com.cycle.home;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.text.TextUtils;

import com.cycle.App;
import com.cycle.R;
import com.cycle.base.BaseFragment;
import com.cycle.router.FragmentFactory;
import com.cycle.util.ConstantUtil;

/**
 *
 * @author cycle.member
 * @date 2017/12/23
 */

public class HomePagerAdapter extends FragmentStatePagerAdapter {

    private static int sPageCount;
    private static String[] sTitles;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
        sTitles = App.getInstance().getResources().getStringArray(R.array.home_tab_names);
        sPageCount = sTitles.length;
    }

    @Override
    public Fragment getItem(int position) {
        BaseFragment fragment = null;

        String url = null;

        switch (position) {
            case 0:
                url = ConstantUtil.BASE_URL;
                break;

            case 1:
                url = ConstantUtil.LATEST_URL;
                break;

            case 2:
                url = ConstantUtil.ELITE_URL;
                break;

            case 3:
                url = ConstantUtil.FOLLOWS_URL;
                break;

            default:
                break;
        }

        if (!TextUtils.isEmpty(url)) {
            fragment = FragmentFactory.getFragmentByUrl(url);
            Bundle bundle = new Bundle();
            bundle.putString(BaseFragment.KEY_URL, url);
            bundle.putString(BaseFragment.KEY_TITLE, getPageTitle(position).toString());
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return sPageCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return sTitles[position];
    }
}
