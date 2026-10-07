import java.util.Locale;
import java.util.Scanner;

public class Cipher
{
	enum cipherType {CAESAR, ATBASZ, POLYBIUSPLUS, COMPLEX}
	private Scanner sc;

	public Cipher(String input, cipherType cypherType, Scanner sc)
	{
		this.sc = sc;
		switch(cypherType)
		{
			case CAESAR -> System.out.println("Twoje nowe, bezpieczne hasło to: " + caesar(input));
			case ATBASZ -> System.out.println("Twoje nowe, bezpieczne hasło to: " + atbasz(input));
			case POLYBIUSPLUS -> System.out.println("Twoje nowe, bezpieczne hasło to: " + polybiusPlus(input));
			case COMPLEX -> System.out.println("Twoje nowe, bezpieczne hasło to: " + complex(input));
		}
	}

	private String caesar(String input)
	{
		StringBuilder result = new StringBuilder();
		input = input.toUpperCase(Locale.ROOT);

		int steps = readInt("Wprowadź liczbę przesunięć: ", 2, 10);

		for(char c : input.toCharArray())
		{
			if (c >= 'A' && c <= 'Z') result.append((char) ('A' + (c - 'A' + steps) % 26));
			else if (c >= 'a' && c <= 'z') result.append((char) ('a' + (c - 'a' + steps) % 26));
			else result.append(c);
		}

		return result.toString();
	}

	private String atbasz(String input)
	{
		StringBuilder result = new StringBuilder();

		for(char c : input.toCharArray())
		{
			if (Character.isLetter(c) && c != ' ')
			{
				if (c >= 'A' && c <= 'Z') result.append((char) ('Z' - (c - 'A')));
				else if (c >= 'a' && c <= 'z') result.append((char) ('z' - (c - 'a')));
				else result.append(c);
			}
			else result.append(c);
		}
		return result.toString();
	}

	private String polybiusPlus(String input)
	{
		StringBuilder result = new StringBuilder();

		int offset = 0;
		while (offset < 10 || offset > 63)
			offset = readInt("Wprowadź offset: ", 55, 63);


		for(char c : input.toCharArray())
		{
			int number = 0;

			switch(c)
			{
				case 'A', 'B', 'C', 'D', 'E', 'a', 'b', 'c', 'd', 'e' -> number += 10;
				case 'F', 'G', 'H', 'I', 'J', 'K', 'f', 'g', 'h', 'i', 'j', 'k' -> number += 20;
				case 'L', 'M', 'N', 'O', 'P', 'l', 'm', 'n', 'o', 'p' -> number += 30;
				case 'Q', 'R', 'S', 'T', 'U', 'q', 'r', 's', 't', 'u' -> number += 40;
				case 'V', 'W', 'X', 'Y', 'Z', 'v', 'w', 'x', 'y', 'z' -> number += 50;

				default -> { result.append(c); continue; }
			}

			switch(c)
			{
				case 'A', 'F', 'L', 'Q', 'V' -> number += 1;
				case 'B', 'G', 'M', 'R', 'W' -> number += 2;
				case 'C', 'H', 'N', 'S', 'X' -> number += 3;
				case 'D', 'I', 'J', 'O', 'T', 'Y' -> number += 4;
				case 'E', 'K', 'P', 'U', 'Z' -> number += 5;
				case 'a', 'f', 'l', 'q', 'v' -> number += 6;
				case 'b', 'g', 'm', 'r', 'w' -> number += 7;
				case 'c', 'h', 'n', 's', 'x' -> number += 8;
				case 'd', 'i', 'j', 'o', 't', 'y' -> number += 9;
			}

			number += offset;

			if ((number >= 65 && number <= 90) || (number >= 97 && number <= 122)) result.append((char) number);
			else result.append(number);
		}
		return result.toString();
	}

	private String complex(String input)
	{
		return polybiusPlus(atbasz(caesar(input)));
	}

	private int readInt(String prompt, int min, int max)
	{
		while (true)
		{
			System.out.print(prompt);
			try
			{
				int value = Integer.parseInt(sc.nextLine().trim());
				if (value >= min && value <= max) return value;
			}
			catch (NumberFormatException ignored) {}
			System.out.println("Podaj liczbę z zakresu " + min + "-" + max + ".");
		}
	}
}
