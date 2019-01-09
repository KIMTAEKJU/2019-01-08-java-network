package thread;

public class UpperCaseAlpabetic extends Thread {
	public void print()
	{
		for (char c = 'A'; c <= 'Z'; c++)
			System.out.print(c);
	}
}
