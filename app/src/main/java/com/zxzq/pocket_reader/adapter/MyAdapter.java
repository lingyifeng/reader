package com.zxzq.pocket_reader.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zxzq.pocket_reader.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/7 0007.
 */
public class MyAdapter extends BaseAdapter {
    ArrayList<String> mArrayList;

    public MyAdapter(ArrayList<String> arrayList) {
        mArrayList = arrayList;
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHodler hodler;
        if(view==null){
            view=View.inflate(viewGroup.getContext(),R.layout.lv_item,null);
            hodler=new ViewHodler(view);
            view.setTag(hodler);
        }
        hodler = (ViewHodler) view.getTag();
        hodler.mTv_name.setText(mArrayList.get(i));
        return view;
    }
    public class ViewHodler{

        private final TextView mTv_name;

        public ViewHodler(View view) {
            mTv_name = (TextView) view.findViewById(R.id.tv_name);
        }
    }
}
