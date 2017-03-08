package com.zxzq.pocket_reader.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zxzq.pocket_reader.R;

/**
 * Created by Administrator on 2017/3/7 0007.
 */
public class CollectionFragment extends Fragment {
    private View mView;
    private ListView mLv_show;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mView == null)
        {
            initView(inflater, container);
        }
        return mView;
    }
    //初始化做界面listView
    private void initView(final LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.list_item, container, false);
        mLv_show = (ListView) mView.findViewById(R.id.lv_show);
    }
}
