package com.car_dealer.shop;

import com.example.car_dealer.R;

import android.R.layout;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Shop_F extends Fragment implements OnClickListener{
	
	private TextView bt_cart_all, bt_cart_low, bt_cart_stock;
	private View show_cart_all, show_cart_low, show_cart_stock;
	private Type_One type_One;
@Override
public View onCreateView(LayoutInflater inflater,
		@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
	View view=LayoutInflater.from(getActivity()).inflate(R.layout.shop, null);
	initView(view);
	return view;
}
	private void initView(View view){
		
		bt_cart_all = (TextView) view.findViewById(R.id.bt_cart_all);
		bt_cart_low = (TextView) view.findViewById(R.id.bt_cart_low);
		bt_cart_stock = (TextView) view.findViewById(R.id.bt_cart_stock);
		show_cart_all = view.findViewById(R.id.show_cart_all);
		show_cart_low = view.findViewById(R.id.show_cart_low);
		show_cart_stock = view.findViewById(R.id.show_cart_stock);

		bt_cart_all.setOnClickListener(this);
		bt_cart_low.setOnClickListener(this);
		bt_cart_stock.setOnClickListener(this);
		
		type_One=new Type_One();
		addFragment(type_One);
		showFragment(type_One);
	}
	@Override
	public void onClick(View v) {
				switch (v.getId()) {
				case R.id.bt_cart_all:
					if (type_One == null) {
						type_One = new Type_One();
						addFragment(type_One);
						showFragment(type_One);
					} else {
						showFragment(type_One);
					}
					show_cart_all.setBackgroundColor(getResources().getColor(R.color.bg_Black));
					show_cart_low.setBackgroundColor(getResources().getColor(R.color.bg_Gray));
					show_cart_stock.setBackgroundColor(getResources().getColor(R.color.bg_Gray));
					break;
				case R.id.bt_cart_low:
			/*		if (lowBaby_F == null) {
						lowBaby_F = new LowBaby_F();
						addFragment(lowBaby_F);
						showFragment(lowBaby_F);
					} else {
						showFragment(lowBaby_F);
					}*/
					show_cart_low.setBackgroundColor(getResources().getColor(R.color.bg_Black));
					show_cart_all.setBackgroundColor(getResources().getColor(R.color.bg_Gray));
					show_cart_stock.setBackgroundColor(getResources().getColor(R.color.bg_Gray));

					break;
				case R.id.bt_cart_stock:
					/*if (stockBaby_F == null) {
						stockBaby_F = new StockBaby_F();
						addFragment(stockBaby_F);
						showFragment(stockBaby_F);
					} else {
						showFragment(stockBaby_F);
					}*/
					show_cart_stock.setBackgroundColor(getResources().getColor(R.color.bg_Black));
					show_cart_all.setBackgroundColor(getResources().getColor(R.color.bg_Gray));
					show_cart_low.setBackgroundColor(getResources().getColor(R.color.bg_Gray));

					break;

				default:
					break;
				}
	}
	/** ÃÌº”Fragment **/
	public void addFragment(Fragment fragment) {
		FragmentTransaction ft = this.getFragmentManager().beginTransaction();
		ft.add(R.id.show_cart_view, fragment);
		ft.commitAllowingStateLoss();
	}
	
	/** œ‘ æFragment **/
	public void showFragment(Fragment fragment) {
		FragmentTransaction ft = this.getFragmentManager().beginTransaction();
		if (type_One != null) {
			ft.hide(type_One);
		}
	
		ft.show(fragment);
		ft.commitAllowingStateLoss();

	}
}
