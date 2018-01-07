import java.util.*;
import java.math.BigInteger;

public class RSACipher extends EncryptionFundamental
{
 //private int keyLength = 1024; //Max Key length
 private int keyLength;
 private BigInteger n;
 private BigInteger phi;
 private BigInteger e; 
 private BigInteger p, q, d;
 
  public RSACipher(int keyLength) //1024 - 4096
  {
    generateKeys(keyLength);
  }
  private void generateKeys(int keyLength)
  {
    p = BigInteger.probablePrime((keyLength/2), randomNum);
    q = BigInteger.probablePrime((keyLength/2), randomNum);
    n = p.multiply(q);
    phi = lcm(p.subtract(BigInteger.ONE), q.subtract(BigInteger.ONE));
    e = new BigInteger("65537");
    d = e.modInverse(phi);
  }
  public BigInteger encrpyt(String message, BigInteger e, BigInteger n)
  {
   BigInteger messageInt = StringToInt(message);
   return(messageInt.modPow(e, n));
  }
   public String decrypt(BigInteger encryptedMessage, BigInteger d, BigInteger n)
   {
     encryptedMessage = encryptedMessage.modPow(d, n);
     return(IntToString(encryptedMessage));
   }
  public BigInteger getPublicExp()
   {
     return(e);
   }
    public BigInteger getPrivateExp()
   {
     return(d);
   }
  public BigInteger getModulus()
  {
    return(n);
  }
}
   
      
      
      
