// Decompiled by DJ v3.4.4.74 Copyright 2003 Atanas Neshkov  Date: 22/07/2008 15:49:01
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   CFrame.java

package br.com.sinteli.componente.swing;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import javax.swing.JFrame;

public class CFrame extends JFrame
{

    public CFrame()
        throws HeadlessException
    {
        init();
    }

    public CFrame(GraphicsConfiguration gc)
    {
        super(gc);
        init();
    }

    public CFrame(String title, GraphicsConfiguration gc)
    {
        super(title, gc);
        init();
    }

    public CFrame(String title)
        throws HeadlessException
    {
        super(title);
        init();
    }

    private void init()
    {
        setDefaultCloseOperation(3);
    }

    private static final long serialVersionUID = 1L;
}