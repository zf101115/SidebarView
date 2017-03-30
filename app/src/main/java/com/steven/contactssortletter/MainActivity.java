package com.steven.contactssortletter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.steven.contactssortletter.SidebarView.OnLetterClickedListener;

public class MainActivity extends Activity {
	@ViewInject(R.id.listView_users)
	private ListView listView_users;
	@ViewInject(R.id.textView_emptyinfo)
	private TextView textView_emptyinfo;
	private List<Area> totallList = null;
	@ViewInject(R.id.sidebarView_main)
	private SidebarView sidebarView_main;
	@ViewInject(R.id.textView_dialog)
	private TextView textView_dialog;
	private MySortAdapter adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
	}

	public void initView() {
		ViewUtils.inject(this);
		sidebarView_main.setTextView(textView_dialog);
		totallList = new ArrayList<Area>();
		totallList = getUserList();

		Collections.sort(totallList, new Comparator<Area>() {
			@Override
			public int compare(Area lhs, Area rhs) {
				if (lhs.getFirstLetter().equals("#")) {
					return 1;
				} else if (rhs.getFirstLetter().equals("#")) {
					return -1;
				} else {
					return lhs.getFirstLetter().compareTo(rhs.getFirstLetter());
				}
			}
		});
		adapter = new MySortAdapter(this, totallList);
		listView_users.setAdapter(adapter);
		listView_users.setEmptyView(textView_emptyinfo);

		listView_users.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String username = totallList.get(position).getUesrname();
				Toast.makeText(getApplicationContext(), username,
						Toast.LENGTH_SHORT).show();
			}
		});

		sidebarView_main
				.setOnLetterClickedListener(new OnLetterClickedListener() {
					@Override
					public void onLetterClicked(String str) {
						int position = adapter.getPositionForSection(str
								.charAt(0));
						listView_users.setSelection(position);
					}
				});
	}

	private List<Area> getUserList() {
		String[] arrUsers = getResources().getStringArray(R.array.arrUsernames);
		List<Area> list = new ArrayList<Area>();
		for (int i = 0; i < arrUsers.length; i++) {
			Area area = new Area();
			String username = arrUsers[i];
			String pinyin = ChineseToPinyinHelper.getInstance().getPinyin(
					username);
			String firstLetter = pinyin.substring(0, 1).toUpperCase();
			if (firstLetter.matches("[A-Z]")) {
				area.setFirstLetter(firstLetter);
			} else {
				area.setFirstLetter("#");
			}
			area.setUesrname(username);
			list.add(area);
		}
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
