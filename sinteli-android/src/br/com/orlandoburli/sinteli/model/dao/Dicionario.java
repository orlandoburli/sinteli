package br.com.orlandoburli.sinteli.model.dao;

public class Dicionario {

	public class Config {
		public static final String TABELA_CONFIG = "config";

		public final class Colunas {
			public static final String ID_CONFIG = "id";
			public static final String NOME = "nome";
			public static final String HOST = "host";
			public static final String USUARIO = "usuario";
			public static final String SENHA = "senha";
			public static final String PADRAO = "padrao";
			public static final String CHAVE_CRIPTO = "chave_cripto";
		}

		public final String[] COLUNAS = { Colunas.ID_CONFIG, Colunas.NOME, Colunas.HOST, Colunas.USUARIO, Colunas.SENHA, Colunas.PADRAO, Colunas.CHAVE_CRIPTO };

	}
}
