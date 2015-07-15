package com.car_dealer.forum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import com.example.car_dealer.R;
import com.jarvis.http.CU_JSONResolve;
import com.jarvis.http.GetHttp;
import com.javis.Adapter.Adapter_ListView_ware;
import com.lesogo.cu.custom.xListview.XListView;
import com.lesogo.cu.custom.xListview.XListView.IXListViewListener;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class Forum_F extends Fragment implements OnTouchListener,IXListViewListener {
	//显示所有商品的列表
		private XListView listView;
		
		/**请求数据的页数*/
		private int pageIndex = 0;
		/**存储网络返回的数据*/
		private HashMap<String, Object> hashMap;
		/**存储网络返回的数据中的data字段*/
		private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
		@Override
		public View onCreateView(LayoutInflater inflater,
				@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
			View view=LayoutInflater.from(getActivity()).inflate(R.layout.forum, null);
			initView(view);
			//请求网络数据
		new WareTask().execute();
		return view;
		}
		private void initView(View view){
			listView =(XListView)view.findViewById(R.id.listView_ware);
			listView.setOnTouchListener(this);
			listView.setXListViewListener(this);
			// 设置可以进行下拉加载的功能
			listView.setPullLoadEnable(true);
			listView.setPullRefreshEnable(true);
		}
		
		private class WareTask extends AsyncTask<Void, Void, HashMap<String, Object>> {
			
			ProgressDialog dialog=null;

			@Override
			protected void onPreExecute() {
				if (dialog==null) {
					dialog=ProgressDialog.show(getActivity(), "","正在加载...");
					dialog.show();
				}
				
				
			}
			@Override
			protected HashMap<String, Object> doInBackground(Void... arg0) {
				String url = "";
				if (pageIndex == 0) {
					url = "http://192.168.0.111:3000/taoBaoQuery";
				} else {
					url = "http://192.168.0.111:3000/taoBaoQuery?pageIndex=" + pageIndex;
				}
				//请求数据，返回json
				String json = GetHttp.RequstGetHttp(url);
				//第一层的数组类型字段
				String[] LIST1_field = { "data" };
				
				//第二层的对象类型字段
				String[] STR2_field = { "id", "name", "price", "sale_num", "address", "pic" };
				ArrayList<String[]> aL_STR2_field = new ArrayList<String[]>();
				//第二层的对象类型字段放入第一层的数组类型字段中
				aL_STR2_field.add(STR2_field);
				//解析返回的json
				hashMap = CU_JSONResolve.parseHashMap2(json, null, LIST1_field, aL_STR2_field);
				return hashMap;
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void onPostExecute(HashMap<String, Object> result) {
				
				if (dialog!=null&&dialog.isShowing()) {
					dialog.dismiss();
					dialog=null;
				}
				//如果网络数据请求失败，那么显示默认的数据
				if (result != null && result.get("data") != null) {
					//得到data字段的数据
					arrayList.addAll((Collection<? extends HashMap<String, Object>>) result.get("data"));
					listView.setAdapter(new Adapter_ListView_ware(getActivity(), arrayList));
					listView.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
						
							
						}
					});
				}else {
					listView.setAdapter(new Adapter_ListView_ware(getActivity()));
					listView.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
						
						}
					});
				}
				// 停止刷新和加载
				onLoad();

			}

		}
		
		/** 简单的时间格式 */
		public static SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");

		public static String getCurrentTime(long time) {
			if (0 == time) {
				return "";
			}

			return mDateFormat.format(new Date(time));

		}
		



		
		/** 停止加载和刷新 */
		private void onLoad() {
			listView.stopRefresh();
			// 停止加载更多
			listView.stopLoadMore();

			// 设置最后一次刷新时间
			listView.setRefreshTime(getCurrentTime(System.currentTimeMillis()));
		}

		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			return false;
		}
		
		//刷新
		@Override
		public void onRefresh() {
			// TODO Auto-generated method stub
			// 刷新数据
			pageIndex = 0;
			arrayList.clear();
			new WareTask().execute();
			// 停止刷新和加载
			onLoad();
		}
		
		//加载更多
		@Override
		public void onLoadMore() {
			// TODO Auto-generated method stub
			pageIndex += 1;
			if (pageIndex >= 4) {
				Toast.makeText(getActivity(), "已经最后一页了", Toast.LENGTH_SHORT).show();
				onLoad();
				return;
			}
			new WareTask().execute();
		}
}
