package br.com.orlandoburli.sinteli.model.vo;

public class LogAcessoVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	private Integer idAcesso;
	private String dataHora;
	private String porta;
	private String destino;
	private String observacao;
	private String nome;
	private String sexo;
	private String foto;
	
	public Integer getIdAcesso() {
		return idAcesso;
	}
	public void setIdAcesso(Integer idAcesso) {
		this.idAcesso = idAcesso;
	}
	public String getDataHora() {
		return dataHora;
	}
	public void setDataHora(String dataHora) {
		this.dataHora = dataHora;
	}
	public String getPorta() {
		return porta;
	}
	public void setPorta(String porta) {
		this.porta = porta;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
}
