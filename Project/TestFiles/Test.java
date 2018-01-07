public class Test
{
	public static void main(String[] args)
	{
   
		RC4 test = new RC4("key");

    String encM = test.encrypt("Brad");
    System.out.println(encM);
    
    RC4 test2 = new RC4("key");
    String decM = test2.decrypt(encM);
    System.out.println(decM);

}	
}