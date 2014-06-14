package br.com.orlandoburli.sinteli.utils;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FontUtils {

	public static void changeFonts(View v) {
		if (v instanceof ViewGroup) {
			Typeface tf = getFontDefault(v);
			
			ViewGroup group = (ViewGroup) v;

			for (int i = 0; i < group.getChildCount(); i++) {
				View child = group.getChildAt(i);

				if (child instanceof TextView) {
					((TextView) child).setTypeface(tf);
				}
			}

		}
	}

	public static Typeface getFontDefault(View v) {
		Typeface tf = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/segoe_ui_light.ttf");
		return tf;
	}
	
	public static Typeface getFontBold(View v) {
		Typeface tf = Typeface.createFromAsset(v.getContext().getAssets(), "fonts/segoe_ui_bold.ttf");
		return tf;
	}
}
