package br.com.orlandoburli.sinteli.model.be.services;

import br.com.orlandoburli.sinteli.model.vo.ConfigVo;
import br.com.orlandoburli.sinteli.utils.cripto.Cripto;
import br.com.orlandoburli.sinteli.utils.cripto.exceptions.CriptoException;
import android.content.Context;

public class LogAcessoService extends BaseHttpService2 {

	private ConfigVo config;
	private Integer idUltimoAcesso;

	public LogAcessoService(Context context, ConfigVo config, Integer idUltimoAcesso) {
		super(context);
		this.idUltimoAcesso = idUltimoAcesso;
		this.setConfig(config);
	}

	@Override
	public String getUrlString() {
		String url = getConfig().getHost();

		if (!url.endsWith("/")) {
			url += "/";
		}

		url += "rest/pgetlistaacesso";

		return url;
	}

	@Override
	public String getDados() {

		String dados = null;
		
		try {
			dados = "{\"cUsuLogin\": \"" + Cripto.cripto(config.getChaveCripto(), config.getUsuario()) + "\", \"cUsuSenha\":\"" + Cripto.cripto(config.getChaveCripto(), config.getSenha()) + "\", \"cAcessoCod\":\"" + Cripto.cripto(config.getChaveCripto(), idUltimoAcesso.toString()) + "\"}";
		} catch (CriptoException e) {
			e.printStackTrace();
		}

		return dados;
	}

	public void inserir() {

	}

	public ConfigVo getConfig() {
		return config;
	}

	public void setConfig(ConfigVo config) {
		this.config = config;
	}

}
