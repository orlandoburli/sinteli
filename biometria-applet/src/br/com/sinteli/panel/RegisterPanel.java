package br.com.sinteli.panel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JScrollPane;

import nitgen.com.eNBSP.eNBSP4JAVA;
import HTTPClient.Codecs;
import HTTPClient.HTTPConnection;
import HTTPClient.HTTPResponse;
import HTTPClient.NVPair;
import HTTPClient.URI;
import br.com.sinteli.biometria.applet.CApplet;
import br.com.sinteli.componente.swing.CButton;
import br.com.sinteli.componente.swing.CPanel;
import br.com.sinteli.componente.swing.CTextArea;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//import br.com.abaco.file.SaveBiometria;

public class RegisterPanel extends CPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private CButton readBio;
	private CTextArea area;
	private JScrollPane pane;
	private eNBSP4JAVA NBioAPI;
	private int NBioAPI_HANDLE;
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

	public RegisterPanel() {
		init();
	}

	public RegisterPanel(CApplet applet) {
		this.applet = applet;
		init();
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

	private void init() {
		NBioAPI_HANDLE = 0;
		NBioAPI = new eNBSP4JAVA();

		setLayout(null);

		readBio = new CButton("Registrar");
		readBio.setMnemonic(82);
		readBio.setToolTipText("Registrar as impressões digitais.");
		readBio.addActionListener(this);

		area = new CTextArea();
		area.setEditable(false);

		pane = new JScrollPane(area);

		CPanel panel = new CPanel();
		panel.setLayout(new FlowLayout());
		panel.add(readBio);

		setLayout(new BorderLayout());
		add(pane, "Center");
		add(panel, "South");
	}

	public void actionPerformed(ActionEvent event) {
		boolean b;
		area.setText("");
		b = false;

		try {
			NBioAPI_HANDLE = NBioAPI.NBioAPI_Init();
			NBioAPI.NBioAPI_OpenDevice(NBioAPI_HANDLE);
			NBioAPI.NBioAPI_Enroll(NBioAPI_HANDLE);
			NBioAPI.NBioAPI_CloseDevice(NBioAPI_HANDLE);
			NBioAPI.NBioAPI_Terminate(NBioAPI_HANDLE);
			// SaveBiometria bio = new SaveBiometria(id);
			// area.setText(getResult(sendRequest(bio.save(NBioAPI.getStrEncodedFIR()))));
			// sendRequest(NBioAPI.getStrEncodedFIR());
			// area.setText(getResult(sendRequest(NBioAPI.getStrEncodedFIR())));
			area.setText(sendRequest(NBioAPI.getStrEncodedFIR()));
			b = true;
		} catch (Exception e) {
			java.io.Writer w = new StringWriter();
			PrintWriter out = new PrintWriter(w);
			e.printStackTrace(out);
			area.setText(w.toString());
		}
		applet.redirect(b, null);
	}

	private String sendRequest(String fileName) throws Exception {

		// setId("1");
		// setLiguagem("C#");
		// setUrlSub("http://localhost:8080/TesteUserControl/servlet/apsetbioforjavacontrol");
		// setUrl("http://localhost/csi_escolar/biometriacontrol/biometria/html/register.html");
		// setUrlSub("http://localhost/csi_escolar/apsetbiocontrol.aspx");

		if (liguagem.equals("java"))
			return getResultJava(fileName);
		else
			return getResultCSharp(fileName);

	}

	public String getUrlSub() {
		return urlSub;
	}

	public void setUrlSub(String urlSub) {
		this.urlSub = urlSub;
	}

	public String getResultJava(String fileName) throws Exception {

		StringBuffer message = new StringBuffer();
		String dado = "";
		try {

			URL docUrl = new URL(urlSub.trim());

			HttpURLConnection conn = (HttpURLConnection) docUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-type", "text/html");
			conn.setDoInput(true);
			conn.setDoOutput(true);

			conn.setRequestProperty("biometria", fileName);
			conn.setRequestProperty("id", id);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			while (null != ((dado = br.readLine()))) {
				message.append(dado);
			}
			br.close();

			// System.out.println(message.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
			message.append(e.getMessage());
			message.append(e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			message.append(e.getMessage());
			message.append(e.toString());
		}

		return message.toString();

	}

	public String getResultCSharp(String fileName) throws Exception {

		URI docUrl = new URI(url);
		URI formUrl = new URI(docUrl, urlSub);

		HTTPConnection conn = new HTTPConnection(formUrl);
		NVPair opts[] = { new NVPair("id", id), new NVPair("biometria", fileName) };

		NVPair files[] = null;
		NVPair hdrs[] = new NVPair[1];

		byte formData[] = Codecs.mpFormDataEncode(opts, files, hdrs);

		HTTPResponse response = conn.Post(formUrl.getPathAndQuery(), formData, hdrs);
		;

		InputStreamReader reader = new InputStreamReader(response.getInputStream());
		BufferedReader br = new BufferedReader(reader);
		StringBuilder message = new StringBuilder();

		for (String line = null; (line = br.readLine()) != null;) {
			message.append(line);
		}

		br.close();
		reader.close();
		return message.toString();
	}
}

// private String getResult(String response) throws Exception {
//
//
// return response;
// BufferedReader br = new BufferedReader(new
// InputStreamReader(response.getInputStream()));
// StringBuffer message = new StringBuffer();
// String dado = "";
// while (null != ((dado = br.readLine()))) {
// message.append(dado);
// }
// br.close();

// InputStreamReader reader = new InputStreamReader(response.getInputStream());
// BufferedReader br = new BufferedReader(reader);
// StringBuilder message = new StringBuilder();
//
// for(String line = null; (line = br.readLine()) != null;) {
// message.append(line);
// }
//
// br.close();
// reader.close();
// return message.toString();
// }
