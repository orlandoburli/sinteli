package br.com.orlandoburli.sinteli.model.vo;

public abstract class BaseVo {

	private boolean isNew;

	public BaseVo() {
		setNew(true);
	}

	public boolean isNew() {
		return isNew;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

}