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

	// 界面底部的菜单按钮
		private ImageView[] bt_menu = new ImageView[4];
		// 界面底部的菜单按钮id
		private int[] bt_menu_id = { R.id.iv_menu_0, R.id.iv_menu_1, R.id.iv_menu_2, R.id.iv_menu_4 };

		// 界面底部的选中菜单按钮资源
		private int[] select_on = { R.drawable.guide_home_on, R.drawable.guide_tfaccount_on, R.drawable.guide_discover_on, R.drawable.guide_account_on };
		// 界面底部的未选中菜单按钮资源
		private int[] select_off = { R.drawable.bt_menu_0_select, R.drawable.bt_menu_1_select, R.drawable.bt_menu_2_select,  R.drawable.bt_menu_4_select };

		/** 主界面 */
		private Home_F home_F;
		//个人中心
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
		
		// 初始化默认显示的界面
				if (home_F == null) {
					home_F = new Home_F();
					addFragment(home_F);
					showFragment(home_F);
				} else {
					showFragment(home_F);
				}
		
		// 设置默认首页为点击时的图片
		bt_menu[0].setImageResource(select_on[0]);
	}
	
	@Override
	public void onClick(View v) {
	
		switch (v.getId()) {
		case R.id.iv_menu_0:
			// 主界面
			if (home_F == null) {
				home_F = new Home_F();
				// 判断当前界面是否隐藏，如果隐藏就进行添加显示，false表示显示，true表示当前界面隐藏
				addFragment(home_F);
				showFragment(home_F);
			} else {
				if (home_F.isHidden()) {
					showFragment(home_F);
				}
			}
			break;
		case R.id.iv_menu_2:
				//吐槽
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
			//个人中心
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
		
		// 设置按钮的选中和未选中资源
		for (int i = 0; i < bt_menu.length; i++) {
			bt_menu[i].setImageResource(select_off[i]);
			if (v.getId() == bt_menu_id[i]) {
				bt_menu[i].setImageResource(select_on[i]);
			}
		}
	}
	
	/** 添加Fragment **/
	public void addFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
		ft.add(R.id.show_layout, fragment);
		ft.commit();
	}
	
	/** 显示Fragment **/
	public void showFragment(Fragment fragment) {
		FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
		// 设置Fragment的切换动画
		ft.setCustomAnimations(R.anim.cu_push_right_in, R.anim.cu_push_left_out);

		// 判断页面是否已经创建，如果已经创建，那么就隐藏掉
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
