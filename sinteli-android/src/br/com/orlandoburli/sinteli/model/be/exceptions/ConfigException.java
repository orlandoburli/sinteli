package br.com.orlandoburli.sinteli.model.be.exceptions;

public class ConfigException extends Exception {

	private static final long serialVersionUID = 1L;
	private String field;

	public ConfigException(String message, String field) {
		super(message);
		this.setField(field);
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
