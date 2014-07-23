package br.com.orlandoburli.sinteli.view.adapters;

import java.util.List;

import br.com.orlandoburli.sinteli.R;
import br.com.orlandoburli.sinteli.model.vo.ConfigVo;
import br.com.orlandoburli.sinteli.utils.FontUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ConfigAdapter extends BaseAdapter {

	private List<ConfigVo> listData;

	private LayoutInflater layoutInflater;

	private Context context;

	public ConfigAdapter(Context context, List<ConfigVo> list) {
		this.context = context;
		setListData(list);
		layoutInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {

			convertView = layoutInflater.inflate(R.layout.config_list_item, null);
			
			FontUtils.changeFonts(convertView);

			holder = new ViewHolder();
			holder.listItemNomeConfig = (TextView) convertView.findViewById(R.id.listItemNomeConfig);
			holder.listItemHostConfig = (TextView) convertView.findViewById(R.id.listItemHostConfig);
			holder.listItemPadraoConfig = (TextView) convertView.findViewById(R.id.listItemPadraoConfig);
			
			holder.listItemNomeConfig.setTypeface(FontUtils.getFontBold(convertView));

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ConfigVo config = listData.get(position);

		holder.listItemNomeConfig.setText(config.getNome());
		holder.listItemHostConfig.setText(config.getHost());

		if (config.isPadrao()) {
			holder.listItemPadraoConfig.setText(context.getResources().getString(R.string.label_config_padrao));
		} else {
			holder.listItemPadraoConfig.setText("");
		}

		return convertView;
	}

	public List<ConfigVo> getListData() {
		return listData;
	}

	public void setListData(List<ConfigVo> listData) {
		this.listData = listData;
	}

	public LayoutInflater getLayoutInflater() {
		return layoutInflater;
	}

	public void setLayoutInflater(LayoutInflater layoutInflater) {
		this.layoutInflater = layoutInflater;
	}

	static class ViewHolder {
		public TextView listItemPadraoConfig;
		public TextView listItemHostConfig;
		public TextView listItemNomeConfig;
	}

}
