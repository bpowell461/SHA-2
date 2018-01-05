import java.math.BigInteger;
import java.util.*;
import java.io.*;

public class RSAExample
{
  public static void main(String[] args)
  {
    RSACipher Bob = new RSACipher();
    RSACipher Alice = new RSACipher();
    Scanner stdin = new Scanner(System.in);
    System.out.print("Message to Encrypt: "); //Bob encrypts message for Alice using Alice's public key
    String testMessage = stdin.nextLine();
    BigInteger message = Bob.encrpyt(testMessage, Alice.getPublicExp(), Alice.getModulus()); 
    uploadData(message, "EncryptedData.dat");
    
    BigInteger deMessage = downloadData("EncryptedData.dat");
    String newMsg = Alice.decrypt(deMessage, Alice.getPrivateExp(), Alice.getModulus()); //Alice decrypts Bob's message using private key
    System.out.println("Decrypted Data: "+newMsg);
  }
  private static BigInteger downloadData(String fileName)
    {
      BigInteger a = null;
      try{
      
        FileInputStream fileIn = new FileInputStream(fileName);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        a = (BigInteger) in.readObject();
        in.close();
        } catch (IOException e) {
          System.out.println(e.getMessage());
          }
         catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
      return a;
      }
     private static void uploadData(BigInteger message, String fileName)
     {
       try{
         FileOutputStream fileOut = new FileOutputStream(fileName);
         ObjectOutputStream out = new ObjectOutputStream(fileOut);
         out.writeObject(message);
         out.close();
         } catch (IOException e) {
           System.out.println(e.getMessage());
         }
     }
}
