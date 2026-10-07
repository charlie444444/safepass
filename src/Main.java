import java.util.Scanner;

public class Main
{
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args)
	{
		System.out.print("Witamy w generatorze bezpiecznych haseł SafePass!\n");
		do
		{
			String input = enterInput();
			Cipher.cipherType choice = chooseMethod();
			new Cipher(input, choice, sc);
		}
		while (askAgain());
	}

	private static String enterInput()
	{
		String input = "";
		while (input.length() < 8 || input.length() > 30)
		{
			System.out.print("""
          Wpisz tekst, który chcesz zaszyfrować.
          Kilka zasad:
          \t* Generator nie obsługuje polskich znaków i nie zostaną one zmodyfikowane
          \t* Generator nie modyfikuje znaków specjalnych ani wpisanych przez użytkownika liczb
          \t* Tekst nie powinien być krótszy niż 8 i nie dłuższy niż 30 znaków

          Tekst do zaszyfrowania:\s
          """);
			input = sc.nextLine();
		}
		return input;
	}

	private static Cipher.cipherType chooseMethod()
	{
		System.out.println("""
		Wybierz sposób szyfrowania
		\t[1] Szyfr Cezara - szyfr przesuwający, w którym każda litera w tekście jest zastępowana inną literą, oddaloną o stałą liczbę pozycji (klucz) w alfabecie.
		\t[2] Szyfr Atbasz - szyfr podstawieniowy, który zamienia litery na litery im przeciwne, np. A - Z, B - Y, C - X, etc...
		\t[3] Szyfr Polibiusz+ - szyfr bazujący na szyfrze Polibiusza, uwzględniający małe litery. Litery zastępowane są liczbami odpowiadającymi ich wierszom i kolumnom tabeli 5x5. Następnie dodawany jest określony przez użytkownika offset, który pozwala na zamianę niektórych liczb na litery
		\t[4] Szyfr kompleksowy - szyfrowanie szyfrem Cezara wyniku szyfrowania szyfrem Atbasza wyniku szyfrowania szyfrem Polibiusz+
		""");

		Cipher.cipherType choice = null;
		while (choice == null)
		{
			System.out.print("Twój wybór (1, 2, 3 lub 4): ");
			choice = switch (sc.nextLine().trim())
			{
				case "1" -> Cipher.cipherType.CAESAR;
				case "2" -> Cipher.cipherType.ATBASZ;
				case "3" -> Cipher.cipherType.POLYBIUSPLUS;
				case "4" -> Cipher.cipherType.COMPLEX;
				default -> null;
			};
		}
		return choice;
	}

	private static boolean askAgain()
	{
		while (true)
		{
			System.out.print("""
          Czy chcesz zaszyfrować kolejny tekst?
          [Y] Tak
          [N] Nie, zamknij program

          Twój wybór:\s""");
			String answer = sc.nextLine().trim();
			if (answer.equalsIgnoreCase("Y")) return true;
			if (answer.equalsIgnoreCase("N")) return false;
		}
	}
}
