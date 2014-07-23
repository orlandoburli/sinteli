package br.com.orlandoburli.sinteli.model.be;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import br.com.orlandoburli.sinteli.model.be.services.LogAcessoService;
import br.com.orlandoburli.sinteli.model.dao.Dicionario.LogAcesso;
import br.com.orlandoburli.sinteli.model.dao.Dicionario;
import br.com.orlandoburli.sinteli.model.dao.GerenciadorDao;
import br.com.orlandoburli.sinteli.model.dao.Dicionario.LogAcesso.Colunas;
import br.com.orlandoburli.sinteli.model.json.AcessoJson;
import br.com.orlandoburli.sinteli.model.vo.ConfigVo;
import br.com.orlandoburli.sinteli.model.vo.LogAcessoVo;

import com.google.gson.Gson;

public class LogAcessoBe {

	private Context context;

	public LogAcessoBe(Context context) {
		this.setContext(context);
	}

	public List<LogAcessoVo> lista(ConfigVo config) {

		LogAcessoService logService = new LogAcessoService(getContext(), config, getIdUltimoAcesso());

		try {
			String resultado = logService.executar();

			JSONObject json;
			json = new JSONObject(resultado);

			String mensagem = json.getString("Mensagem");
			System.out.println("Mensagem: " + mensagem);

			// Lista dos resultados
			String listaAcessosString = json.getString("Json");
			System.out.println(listaAcessosString);

			AcessoJson[] json2 = new Gson().fromJson(listaAcessosString, AcessoJson[].class);

			List<LogAcessoVo> salvarAcessos = salvarAcessos(json2);

			return salvarAcessos;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Integer getIdUltimoAcesso() {
		int retorno = 0;

		GerenciadorDao dao = new GerenciadorDao(context);

		try {

			Cursor cursor = dao.getReadableDatabase().rawQuery("SELECT MAX(" + Colunas.ID_ACESSO + ") AS " + Colunas.ID_ACESSO + " FROM " + LogAcesso.TABELA_LOG_ACESSO, null);

			if (cursor != null && cursor.moveToFirst()) {
				do {
					//

					retorno = cursor.getInt(cursor.getColumnIndex(Colunas.ID_ACESSO));

				} while (cursor.moveToNext());
			}
		} finally {
			dao.close();
		}

		// Retornar id do Ultimo acesso
		return retorno;
	}

	public List<LogAcessoVo> salvarAcessos(AcessoJson[] listaJson) {
		// Salvar os acessos

		List<LogAcessoVo> list = new ArrayList<LogAcessoVo>();

		for (AcessoJson acesso : listaJson) {

			LogAcessoVo logAcesso = new LogAcessoVo();

			logAcesso.setIdAcesso(Integer.parseInt(acesso.getAcessoCod()));
			logAcesso.setDataHora(acesso.getAcessoHorarioComp());
			logAcesso.setPorta(acesso.getAcessoPorta());
			logAcesso.setDestino(acesso.getAcessoDestino());
			logAcesso.setObservacao(acesso.getAcessoObs());
			logAcesso.setNome(acesso.getPesNom());
			logAcesso.setSexo(acesso.getPesSexo().equals("1") ? "Masculino" : "Feminino");
			logAcesso.setFoto(acesso.getAcessoFoto());

			inserir(logAcesso);

			list.add(logAcesso);
		}

		return list;

	}

	public void inserir(LogAcessoVo logAcesso) {
		GerenciadorDao dao = new GerenciadorDao(context);

		try {
			Integer idNovo = (int) dao.getWritableDatabase().insert(LogAcesso.TABELA_LOG_ACESSO, null, getContentValues(logAcesso));
			logAcesso.setIdAcesso(idNovo);
		} finally {
			dao.getWritableDatabase().close();
		}

	}

	public List<LogAcessoVo> getList() {
		GerenciadorDao dao = new GerenciadorDao(context);

		List<LogAcessoVo> list = new ArrayList<LogAcessoVo>();

		try {
			String selection = null;
			String[] selectionArgs = null;
			String groupBy = null;
			String having = null;
			String orderBy = Colunas.ID_ACESSO + " DESC, " + Colunas.NOME;

			String[] colunas = { Colunas.ID_ACESSO, Colunas.DATA_HORA, Colunas.PORTA, Colunas.DESTINO, Colunas.OBSERVACAO, Colunas.NOME, Colunas.SEXO, Colunas.FOTO };

			Cursor cursor = dao.getReadableDatabase().query(Dicionario.LogAcesso.TABELA_LOG_ACESSO, colunas, selection, selectionArgs, groupBy, having, orderBy);

			if (cursor != null && cursor.moveToFirst()) {
				do {
					LogAcessoVo item = new LogAcessoVo();

					item.setNew(false);

					item.setIdAcesso(cursor.getInt(cursor.getColumnIndex(Colunas.ID_ACESSO)));
					item.setDataHora(cursor.getString(cursor.getColumnIndex(Colunas.DATA_HORA)));
					item.setPorta(cursor.getString(cursor.getColumnIndex(Colunas.PORTA)));
					item.setDestino(cursor.getString(cursor.getColumnIndex(Colunas.DESTINO)));
					item.setObservacao(cursor.getString(cursor.getColumnIndex(Colunas.OBSERVACAO)));
					item.setNome(cursor.getString(cursor.getColumnIndex(Colunas.NOME)));
					item.setSexo(cursor.getString(cursor.getColumnIndex(Colunas.SEXO)));
					item.setFoto(cursor.getString(cursor.getColumnIndex(Colunas.FOTO)));

					list.add(item);
				} while (cursor.moveToNext());
			}

		} finally {
			dao.close();
		}

		return list;

	}

	private ContentValues getContentValues(LogAcessoVo logAcesso) {
		ContentValues valores = new ContentValues();

		valores.put(Colunas.ID_ACESSO, logAcesso.getIdAcesso());
		valores.put(Colunas.DATA_HORA, logAcesso.getDataHora());
		valores.put(Colunas.PORTA, logAcesso.getPorta());
		valores.put(Colunas.DESTINO, logAcesso.getDestino());
		valores.put(Colunas.OBSERVACAO, logAcesso.getObservacao());
		valores.put(Colunas.NOME, logAcesso.getNome());
		valores.put(Colunas.SEXO, logAcesso.getSexo());
		valores.put(Colunas.FOTO, logAcesso.getFoto());

		return valores;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
}
