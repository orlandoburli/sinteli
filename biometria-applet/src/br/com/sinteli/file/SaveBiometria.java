// Decompiled by DJ v3.4.4.74 Copyright 2003 Atanas Neshkov  Date: 22/07/2008 15:51:12
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SaveBiometria.java

package br.com.sinteli.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class SaveBiometria implements Serializable {
    private static final long serialVersionUID = 1L;
    private String fileName;

    public SaveBiometria(String id) {
        fileName = (new StringBuilder(String.valueOf(id))).append(".bio").toString();
    }

    public String save(String code) throws IOException {
        fileName = (new StringBuilder(String.valueOf(System.getProperties().getProperty("java.io.tmpdir")))).append(fileName).toString();
        File f = new File(fileName);
        FileOutputStream out = new FileOutputStream(f);
        
        out.write(code.getBytes());
        out.close();
        
        return fileName;
    }

    public String getFileName() {
        return fileName;
    }
}