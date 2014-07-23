// Decompiled by DJ v3.4.4.74 Copyright 2003 Atanas Neshkov  Date: 22/07/2008 15:49:17
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CLabel.java

package br.com.sinteli.componente.swing;

import br.com.sinteli.componente.Util;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JLabel;

public class CLabel extends JLabel
{

    public CLabel()
    {
        init();
    }

    public CLabel(Icon image, int horizontalAlignment)
    {
        super(image, horizontalAlignment);
        init();
    }

    public CLabel(Icon image)
    {
        super(image);
        init();
    }

    public CLabel(String text, Icon image, int horizontalAlignment)
    {
        super(text, image, horizontalAlignment);
        init();
    }

    public CLabel(String text, int horizontalAlignment)
    {
        super(text, horizontalAlignment);
        init();
    }

    public CLabel(String text)
    {
        super(text);
        init();
    }

    public CLabel(Component referenced, int key, String label)
    {
        init();
        setDisplayedMnemonic(key);
        setLabelFor(referenced);
        setText(label);
    }

    private void init()
    {
        setFont(Util.getFont());
    }

    private static final long serialVersionUID = 1L;
}