package br.com.orlandoburli.sinteli;

import br.com.orlandoburli.sinteli.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends Activity {

	private static int SPLASH_TIME_OUT = 2500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		ImageView image = (ImageView) findViewById(R.id.logoGuieCSplash);

		Animation a = AnimationUtils.loadAnimation(this, R.animator.fade_in);
		image.startAnimation(a);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				startActivity(i);
				finish();
			}
		}, SPLASH_TIME_OUT);
	}
}
