package br.com.sinteli.biometria.applet;

import java.awt.BorderLayout;

import br.com.sinteli.panel.VerifyPanel;

public class VerifyFinger extends CApplet {
	private static final long serialVersionUID = 1L;

	public void init() {
		super.init();
		VerifyPanel p = new VerifyPanel(this);
		p.setUrl(getParameter("url"));
		p.setUrlSub(getParameter("urlSub"));
		p.setLiguagem(getParameter("liguagem"));
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(p, "Center");
		p.checkFinger(getCard());
	}
}