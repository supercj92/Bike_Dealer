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
	//��ʾ������Ʒ���б�
		private XListView listView;
		
		/**�������ݵ�ҳ��*/
		private int pageIndex = 0;
		/**�洢���緵�ص�����*/
		private HashMap<String, Object> hashMap;
		/**�洢���緵�ص������е�data�ֶ�*/
		private ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
		@Override
		public View onCreateView(LayoutInflater inflater,
				@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
			View view=LayoutInflater.from(getActivity()).inflate(R.layout.forum, null);
			initView(view);
			//������������
		new WareTask().execute();
		return view;
		}
		private void initView(View view){
			listView =(XListView)view.findViewById(R.id.listView_ware);
			listView.setOnTouchListener(this);
			listView.setXListViewListener(this);
			// ���ÿ��Խ����������صĹ���
			listView.setPullLoadEnable(true);
			listView.setPullRefreshEnable(true);
		}
		
		private class WareTask extends AsyncTask<Void, Void, HashMap<String, Object>> {
			
			ProgressDialog dialog=null;

			@Override
			protected void onPreExecute() {
				if (dialog==null) {
					dialog=ProgressDialog.show(getActivity(), "","���ڼ���...");
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
				//�������ݣ�����json
				String json = GetHttp.RequstGetHttp(url);
				//��һ������������ֶ�
				String[] LIST1_field = { "data" };
				
				//�ڶ���Ķ��������ֶ�
				String[] STR2_field = { "id", "name", "price", "sale_num", "address", "pic" };
				ArrayList<String[]> aL_STR2_field = new ArrayList<String[]>();
				//�ڶ���Ķ��������ֶη����һ������������ֶ���
				aL_STR2_field.add(STR2_field);
				//�������ص�json
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
				//���������������ʧ�ܣ���ô��ʾĬ�ϵ�����
				if (result != null && result.get("data") != null) {
					//�õ�data�ֶε�����
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
				// ֹͣˢ�ºͼ���
				onLoad();

			}

		}
		
		/** �򵥵�ʱ���ʽ */
		public static SimpleDateFormat mDateFormat = new SimpleDateFormat("MM-dd HH:mm");

		public static String getCurrentTime(long time) {
			if (0 == time) {
				return "";
			}

			return mDateFormat.format(new Date(time));

		}
		



		
		/** ֹͣ���غ�ˢ�� */
		private void onLoad() {
			listView.stopRefresh();
			// ֹͣ���ظ���
			listView.stopLoadMore();

			// �������һ��ˢ��ʱ��
			listView.setRefreshTime(getCurrentTime(System.currentTimeMillis()));
		}

		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			return false;
		}
		
		//ˢ��
		@Override
		public void onRefresh() {
			// TODO Auto-generated method stub
			// ˢ������
			pageIndex = 0;
			arrayList.clear();
			new WareTask().execute();
			// ֹͣˢ�ºͼ���
			onLoad();
		}
		
		//���ظ���
		@Override
		public void onLoadMore() {
			// TODO Auto-generated method stub
			pageIndex += 1;
			if (pageIndex >= 4) {
				Toast.makeText(getActivity(), "�Ѿ����һҳ��", Toast.LENGTH_SHORT).show();
				onLoad();
				return;
			}
			new WareTask().execute();
		}
}
