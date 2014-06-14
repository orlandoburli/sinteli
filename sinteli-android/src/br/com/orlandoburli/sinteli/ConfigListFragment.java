package br.com.orlandoburli.sinteli;

import java.util.List;

import br.com.orlandoburli.sintel.R;
import br.com.orlandoburli.sinteli.model.be.ConfigBe;
import br.com.orlandoburli.sinteli.model.vo.ConfigVo;
import br.com.orlandoburli.sinteli.utils.FontUtils;
import br.com.orlandoburli.sinteli.utils.FragmentUtils;
import br.com.orlandoburli.sinteli.view.adapters.ConfigAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ConfigListFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.config_list, null);

		FontUtils.changeFonts(view);
		
		Button btnNovaConfiguracao = (Button) view.findViewById(R.id.btnNovaConfiguracao);

		btnNovaConfiguracao.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Ir para o cadastro
				FragmentUtils.gotoFragment(new ConfigViewFragment(), getFragmentManager());
			}
		});
		
		btnNovaConfiguracao.setTypeface(FontUtils.getFontDefault(view));

		Button btnVoltarLogin = (Button) view.findViewById(R.id.btnVoltarLogin);

		btnVoltarLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Voltar para o login
				FragmentUtils.gotoFragment(new LoginFragment(), getFragmentManager());
			}

		});
		
		btnVoltarLogin.setTypeface(FontUtils.getFontDefault(view));

		// Lista

		ConfigBe be = new ConfigBe(getActivity());
		List<ConfigVo> list = be.getList();

		final ListView listConfig = (ListView) view.findViewById(R.id.listConfig);

		listConfig.setAdapter(new ConfigAdapter(getActivity(), list));

		listConfig.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
				Object o = listConfig.getItemAtPosition(position);

				ConfigVo config = (ConfigVo) o;
				ConfigViewFragment fragment = new ConfigViewFragment();
				fragment.setConfig(config);

				FragmentUtils.gotoFragment(fragment, getFragmentManager());
			}
		});
		
//		listConfig.setOnDragListener(l);

		return view;
	}

}
