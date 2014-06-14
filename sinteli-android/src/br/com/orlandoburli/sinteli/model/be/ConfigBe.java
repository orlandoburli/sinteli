package br.com.orlandoburli.sinteli.model.be;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import br.com.orlandoburli.sinteli.model.be.exceptions.ConfigException;
import br.com.orlandoburli.sinteli.model.be.services.ConfigLoginService;
import br.com.orlandoburli.sinteli.model.dao.Dicionario;
import br.com.orlandoburli.sinteli.model.dao.GerenciadorDao;
import br.com.orlandoburli.sinteli.model.dao.Dicionario.Config;
import br.com.orlandoburli.sinteli.model.dao.Dicionario.Config.Colunas;
import br.com.orlandoburli.sinteli.model.vo.ConfigVo;

public class ConfigBe {

	private Context context;

	public ConfigBe(Context context) {
		this.context = context;
	}

	public void save(ConfigVo config) throws ConfigException {
		if (config.isNew()) {
			inserir(config);
		} else {
			alterar(config);
		}
	}

	public void inserir(ConfigVo config) throws ConfigException {
		validar(config);

		GerenciadorDao dao = new GerenciadorDao(context);

		try {
			dao.getWritableDatabase().insert(Config.TABELA_CONFIG, null, getContentValues(config));
		} finally {
			dao.getWritableDatabase().close();
		}

		checkPadrao(config);
	}

	private ContentValues getContentValues(ConfigVo config) {
		ContentValues valores = new ContentValues();

		valores.put(Colunas.NOME, config.getNome());
		valores.put(Colunas.HOST, config.getHost());
		valores.put(Colunas.USUARIO, config.getUsuario());
		valores.put(Colunas.SENHA, config.getSenha());
		valores.put(Colunas.PADRAO, config.isPadrao() ? 1 : 0);
		valores.put(Colunas.CHAVE_CRIPTO, config.getChaveCripto());

		return valores;
	}

	public void alterar(ConfigVo config) throws ConfigException {
		validar(config);

		GerenciadorDao dao = new GerenciadorDao(context);

		try {

			String whereClause = Colunas.ID_CONFIG + " = ?";
			String[] whereArgs = new String[] { config.getId().toString() };

			dao.getWritableDatabase().update(Config.TABELA_CONFIG, getContentValues(config), whereClause, whereArgs);
		} finally {
			dao.getWritableDatabase().close();
		}

		checkPadrao(config);
	}

	public void excluir(ConfigVo config) throws ConfigException {
		GerenciadorDao dao = new GerenciadorDao(context);

		try {
			String whereClause = Colunas.ID_CONFIG + " = ?";
			String[] whereArgs = new String[] { config.getId().toString() };

			dao.getWritableDatabase().delete(Config.TABELA_CONFIG, whereClause, whereArgs);
		} finally {
			dao.getWritableDatabase().close();
		}

		checkPadrao(config);
	}

	private void validar(ConfigVo config) throws ConfigException {
		if (config.getHost() == null || config.getHost().trim().equals("")) {
			throw new ConfigException("Informe o host!", Config.Colunas.HOST);
		}

		if (config.getNome() == null || config.getNome().trim().equals("")) {
			throw new ConfigException("Informe o nome do condomínio!", Dicionario.Config.Colunas.NOME);
		}

		if (config.getUsuario() == null || config.getUsuario().trim().equals("")) {
			throw new ConfigException("Informe o email!", Dicionario.Config.Colunas.USUARIO);
		}

		if (config.getSenha() == null || config.getSenha().trim().equals("")) {
			throw new ConfigException("Informe a senha!", Dicionario.Config.Colunas.SENHA);
		}

		if (config.getChaveCripto() == null || config.getChaveCripto().trim().equals("")) {
			throw new ConfigException("Informe a chave!", Dicionario.Config.Colunas.CHAVE_CRIPTO);
		}

		if (config.getHost() != null && !config.getHost().startsWith("http://")) {
			config.setHost("http://" + config.getHost());
		}
	}

