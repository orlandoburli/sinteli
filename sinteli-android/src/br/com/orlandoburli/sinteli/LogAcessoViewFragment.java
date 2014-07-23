package br.com.orlandoburli.sinteli;

import br.com.orlandoburli.sinteli.model.vo.ConfigVo;
import br.com.orlandoburli.sinteli.model.vo.LogAcessoVo;
import br.com.orlandoburli.sinteli.utils.FontUtils;
import br.com.orlandoburli.sinteli.utils.FragmentUtils;
import br.com.orlandoburli.sinteli.utils.ImageDownloaderTask;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LogAcessoViewFragment extends Fragment {

	private LogAcessoVo logAcesso;
	protected ConfigVo config;

	public LogAcessoViewFragment(LogAcessoVo logAcesso, ConfigVo config) {
		this.config = config;
		this.setLogAcesso(logAcesso);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.acesso_view, null);

		FontUtils.changeFonts(view);

		Button btnVoltarListaAcesso = (Button) view.findViewById(R.id.btnVoltarListaAcesso);

		btnVoltarListaAcesso.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentUtils.gotoFragment(new LogAcessoListFragment(config), getFragmentManager());
			}
		});

		btnVoltarListaAcesso.setTypeface(FontUtils.getFontDefault(view));

		// Exibir os dados

		if (logAcesso != null) {
			TextView textView1 = (TextView) view.findViewById(R.id.textView1);
			TextView textView2 = (TextView) view.findViewById(R.id.textView2);
			TextView textView3 = (TextView) view.findViewById(R.id.textView3);
			TextView textView4 = (TextView) view.findViewById(R.id.textView4);
			TextView textView5 = (TextView) view.findViewById(R.id.textView5);

			textView1.setTypeface(FontUtils.getFontBold(view));
			textView2.setTypeface(FontUtils.getFontBold(view));
			textView3.setTypeface(FontUtils.getFontBold(view));
			textView4.setTypeface(FontUtils.getFontBold(view));
			textView5.setTypeface(FontUtils.getFontBold(view));

			TextView textAcessoNomeSexo = (TextView) view.findViewById(R.id.textAcessoNomeSexo);
			TextView textAcessoDataHora = (TextView) view.findViewById(R.id.textAcessoDataHora);
			TextView textAcessoPortaDestino = (TextView) view.findViewById(R.id.textAcessoPortaDestino);
			TextView textAcessoObservacao = (TextView) view.findViewById(R.id.textAcessoObservacao);

			ImageView imageAcessoFoto = (ImageView) view.findViewById(R.id.imageAcessoFoto);

			textAcessoNomeSexo.setText(logAcesso.getNome() + " / " + logAcesso.getSexo());
			textAcessoDataHora.setText(logAcesso.getDataHora());
			textAcessoPortaDestino.setText(logAcesso.getPorta() + " / " + logAcesso.getDestino());
			textAcessoObservacao.setText(logAcesso.getObservacao());

			textAcessoNomeSexo.setTypeface(FontUtils.getFontDefault(view));
			textAcessoDataHora.setTypeface(FontUtils.getFontDefault(view));
			textAcessoPortaDestino.setTypeface(FontUtils.getFontDefault(view));
			textAcessoObservacao.setTypeface(FontUtils.getFontDefault(view));

			// TODO Imagem
			System.out.println("Foto acesso: " + logAcesso.getFoto());
			System.out.println("Baixando foto...");

			new ImageDownloaderTask(imageAcessoFoto).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, logAcesso.getFoto().trim());

			imageAcessoFoto.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(view.getContext(), FullScreenImage.class);
					i.putExtra("image", logAcesso.getFoto());
					startActivity(i);
				}
			});
		}

		return view;
	}

	public LogAcessoVo getLogAcesso() {
		return logAcesso;
	}

	public void setLogAcesso(LogAcessoVo logAcesso) {
		this.logAcesso = logAcesso;
	}
}
