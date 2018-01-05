import java.util.*;
import java.math.BigInteger;

public class RSACipher extends EncryptionFundamental
{
 private int keyLength = 1024;
 private BigInteger p;
 private BigInteger q;
 private BigInteger n;
 private BigInteger phi;
 private BigInteger e,d; 
 
  protected RSACipher()
  {
    generateKeys(keyLength);
  }
  protected void generateKeys(int keyLength)
  {
    p = BigInteger.probablePrime((keyLength/2), randomNum);
    q = BigInteger.probablePrime((keyLength/2), randomNum);
    n = p.multiply(q);
    phi = lcm(p.subtract(BigInteger.ONE), q.subtract(BigInteger.ONE));
    e = new BigInteger("65537");
    /*do{
      e.add(BigInteger.ONE);
    }while(gcd(e, phi)!= BigInteger.ONE);*/
    d = e.modInverse(phi);
  }
  protected BigInteger encrpyt(String message)
  {
   BigInteger messageInt = StringToInt(message);
   /*do{
      generateKeys(keyLength);
   }while(n.compareTo(messageInt) == -1);*/
   return(messageInt.modPow(e, n));
   }
   protected String decrypt(BigInteger encryptedMessage)
   {
     encryptedMessage = encryptedMessage.modPow(d, n);
     return(IntToString(encryptedMessage));
     
   }
}
   
      
      
      