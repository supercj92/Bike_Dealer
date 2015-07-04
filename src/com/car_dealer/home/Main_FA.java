package com.car_dealer.home;


import com.car_dealer.forum.Forum_F;
import com.car_dealer.user.User_F;
import com.example.car_dealer.R;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class Main_FA extends FragmentActivity implements OnClickListener {

	// ����ײ��Ĳ˵���ť
		private ImageView[] bt_menu = new ImageView[4];
		// ����ײ��Ĳ˵���ťid
		private int[] bt_menu_id = { R.id.iv_menu_0, R.id.iv_menu_1, R.id.iv_menu_2, R.id.iv_menu_4 };

		// ����ײ���ѡ�в˵���ť��Դ
		private int[] select_on = { R.drawable.guide_home_on, R.drawable.guide_tfaccount_on, R.drawable.guide_discover_on, R.drawable.guide_account_on };
		// ����ײ���δѡ�в˵���ť��Դ
		private int[] select_off = { R.drawable.bt_menu_0_select, R.drawable.bt_menu_1_select, R.drawable.bt_menu_2_select,  R.drawable.bt_menu_4_select };

		/** ������ */
		private Home_F home_F;
		//��������
		private User_F user_F;
		
		private Forum_F forum_F;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}
	
	private void initView(){
		for (int i = 0; i < bt_menu.length; i++) {
			bt_menu[i] = (ImageView) findViewById(bt_menu_id[i]);
			bt_menu[i].setOnClickListener(this);
		}
		
		// ��ʼ��Ĭ����ʾ�Ľ���
				if (home_F == null) {
					home_F = new Home_F();
					addFragment(home_F);
					showFragment(home_F);
				} else {
					showFragment(home_F);
				}
		
		// ����Ĭ����ҳΪ���ʱ��ͼƬ
		bt_menu[0].setImageResource(select_on[0]);
	}
	
	@Override
	public void onClick(View v) {
	
		switch (v.getId()) {
		case R.id.iv_menu_0:
			// ������
			if (home_F == null) {
				home_F = new Home_F();
				// �жϵ�ǰ�����Ƿ����أ�������ؾͽ��������ʾ��false��ʾ��ʾ��true��ʾ��ǰ��������
				addFragment(home_F);
				showFragment(home_F);
			} else {
				if (home_F.isHidden()) {
					showFragment(home_F);
				}
			}
			break;
		case R.id.iv_menu_2:
				//�²�
			if(forum_F==null){
				forum_F=new Forum_F();
				addFragment(forum_F);
				showFragment(forum_F);
			}else {
				if(forum_F.isHidden()){
					showFragment(forum_F);
				}
			}
			break;
		case R.id.iv_menu_4:
			//��������
			if(user_F==null){
				user_F=new User_F();
				addFragment(user_F);
				showFragment(user_F);
			}else {
				if(user_F.isHidden()){
					showFragment(user_F);
				}
			}
			break;
			
		}
		
		// ���ð�ť��ѡ�к�δѡ����Դ
		for (int i = 0; i < bt_menu.length; i++) {
			bt_menu[i].setImageResource(select_off[i]);
			if (v.getId() == bt_menu_id[i]) {
				bt_menu[i].setImageResource(select_on[i]);
			}
		}
	}
	
	/** ���Fragment **/
	public void addFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
		ft.add(R.id.show_layout, fragment);
		ft.commit();
	}
	
	/** ��ʾFragment **/
	public void showFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
		// ����Fragment���л�����
		ft.setCustomAnimations(R.anim.cu_push_right_in, R.anim.cu_push_left_out);

		// �ж�ҳ���Ƿ��Ѿ�����������Ѿ���������ô�����ص�
		if (home_F != null) {
			ft.hide(home_F);
		}
		if(user_F!=null){
			ft.hide(user_F);
		}
		if(forum_F!=null){
			ft.hide(forum_F);
		}
		ft.show(fragment);
		ft.commitAllowingStateLoss();

	}
	
}
