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

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public abstract class BaseHttpService extends AsyncTask<String, String, String> {

	private Context context;
	private ProgressDialog pdia;

	public BaseHttpService(Context context) {
		this.setContext(context);
	}

	@Override
	protected String doInBackground(String... params) {
		System.out.println("doInBackground");
		// ProgressDialog dialog = ProgressDialog.show(getContext(),
		// "Aguarde...", "Carregando...");

		System.out.println("URL: " + getUrlString());
		System.out.println("dados: " + getDados());

		String resultToDisplay = "";
		try {
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(getUrlString());
			post.addHeader("Content-Type", "application/json");
			post.setEntity(new StringEntity(getDados()));

			HttpResponse response = client.execute(post);

			resultToDisplay = EntityUtils.toString(response.getEntity());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Resultado: " + resultToDisplay);

		return resultToDisplay;
	}

	public abstract String getUrlString();

	public abstract String getDados();

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		pdia = new ProgressDialog(getContext());
		pdia.setMessage("Loading...");
		pdia.show();

		System.out.println("Pre-execute");
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		pdia.dismiss();
		System.out.println("Post-execute");
	}

}
