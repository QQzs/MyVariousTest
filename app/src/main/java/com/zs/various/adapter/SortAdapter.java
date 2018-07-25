package com.zs.various.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zs.various.R;
import com.zs.various.bean.SortModel;

import java.util.ArrayList;

public class SortAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<SortModel> mSortlist;

	public SortAdapter(Context context, ArrayList<SortModel> sortlist) {
		mContext = context;
		mSortlist = sortlist;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mSortlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mSortlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (view == null) {
			view = LayoutInflater.from(mContext).inflate(R.layout.adapter_sort,
					null);
			holder = new ViewHolder();
			holder.letter_tv = (TextView) view.findViewById(R.id.tv_letter);
			holder.name_tv = (TextView) view.findViewById(R.id.name);
			holder.line_view = view.findViewById(R.id.line_view);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		if (!compareSection(position)) {
			holder.letter_tv.setText(mSortlist.get(position).getLetter());
			holder.letter_tv.setVisibility(View.VISIBLE);
            holder.line_view.setVisibility(View.GONE);
		} else {
			holder.letter_tv.setVisibility(View.GONE);
            holder.line_view.setVisibility(View.VISIBLE);
		}
		holder.name_tv.setText(mSortlist.get(position).getName());
		return view;
	}

	class ViewHolder {
		TextView letter_tv, name_tv;
		View line_view;
	}

	// 刷新数据
	public void updatelistview(ArrayList<SortModel> sortlist) {
		mSortlist = sortlist;
		notifyDataSetChanged();
	}

	public boolean compareSection(int position){
	    if (position == 0){
	        return false;
        }else{
	        int current = getSectionForPosition(position);
	        int previous = getSectionForPosition(position -1);
	        if (current == previous){
                return true;
            }else{
                return false;
            }
        }

    }

	// 获取当前位置的首字母(int表示ascii码)
	public int getSectionForPosition(int position) {
		return mSortlist.get(position).getLetter().charAt(0);
	}

	// 获取字母首次出现的位置
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String s = mSortlist.get(i).getLetter();
			char firstChar = s.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		return -1;
	}

}
