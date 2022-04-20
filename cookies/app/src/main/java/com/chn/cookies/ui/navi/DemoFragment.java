package com.chn.cookies.ui.navi;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chn.cookies.flexbox.activity.CenterGridActivity;
import com.chn.cookies.flexbox.activity.LabelActivity;
import com.chn.cookies.flexbox.activity.PayActivity;
import com.chn.cookies.flexbox.activity.SettingActivity;
import com.chn.cookies.login.LoginActivity;
import com.chn.cookies.ui.home.HomeFragment;
import com.chn.navi.AHBottomNavigation;
import com.chn.cookies.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class DemoFragment extends Fragment {
	
	private FrameLayout fragmentContainer;
	private RecyclerView recyclerView;
	private RecyclerView.LayoutManager layoutManager;
	private LinkedHashMap map = new LinkedHashMap<Integer,String>();
	
	/**
	 * Create a new instance of the fragment
	 */
	public static DemoFragment newInstance(int index) {
		DemoFragment fragment = new DemoFragment();
		Bundle b = new Bundle();
		b.putInt("index", index);
		fragment.setArguments(b);
		return fragment;
	}
	
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (getArguments().getInt("index", 0) == 0) {
			View view = inflater.inflate(R.layout.cookies_view, container, false);

			initCookiesView(view);
			return view;
		}else if (getArguments().getInt("index", 1) == 1) {
			View view = inflater.inflate(R.layout.fragment_demo_list, container, false);
			initDemoList(view);
			return view;
		}  else {
			View view = inflater.inflate(R.layout.fragment_demo_settings, container, false);
			initDemoSettings(view);
			return view;
		}
	}
	
	/**
	 * Init demo settings
	 */
	private void initDemoSettings(View view) {
		
		final DemoActivity demoActivity = (DemoActivity) getActivity();
		final SwitchCompat switchColored = view.findViewById(R.id.fragment_demo_switch_colored);
		final SwitchCompat switchFiveItems = view.findViewById(R.id.fragment_demo_switch_five_items);
		final SwitchCompat showHideBottomNavigation = view.findViewById(R.id.fragment_demo_show_hide);
		final SwitchCompat showSelectedBackground = view.findViewById(R.id.fragment_demo_selected_background);
		final Spinner spinnerTitleState = view.findViewById(R.id.fragment_demo_title_state);
		final SwitchCompat switchTranslucentNavigation = view.findViewById(R.id.fragment_demo_translucent_navigation);
		
		switchColored.setChecked(demoActivity.isBottomNavigationColored());
		switchFiveItems.setChecked(demoActivity.getBottomNavigationNbItems() == 5);
		switchTranslucentNavigation.setChecked(getActivity()
				.getSharedPreferences("shared", Context.MODE_PRIVATE)
				.getBoolean("translucentNavigation", false));
		switchTranslucentNavigation.setVisibility(
				Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? View.VISIBLE : View.GONE);
		
		switchTranslucentNavigation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				getActivity()
						.getSharedPreferences("shared", Context.MODE_PRIVATE)
						.edit()
						.putBoolean("translucentNavigation", isChecked)
						.apply();
				demoActivity.reload();
			}
		});
		switchColored.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				demoActivity.updateBottomNavigationColor(isChecked);
			}
		});
		switchFiveItems.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				demoActivity.updateBottomNavigationItems(isChecked);
			}
		});
		showHideBottomNavigation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				demoActivity.showOrHideBottomNavigation(isChecked);
			}
		});
		showSelectedBackground.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				demoActivity.updateSelectedBackgroundVisibility(isChecked);
			}
		});
		final List<String> titleStates = new ArrayList<>();
		for (AHBottomNavigation.TitleState titleState : AHBottomNavigation.TitleState.values()) {
			titleStates.add(titleState.toString());
		}
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, titleStates);
		spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerTitleState.setAdapter(spinnerAdapter);
		spinnerTitleState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				AHBottomNavigation.TitleState titleState = AHBottomNavigation.TitleState.valueOf(titleStates.get(position));
				demoActivity.setTitleState(titleState);
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// do nothing
			}
		});
	}
	
	/**
	 * Init the fragment
	 */
	private void initDemoList(View view) {
		
		fragmentContainer = view.findViewById(R.id.fragment_container);
		recyclerView = view.findViewById(R.id.fragment_demo_recycler_view);
		recyclerView.setHasFixedSize(true);
		layoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(layoutManager);
		
		ArrayList<String> itemsData = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			itemsData.add("Fragment " + getArguments().getInt("index", -1) + " / Item " + i);
		}
		
		DemoAdapter adapter = new DemoAdapter(itemsData);
		recyclerView.setAdapter(adapter);
	}
	
	/**
	 * Refresh
	 */
	public void refresh() {
		if (getArguments().getInt("index", 0) > 0 && recyclerView != null) {
			recyclerView.smoothScrollToPosition(0);
		}
	}
	
	/**
	 * Called when a fragment will be displayed
	 */
	public void willBeDisplayed() {
		// Do what you want here, for example animate the content
		if (fragmentContainer != null) {
			Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
			fragmentContainer.startAnimation(fadeIn);
		}
	}
	
	/**
	 * Called when a fragment will be hidden
	 */
	public void willBeHidden() {
		if (fragmentContainer != null) {
			Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
			fragmentContainer.startAnimation(fadeOut);
		}
	}


	protected void initRes() {
		map.clear();
		map.put(R.drawable.weinxin_2x, "微信");
		map.put(R.drawable.zhifubao_2x, "支付宝");
		map.put(R.drawable.yinlian_2x, "银联");
		map.put(R.mipmap.dingdan, "订单");
		map.put(R.mipmap.temai, "特卖");
		map.put(R.mipmap.gouwuche, "购物车");
		map.put(R.mipmap.yinhangka, "银行卡");
		map.put(R.mipmap.licai, "理财");
		map.put(R.mipmap.dae, "大额");
		map.put(R.mipmap.renzheng, "认证");
		map.put(R.mipmap.zhiwen, "指纹");
		map.put(R.mipmap.renlian, "人脸");
		map.put(R.mipmap.check_updata, "更新");
		map.put(R.mipmap.dizhi, "地址");
		map.put(R.mipmap.setting, "设置");

	}
	protected void initCookiesView(View main)
	{
		initRes();

		if(main == null) return;
		View column = main.findViewById(R.id.item_column);
		if(column == null) return;

		View row1 = column.findViewById(R.id.item_row1);
		View row2 = column.findViewById(R.id.item_row2);
		View row3 = column.findViewById(R.id.item_row3);
		View row4 = column.findViewById(R.id.item_row4);
		View row5 = column.findViewById(R.id.item_row5);
		setRow(row1,1);
		setRow(row2,2);
		setRow(row3,3);
		setRow(row4,4);
		setRow(row5,5);
	}

	protected void initCookies(View root)
	{
		if(root == null) return;
		View main = root.findViewById(R.id.cookies_main);
		if(main == null) return;
		initCookiesView(main);
	}
	protected void setRow(View row,int nRow)
	{
		if(row == null) return;
		View single1 = row.findViewById(R.id.item_single1);
		View single2 = row.findViewById(R.id.item_single2);
		View single3 = row.findViewById(R.id.item_single3);
//        setItem(single1,"Wechat",R.drawable.weinxin_2x);

		int m = 0;
		Iterator<LinkedHashMap.Entry<Integer, String>> iterator1 = map.entrySet().iterator();
		LinkedHashMap.Entry<Integer, String> entry;
		while (iterator1.hasNext()) {
			entry = iterator1.next();

			if(m > 3*nRow) break;
			if(m >= 3*(nRow-1) && m < 3*nRow)
			{
				int id = entry.getKey().intValue();
				String text = entry.getValue();

				View view = null;
				if(m%3 == 0)
					view = single1;
				else if(m%3 == 1)
					view = single2;
				else
					view = single3;

				setItem(view,text,id);
			}

			m++;
		}

	}

	protected void setItem(View item, CharSequence text, int idImg)
	{
		if(item == null) return;

		ImageView imgView = item.findViewById(R.id.item_img);
		TextView txtView = item.findViewById(R.id.item_text);
		if(imgView != null)
			imgView.setImageDrawable(getResources().getDrawable(idImg));
		//imgView.setBackground(getResources().getDrawable(R.drawable.weinxin_2x));
		if(txtView != null)
			txtView.setText(text);

		item.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int id = v.getId();
				ViewParent parent = v.getParent();
				String str = parent.getClass().toString();
				View focus = (View)parent;
				int iRoot = focus.getId();
				process(iRoot,id);
			}
		});
	}


	protected int toRow(int idRoot)
	{
		int row = 0;
		switch (idRoot)
		{
			case R.id.item_row1: { row = 1;break; }
			case R.id.item_row2: { row = 2;break; }
			case R.id.item_row3: { row = 3;break; }
			case R.id.item_row4: { row = 4;break; }
			case R.id.item_row5: { row = 5;break; }
		}
		return row;
	}
	protected int toColumn(int id)
	{
		int column = 0;
		switch (id)
		{
			case R.id.item_single1: { column = 1;break; }
			case R.id.item_single2: { column = 2;break; }
			case R.id.item_single3: { column = 3;break; }
		}
		return column;
	}

	protected void process(int idRoot,int id)
	{
		int column = toColumn(id) - 1;
		int row = toRow(idRoot) - 1;
		int index = (row)*3+column;
		int m = 0;
		Iterator<LinkedHashMap.Entry<Integer, String>> iterator1 = map.entrySet().iterator();
		LinkedHashMap.Entry<Integer, String> entry;
		while (iterator1.hasNext()) {
			entry = iterator1.next();
			if(m == index){
				int i = entry.getKey().intValue();
				String text = entry.getValue();
				distribute(i);
				break;
			}
			m++;
		}
	}

	protected void distribute(int id)
	{
		FragmentActivity activity = DemoFragment.this.getActivity();
		int m = 0;
		switch (id) {
			case R.drawable.weinxin_2x:
				startActivity(new Intent(activity, PayActivity.class));
				break;
			case R.drawable.zhifubao_2x:
				startActivity(new Intent(activity, CenterGridActivity.class));
				break;
			case R.drawable.yinlian_2x:
				startActivity(new Intent(activity, LabelActivity.class));
				break;
			case R.mipmap.dingdan:
				startActivity(new Intent(activity, DemoActivity.class));
				break;
			case R.mipmap.temai: m--;
				break;
			case R.mipmap.gouwuche: ++m;
				break;
			case R.mipmap.yinhangka: m++;
				break;
			case R.mipmap.licai: m--;
				break;
			case R.mipmap.dae: ++m;
				break;
			case R.mipmap.renzheng:
				startActivity(new Intent(activity, LoginActivity.class));
				break;
			case R.mipmap.zhiwen: m--;
				break;
			case R.mipmap.renlian: ++m;
				break;
			case R.mipmap.check_updata: m++;
				break;
			case R.mipmap.dizhi: m--;
				break;
			case R.mipmap.setting:
				startActivity(new Intent(activity, SettingActivity.class));
				break;
		}
	}

}
