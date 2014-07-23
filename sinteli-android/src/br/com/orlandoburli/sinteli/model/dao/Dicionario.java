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
	
	public class LogAcesso {
		public static final String TABELA_LOG_ACESSO = "log_acesso";
		
		public final class Colunas {
			public static final String ID_ACESSO = "id";
			public static final String DATA_HORA = "data_hora";
			public static final String PORTA = "porta";
			public static final String DESTINO = "destino";
			public static final String OBSERVACAO = "observacao";
			public static final String NOME = "nome";
			public static final String SEXO = "sexo";
			public static final String FOTO = "foto";
			
		}
	}
}
