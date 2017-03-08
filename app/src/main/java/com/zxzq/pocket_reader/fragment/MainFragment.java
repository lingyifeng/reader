package com.zxzq.pocket_reader.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zxzq.pocket_reader.R;
import com.zxzq.pocket_reader.activity.MainActivity;
import com.zxzq.pocket_reader.activity.ReadActivity;
import com.zxzq.pocket_reader.adapter.MyAdapter;
import com.zxzq.pocket_reader.filter.MyFilter;
import com.zxzq.pocket_reader.manager.FileManager;
import com.zxzq.pocket_reader.manager.MemoryManager;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/7 0007.
 */
public class MainFragment extends Fragment {
    private View mView;
    private ListView mLv_show;
    private ProgressDialog mDialog;
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
        mDialog = ProgressDialog.show(getActivity(), null, " 加载中，请稍候。。。 ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileManager fileManager = new FileManager();
                File file = new File(MemoryManager.getPhoneInSDCardPath());
                final ArrayList<String> txtName = fileManager.getTXTName(file, new MyFilter(new String[]{".txt"}));
                final ArrayList<String> txtPath = fileManager.getTXTpath(file, new MyFilter(new String[]{".txt"}));
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                        MyAdapter myAdapter = new MyAdapter(txtName);
                        mLv_show.setAdapter(myAdapter);
                        mLv_show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(getActivity(), ReadActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("path", txtPath.get(i));
                                bundle.putInt("id",i);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        }).start();

    }
}
