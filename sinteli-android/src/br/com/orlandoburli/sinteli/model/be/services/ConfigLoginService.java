package br.com.orlandoburli.sinteli.model.be.services;

import android.content.Context;
import br.com.orlandoburli.sinteli.model.be.ConfigBe;
import br.com.orlandoburli.sinteli.model.vo.ConfigVo;
import br.com.orlandoburli.sinteli.utils.cripto.Cripto;
import br.com.orlandoburli.sinteli.utils.cripto.exceptions.CriptoException;

public class ConfigLoginService extends BaseHttpService {

	private ConfigVo config;
	private ConfigBe be;

	public ConfigLoginService(ConfigVo config, ConfigBe be, Context context) {
		super(context);
		this.be = be;
		this.setConfig(config);
	}

	@Override
	public String getUrlString() {
		// Configuração da url
		String url = getConfig().getHost();

		if (!url.endsWith("/")) {
			url += "/";
		}

		url += "rest/pgetusuario";

		return url;
	}

	@Override
	public String getDados() {
		String dados = null;

		try {
			dados = "{\"UsuLogin\": \"" + Cripto.cripto(config.getChaveCripto(), config.getUsuario()) + "\", \"UsuSenha\":\"" + Cripto.cripto(config.getChaveCripto(), config.getSenha()) + "\"}";
		} catch (CriptoException e) {
			e.printStackTrace();
		}

		return dados;
	}

	public ConfigVo getConfig() {
		return config;
	}

	public void setConfig(ConfigVo config) {
		this.config = config;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		be.retornoLogin(result);
	}
}
