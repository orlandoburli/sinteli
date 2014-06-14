package br.com.orlandoburli.sinteli.model.vo;

public class ConfigVo extends BaseVo {

	private Integer id;
	private String nome;
	private String host;
	private String usuario;
	private String senha;
	private boolean padrao;
	private String chaveCripto;
	
	@Override
	public String toString() {
		return getNome();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isPadrao() {
		return padrao;
	}

	public void setPadrao(boolean padrao) {
		this.padrao = padrao;
	}

	public String getChaveCripto() {
		return chaveCripto;
	}

	public void setChaveCripto(String chaveCripto) {
		this.chaveCripto = chaveCripto;
	}
}
