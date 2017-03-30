package com.steven.contactssortletter;

import java.util.List;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MySortAdapter extends BaseAdapter implements SectionIndexer {
	private List<Area> list = null;
	private Context context = null;

	public MySortAdapter(Context context, List<Area> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder mHolder = null;
		if (convertView == null) {
			mHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_listview_main, parent, false);
			ViewUtils.inject(mHolder, convertView);
			convertView.setTag(mHolder);
		} else {
			mHolder = (ViewHolder) convertView.getTag();
		}
		Area area = list.get(position);
		mHolder.textView_item_username.setText(area.getUesrname());

		int section = getSectionForPosition(position);
		if (position == getPositionForSection(section)) {
			// 第一次出现该section
			mHolder.textView_item_firstletter.setVisibility(View.VISIBLE);
			mHolder.textView_item_firstletter.setText(area
					.getFirstLetter());
			mHolder.textView_item_firstletter.getPaint().setFlags(
					Paint.UNDERLINE_TEXT_FLAG);
		} else {
			mHolder.textView_item_firstletter.setVisibility(View.GONE);
		}
		return convertView;
	}

	class ViewHolder {
		@ViewInject(R.id.textView_item_username)
		private TextView textView_item_username;
		@ViewInject(R.id.textView_item_firstletter)
		private TextView textView_item_firstletter;
		@ViewInject(R.id.imageView_item_userface)
		private ImageView imageView_item_userface;
	}

	@Override
	public Object[] getSections() {
		return null;
	}

	// 做字母索引的时候常常会用到SectionIndexer这个接口
	// 1. getSectionForPosition() 通过该项的位置，获得所在分类组的索引号
	// 2. getPositionForSection() 根据分类列的索引号获得该序列的首个位置

	@Override
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String firstLetter = list.get(i).getFirstLetter();
			char firstChar = firstLetter.charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		return -1;
	}

	// 根据position获取分类的首字母的Char ascii值
	@Override
	public int getSectionForPosition(int position) {
		return list.get(position).getFirstLetter().charAt(0);
	}

}
