package br.com.orlandoburli.sinteli.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class LogSistemaService extends Service {

	private CheckLogAcessoTask checkLogAcessoTask;

	@Override
	public IBinder onBind(Intent intent) {
		Log.i("LogSistemaService", "Bind...");
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("LogSistemaService", "Iniciando serviço");
		
		checkLogAcessoTask = new CheckLogAcessoTask(this);
		checkLogAcessoTask.execute(new String[] {});

		return 0;
	}

	public void onDestroy() {
		if (checkLogAcessoTask != null) {
			checkLogAcessoTask.stop();
		}

		Log.i("LogSistemaService", "Serviço encerrado!");
	}
}
