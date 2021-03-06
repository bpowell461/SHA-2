import java.util.*;

public class SHA2 extends EncryptionFundamental
{
  private StringBuffer M = new StringBuffer(); //String to be hashed
  
  private int[] K = {0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5,0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3,0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174, 0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc, 0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da, 0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7, 0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967, 0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13, 0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85, 0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3, 0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070, 0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5, 0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3, 0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208, 0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2};
 /*Array of 32-Bit Round Constants(0-63) "These words represent the first thirty-two bits of the fractional parts of
  *the cube roots of the first sixty-four prime numbers." - NIST.gov
  */
  private int[] initH = {0x6a09e667, 0xbb67ae85, 0x3c6ef372, 0xa54ff53a, 0x510e527f, 0x9b05688c, 0x1f83d9ab, 0x5be0cd19};
  //Array of Initial Hashes
  
  private StringBuffer digest = new StringBuffer(); //Generated Hash 
  
   public SHA2(String message)
   {
     M.append(StringToBit(message)); //Converts message to Binary value of String
     int l = message.length()*8; //length of String message in bits
     padMessage(M, l); //Pads Message to a length multiple of 512
    
     StringBuffer[] chunks = new StringBuffer[(M.length()/512)]; //Divides message into 512-bit chunks
     
     for(int i=0; i<chunks.length; i++)
     {
       chunks[i] = new StringBuffer(M.substring(0, 512)); //Divides message into 512-bit chunks
       M.delete(0, 512);
     }
     
     long[] W = new long[64]; //64 blocks of 32-bit words
     
     for(int i=0; i<chunks.length; i++)
     {
     parseMessage(chunks[i], W);
     processHash(W);
     compressionFunction(W);
     }
     generateDigest();
     
     resetHash(); //Resets Hash 
   }
   
   public void padMessage(StringBuffer M, int l)
   {
     int k=0; //K 0 bits
     M.append("1"); //add "1" bit to end of string
     String binL = "0" + Integer.toBinaryString(l);
     
     //l + k + 1 + 64 is a multiple of 512   M.length()%512==0;
     while(binL.length()!=64)
     {
       binL = "0" + binL;
     }
     while(((l+1+k+64)%512)!=0)
     {
       M.append("0");
       k++;
     }
     M.append(binL);
   }
   
   public void parseMessage(StringBuffer M, long[] W)
   {
     for(int i =0; i<16; i++){
       W[i] = Long.parseLong(M.substring(0, 32), 2);
       M.delete(0,32);
       
     }
   }
   public void processHash(long[] W)//blocks is the 16-word array, W is the array to be processed, NOTE: blocks must be 16-words
   {
     for(int i=16; i<64; i++)
     {
       int binaryW = (int) W[i-15];
       int s0 = delta0(binaryW);
       binaryW = (int) W[i-2];
       int s1 = delta1(binaryW);
       int sum = s0 + (int) W[i-16] + s1 + (int) W[i-7];
       W[i] = sum;
     }
   }
   public void compressionFunction(long[] W)
   {
     int a = initH[0];
     int b = initH[1];
     int c = initH[2];
     int d = initH[3];
     int e = initH[4];
     int f = initH[5];
     int g = initH[6];
     int h = initH[7];
     
     for(int i = 0; i<64; i++)
     {
       int S1 = sigma1(e);
       int intCh = Ch(e, f, g);
       long t1 = h + S1 + intCh + K[i] + W[i];
       int S0 = sigma0(a);
       int intMaj = Maj(a, b, c);
       int t2 = S0+intMaj;
       
       h = g;
       g = f;
       f = e;
       e = d + (int)t1;
       d = c;
       c = b;
       b = a;
       a = (int) t1 + t2;
     }    
      initH[0]= initH[0] + a;
      initH[1]= initH[1] + b;
      initH[2]= initH[2] + c;
      initH[3]= initH[3] + d;
      initH[4]= initH[4] + e;
      initH[5]= initH[5] + f;
      initH[6]= initH[6] + g;
      initH[7]= initH[7] + h;      
   }    
   
   public void generateDigest() //Generates Hash
   {
     //digest.delete(0, digest.length()); //Clears digest
     for(int i = 0; i<8; i++)
     {
       digest.append(Integer.toHexString(initH[i]));
     }
   }
   
    public String toString()
    {
      return("SHA-256 Hash = " + digest.toString());
    }
    private void resetHash()
    {
     initH[0] = 0x6a09e667;
     initH[1] = 0xbb67ae85;
     initH[2] = 0x3c6ef372;
     initH[3] = 0xa54ff53a;
     initH[4] = 0x510e527f;
     initH[5] = 0x9b05688c;
     initH[6] = 0x1f83d9ab;
     initH[7] = 0x5be0cd19;
   }
}
     
     
     
