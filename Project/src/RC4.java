public class RC4 extends EncryptionFundamental
{
	private int[] S; //S-Box of all possible bytes
	private int[] T;
	private final int MAX_SIZE = 256;
 
	public RC4(String input)
  {
      byte[] key = input.getBytes();
      
			S = new int[MAX_SIZE];
			T = new int[MAX_SIZE];
      
			for (int i=0; i<MAX_SIZE; i++) 
      {
				S[i] = i;
				T[i] = key[i % key.length];
			}
      
			
      int j = 0;
      
			for (int i=0; i<MAX_SIZE; i++) {
				j = (j + S[i] + T[i]) % MAX_SIZE;
				S[i] ^= S[j];
				S[j] ^= S[i];
				S[i] ^= S[j];
			}
		}
	
	public String encrypt(String input) 
  {
    byte[] messageByte = input.getBytes();
 
		byte[] encByte = new byte[messageByte.length];
		int i = 0;
    int j = 0;
    int k;
    int xorIndex;
   
		for (int q=0; q<messageByte.length; q++) 
    {
			i = (i + 1) % MAX_SIZE;
			j = (j + S[i]) % MAX_SIZE;
			S[i] ^= S[j];
			S[j] ^= S[i];
			S[i] ^= S[j];
			xorIndex = (S[i] + S[j]) % MAX_SIZE;
			k = S[xorIndex];
			encByte[q] = (byte) (messageByte[q] ^ k);
		}
    StringBuilder encBuild = new StringBuilder();
    for(int q=0; q<encByte.length; q++)
    {
      encBuild.append(String.format("%02x", encByte[q]));
    }
		return (encBuild.toString());
	}
	public String encrypt(byte[] messageByte) 
  {
		byte[] encByte = new byte[messageByte.length];
		int i = 0;
    int j = 0;
    int k;
    int xorIndex;
   
		for (int q=0; q<messageByte.length; q++) 
    {
			i = (i + 1) % MAX_SIZE;
			j = (j + S[i]) % MAX_SIZE;
			S[i] ^= S[j];
			S[j] ^= S[i];
			S[i] ^= S[j];
			xorIndex = (S[i] + S[j]) % MAX_SIZE;
			k = S[xorIndex];
			encByte[q] = (byte) (messageByte[q] ^ k);
		}
    StringBuilder encBuild = new StringBuilder();
    for(int q=0; q<encByte.length; q++)
    {
      encBuild.append(String.format("%02x", encByte[q]));
    }
		return (encBuild.toString());
	}
	public String decrypt(String input) 
  {
     byte[] msg = HexToByteArray(input);
     String decM = encrypt(msg);
     return(HexToString(decM));
	}

}