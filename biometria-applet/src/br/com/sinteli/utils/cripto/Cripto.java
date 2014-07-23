package br.com.sinteli.utils.cripto;

import br.com.sinteli.utils.cripto.exceptions.CriptoException;

public final class Cripto {

	private static char getKeyPos(String key, int pos) {
		return key.substring(pos, pos + 1).toCharArray()[0];
	}

	public static String cripto(String key, String value) throws CriptoException {
		if (key == null || key.length() <= 0) {
			throw new CriptoException("Invalid key!");
		}

		int keyPosition = 0;
		int valuePosition = 0;

		Integer digito = value.length();

		// Digito verificador
		String digitoStr = digito.toString().substring(digito.toString().length() - 1);
		digito = Integer.parseInt(digitoStr);

		if (digito <= 0) {
			digito = 1;
		}

		String result = "";

		for (int i = 0; i < value.length(); i++) {
			int x = getKeyPos(key, keyPosition);
			int y = getKeyPos(value, valuePosition);

			// Multiplica pelo digito
			Integer z = (x * y * digito);

//			System.out.println("X: " + x + ", Y, " + y + ", Z: " + z);
			String teste = "";
			teste += (char) y;
			
			System.out.println("Y: " + y + " Char: " + teste);

			String temp = fillString(Integer.toHexString(z).toUpperCase(), "0", 6, 1);

			result += temp;

			keyPosition++;
			valuePosition++;

			if (keyPosition >= key.length()) {
				keyPosition = 0;
			}

			if (valuePosition >= value.length()) {
				valuePosition = 0;
			}
		}

		return result + digito.toString();
	}

	public static String decripto(String key, String value) throws CriptoException {
		int digitos = value.length() - 1;

		if (digitos % 6 > 0) {
			throw new CriptoException("Invalid value cripto size!");
		}

		int digito = Integer.parseInt(value.substring(value.length() - 1, value.length()));

		int numCaracteres = digitos / 6;

		String retorno = "";

		int keyPosition = 0;

		for (int i = 0; i < numCaracteres; i++) {
			int x = getKeyPos(key, keyPosition);
			int start = (i) * 6;
			String item = value.substring(start, start + 6);

			int z = Integer.parseInt(item, 16);

			int y = (z / x / digito);

			// char c = y;

			retorno += (char) y;

			keyPosition++;

			if (keyPosition >= key.length()) {
				keyPosition = 0;
			}
		}

		return retorno;
	}

	/**
	 * 
	 * @param value
	 * @param fillWith
	 * @param size
	 * @param direction
	 *            1=Esquerda 2=Direita
	 * @return
	 */
	private static String fillString(Object ovalue, String fillWith, int size, int direction) {
		String value = ovalue.toString();
		// Checa se Linha a preencher ??? nula ou branco
		if (value == null || value.trim() == "") {
			value = "";
		}
		// if (value.length() > size) {
		// value = value.substring(0, size);
		// return value;
		// }
		// Enquanto Linha a preencher possuir 2 espa???os em branco seguidos,
		// substitui por 1 espa???o apenas
		while (value.contains("  ")) {
			value = value.replaceAll("  ", " ").trim();
		}
		// Retira caracteres estranhos
		value = value.replaceAll("[./-]", "");
		StringBuffer sb = new StringBuffer(value);
		if (direction == 1) { // a Esquerda
			for (int i = sb.length(); i < size; i++) {
				sb.insert(0, fillWith);
			}
		} else if (direction == 2) {// a Direita
			for (int i = sb.length(); i < size; i++) {
				sb.append(fillWith);
			}
		}
		return sb.toString();
	}
}
