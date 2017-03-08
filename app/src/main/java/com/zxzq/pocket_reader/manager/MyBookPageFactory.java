package com.zxzq.pocket_reader.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.Vector;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

public class MyBookPageFactory {
	private int mHeight, mWidth;
	private int mVisibleHeight, mVisibleWidth;
	private int mPageLineCount;
	private int mLineSpace = 2;
	private int m_mpBufferLen;
	private MappedByteBuffer m_mpBuff;
	private int m_mbBufEndPos = 0;
	private int m_mbBufBeginPos =0;
	private Paint mPaint;
	private int margingHeight = 30;
	private int margingWeight = 30;
	private int mFontSize = 30;
	private Bitmap m_book_bg;
	private Vector<String> m_lines = new Vector<String>();
	
	public MyBookPageFactory(int w, int h, int fontsize){	
		mWidth = w;
		mHeight = h;
		mFontSize = fontsize;
		mVisibleHeight = mHeight - margingHeight*2 - mFontSize;
		mVisibleWidth = mWidth -margingWeight*2;
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setTextSize(mFontSize);
		mPaint.setColor(Color.BLACK);
		mPageLineCount = (int)mVisibleHeight/(mFontSize+2);
	}
	public void openBook(String path, int[] position) throws FileNotFoundException, IOException{
		File file = new File(path);
		long length = file.length();
		m_mpBufferLen = (int)length;
		m_mpBuff = new RandomAccessFile(file, "r"). getChannel().map(FileChannel.MapMode.READ_ONLY, 0, length);
		m_mbBufEndPos = position[1];
		m_mbBufBeginPos = position[0];
	}
	public void onDrow(Canvas canvas){
		if(m_lines.size()==0){
			m_mbBufEndPos = m_mbBufBeginPos;
			m_lines = pageDown();
		}
		if(m_lines.size()>0){
			int y = margingHeight;
			if(m_book_bg != null){
				Rect rectF = new Rect(0, 0, mWidth, mHeight);
				canvas.drawBitmap(m_book_bg, null, rectF, null);
			}else{
				canvas.drawColor(Color.WHITE);
			}
			for(String line : m_lines){
				y+=mFontSize+mLineSpace;
				canvas.drawText(line, margingWeight, y, mPaint);
			}
			float persent = (float)m_mbBufBeginPos*100/m_mpBufferLen;
			DecimalFormat strPersent  = new DecimalFormat("#0.00");
			int strLen = (int)mPaint.measureText(strPersent.format(persent));
			canvas.drawText(strPersent.format(persent) + "%", (mWidth-strLen)/2, mHeight-margingHeight, mPaint);
		}
		
	}
	private void pageUp() {
		// TODO Auto-generated method stub
		String strParagraph = "";
		Vector<String> lines = new Vector<String>();
		while((lines.size() < mPageLineCount) && (m_mbBufBeginPos > 0)){
			Vector<String> paraLines = new Vector<String>();

			byte[] parabuffer = readParagraphBack(m_mbBufBeginPos);
			
			m_mbBufBeginPos -= parabuffer.length;
			try {
				strParagraph = new String(parabuffer, "GBK");
//				Log.d("xjd", "strParagraph"+strParagraph);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			strParagraph = strParagraph.replaceAll("\r\n", "  ");
			strParagraph = strParagraph.replaceAll("\n", " ");

			while(strParagraph.length() > 0){
				int paintSize = mPaint.breakText(strParagraph, true, mVisibleWidth, null);
				paraLines.add(strParagraph.substring(0, paintSize));
				strParagraph = strParagraph.substring(paintSize);
			}
			lines.addAll(0, paraLines);
			
			while (lines.size() > mPageLineCount) {
				try {
					m_mbBufBeginPos += lines.get(0).getBytes("GBK").length;
					lines.remove(0);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			m_mbBufEndPos = m_mbBufBeginPos;
		}
	}	
	private Vector<String> pageDown() {
		// TODO Auto-generated method stub
		String strParagraph = "";
		Vector<String> lines = new Vector<String>();
		while((lines.size() < mPageLineCount) && (m_mbBufEndPos < m_mpBufferLen)){
			byte[] parabuffer = readParagraphForward(m_mbBufEndPos);
			m_mbBufEndPos += parabuffer.length;
			try {
				strParagraph = new String(parabuffer, "GBK");
//				Log.d("xjd", strParagraph);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			strParagraph = strParagraph.replaceAll("\r\n", "  ");
			strParagraph = strParagraph.replaceAll("\n", " ");

			while(strParagraph.length() > 0){
				int paintSize = mPaint.breakText(strParagraph, true, mVisibleWidth, null);
				lines.add(strParagraph.substring(0, paintSize));
				strParagraph = strParagraph.substring(paintSize);
				if(lines.size() >= mPageLineCount){
					break;
				}
			}
			if(strParagraph.length()!=0){
				try {
					m_mbBufEndPos -= (strParagraph).getBytes("GBK").length;
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return lines;
	}
	private byte[] readParagraphForward(int m_mbBufPos) {
		// TODO Auto-generated method stub
		byte b0, b1 ;
		int i = m_mbBufPos;
		while(i < m_mpBufferLen){
			b0 = m_mpBuff.get(i++);
			if(b0 == 0x0a){
				break;
			}
		}		
		int nParaSize = i - m_mbBufPos;
		byte[] buf = new byte[nParaSize];
		for (i = 0; i < nParaSize; i++) {
			buf[i] = m_mpBuff.get(m_mbBufPos + i);
		}
		return buf;
	}
	private byte[] readParagraphBack(int m_mbBufBeginPos) {
		// TODO Auto-generated method stub
		byte b0, b1 ;
		int i = m_mbBufBeginPos -1 ;
		while(i > 0){
			b0 = m_mpBuff.get(i);
			if(b0 == 0x0a && i != m_mbBufBeginPos -1 ){
				i++;
				break;
			}
			i--;
		}		
		int nParaSize = m_mbBufBeginPos -i ;
		byte[] buf = new byte[nParaSize];
		for (int j = 0; j < nParaSize; j++) {
			buf[j] = m_mpBuff.get(i + j);
		}
		return buf;
	}
	public void nextPage() {
		// TODO Auto-generated method stub
		if(m_mbBufEndPos >= m_mpBufferLen){
			return;
		}else{
			m_lines.clear();
			m_mbBufBeginPos = m_mbBufEndPos;
			m_lines = pageDown();
		}
	}
	public void prePage() {
		// TODO Auto-generated method stub
		if(m_mbBufBeginPos<=0){
			return;
		}
		m_lines.clear();
		pageUp();
		m_lines = pageDown();
	}
	public int[] getPosition(){
		int[] a = new int[]{m_mbBufBeginPos, m_mbBufEndPos};
		return a;
	}
	public void setTextFont(int fontsize) {
		// TODO Auto-generated method stub
		mFontSize = fontsize;
		mPaint.setTextSize(mFontSize);
		mPageLineCount = mVisibleHeight/(mFontSize+mLineSpace);
		m_mbBufEndPos = m_mbBufBeginPos;
		nextPage();
	}
	public int getTextFont() {
		// TODO Auto-generated method stub
		return mFontSize;
	}
	public void setPersent(int persent) {
		// TODO Auto-generated method stub
		float a =  (float)(m_mpBufferLen*persent)/100;
		m_mbBufEndPos = (int)a;
		if(m_mbBufEndPos == 0){
			nextPage();
		}else{
			nextPage();
			prePage();
			nextPage();
		}
	}
	public void setBgBitmap(Bitmap BG) {
		m_book_bg = BG;
	}
}
