package br.com.orlandoburli.sinteli;

import java.util.List;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import br.com.orlandoburli.sinteli.model.be.ConfigBe;
import br.com.orlandoburli.sinteli.model.be.services.RetornoLoginService;
import br.com.orlandoburli.sinteli.model.vo.ConfigVo;
import br.com.orlandoburli.sinteli.utils.FontUtils;
import br.com.orlandoburli.sinteli.utils.FragmentUtils;
import br.com.orlandoburli.sinteli.view.adapters.CustomSpinnerAdapter;

public class LoginFragment extends Fragment {

	private List<ConfigVo> listConfig;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.fragment_main, null);

		FontUtils.changeFonts(view);

		Button btnEntrar = (Button) view.findViewById(R.id.btnEntrar);

		btnEntrar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				try {

					Spinner spinnerConfig = (Spinner) view.findViewById(R.id.spinnerConfig);

					final ConfigVo config = (ConfigVo) spinnerConfig.getSelectedItem();

					if (config == null) {
						Toast.makeText(getActivity(), "Nenhum condomínio selecionado!", Toast.LENGTH_LONG).show();
						return;
					}

					final ProgressDialog pdia = new ProgressDialog(v.getContext());
					pdia.setMessage("Entrando, aguarde...");
					pdia.show();

					new ConfigBe(getActivity()).login(config, new RetornoLoginService() {

						@Override
						public void onRetornoLogin(boolean isLogin) {
							pdia.dismiss();

							if (isLogin) {
								Toast.makeText(getActivity(), "Login efetuado com sucesso!", Toast.LENGTH_LONG).show();
								FragmentUtils.gotoFragment(new HomeFragment(config), getFragmentManager());
							} else {
								Toast.makeText(getActivity(), "Configuração inválida, verifique o cadastro do condomínio!", Toast.LENGTH_LONG).show();
							}
						}
					});

				} finally {
					//
				}
			}
		});

		Button btnConfig = (Button) view.findViewById(R.id.btnConfiguracoes);

		btnConfig.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentUtils.gotoFragment(new ConfigListFragment(), getFragmentManager());
			}
		});

		Button btnSairGeral = (Button) view.findViewById(R.id.btnSairGeral);

		btnSairGeral.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().finish();
			}
		});

		// Carregar spinner de Config's

		Spinner spinnerConfig = (Spinner) view.findViewById(R.id.spinnerConfig);

		listConfig = new ConfigBe(getActivity()).getList();

		SpinnerAdapter adapter = new CustomSpinnerAdapter<ConfigVo>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listConfig);

		spinnerConfig.setAdapter(adapter);

		return view;
	}
}
