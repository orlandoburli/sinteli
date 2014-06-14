package br.com.orlandoburli.sinteli;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;
import br.com.orlandoburli.sintel.R;
import br.com.orlandoburli.sinteli.model.be.ConfigBe;
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

					ConfigVo config = (ConfigVo) spinnerConfig.getSelectedItem();

					if (config == null) {

						AlertDialog dialog = new AlertDialog.Builder(view.getContext()).create();
						dialog.setTitle("Informação");
						dialog.setMessage("Nenhum condomínio selecionado!");
						dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.cancel();
							}
						});
						dialog.show();
						return;
					}

					boolean login = new ConfigBe(getActivity()).login(config);

					if (login) {
						Toast.makeText(getActivity(), "Login efetuado com sucesso!", Toast.LENGTH_LONG).show();
						FragmentUtils.gotoFragment(new HomeFragment(), getFragmentManager());
					} else {
						Toast.makeText(getActivity(), "Configuração inválida, verifique o cadastro do condomínio!", Toast.LENGTH_LONG).show();
					}
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
