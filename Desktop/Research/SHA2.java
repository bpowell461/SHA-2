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
  
  private StringBuffer digest;
  
   public SHA2(String message)
   {
     //System.out.println("Message: " + message);
     M.append(StringToBit(message));
     //System.out.println("Binary Message: " + M);
     int l = M.length(); //length of String M in bits
     int k = 448-(l+1);  //Number of "0" bits 
     padMessage(M, k, l);
     //System.out.println("Padded Message: " + M);
     //debugLength();
     String[] blocks = new String[16]; //Blocks of 32-Bit Words divided into 16 blocks M(t)(0-15)
     parseMessage(M, blocks);
     String[] W = new String[64];
     //debugArray(blocks);
     processHash(blocks, W);
     
     compressionFunction(initH, W);
     generateDigest(initH, digest);
     
     //System.out.println(toString());
     resetHash();
   }
   
   public void padMessage(StringBuffer M, int k, int l)
   {
     M.append("1"); //add "1" bit to end of string
     for(int i=0; i<k; i++)
       M.append("0");
     
     int difference = 512 - (l+1+k);
     String binL = "0" + Integer.toBinaryString(l); //Binary Representation of int l w/ leading "0"
     difference = difference - (binL.length());
     for(int i = 0; i<difference; i++)
       M.append("0");
     M.append(binL);
   }
   
   public void parseMessage(StringBuffer M, String[] blocks)
   {
     for(int i =0; i<16; i++){
       blocks[i] = M.substring(0, 32);
       M.delete(0,32);
     }
   }
   public void processHash(String[] blocks, String[] W)//blocks is the 16-word array, W is the array to be processed, NOTE: blocks must be 16-words
   {
     for(int i=0; i<16; i++)
     {
       W[i]=blocks[i];
     }
     for(int i=16; i<64; i++)
     {
       int binaryW = Integer.parseInt(W[i-15], 2);
       int s0 = delta0(binaryW);
       binaryW = Integer.parseInt(W[i-2], 2);
       int s1 = delta1(binaryW);
       int sum = s0 + (Integer.parseInt(W[i-16], 2)) + s1 + (Integer.parseInt(W[i-7], 2));
       W[i] = Integer.toBinaryString(sum);
     }
   }
   public void compressionFunction(int[] initH, String[] W)
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
       int t1 = h + S1 + intCh + K[i] + (Integer.parseInt(W[i], 2));
       int S0 = sigma0(a);
       int intMaj = Maj(a, b, c);
       int t2 = S0+intMaj;
       
       h = g;
       g = f;
       f = e;
       e = d + t1;
       d = c;
       c = b;
       b = a;
       a = t1 + t2;
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
   
   public void generateDigest(int[] initH, StringBuffer digest)
   {
     for(int i = 0; i<initH.length; i++)
     {
       digest.append(Integer.toHexString(initH[i]));
     }
   }
   
   public void debugLength()
   {
     System.out.println("Message Bit Length: " + M.length());
   }
   public void debugArray(String[] blocks)
   {
     for(int i = 0; i<blocks.length; i++){
       System.out.print("Block "+i+": ");
       System.out.println(blocks[i]);
     }
   
   }
    private int Ch(int x, int y, int z) 
    {
        return (x & y) ^ ((~x) & z);
    }

    private int Maj(int x, int y, int z) 
    {
        return (x & y) ^ (x & z) ^ (y & z);
    }

    private int rightShift(int x, int n ) 
    {
        return (x >>> n);
    }

    private int rightRotation(int x, int n) 
    {
        return (x >>> n) | (x << (32 - n));
    }

    private int sigma0(int x) 
    {
        return (rightRotation(x, 2) ^ rightRotation(x, 13) ^ rightRotation(x, 22));
    }

    private int sigma1(int x) 
    {
        return (rightRotation( x, 6 ) ^ rightRotation( x, 11 ) ^ rightRotation( x, 25 ));
    }

    private int delta0(int x) 
    {
        return (rightRotation(x, 7) ^ rightRotation(x, 18) ^ rightShift(x, 3));
    }

    private int delta1(int x) 
    {
        return (rightRotation(x, 17) ^ rightRotation(x, 19) ^ rightShift(x, 10));
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
     
     
     