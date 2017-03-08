package com.zxzq.pocket_reader.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

public class MyPageWidget extends View{
	private Bitmap mCurBitMap;
	public MyPageWidget(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		canvas.save();
		canvas.drawBitmap(mCurBitMap, 0, 0, null);
		canvas.restore();
	}
	
	public void setDrawBitMap(Bitmap bitmap ){
		mCurBitMap = bitmap;
	}
}
