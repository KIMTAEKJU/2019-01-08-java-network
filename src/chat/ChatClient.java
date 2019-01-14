package chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatClient extends ChatServer {

	private static final String IPADDRESS = "218.39.221.93";
	private static final int PORT = 5000;
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Socket socket;
		
		try 
		{
			socket = new Socket();
			
			socket.connect(new InetSocketAddress(IPADDRESS, PORT));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			Scanner scan = new Scanner(System.in);
			
			System.out.print("닉네임 : ");
			pw.println("join:" + scan.nextLine());
			
			new ChatClientThread(socket).start();

			while (true)
			{
				String data = scan.nextLine();
				if (data.equals("quit"))
					pw.println(data);
				pw.println("message:" + data);
			}
			
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
		}
	}

}