	public boolean login(ConfigVo config) {
		// Login

		ConfigLoginService service = new ConfigLoginService(config, context);
		AsyncTask<String, String, String> task = service.execute("");

		try {
			String retorno = task.get();
			JSONObject json = new JSONObject(retorno);
			String isAcesso = json.getString("IsAcesso");

			return isAcesso == null ? false : isAcesso.equals("true");

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	public void checkPadrao(ConfigVo config) throws ConfigException {

		if (!config.isPadrao()) {
			// Se este item nao for padrao, nao precisa fazer nada...
			return;
		}

		// Check Padrao - Nao pode ter outros com o valor padrao alem dele

		List<ConfigVo> list = getList();

		for (ConfigVo item : list) {
			if (item.isPadrao() && !item.getId().equals(config.getId())) {
				item.setPadrao(false);
				alterar(item);
			}
		}
	}

	public List<ConfigVo> getList() {
		GerenciadorDao dao = new GerenciadorDao(context);

		List<ConfigVo> list = new ArrayList<ConfigVo>();

		try {

			String selection = null;
			String[] selectionArgs = null;
			String groupBy = null;
			String having = null;
			String orderBy = Colunas.PADRAO + " DESC, " + Colunas.NOME;

			String[] colunas = { Colunas.ID_CONFIG, Colunas.NOME, Colunas.HOST, Colunas.USUARIO, Colunas.SENHA, Colunas.PADRAO, Colunas.CHAVE_CRIPTO };

			Cursor cursor = dao.getReadableDatabase().query("config", colunas, selection, selectionArgs, groupBy, having, orderBy);

			if (cursor != null && cursor.moveToFirst()) {
				do {
					ConfigVo item = new ConfigVo();

					item.setNew(false);

					item.setId(cursor.getInt(cursor.getColumnIndex(Colunas.ID_CONFIG)));
					item.setHost(cursor.getString(cursor.getColumnIndex(Colunas.HOST)));
					item.setNome(cursor.getString(cursor.getColumnIndex(Colunas.NOME)));
					item.setUsuario(cursor.getString(cursor.getColumnIndex(Colunas.USUARIO)));
					item.setSenha(cursor.getString(cursor.getColumnIndex(Colunas.SENHA)));
					item.setChaveCripto(cursor.getString(cursor.getColumnIndex(Colunas.CHAVE_CRIPTO)));
					item.setPadrao(cursor.getInt(cursor.getColumnIndex(Colunas.PADRAO)) == 1 ? true : false);

					list.add(item);
				} while (cursor.moveToNext());
			}
		} finally {
			dao.close();
		}
		return list;
	}

	public ConfigVo getPadrao() {
		GerenciadorDao dao = new GerenciadorDao(context);

		List<ConfigVo> list = new ArrayList<ConfigVo>();

		try {

			String selection = Colunas.PADRAO + " = ?";
			String[] selectionArgs = new String[] { "1" };
			String groupBy = null;
			String having = null;
			String orderBy = Colunas.PADRAO + " DESC, " + Colunas.NOME;

			String[] colunas = { Colunas.ID_CONFIG, Colunas.NOME, Colunas.HOST, Colunas.USUARIO, Colunas.SENHA, Colunas.PADRAO, Colunas.CHAVE_CRIPTO };

			Cursor cursor = dao.getReadableDatabase().query("config", colunas, selection, selectionArgs, groupBy, having, orderBy);

			if (cursor != null && cursor.moveToFirst()) {
				do {
					ConfigVo item = new ConfigVo();

					item.setNew(false);

					item.setId(cursor.getInt(cursor.getColumnIndex(Colunas.ID_CONFIG)));
					item.setHost(cursor.getString(cursor.getColumnIndex(Colunas.HOST)));
					item.setNome(cursor.getString(cursor.getColumnIndex(Colunas.NOME)));
					item.setUsuario(cursor.getString(cursor.getColumnIndex(Colunas.USUARIO)));
					item.setSenha(cursor.getString(cursor.getColumnIndex(Colunas.SENHA)));
					item.setPadrao(cursor.getInt(cursor.getColumnIndex(Colunas.PADRAO)) == 1 ? true : false);

					list.add(item);
				} while (cursor.moveToNext());
			}
		} finally {
			dao.close();
		}

		if (list.size() > 0) {
			return list.get(0);
		}

		return null;
	}
}
