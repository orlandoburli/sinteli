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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JScrollPane;

import HTTPClient.HTTPConnection;
import HTTPClient.HTTPResponse;
import HTTPClient.ModuleException;
import HTTPClient.ParseException;
import HTTPClient.URI;
import br.com.sinteli.biometria.applet.CApplet;
import br.com.sinteli.componente.swing.CButton;
import br.com.sinteli.componente.swing.CLabel;
import br.com.sinteli.componente.swing.CPanel;
import br.com.sinteli.componente.swing.CTextArea;
import br.com.sinteli.componente.swing.CTextField;
import br.com.sinteli.model.RetornoPessoaDigital;
import br.com.sinteli.utils.cripto.Cripto;
import br.com.sinteli.utils.cripto.exceptions.CriptoException;

import com.google.gson.Gson;
import com.nitgen.SDK.BSP.NBioBSPJNI;
import com.nitgen.SDK.BSP.NBioBSPJNI.IndexSearch;

public class SearchPanel extends CPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	private CLabel cardLabel;
	private CTextField card;
	private CTextArea area;
	private JScrollPane pane;
	private CButton verify;
	private CButton close;
	private CPanel center;
	private CPanel botton;
	// private eNBSP4JAVA NBioAPI;
	private NBioBSPJNI bsp;
	private String id;
	private String url;
	private String urlSub;
	private CApplet applet;
	private String liguagem;
	private IndexSearch IndexSearchEngine;
	private String urlDados;
	private String chaveCripto;

	public String getLiguagem() {
		return liguagem;
	}

	public void setLiguagem(String liguagem) {
		this.liguagem = liguagem;
	}

	public SearchPanel() {
		init();
	}

	public SearchPanel(CApplet applet) {
		this.applet = applet;
		init();
	}

	private void init() {
		id = "1";
		this.bsp = new NBioBSPJNI();

		card = new CTextField();
		card.setText(applet.getCard());

		cardLabel = new CLabel(card, 67, "Código");
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
		// close.addActionListener(this);
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

	private String sendRequest() {
		return getResultCSharp();
	}

	public boolean checkFinger(String card) throws CriptoException {
		try {
			System.out.println("Carregando digitais...");

			IndexSearchEngine = bsp.new IndexSearch();

			String s = sendRequest();

			System.out.println("Retorno: " + s);

			if (s.startsWith("Erro:")) {
				area.setText(s);
			} else {

				if (CheckError())
					return false;

				// Carregar digitais
				Gson gson = new Gson();
				RetornoPessoaDigital[] lista = gson.fromJson(s, RetornoPessoaDigital[].class);

				for (RetornoPessoaDigital item : lista) {

					// Descriptografa os dados

					item.setPesCod(Cripto.decripto(chaveCripto, item.getPesCod()));
					item.setPesDigitalCod(Cripto.decripto(chaveCripto, item.getPesDigitalCod()));
					item.setPesDigitalString(Cripto.decripto(chaveCripto, item.getPesDigitalString()));

					// Adicionar a lista do dispositivo

					NBioBSPJNI.FIR_TEXTENCODE digital = bsp.new FIR_TEXTENCODE();

					digital.TextFIR = item.getPesDigitalString();

					int nUserID = Integer.parseInt(item.getPesCod().trim());

					NBioBSPJNI.INPUT_FIR inputFIR = bsp.new INPUT_FIR();
					NBioBSPJNI.IndexSearch.SAMPLE_INFO sampleInfo = IndexSearchEngine.new SAMPLE_INFO();

					inputFIR.SetTextFIR(digital);

					IndexSearchEngine.AddFIR(inputFIR, nUserID, sampleInfo);

				}

				System.out.println("Digitais carregadas.");

				search();

			}
		} finally {
			bsp.CloseDevice();
		}

		return true;
	}

	public void search() {
		// Busca
		System.out.println("Iniciando search, aguardando leitura.");

		bsp.OpenDevice();

		NBioBSPJNI.WINDOW_OPTION winOption;
		winOption = bsp.new WINDOW_OPTION();

		winOption.WindowStyle = NBioBSPJNI.WINDOW_STYLE.INVISIBLE;

		int nMaxSearchTime = 5000;

		NBioBSPJNI.FIR_HANDLE hCapturedFIR = bsp.new FIR_HANDLE();

		bsp.Capture(0, hCapturedFIR, nMaxSearchTime, null, winOption);

		if (CheckError()) {
			if (bsp.GetErrorCode() == NBioBSPJNI.ERROR.NBioAPIERROR_INDEXSEARCH_IDENTIFY_FAIL) {
				String[] szFinds = new String[1];
				szFinds[0] = "Can not find user";
				System.out.println("Não encontrado.");
			} else if (bsp.GetErrorCode() == NBioBSPJNI.ERROR.NBioAPIERROR_INDEXSEARCH_IDENTIFY_STOP || bsp.GetErrorCode() == NBioBSPJNI.ERROR.NBioAPIERROR_CAPTURE_FAKE_SUSPICIOUS) {
				String[] szFinds = new String[1];
				szFinds[0] = "Time out";
				System.out.println("Time Out.");
				search();
			} else if (bsp.GetErrorCode() == 516) {
				System.out.println("516???");
				search();
			}
		}

		// if (CheckError())
		// return;

		NBioBSPJNI.INPUT_FIR inputFIR = bsp.new INPUT_FIR();
		inputFIR.SetFIRHandle(hCapturedFIR);

		NBioBSPJNI.IndexSearch.FP_INFO fpInfo = IndexSearchEngine.new FP_INFO();

		IndexSearchEngine.Identify(inputFIR, 5, fpInfo, nMaxSearchTime);

		System.out.println("Teste 1");

		if (CheckError()) {
			System.out.println("Error");

			if (bsp.GetErrorCode() == NBioBSPJNI.ERROR.NBioAPIERROR_INDEXSEARCH_IDENTIFY_FAIL) {
				String[] szFinds = new String[1];
				szFinds[0] = "Can not find user";
				System.out.println("Não encontrado.");
				applet.redirect(false, "-1");
			} else if (bsp.GetErrorCode() == NBioBSPJNI.ERROR.NBioAPIERROR_INDEXSEARCH_IDENTIFY_STOP) {
				String[] szFinds = new String[1];
				szFinds[0] = "Time out";
				System.out.println("Time out!!!");
//				applet.redirect(false, "-1");
			}

			return;
		}
		
		System.out.println("Teste 2");

		String[] szValues = new String[4];

		szValues[0] = "Find Result";
		szValues[1] = "UserID: " + fpInfo.ID;
		szValues[2] = "Finger Num: " + fpInfo.FingerID;
		szValues[3] = "Sample Num: " + fpInfo.SampleNumber;

		for (int i = 0; i < szValues.length; i++) {
			System.out.println(szValues[i]);
		}

		applet.redirect(true, Integer.toString(fpInfo.ID));

		bsp.CloseDevice();

	}

	private Boolean CheckError() {
		if (bsp.IsErrorOccured()) {
			System.out.println("NBioBSP Error Occured [" + bsp.GetErrorCode() + "]");
			return true;
		}

		return false;
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource().equals(verify)) {
			try {
				checkFinger(card.getText());
			} catch (CriptoException e) {
				e.printStackTrace();
			}
		} else {
			if (event.getSource().equals(close) && applet != null) {
				// applet.redirect(false, "");
			}
		}
	}

	public void keyPressed(KeyEvent event) {
		if (event.getSource().equals(card) && event.getKeyCode() == 10) {
			try {
				checkFinger(card.getText());
			} catch (CriptoException e) {
				e.printStackTrace();
			}
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

			URL docUrl = new URL(urlDados.trim());

			HttpURLConnection conn = (HttpURLConnection) docUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "text/json");
			conn.setDoInput(true);
			conn.setDoOutput(true);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			while (null != ((dado = br.readLine()))) {
				message.append(dado);
			}
			br.close();

			// System.out.println(message.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return message.toString();

	}

	public String getResultCSharp() {

		URI docUrl;
		try {
			docUrl = new URI(getUrlDados());

			HTTPConnection conn = new HTTPConnection(docUrl);

			HTTPResponse response = conn.Get(docUrl.getPathAndQuery());

			InputStreamReader reader = new InputStreamReader(response.getInputStream());
			BufferedReader br = new BufferedReader(reader);
			StringBuilder message = new StringBuilder();

			for (String line = null; (line = br.readLine()) != null;) {
				message.append(line);
			}

			br.close();
			reader.close();
			return message.toString();
		} catch (ParseException | IOException | ModuleException e) {
			e.printStackTrace();
		}

		return "";

	}

	public String getUrlDados() {
		return urlDados;
	}

	public void setUrlDados(String urlDados) {
		this.urlDados = urlDados;
	}

	public String getChaveCripto() {
		return chaveCripto;
	}

	public void setChaveCripto(String chaveCripto) {
		this.chaveCripto = chaveCripto;
	}
}
