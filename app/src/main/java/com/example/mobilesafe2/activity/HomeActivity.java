package com.example.mobilesafe2.activity;

import com.example.mobilesafe2.R;
import com.example.mobilesafe2.R.layout;
import com.example.mobilesafe2.adapter.HomeAdapter;
import com.example.mobilesafe2.bean.HomeBean;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.GridLayoutAnimationController;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends Activity implements AdapterView.OnItemClickListener {

	private ImageView iv_logo;
	private GridView gridview;

	private final static String[] TITLES = new String[] {  "常用工具","进程管理", "手机杀毒","功能设置"};

	private final static String[] DESCS = new String[] { "工具大全" ,"管理运行进程", "病毒无处藏身","管理您的软件"};

	private final static int[] ICONS = new int[] {  R.drawable.cygj, R.drawable.jcgl,R.drawable.sjsd ,R.drawable.rjgj};

	List<HomeBean> datas = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		initView();
		initData();
	}

	private void initView() {
		
		iv_logo = (ImageView) findViewById(R.id.iv_logo);
//		iv_logo.setRotationY(rotationY);

		gridview = (GridView) findViewById(R.id.gridview);


	}

	private void initData() {
		
		ObjectAnimator oa = ObjectAnimator.ofFloat(iv_logo, "rotationY", 0,45,90,180,270,360);
		oa.setDuration(1000);
		oa.setRepeatCount(Animation.INFINITE);
		oa.setRepeatMode(Animation.REVERSE);
		//TODO
		oa.start();

		//通过javaBean将数据传递给adapter
		for (int i = 0; i < TITLES.length; i++) {
			HomeBean homeBean  = new HomeBean();
			homeBean.title = TITLES[i];
			homeBean.desc = DESCS[i];
			homeBean.imageId = ICONS[i];

			datas.add(homeBean);
		}

		HomeAdapter homeAdapter  = new HomeAdapter(datas,HomeActivity.this);
		gridview.setAdapter(homeAdapter);

		//给gridview设置条目点击事件
		gridview.setOnItemClickListener(this);

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (position){
		    case 0:
				System.out.println("常用工具");
				break;
			case 1:
				System.out.println("进程管理");
				break;
			case 2:
				System.out.println("手机杀毒");
				break;
			case 3:
				System.out.println("功能设置");
				startActivity(new Intent(HomeActivity.this,SettingActivity.class));
				break;
		    default:

		        break;
		}
	}
}
