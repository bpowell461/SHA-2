import java.math.BigInteger;
import java.util.*;
import java.io.*;

public class RSAExample
{
  public static void main(String[] args)
  {
    RSACipher RSA = new RSACipher();
    Scanner stdin = new Scanner(System.in);
    System.out.print("Message to Encrypt: ");
    String testMessage = stdin.nextLine();
    BigInteger message = RSA.encrpyt(testMessage);
    System.out.println("\nMessage encrypted: '"+testMessage+"'");
    System.out.println("\nEncrpyted");
    System.out.println("--------------\n");
    System.out.println(message);
    uploadData(message, "EncryptedData.dat");
    String decryptedMessage = RSA.decrypt(downloadData("EncryptedData.dat"));
    System.out.println("\nDecrypted");
    System.out.println("--------------\n");
    System.out.println(decryptedMessage);
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
