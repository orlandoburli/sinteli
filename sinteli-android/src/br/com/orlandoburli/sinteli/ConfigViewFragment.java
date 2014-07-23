package br.com.orlandoburli.sinteli;

import br.com.orlandoburli.sinteli.R;
import br.com.orlandoburli.sinteli.model.be.ConfigBe;
import br.com.orlandoburli.sinteli.model.be.exceptions.ConfigException;
import br.com.orlandoburli.sinteli.model.dao.Dicionario;
import br.com.orlandoburli.sinteli.model.vo.ConfigVo;
import br.com.orlandoburli.sinteli.utils.FontUtils;
import br.com.orlandoburli.sinteli.utils.FragmentUtils;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ConfigViewFragment extends Fragment {

	private ConfigVo config;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		final View view = inflater.inflate(R.layout.config_view, null);

		FontUtils.changeFonts(view);

		// Botao Salvar

		Button btnSalvarConfig = (Button) view.findViewById(R.id.btnSalvarConfig);

		btnSalvarConfig.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//
				EditText editEmailConfig = (EditText) view.findViewById(R.id.editEmailConfig);
				EditText editNomeConfig = (EditText) view.findViewById(R.id.editNomeConfig);
				EditText editHostConfig = (EditText) view.findViewById(R.id.editHostConfig);
				EditText editSenhaConfig = (EditText) view.findViewById(R.id.editSenhaConfig);
				EditText editChaveCriptoConfig = (EditText) view.findViewById(R.id.editChaveCriptoConfig);
				ToggleButton togglePadrao = (ToggleButton) view.findViewById(R.id.togglePadrao);

				ConfigBe be = new ConfigBe(getActivity());

				if (config == null) {
					config = new ConfigVo();
				}

				config.setUsuario(editEmailConfig.getText().toString().trim());
				config.setSenha(editSenhaConfig.getText().toString().trim());
				config.setHost(editHostConfig.getText().toString().trim());
				config.setNome(editNomeConfig.getText().toString().trim());
				config.setChaveCripto(editChaveCriptoConfig.getText().toString().trim());
				config.setPadrao(togglePadrao.isChecked());

				try {
					be.save(config);
				} catch (ConfigException e) {
					Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();

					if (e.getField().equals(Dicionario.Config.Colunas.NOME)) {
						editNomeConfig.requestFocus();
					} else if (e.getField().equals(Dicionario.Config.Colunas.HOST)) {
						editHostConfig.requestFocus();
					} else if (e.getField().equals(Dicionario.Config.Colunas.USUARIO)) {
						editEmailConfig.requestFocus();
					} else if (e.getField().equals(Dicionario.Config.Colunas.SENHA)) {
						editSenhaConfig.requestFocus();
					} else if (e.getField().equals(Dicionario.Config.Colunas.CHAVE_CRIPTO)) {
						editChaveCriptoConfig.requestFocus();
					}
					return;
				}

				// Volta para lista
				Toast.makeText(getActivity(), "Condomínio salvo com sucesso!", Toast.LENGTH_LONG).show();

				FragmentUtils.gotoFragment(new ConfigListFragment(), getFragmentManager());
			}
		});

		// Botao Voltar

		Button btnVoltarConfig = (Button) view.findViewById(R.id.btnVoltarConfig);

		btnVoltarConfig.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Voltar para a lista de config
				FragmentUtils.gotoFragment(new ConfigListFragment(), getFragmentManager());
			}
		});

		// Botao Excluir

		Button btnExcluirConfig = (Button) view.findViewById(R.id.btnExcluirConfig);

		btnExcluirConfig.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Excluir o registro

				if (config != null) {
					ConfigBe be = new ConfigBe(getActivity());
					try {
						be.excluir(config);
					} catch (ConfigException e) {
						Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
						return;
					}
				}

				// Voltar para a lista de config
				FragmentUtils.gotoFragment(new ConfigListFragment(), getFragmentManager());
			}
		});

		btnExcluirConfig.setTypeface(FontUtils.getFontDefault(view));
		btnSalvarConfig.setTypeface(FontUtils.getFontDefault(view));
		btnVoltarConfig.setTypeface(FontUtils.getFontDefault(view));

		// Load

		if (config != null) {
			EditText editEmailConfig = (EditText) view.findViewById(R.id.editEmailConfig);
			EditText editNomeConfig = (EditText) view.findViewById(R.id.editNomeConfig);
			EditText editHostConfig = (EditText) view.findViewById(R.id.editHostConfig);
			EditText editSenhaConfig = (EditText) view.findViewById(R.id.editSenhaConfig);
			EditText editChaveCriptoConfig = (EditText) view.findViewById(R.id.editChaveCriptoConfig);

			ToggleButton togglePadrao = (ToggleButton) view.findViewById(R.id.togglePadrao);

			editEmailConfig.setText(config.getUsuario());
			editNomeConfig.setText(config.getNome());
			editHostConfig.setText(config.getHost());
			editSenhaConfig.setText(config.getSenha());
			editChaveCriptoConfig.setText(config.getChaveCripto());

			togglePadrao.setChecked(config.isPadrao());
		}

		return view;
	}

	public void setConfig(ConfigVo config) {
		this.config = config;
	}
}
