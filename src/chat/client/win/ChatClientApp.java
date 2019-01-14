package chat.client.win;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import chat.ChatClientThread;

public class ChatClientApp {

	private static final String SERVER_IP = "218.39.221.94";
	private static final int SERVER_PORT = 5000;
	
	public static void main(String[] args) {
		
		String name = null;
		Scanner scanner = new Scanner(System.in);
		
		Socket socket = null;
		
		try 
		{
			socket = new Socket();
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			Scanner scan = new Scanner(System.in);
			
			while( true ) 
			{
				System.out.println("대화명을 입력하세요.");
				System.out.print(">>> ");
				name = scanner.nextLine();
				
				if (!name.isEmpty())
				{
					pw.println("join:" + name);
					break;
				}
				
				System.out.println("대화명은 한글자 이상 입력해야 합니다.\n");
			}
			
			ChatWindow cw = new ChatWindow(name, pw, socket);
			
			cw.show();

		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		scanner.close();
		
		// join 처리
		// response가 "join:ok" 이면
	}

}
