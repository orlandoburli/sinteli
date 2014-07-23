// Decompiled by DJ v3.4.4.74 Copyright 2003 Atanas Neshkov  Date: 22/07/2008 15:49:39
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CPanel.java

package br.com.sinteli.componente.swing;

import java.awt.LayoutManager;
import javax.swing.JPanel;

public class CPanel extends JPanel
{

    public CPanel()
    {
    }

    public CPanel(boolean isDoubleBuffered)
    {
        super(isDoubleBuffered);
    }

    public CPanel(LayoutManager layoutManager, boolean isDoubleBuffered)
    {
        super(layoutManager, isDoubleBuffered);
    }

    public CPanel(LayoutManager layoutManager)
    {
        super(layoutManager);
    }

    private static final long serialVersionUID = 1L;
}