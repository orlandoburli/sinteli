using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace CriptoSintelLibTest
{
    [TestClass]
    public class TesteLibCripto
    {
        [TestMethod]
        public void testeValidaCripto()
        {
            String chave = "cripto123";
            String valor = "Orlando Burli";

            String valorCripto = new Cripto.Cripto().cripto(chave, valor);

            Assert.AreEqual("005BA700984C0084E4007F50009588008214003FBD0012C00027720087BD00984C0084E40089D03", valorCripto);

            String valorDecripto = new Cripto.Cripto().decripto(chave, valorCripto);

            Assert.AreEqual(valorDecripto, valor);
        }

        public static void Main(String[] args)
        {
            String chave = "cripto123";
            String valor = "Orlando Burli";

            String valorCripto = new Cripto.Cripto().cripto(chave, valor);
            String valorDecripto = new Cripto.Cripto().decripto(chave, valorCripto);

            Console.WriteLine("Valor Cripto: ");
            Console.WriteLine(valorCripto);

            Console.WriteLine("Valor Decripto: ");
            Console.WriteLine(valorDecripto);

            Console.ReadKey();
        }
    }
}
