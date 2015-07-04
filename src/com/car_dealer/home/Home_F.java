package com.car_dealer.home;


import java.util.ArrayList;

import com.example.car_dealer.R;
import com.javis.Adapter.Adapter_GridView;
import com.javis.Adapter.Adapter_GridView_hot;
import com.javis.ab.view.AbOnItemClickListener;
import com.javis.ab.view.AbSlidingPlayView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class Home_F extends Fragment {
	
	// 分类九宫格的资源文件
	private int[] pic_path_classify = { R.drawable.menu_guide_2, R.drawable.menu_guide_3, R.drawable.menu_guide_4, R.drawable.menu_guide_5, R.drawable.menu_guide_6, R.drawable.menu_guide_7, R.drawable.menu_guide_8 };
	// 热门市场的资源文件
	private int[] pic_path_hot = { R.drawable.menu_1, R.drawable.menu_2, R.drawable.menu_3, R.drawable.menu_4, R.drawable.menu_5, R.drawable.menu_6 };
	//分类的九宫格
	private GridView gridView_classify;
	//失物招领
	private GridView my_gridView_hot;
	//gridview适配器
	private Adapter_GridView_hot adapter_GridView_hot;
	private Adapter_GridView adapter_GridView_classify;
	public AbSlidingPlayView viewPager;
	/**存储首页轮播的界面*/
	private ArrayList<View> allListView;
	/**首页轮播的界面的资源*/
	private int[] resId = { R.drawable.show_m1, R.drawable.menu_viewpager_1, R.drawable.menu_viewpager_2, R.drawable.menu_viewpager_3, R.drawable.menu_viewpager_4, R.drawable.menu_viewpager_5 };

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.home, null);
		initView(view);
		return view;
	}
	
	private void initView(View view){
		//分类
		gridView_classify = (GridView) view.findViewById(R.id.my_gridview);
		gridView_classify.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter_GridView_classify = new Adapter_GridView(getActivity(), pic_path_classify);
		gridView_classify.setAdapter(adapter_GridView_classify);
		
		gridView_classify.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				//挑战到宝贝搜索界面
				//Intent intent = new Intent(getActivity(), WareActivity.class);
				//startActivity(intent);
			}
		});
		//失物招领
		my_gridView_hot=(GridView)view.findViewById(R.id.my_gridview_hot);
		my_gridView_hot.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter_GridView_hot=new Adapter_GridView_hot(getActivity(), pic_path_hot);
		my_gridView_hot.setAdapter(adapter_GridView_hot);
		my_gridView_hot.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getActivity(), "ViewId--->>>"+view.getId(), Toast.LENGTH_SHORT).show();
			}
		});
		viewPager = (AbSlidingPlayView) view.findViewById(R.id.viewPager_menu);
		//设置播放方式为顺序播放
		viewPager.setPlayType(1);
		//设置播放间隔时间
		viewPager.setSleepTime(3000);
		initViewPager();
	}
	
	private void initViewPager() {

		if (allListView != null) {
			allListView.clear();
			allListView = null;
		}
		allListView = new ArrayList<View>();

		for (int i = 0; i < resId.length; i++) {
			//导入ViewPager的布局
			View view = LayoutInflater.from(getActivity()).inflate(R.layout.pic_item, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.pic_item);
			imageView.setImageResource(resId[i]);
			allListView.add(view);
		}
		
		
		viewPager.addViews(allListView);
		//开始轮播
		viewPager.startPlay();
		viewPager.setOnItemClickListener(new AbOnItemClickListener() {
			@Override
			public void onClick(int position) {
				//跳转到详情界面
			/*	Intent intent = new Intent(getActivity(), BabyActivity.class);
				startActivity(intent);*/
			}
		});
	}
}
