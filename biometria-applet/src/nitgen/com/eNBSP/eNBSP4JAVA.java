// Decompiled by DJ v3.4.4.74 Copyright 2003 Atanas Neshkov  Date: 22/07/2008 16:03:26
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   eNBSP4JAVA.java

package nitgen.com.eNBSP;


public class eNBSP4JAVA {
    public static int eNBSP4JAVAERROR_NONE = 0;
    public static int eNBSP4JAVAERROR_BASE;
    public static int eNBSP4JAVAERROR_MEMORY_ALLOC_FAIL;
    public static int eNBSP4JAVAERROR_GETFIELDID;
    public static int eNBSP4JAVAERROR_INVALID_PARAM;
    public static int eNBSP4JAVAERROR_INVALID_HANDLE;
    public static int eNBSP4JAVAERROR_GETSTRINGUTFCHARS;
    private String strEncodedFIR;
    private boolean bResult;

    static {
        eNBSP4JAVAERROR_BASE = 1536;
        eNBSP4JAVAERROR_MEMORY_ALLOC_FAIL = eNBSP4JAVAERROR_BASE + 1;
        eNBSP4JAVAERROR_GETFIELDID = eNBSP4JAVAERROR_BASE + 2;
        eNBSP4JAVAERROR_INVALID_PARAM = eNBSP4JAVAERROR_BASE + 3;
        eNBSP4JAVAERROR_INVALID_HANDLE = eNBSP4JAVAERROR_BASE + 4;
        eNBSP4JAVAERROR_GETSTRINGUTFCHARS = eNBSP4JAVAERROR_BASE + 5;
        
        try {
            System.loadLibrary("eNBSP4JAVA");
        } catch(Exception e) {
            System.out.println("Biblioteca 'eNBSP4JAVA' n\343o encontrada!");
        }
    }
    
    public native int NBioAPI_VerifyMatch(int i, String s, String s1);
    public native int NBioAPI_Verify(int i, String s);
    public native int NBioAPI_Init();
    public native int NBioAPI_OpenDevice(int i);
    public native int NBioAPI_CloseDevice(int i);
    public native int NBioAPI_Enroll(int i);
    public native int NBioAPI_Capture(int i);
    public native int NBioAPI_Terminate(int i);
    public native int testBSP();

    public eNBSP4JAVA() {
        strEncodedFIR = "";
        bResult = false;
    }

    public String getStrEncodedFIR() {
        return strEncodedFIR;
    }

    public void setStrEncodedFIR(String fir) {
        strEncodedFIR = fir;
    }

    public boolean getResult() {
        return bResult;
    }

    public void setResult(boolean result) {
        bResult = result;
    }
}