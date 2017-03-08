package com.zxzq.pocket_reader.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.zxzq.pocket_reader.R;
import com.zxzq.pocket_reader.manager.MyBookPageFactory;
import com.zxzq.pocket_reader.view.MyPageWidget;

public class ReadActivity extends AppCompatActivity {
    private MyPageWidget mPageWidget;
    private Bitmap mCurrentPageBitmap, mNextPageBitmap;
    private Canvas mCurrentPageCanvas, mNextPageCanvas;
    private DisplayMetrics dm;
    private MyBookPageFactory pagefactory;
    private int id;
    private SharedPreferences sp;
    private int[] position = new int[]{0, 0};
    private int fontsize = 30;
    private LayoutInflater mInflater;
    private String mPath;
    private Button mBt_change;
    private Button mBt_choose;
    private Button mBt_collect;
    private MyPageWidget mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        mPath = getIntent().getStringExtra("path");
        initView();

    }

    private void initView() {
        mBt_change = (Button) findViewById(R.id.bt_change);
        mBt_choose = (Button) findViewById(R.id.bt_choose);
        mBt_collect = (Button) findViewById(R.id.bt_collect);
        mPager = (MyPageWidget) findViewById(R.id.pager);
    }

    public class GestureListener extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {
        /**
         * 左右滑动的最短距离
         */
        private int distance = 100;
        /**
         * 左右滑动的最大速度
         */
        private int velocity = 200;

        private GestureDetector gestureDetector;

        public GestureListener(Context context) {
            super();
            gestureDetector = new GestureDetector(context, this);
        }

        /**
         * 向左滑的时候调用的方法，子类应该重写
         *
         * @return
         */
        public boolean right() {
            pagefactory.nextPage();
            pagefactory.onDrow(mCurrentPageCanvas);
            mPageWidget.setDrawBitMap(mCurrentPageBitmap);
            mPageWidget.invalidate();
            return true;
        }

        /**
         * 向右滑的时候调用的方法，子类应该重写
         *
         * @return
         */
        public boolean left() {
            pagefactory.prePage();
            pagefactory.onDrow(mCurrentPageCanvas);
            mPageWidget.setDrawBitMap(mCurrentPageBitmap);
            mPageWidget.invalidate();
            System.out.println("55555");
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            // e1：第1个ACTION_DOWN MotionEvent
            // e2：最后一个ACTION_MOVE MotionEvent
            // velocityX：X轴上的移动速度（像素/秒）
            // velocityY：Y轴上的移动速度（像素/秒）

            // 向左滑
            if (e1.getX() - e2.getX() > distance
                    && Math.abs(velocityX) > velocity) {
                right();
            }
            // 向右滑
            if (e2.getX() - e1.getX() > distance
                    && Math.abs(velocityX) > velocity) {
                left();
            }

            return true;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            gestureDetector.onTouchEvent(event);
            return false;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getVelocity() {
            return velocity;
        }

        public void setVelocity(int velocity) {
            this.velocity = velocity;
        }

        public GestureDetector getGestureDetector() {
            return gestureDetector;
        }

        public void setGestureDetector(GestureDetector gestureDetector) {
            this.gestureDetector = gestureDetector;
        }
    }
}
