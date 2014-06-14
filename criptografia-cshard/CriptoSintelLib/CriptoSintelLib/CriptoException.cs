using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CriptoSintelLib.Exceptions
{
    [Serializable]
    public class CriptoException : Exception
    {
        public CriptoException(String message) : base(message)
        {
            
        }
    }
}
