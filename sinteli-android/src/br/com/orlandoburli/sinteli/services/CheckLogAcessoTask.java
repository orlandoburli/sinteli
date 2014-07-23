package br.com.orlandoburli.sinteli.services;

import java.util.Date;
import java.util.List;

import br.com.orlandoburli.sinteli.MainActivity;
import br.com.orlandoburli.sinteli.R;
import br.com.orlandoburli.sinteli.model.be.ConfigBe;
import br.com.orlandoburli.sinteli.model.be.LogAcessoBe;
import br.com.orlandoburli.sinteli.model.vo.ConfigVo;
import br.com.orlandoburli.sinteli.model.vo.LogAcessoVo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class CheckLogAcessoTask extends AsyncTask<String, String, String> {

	private boolean stop;

	Context context;

	public CheckLogAcessoTask(Context context) {
		this.context = context;
	}

	@Override
	protected String doInBackground(String... params) {

		stop = false;
		horas();

		return null;
	}

	private void horas() {
		while (!stop) {
			try {
				Thread.sleep(5000);
				Date data = new Date();
				Log.i("LogSistemaService", "Hora: " + data.toString());

				// Serviço de notificação

				ConfigVo configVo = new ConfigBe(context).getPadrao();

				List<LogAcessoVo> lista = new LogAcessoBe(context).lista(configVo);

				if (lista != null) {

					for (LogAcessoVo acesso : lista) {
						
						String textoAcesso = "Acesso por " + acesso.getNome() + " em " + acesso.getDestino() + " às " + acesso.getDataHora();
						
						// Cria a notificacao
						Intent intent = new Intent(context, MainActivity.class);
						
						Bundle extras = new Bundle();
						
						extras.putSerializable("config", configVo);
						extras.putSerializable("acesso", acesso);
						
						intent.putExtras(extras);
						
						PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

						Notification n = new Notification.Builder(context)
								.setContentTitle("Notificação de acesso")
								.setContentText(textoAcesso)
								.setSmallIcon(R.drawable.ic_launcher)
								.setContentIntent(pIntent).setAutoCancel(true)
								.addAction(R.drawable.ic_launcher, "Abrir Sinteli", pIntent).build();

						n.defaults = Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
						
						NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

						notificationManager.notify(acesso.getIdAcesso(), n);
					}
				}

			} catch (InterruptedException e) {
				Log.e("LogSistemaService", "Error: " + e.getMessage());
			}
		}
	}

	public void stop() {
		Log.i("LogSistemaService", "Parando serviço");
		this.stop = true;
	}

}
