package br.com.orlandoburli.sinteli;

import br.com.orlandoburli.sinteli.R;
import br.com.orlandoburli.sinteli.model.vo.ConfigVo;
import br.com.orlandoburli.sinteli.model.vo.LogAcessoVo;
import br.com.orlandoburli.sinteli.utils.FragmentUtils;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		// Verifica os parametros passados
		if (getIntent().getExtras() != null) {
			ConfigVo config = (ConfigVo) getIntent().getExtras().getSerializable("config");

			if (config != null) {
				LogAcessoVo acesso = (LogAcessoVo) getIntent().getExtras().getSerializable("acesso");

				if (acesso != null) {
					FragmentUtils.gotoFragment(new LogAcessoViewFragment(acesso, config), getFragmentManager());
				} else {
					FragmentUtils.gotoFragment(new HomeFragment(config), getFragmentManager());
				}
			}
		} else {
			getFragmentManager().beginTransaction().add(R.id.container, new LoginFragment()).commit();
		}

		Intent intentService = new Intent("LogSistemaService");

		if (isRunningService(intentService)) {
			stopService(intentService);
		}

		startService(intentService);
	}

	public boolean isRunningService(Intent intent) {
		ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (service.service.getClassName().contains(intent.getAction())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void onBackPressed() {
		// Do nothing...
	}

}
