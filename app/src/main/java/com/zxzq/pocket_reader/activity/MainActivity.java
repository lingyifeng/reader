package com.zxzq.pocket_reader.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zxzq.pocket_reader.fragment.MainFragment;
import com.zxzq.pocket_reader.R;

public class MainActivity extends AppCompatActivity {


    private Button mBt_collection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();


    }

    private void initView() {
        inflateHomeFragment(new MainFragment(), R.id.fm_show);
        mBt_collection = (Button) findViewById(R.id.bt_collection);
        mBt_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }






    private void inflateHomeFragment(Fragment fragment,int resid) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(resid,fragment).commit();
    }

}
