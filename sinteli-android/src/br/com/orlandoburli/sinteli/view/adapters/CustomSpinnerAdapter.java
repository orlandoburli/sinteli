package br.com.orlandoburli.sinteli.view.adapters;

import java.util.List;

import br.com.orlandoburli.sinteli.utils.FontUtils;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomSpinnerAdapter<E> extends ArrayAdapter<E> {

	public CustomSpinnerAdapter(Context context, int textViewResourceId, List<E> objects) {
		super(context, textViewResourceId, objects);
	}

	public TextView getView(int position, View convertView, ViewGroup parent) {
		TextView v = (TextView) super.getView(position, convertView, parent);
		v.setTypeface(FontUtils.getFontDefault(v));
		return v;
	}

	public TextView getDropDownView(int position, View convertView, ViewGroup parent) {
		TextView v = (TextView) super.getView(position, convertView, parent);
		v.setTypeface(FontUtils.getFontDefault(v));
		return v;
	}

}
