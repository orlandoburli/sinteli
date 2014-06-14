package br.com.orlandoburli.sinteli;

import br.com.orlandoburli.sintel.R;
import br.com.orlandoburli.sinteli.utils.FontUtils;
import br.com.orlandoburli.sinteli.utils.FragmentUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

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

		return view;
	}
}
