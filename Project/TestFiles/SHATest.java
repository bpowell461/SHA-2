import java.util.Scanner;

public class SHATest{
	public static void main(String[] args)
	{
    Scanner stdin = new Scanner(System.in);
    System.out.print("Message to be hashed: ");
    String input = stdin.nextLine();
		SHA2 hash = new SHA2(input);
    System.out.println("\n");
    System.out.println(hash);
	}
}
