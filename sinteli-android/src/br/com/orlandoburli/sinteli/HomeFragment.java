package br.com.orlandoburli.sinteli;

import br.com.orlandoburli.sinteli.R;
import br.com.orlandoburli.sinteli.model.vo.ConfigVo;
import br.com.orlandoburli.sinteli.utils.FontUtils;
import br.com.orlandoburli.sinteli.utils.FragmentUtils;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

	private ConfigVo config;

	public HomeFragment(ConfigVo config) {
		this.config = config;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.home_fragment, null);

		FontUtils.changeFonts(view);

		Button btnSair = (Button) view.findViewById(R.id.btnSair);

		btnSair.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentUtils.gotoFragment(new LoginFragment(), getFragmentManager());
			}
		});

		btnSair.setTypeface(FontUtils.getFontDefault(view));

		Button btnListarAcessos = (Button) view.findViewById(R.id.btnListarAcessos);

		btnListarAcessos.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentUtils.gotoFragment(new LogAcessoListFragment(config), getFragmentManager());
			}
		});

		btnListarAcessos.setTypeface(FontUtils.getFontDefault(view));

		Button btnLiberarAcessoVisitante = (Button) view.findViewById(R.id.btnLiberarAcessoVisitante);
		btnLiberarAcessoVisitante.setTypeface(FontUtils.getFontDefault(view));

		Button btnLiberarAcessoPrestador = (Button) view.findViewById(R.id.btnLiberarAcessoPrestador);
		btnLiberarAcessoPrestador.setTypeface(FontUtils.getFontDefault(view));

		Button btnMeusDados = (Button) view.findViewById(R.id.btnMeusDados);
		btnMeusDados.setTypeface(FontUtils.getFontDefault(view));

		return view;
	}
}
