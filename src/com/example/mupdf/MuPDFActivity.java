package com.example.mupdf;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.artifex.mupdf.MuPDFCore;
import com.artifex.mupdf.MuPDFPageAdapter;
import com.artifex.mupdf.view.DocumentReaderView;
import com.artifex.mupdf.view.ReaderView;

public class MuPDFActivity extends Activity
{
	private static final String TAG = "MuPDFActivity";
	private MuPDFCore core;
	private MuPDFCore cores[];
	private ReaderView docView;
	private MuPDFPageAdapter mDocViewAdapter;
	String paths[]={"//sdcard/dcim/congthuclop10coban.pdf"};
	String paths1[] = {"//sdcard/dcim/congthuclop10nangcao.pdf"};
	String paths2[] = {"//sdcard/dcim/congthuclop11.pdf"};
	String paths3[] = {"//sdcard/dcim/congthuclop12full.pdf"};
	String paths4[] = {"//sdcard/dcim/congthuclop12tonghop.pdf"};

	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		Intent itent = getIntent();
		String is = itent.getStringExtra("i");
		if (is.equals("0")) {
			path(paths);
		}
		if (is.equals("1")) {
			path(paths1);
		}
		if (is.equals("2")) {
			path(paths2);
		}
		if (is.equals("3")) {
			path(paths3);
		}
		if (is.equals("4")) {
			path(paths4);
		}
		createUI(savedInstanceState);
	}
	private void path(String[] paths2){
		cores= new MuPDFCore[paths2.length];
		
		for(int i=0;i<paths2.length;i++)
		{
			
			cores[i] = openFile(paths2[i]);
			
		}
		
	}
	private void createUI(Bundle savedInstanceState) 
	{
		docView = new DocumentReaderView(this)
		{
			@Override
			protected void onMoveToChild(View view, int i) 
			{
				super.onMoveToChild(view, i);
			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,float distanceX, float distanceY)
			{
				return super.onScroll(e1, e2, distanceX, distanceY);
			}

			@Override
			protected void onContextMenuClick() 
			{

			}

			@Override
			protected void onBuy(String path) 
			{
			
			}

		};
		
		mDocViewAdapter = new MuPDFPageAdapter(this, cores);
		docView.setAdapter(mDocViewAdapter);
		RelativeLayout layout = new RelativeLayout(this);
		layout.addView(docView);
		layout.setBackgroundColor(Color.BLACK);
		setContentView(layout);
	}
	

	private MuPDFCore openFile(String path) 
	{
		try
		{
			core = new MuPDFCore(path);
		} catch (Exception e) {
			Log.e(TAG, "get core failed", e);
			return null;
		}
		return core;
	}
}