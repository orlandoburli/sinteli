// Decompiled by DJ v3.4.4.74 Copyright 2003 Atanas Neshkov  Date: 22/07/2008 15:52:24
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   VerifyPanel.java

package br.com.sinteli.panel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JScrollPane;

import nitgen.com.eNBSP.eNBSP4JAVA;
import HTTPClient.HTTPConnection;
import HTTPClient.HTTPResponse;
import HTTPClient.NVPair;
import HTTPClient.URI;
import br.com.sinteli.biometria.applet.CApplet;
import br.com.sinteli.componente.swing.CButton;
import br.com.sinteli.componente.swing.CLabel;
import br.com.sinteli.componente.swing.CPanel;
import br.com.sinteli.componente.swing.CTextArea;
import br.com.sinteli.componente.swing.CTextField;

public class VerifyPanel extends CPanel implements ActionListener, KeyListener {
    private static final long serialVersionUID = 1L;
    private CLabel cardLabel;
    private CTextField card;
    private CTextArea area;
    private JScrollPane pane;
    private CButton verify;
    private CButton close;
    private CPanel center;
    private CPanel botton;
    private int NBioAPI_HANDLE;
    private eNBSP4JAVA NBioAPI;
    private String id;
    private String url;
    private String urlSub;
    private CApplet applet;
    private String liguagem;

    
    public String getLiguagem() {
		return liguagem;
	}

	public void setLiguagem(String liguagem) {
		this.liguagem = liguagem;
	}

	public VerifyPanel() {
        init();
    }

    public VerifyPanel(CApplet applet) {
        this.applet = applet;
        init();
    }

