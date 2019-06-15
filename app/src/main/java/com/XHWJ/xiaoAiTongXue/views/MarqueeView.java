package com.XHWJ.xiaoAiTongXue.views;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

/**
 * 实现跑马灯效果
 * 
 * @author LiBing
 * 网页标题自定义
 * 
 */
public class MarqueeView extends android.support.v7.widget.AppCompatTextView {

	public MarqueeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		if (focused)
			super.onFocusChanged(focused, direction, previouslyFocusedRect);
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		if (hasWindowFocus)
			super.onWindowFocusChanged(hasWindowFocus);
	}

	@Override
	public boolean isFocused() {
		return true;
	}

}