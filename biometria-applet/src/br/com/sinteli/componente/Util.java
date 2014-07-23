package br.com.sinteli.componente;

import java.awt.Font;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import br.com.sinteli.componente.Util;

public class Util {
    private static Font font = new Font("Tahoma", 0, 12);
    private static Context context = null;

    public Util() {
    }

    public static void setFont(Font font) {
        Util.font = font;
    }

    public static Font getFont() {
        return font;
    }

    public static void createContext(String url) throws Exception {
        if(context != null) {
            context = null;
        }
        
        Hashtable<String, String> env = new Hashtable<String, String>();
        env.put("java.naming.factory.initial", "com.evermind.server.rmi.RMIInitialContextFactory");
        env.put("java.naming.security.principal", "oc4jadmin");
        env.put("java.naming.security.credentials", "hitokiri");
        env.put("java.naming.provider.url", url);
        context = new InitialContext(env);
    }

    public static Object getRemoteObject(String objectName) throws Exception {
        Object obj = null;
        
        if(context != null) {
            obj = context.lookup(objectName);
        }
        
        return obj;
    }
}