    private void init() {
        id = "1";
        NBioAPI = new eNBSP4JAVA();
        card = new CTextField();   
        card.setText(applet.getCard());
        
        cardLabel = new CLabel(card, 67, "C�digo");
        area = new CTextArea();
        area.setEditable(false);
        pane = new JScrollPane(area);
        verify = new CButton("Verificar");
        close = new CButton("Fechar");
        center = new CPanel();  
        botton = new CPanel();
        verify.setMnemonic(86);
        verify.addActionListener(this);
        close.setMnemonic(70);
        //close.addActionListener(this);
        card.addKeyListener(this);

        center.setLayout(null);
        botton.setLayout(new FlowLayout());
        setLayout(new BorderLayout());
        add(center, "Center");
        add(botton, "South");
        cardLabel.setBounds(10, 10, 300, 25);
        card.setBounds(10, 30, 300, 25);
        pane.setBounds(10, 60, 300, 200);
        center.add(cardLabel);
        center.add(card);
        center.add(pane);
        botton.add(verify);
        //botton.add(close);
        card.setEnabled(false);

    }

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url; 
    }    

    private String sendRequest() throws Exception {
    	
//    	setId("1");
//    	setUrlSub("http://localhost:8080/TesteUserControl/servlet/apgetbioforjavacontrol");
    	
    	
    	if(liguagem.equals("java"))
    		return getResultJava();
    	else
    		return getResultCSharp();
    	
    	
  
    }
    
    public boolean checkFinger(String card) {
        String cardNumber;
        boolean b;
        cardNumber = card.trim();
        b = false;
        
        if(cardNumber.length() > 0) {
            try {
                String s = sendRequest();
                
                if (s.startsWith("Erro:")) {
                    area.setText(s);
                } else {
                    try {
                        NBioAPI_HANDLE = NBioAPI.NBioAPI_Init();
                        NBioAPI.NBioAPI_OpenDevice(NBioAPI_HANDLE);
                        NBioAPI.NBioAPI_Capture(NBioAPI_HANDLE);
                        NBioAPI.NBioAPI_CloseDevice(NBioAPI_HANDLE);
                        String finger = NBioAPI.getStrEncodedFIR();
                        NBioAPI.NBioAPI_VerifyMatch(NBioAPI_HANDLE, s, finger);
                        b = NBioAPI.getResult();
                        NBioAPI.NBioAPI_Terminate(NBioAPI_HANDLE);
                        
                        if (b) {
                            area.setText("Correto!");
                        } else {
                            area.setText("Invalido!");
                        }
                    } catch(Exception e) {
                    	throw e;
                    }
                }
            } catch(Exception e) {
                java.io.Writer w = new StringWriter();
                PrintWriter out = new PrintWriter(w);
                e.printStackTrace(out);
                area.setText(w.toString());
            }
            
            applet.redirect(b, null);

        }
        
        return b;
    }

    public void actionPerformed(ActionEvent event) {
        if(event.getSource().equals(verify)) {
            checkFinger(card.getText());
        } else {
            if(event.getSource().equals(close) && applet != null) {
                applet.redirect(false, "");
            }
        }
    }

    public void keyPressed(KeyEvent event) {
        if(event.getSource().equals(card) && event.getKeyCode() == 10) {
            checkFinger(card.getText());
        }
    }

    public void keyReleased(KeyEvent keyevent) {
    }

    public void keyTyped(KeyEvent keyevent) {
    }

	public String getUrlSub() {
		return urlSub;
	}

	public void setUrlSub(String urlSub) {
		this.urlSub = urlSub;
	}
	
	public String getResultJava() throws Exception {
		
     	StringBuffer message = new StringBuffer(); 
    	String dado = "";
		try {

	        URL docUrl = new URL(urlSub.trim());

			HttpURLConnection conn = (HttpURLConnection)  docUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "text/html");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			
			
			id = card.getText();
			conn.setRequestProperty("code", card.getText());
			conn.setRequestProperty("id", id);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			while (null != ((dado = br.readLine()))) {
				message.append(dado);
			}
			br.close();

			//System.out.println(message.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return message.toString();
    	
		
	}
	public String getResultCSharp() throws Exception {
		
////	setUrl("http://sigeduca.02.des.seduc.mt.gov.br/ged/biometria/html/verify.html");
////	setUrlSub("http://sigeduca.02.des.seduc.mt.gov.br/ged/apbioverify.aspx");
		
		    URI docUrl = new URI(url);
		    URI formUrl = new URI(docUrl, urlSub);
		    
		    id = card.getText();
		    HTTPConnection conn = new HTTPConnection(formUrl);
		    NVPair formData[] = {
		        new NVPair("id", id), new NVPair("code", card.getText())
		    };
    
	        
	        HTTPResponse response = conn.Post(formUrl.getPathAndQuery(), formData);   
	        
	        InputStreamReader reader = new InputStreamReader(response.getInputStream());
	        BufferedReader br = new BufferedReader(reader);
	        StringBuilder message = new StringBuilder();
	        
	        for(String line = null; (line = br.readLine()) != null;) {
	            message.append(line);
	        }
	        
	        br.close();
	        reader.close();
	        return message.toString();
	}

}

//private HTTPResponse sendRequest() throws Exception {
//	
////	setUrl("http://sigeduca.02.des.seduc.mt.gov.br/ged/biometria/html/verify.html");
////	setUrlSub("http://sigeduca.02.des.seduc.mt.gov.br/ged/apbioverify.aspx");
//	
////    URI docUrl = new URI(url); 
////    URI formUrl = new URI(docUrl, "http://localhost/ged/apbioverify.aspx");
//		    	
//    URI docUrl = new URI(url);
//    URI formUrl = new URI(docUrl, urlSub);
//    
//    id = card.getText();
//    HTTPConnection conn = new HTTPConnection(formUrl);
//    NVPair formData[] = {
//        new NVPair("id", id), new NVPair("code", card.getText())
//    };
//    
//    return conn.Post(formUrl.getPathAndQuery(), formData);
//}

//private String getResult(HTTPResponse response) throws Exception {
//    InputStreamReader reader = new InputStreamReader(response.getInputStream());
//    BufferedReader br = new BufferedReader(reader);
//    StringBuilder message = new StringBuilder();
//    
//    for(String line = null; (line = br.readLine()) != null;) {
//        message.append(line);
//    }
//
//    br.close();
//    reader.close();
//    return message.toString();
//}
