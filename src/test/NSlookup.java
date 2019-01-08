package test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSlookup{

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scan = new Scanner(System.in);
		String userInput = null;
		
		try 
		{
			while(true) 
			{
				System.out.print(">");
				userInput = scan.nextLine();
				
				if (userInput.equals("exit"))
					break;
				InetAddress[] inetAddress = InetAddress.getAllByName(userInput);
				
				for (InetAddress i : inetAddress)
				{
					System.out.print(i.getHostName() + " : ");
					System.out.println(i.getHostAddress());
				}
			}
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			scan.close();
		}
	}

}
