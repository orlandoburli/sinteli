package br.com.sinteli.biometria.applet;

import java.awt.BorderLayout;

import br.com.sinteli.panel.RegisterPanel;

public class Register extends CApplet {
	private static final long serialVersionUID = 1L;

	public void init() {
		super.init();
		RegisterPanel panel = new RegisterPanel(this);
		panel.setId(getParameter("id"));
		panel.setUrl(getParameter("url"));
		panel.setUrlSub(getParameter("urlSub"));
		panel.setLiguagem(getParameter("liguagem"));
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(panel, "Center");
	}
}