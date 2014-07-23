package br.com.orlandoburli.sinteli;

import br.com.orlandoburli.sinteli.utils.ImageDownloaderTask;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class FullScreenImage extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.image_full_screen);
		Intent intent = getIntent();

		String pathFoto = intent.getExtras().getString("image");
		ImageView imageView = (ImageView) findViewById(R.id.imageViewFullScreen);

		imageView.setScaleType(ImageView.ScaleType.FIT_XY);

		new ImageDownloaderTask(imageView).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, pathFoto);

		Toast.makeText(getApplicationContext(), "Toque na foto para voltar", Toast.LENGTH_LONG).show();

		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Fecha a activity
				finish();
			}
		});
	}
}
