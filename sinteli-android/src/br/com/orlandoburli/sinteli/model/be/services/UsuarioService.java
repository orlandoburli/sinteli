package br.com.orlandoburli.sinteli.model.be.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import br.com.orlandoburli.sinteli.utils.cripto.Cripto;
import br.com.orlandoburli.sinteli.utils.cripto.exceptions.CriptoException;
import android.os.AsyncTask;

@Deprecated
public class UsuarioService extends AsyncTask<String, String, String> {

	@Override
	protected String doInBackground(String... params) {

		String urlString = params[0]; // URL to call
		String usuLogin = params[1];
		String usuSenha = params[2];

		String dados = null;
		try {
			dados = "{\"UsuLogin\": \"" + Cripto.cripto("cripto123", usuLogin) + "\", \"UsuSenha\":\"" + Cripto.cripto("cripto123", usuSenha) + "\"}";
		} catch (CriptoException e1) {
			e1.printStackTrace();
		}

		String resultToDisplay = "";
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(urlString);
			post.addHeader("Content-Type", "application/json");
			post.setEntity(new StringEntity(dados));

			HttpResponse response = client.execute(post);

			resultToDisplay = EntityUtils.toString(response.getEntity());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return resultToDisplay;
	}
}
