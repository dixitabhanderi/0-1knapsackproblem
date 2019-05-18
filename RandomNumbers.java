/*
Dixita Bhanderi
CSC206 Assignment 4
Spring 2019
OS: Mac
*/
import java.util.Random;


public final class RandomNumbers
{
	private static Random r;

	public static int nextNumber()
	{
		if (r == null)
			seed();

		return r.nextInt();
	}

	public static int nextNumber(int ceiling)
	{
		if (r == null)
			seed();

		return r.nextInt(ceiling);
	}

	public static void seed()
	{
		r = new Random();
	}

	public static void seed(int seed)
	{
		r = new Random(seed);
	}
}