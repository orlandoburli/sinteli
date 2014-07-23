package br.com.sinteli.biometria.applet;

import java.awt.BorderLayout;

import br.com.sinteli.panel.SearchPanel;

public class Search extends CApplet {

	private static final long serialVersionUID = 1L;

	public void init() {
		super.init();

		System.out.println("Versao 1.0.0.8");
		
		SearchPanel p = new SearchPanel(this);
		p.setUrl(getParameter("url"));
		p.setUrlSub(getParameter("urlSub"));
		// p.setLiguagem(getParameter("liguagem"));
		p.setUrlDados(getParameter("dados"));
		p.setChaveCripto(getParameter("chave"));
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(p, "Center");
		
		try {
			p.checkFinger(getCard());
		} catch (Exception e) {
			e.printStackTrace();
		}
		p.setVisible(false);

	}

}
