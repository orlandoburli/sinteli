package br.com.orlandoburli.sintel.util.cripto.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import br.com.orlandoburli.sintel.utils.cripto.Cripto;
import br.com.orlandoburli.sintel.utils.cripto.exceptions.CriptoException;

@RunWith(JUnit4.class)
public class TestCripto {

	@Test
	public void testCripto() throws CriptoException {

		String key = "cripto123";
		String value = "Orlando Burli Júnior";
		
		String cripto = Cripto.cripto(key, value);
		System.out.println(cripto);
		assertEquals("001E8D0032C4002C4C002A700031D8002B5C00153F000640000D26002D3F0032C4002C4C002DF0000E80002016002FDA00157C0014EB002AED0032C41", cripto);
		
		String decripto = Cripto.decripto(key, cripto);
		
		assertEquals(value, decripto);
		
	}
}
