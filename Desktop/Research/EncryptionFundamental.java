import java.security.SecureRandom;
import java.math.BigInteger;
import java.util.Arrays;

public abstract class EncryptionFundamental
{
 public final SecureRandom randomNum = new SecureRandom(); //Random Used for RSA

 public BigInteger StringToInt(String message)
 {
  byte[] messageBytes = message.getBytes();
  BigInteger messageInt = new BigInteger(messageBytes);
  return messageInt;
 }
 public String IntToString(BigInteger message)
 {
  return(new String(message.toByteArray()));
 }

 public BigInteger lcm(BigInteger x, BigInteger y) //Least Common Multiple
 {
  return((x.multiply(y)).divide(gcd(x, y)));
 }
  
 public BigInteger gcd(BigInteger x, BigInteger y) //Greatest Common Denominator
 {
  return (x.gcd(y));
 }
 public String StringToBit(String message) //Binary Value of String with leading zero.
 {
   byte[] bytes = message.getBytes();
        StringBuffer test = new StringBuffer();
        for(int i = 0; i<bytes.length; i++){
            StringBuffer temp = new StringBuffer();
            temp.append(Integer.toBinaryString(bytes[i]));
            while(temp.length()!=8)
            {
              temp.insert(0, "0");
            }
            test.append(temp);
            temp.delete(0, temp.length());
        }
        return(test.toString());
 }
}
   
