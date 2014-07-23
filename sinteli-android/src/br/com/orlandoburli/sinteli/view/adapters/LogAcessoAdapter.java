package br.com.orlandoburli.sinteli.view.adapters;

import java.util.List;

import br.com.orlandoburli.sinteli.R;
import br.com.orlandoburli.sinteli.model.vo.LogAcessoVo;
import br.com.orlandoburli.sinteli.utils.FontUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LogAcessoAdapter extends BaseAdapter {

	private List<LogAcessoVo> listData;

	private LayoutInflater layoutInflater;

	private Context context;

	public LogAcessoAdapter(Context context, List<LogAcessoVo> list) {
		this.setContext(context);
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

			convertView = layoutInflater.inflate(R.layout.acesso_list_item, null);

			FontUtils.changeFonts(convertView);

			holder = new ViewHolder();
			holder.listAcessoNome = (TextView) convertView.findViewById(R.id.listAcessoNome);
			holder.listAcessoDataHora = (TextView) convertView.findViewById(R.id.listAcessoDataHora);
			holder.listAcessoDestino = (TextView) convertView.findViewById(R.id.listAcessoDestino);

			holder.listAcessoNome.setTypeface(FontUtils.getFontBold(convertView));

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		LogAcessoVo logAcesso = listData.get(position);

		holder.listAcessoNome.setText(logAcesso.getNome());
		holder.listAcessoDataHora.setText(logAcesso.getDataHora());
		holder.listAcessoDestino.setText(logAcesso.getDestino());

		return convertView;
	}

	public List<LogAcessoVo> getListData() {
		return listData;
	}

	public void setListData(List<LogAcessoVo> listData) {
		this.listData = listData;
	}

	public LayoutInflater getLayoutInflater() {
		return layoutInflater;
	}

	public void setLayoutInflater(LayoutInflater layoutInflater) {
		this.layoutInflater = layoutInflater;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	static class ViewHolder {
		public TextView listAcessoNome;
		public TextView listAcessoDataHora;
		public TextView listAcessoDestino;
	}
}
