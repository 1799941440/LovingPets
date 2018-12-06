package com.zhuangfei.expandedittext.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class InputMethodUtils {

	public static void close(Activity context){
		InputMethodManager imm=(InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);
	}
	
	public static void open(Activity content){
		InputMethodManager imm = (InputMethodManager) content.getSystemService(Context.INPUT_METHOD_SERVICE);  
		imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED); 
	}
}
