package br.com.orlandoburli.sinteli;

import br.com.orlandoburli.sintel.R;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		getSupportFragmentManager().beginTransaction().add(R.id.container, new LoginFragment()).commit();
	}
	
	@Override
	public void onBackPressed() {
		// Do nothing...
	}

}
