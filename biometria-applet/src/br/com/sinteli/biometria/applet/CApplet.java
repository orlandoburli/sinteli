// Decompiled by DJ v3.4.4.74 Copyright 2003 Atanas Neshkov  Date: 22/07/2008 15:46:20
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CApplet.java

package br.com.sinteli.biometria.applet;

import java.net.URL;

import javax.swing.JApplet;

public class CApplet extends JApplet {
	private static final long serialVersionUID = 1L;
	private String success;
	private String fail;
	private String card;

	public void init() {
		success = getParameter("success");
		fail = getParameter("fail");
		card = getParameter("card");
	}

	public String getCard() {
		return card;
	}

	public void redirect(boolean result, String card) {
		try {
			if (card != null) {
				success = (new StringBuilder(String.valueOf(success))).append(card).toString();
			}

			URL dest = new URL(result ? success : fail);

			System.out.println("Redirecionando para " + dest);
			
			if ((success != "" && result) || (fail != "" && !result))
				getAppletContext().showDocument(dest);

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
}