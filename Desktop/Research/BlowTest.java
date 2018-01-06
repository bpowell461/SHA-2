public class BlowTest
{
	public static void main(String[] args)
	{
	
		Blowfish test = new Blowfish();

		String message = "enc";

		System.out.println(test.encrypt(message, "brad"));

	}
}	
