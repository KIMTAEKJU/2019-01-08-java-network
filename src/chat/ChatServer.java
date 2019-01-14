package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer 
{
	private static final int port = 5000;
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null;
		ArrayList<PrintWriter> printWriterPool = new ArrayList<>();
		 
		try {
			serverSocket = new ServerSocket();
			
			// 옵션 SO_REUSEADDR 사용 (종료후 빨리 바인딩을 하기 위해서)
			serverSocket.setReuseAddress(true);
			
			InetAddress inetAddress = InetAddress.getLocalHost();
			serverSocket.bind(new InetSocketAddress(inetAddress, port));
			System.out.println("[server] binding " + inetAddress + ":" + port);
			
			while (true)
			{
				Socket socket = serverSocket.accept();
				
				new ChatServerThread(printWriterPool, socket).start();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
