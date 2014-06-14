package br.com.orlandoburli.sinteli.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import br.com.orlandoburli.sintel.R;

public final class FragmentUtils {

	public static void gotoFragment(Fragment fragment, FragmentManager fragmentManager) {
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.setCustomAnimations(R.animator.slide_down, R.animator.slide_up);
		fragmentTransaction.replace(R.id.container, fragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}
}
