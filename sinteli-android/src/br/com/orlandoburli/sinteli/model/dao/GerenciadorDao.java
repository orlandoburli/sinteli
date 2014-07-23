package br.com.orlandoburli.sinteli.model.dao;

import br.com.orlandoburli.sinteli.model.dao.Dicionario.Config;
import br.com.orlandoburli.sinteli.model.dao.Dicionario.LogAcesso;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GerenciadorDao extends SQLiteOpenHelper {

	public static final String DB_NAME = "sinteli.db";
	public static final int DB_SCHEMA_VERSION = 3;

	public GerenciadorDao(Context context) {
		super(context, DB_NAME, null, DB_SCHEMA_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// Cria a tabela de config

		String sqlCreateTableConfig = "CREATE TABLE " + Dicionario.Config.TABELA_CONFIG + " ";
		sqlCreateTableConfig += "(" + Config.Colunas.ID_CONFIG + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
		sqlCreateTableConfig += Config.Colunas.NOME + " TEXT, ";
		sqlCreateTableConfig += Config.Colunas.HOST + " TEXT, ";
		sqlCreateTableConfig += Config.Colunas.USUARIO + " TEXT, ";
		sqlCreateTableConfig += Config.Colunas.SENHA + " TEXT, ";
		sqlCreateTableConfig += Config.Colunas.CHAVE_CRIPTO + " TEXT, ";
		sqlCreateTableConfig += Config.Colunas.PADRAO + " INTEGER)";

		db.execSQL(sqlCreateTableConfig);

		// Cria a tabela de log de acesso (adicionado na versao 3)

		String sqlCreateTableLogAcesso = "CREATE TABLE " + Dicionario.LogAcesso.TABELA_LOG_ACESSO + " ";
		sqlCreateTableLogAcesso += "(" + LogAcesso.Colunas.ID_ACESSO + " INTEGER PRIMARY KEY, ";
		sqlCreateTableLogAcesso += LogAcesso.Colunas.DATA_HORA + " TEXT, ";
		sqlCreateTableLogAcesso += LogAcesso.Colunas.PORTA + " TEXT, ";
		sqlCreateTableLogAcesso += LogAcesso.Colunas.DESTINO + " TEXT, ";
		sqlCreateTableLogAcesso += LogAcesso.Colunas.OBSERVACAO + " TEXT, ";
		sqlCreateTableLogAcesso += LogAcesso.Colunas.NOME + " TEXT, ";
		sqlCreateTableLogAcesso += LogAcesso.Colunas.SEXO + " TEXT, ";
		sqlCreateTableLogAcesso += LogAcesso.Colunas.FOTO + " TEXT)";
		
		db.execSQL(sqlCreateTableLogAcesso);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("On Upgrade: Old Version = " + oldVersion);

		if (oldVersion <= 1) {
			String alterTableConfig = "ALTER TABLE " + Dicionario.Config.TABELA_CONFIG + " ";
			alterTableConfig += " ADD " + Config.Colunas.CHAVE_CRIPTO + " TEXT";

			db.execSQL(alterTableConfig);
		}
		
		if (oldVersion < 3) {
			String sqlCreateTableLogAcesso = "CREATE TABLE " + Dicionario.LogAcesso.TABELA_LOG_ACESSO + " ";
			sqlCreateTableLogAcesso += "(" + LogAcesso.Colunas.ID_ACESSO + " INTEGER PRIMARY KEY, ";
			sqlCreateTableLogAcesso += LogAcesso.Colunas.DATA_HORA + " TEXT, ";
			sqlCreateTableLogAcesso += LogAcesso.Colunas.PORTA + " TEXT, ";
			sqlCreateTableLogAcesso += LogAcesso.Colunas.DESTINO + " TEXT, ";
			sqlCreateTableLogAcesso += LogAcesso.Colunas.OBSERVACAO + " TEXT, ";
			sqlCreateTableLogAcesso += LogAcesso.Colunas.NOME + " TEXT, ";
			sqlCreateTableLogAcesso += LogAcesso.Colunas.SEXO + " TEXT, ";
			sqlCreateTableLogAcesso += LogAcesso.Colunas.FOTO + " TEXT)";
			
			db.execSQL(sqlCreateTableLogAcesso);
		}
	}

}
