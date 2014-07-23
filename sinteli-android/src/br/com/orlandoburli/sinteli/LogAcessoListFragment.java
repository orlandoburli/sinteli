package br.com.orlandoburli.sinteli;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import br.com.orlandoburli.sinteli.model.be.LogAcessoBe;
import br.com.orlandoburli.sinteli.model.vo.ConfigVo;
import br.com.orlandoburli.sinteli.model.vo.LogAcessoVo;
import br.com.orlandoburli.sinteli.utils.FontUtils;
import br.com.orlandoburli.sinteli.utils.FragmentUtils;
import br.com.orlandoburli.sinteli.view.adapters.LogAcessoAdapter;

public class LogAcessoListFragment extends Fragment {

	private ConfigVo config;

	public LogAcessoListFragment(ConfigVo config) {
		this.config = config;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.acesso_list, null);

		FontUtils.changeFonts(view);

		Button btnVoltarLogin = (Button) view.findViewById(R.id.btnVoltarLogin);

		btnVoltarLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Voltar para o login
				FragmentUtils.gotoFragment(new HomeFragment(config), getFragmentManager());
			}

		});

		btnVoltarLogin.setTypeface(FontUtils.getFontDefault(view));

		// Lista

		List<LogAcessoVo> list = new LogAcessoBe(getActivity()).getList();

		final ListView listAcessos = (ListView) view.findViewById(R.id.listAcessos);

		listAcessos.setAdapter(new LogAcessoAdapter(getActivity(), list));

		listAcessos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				Object o = listAcessos.getItemAtPosition(position);

				LogAcessoVo logAcesso = (LogAcessoVo) o;

				FragmentUtils.gotoFragment(new LogAcessoViewFragment(logAcesso, config), getFragmentManager());
			}
		});

		// TODO On Click

		/*
		 * 
		 * ConfigBe be = new ConfigBe(getActivity()); List<ConfigVo> list =
		 * be.getList();
		 * 
		 * final ListView listConfig = (ListView)
		 * view.findViewById(R.id.listConfig);
		 * 
		 * listConfig.setAdapter(new ConfigAdapter(getActivity(), list));
		 * 
		 * listConfig.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> a, View v, int
		 * position, long id) { Object o =
		 * listConfig.getItemAtPosition(position);
		 * 
		 * ConfigVo config = (ConfigVo) o; ConfigViewFragment fragment = new
		 * ConfigViewFragment(); fragment.setConfig(config);
		 * 
		 * FragmentUtils.gotoFragment(fragment, getFragmentManager()); } });
		 */

		return view;
	}

}
