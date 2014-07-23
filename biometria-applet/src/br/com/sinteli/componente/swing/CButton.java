// Decompiled by DJ v3.4.4.74 Copyright 2003 Atanas Neshkov  Date: 22/07/2008 15:48:44
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CButton.java

package br.com.sinteli.componente.swing;

import br.com.sinteli.componente.Util;

import javax.swing.*;

public class CButton extends JButton
{

    public CButton()
    {
        init();
    }

    public CButton(Action action)
    {
        super(action);
        init();
    }

    public CButton(Icon icon)
    {
        super(icon);
        init();
    }

    public CButton(String text, Icon icon)
    {
        super(text, icon);
        init();
    }

    public CButton(String text)
    {
        super(text);
        init();
    }

    private void init()
    {
        setFont(Util.getFont());
    }

    private static final long serialVersionUID = 1L;
}