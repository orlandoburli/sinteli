// Decompiled by DJ v3.4.4.74 Copyright 2003 Atanas Neshkov  Date: 22/07/2008 15:50:30
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CTextField.java

package br.com.sinteli.componente.swing;

import br.com.sinteli.componente.Util;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class CTextField extends JTextField
{

    public CTextField()
    {
        init();
    }

    public CTextField(Document arg0, String arg1, int arg2)
    {
        super(arg0, arg1, arg2);
        init();
    }

    public CTextField(int arg0)
    {
        super(arg0);
        init();
    }

    public CTextField(String arg0, int arg1)
    {
        super(arg0, arg1);
        init();
    }

    public CTextField(String arg0)
    {
        super(arg0);
        init();
    }

    private void init()
    {
        setFont(Util.getFont());
    }

    private static final long serialVersionUID = 1L;
}