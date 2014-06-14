package br.com.orlandoburli.sinteli.model.be;

import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import br.com.orlandoburli.sinteli.model.be.exceptions.UsuarioException;
import br.com.orlandoburli.sinteli.model.be.services.UsuarioService;
import br.com.orlandoburli.sinteli.model.vo.ConfigVo;

@Deprecated
public class UsuarioBe {

	public boolean login(ConfigVo config) throws UsuarioException {

		AsyncTask<String, String, String> execute = new UsuarioService().execute(config.getHost() + "/rest/pgetusuario", config.getUsuario(), config.getSenha());

		try {
			throw new UsuarioException(execute.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return false;

	}

}
