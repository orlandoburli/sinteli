using CriptoSintelLib.Exceptions;
using System;
using System.Collections.Generic;
using System.Globalization;
using System.Text;

namespace Cripto
{
    public class Cripto
    {
        private Char getKeyPos(String key, int pos)
        {
            return key.Substring(pos, 1).ToCharArray()[0];
        }

        public String cripto(String key, String value)
        {
            if (key == null || key.Length <= 0)
            {
                throw new CriptoException("Invalid key!");
            }

            int keyPosition = 0;
            int valuePosition = 0;

            int digito = value.Length;

            // Digito verificador
            String digitoStr = digito.ToString().Substring(digito.ToString().Length - 1);
            digito = Int32.Parse(digitoStr);

            if (digito <= 0)
            {
                digito = 1;
            }

            digitoStr = digito.ToString();

            String result = "";

            for (int i = 0; i < value.Length; i++)
            {
                int x = getKeyPos(key, keyPosition);
                int y = getKeyPos(value, valuePosition);
                    
                // Multiplica pelo digito
                int z = (x * y * digito);

                String temp =  z.ToString("X6");

                result += temp;

                keyPosition++;
                valuePosition++;

                if (keyPosition >= key.Length)
                {
                    keyPosition = 0;
                }

                if (valuePosition >= value.Length)
                {
                    valuePosition = 0;
                }
            }

            return result + digitoStr;
        }

        public String decripto(String key, String value)
        {
            int digitos = value.Length - 1;

            if (digitos % 6 > 0)
            {
                throw new CriptoException("Invalid value cripto size!");
            }

            int digito = Int32.Parse(value.Substring(value.Length - 1, 1));

            int numCaracteres = digitos / 6;

            String retorno = "";

            int keyPosition = 0;

            for (int i = 0; i < numCaracteres; i++)
            {
                int x = getKeyPos(key, keyPosition);
                int start = (i) * 6;
                String item = value.Substring(start, 6);

                int z = Int32.Parse(item, NumberStyles.HexNumber);

                int y = (z / x / digito);

                Char c = (Char)y;

                retorno += c;

                keyPosition++;

                if (keyPosition >= key.Length)
                {
                    keyPosition = 0;
                }
            }

            return retorno;
        }
    }
}
