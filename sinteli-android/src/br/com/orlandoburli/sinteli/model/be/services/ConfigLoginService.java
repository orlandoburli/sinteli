package br.com.orlandoburli.sinteli.model.be.services;

import android.content.Context;
import br.com.orlandoburli.sintel.utils.cripto.Cripto;
import br.com.orlandoburli.sintel.utils.cripto.exceptions.CriptoException;
import br.com.orlandoburli.sinteli.model.vo.ConfigVo;

public class ConfigLoginService extends BaseHttpService {

	private ConfigVo config;

	public ConfigLoginService(ConfigVo config, Context context) {
		super(context);
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
		} catch (CriptoException e1) {
			e1.printStackTrace();
		}

		return dados;
	}

	public ConfigVo getConfig() {
		return config;
	}

	public void setConfig(ConfigVo config) {
		this.config = config;
	}
}
