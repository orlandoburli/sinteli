package br.com.orlandoburli.sinteli.model.vo;

import java.io.Serializable;

public abstract class BaseVo implements Serializable {

	private static final long serialVersionUID = 1L;

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