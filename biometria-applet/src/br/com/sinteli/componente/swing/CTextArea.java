// Decompiled by DJ v3.4.4.74 Copyright 2003 Atanas Neshkov  Date: 22/07/2008 15:50:02
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CTextArea.java

package br.com.sinteli.componente.swing;

import br.com.sinteli.componente.Util;

import javax.swing.JTextArea;

public class CTextArea extends JTextArea
{

    public CTextArea()
    {
        init();
    }

    private void init()
    {
        setFont(Util.getFont());
    }

    private static final long serialVersionUID = 1L;
}