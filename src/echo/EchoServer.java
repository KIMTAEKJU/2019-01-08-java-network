package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class EchoServer {

	private static final String SERVER_IP = "218.39.221.94";
	private static final int SERVER_PORT = 5000;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ServerSocket serverSocket = null;
		
		try 
		{
			serverSocket = new ServerSocket();
			InetAddress inetAddress = InetAddress.getLocalHost();
			InetAddress inetLocalAddress = inetAddress.getLocalHost();
			serverSocket.bind(new InetSocketAddress(inetLocalAddress, SERVER_PORT));
			System.out.println("[server] binding " + inetLocalAddress + ":" + SERVER_PORT);
			
			Socket socket = serverSocket.accept();
		
			InetAddress socketIPAddress = socket.getInetAddress();
			int socketPORT = socket.getLocalPort();
			System.out.println("[client] " + socketIPAddress + ":" + socketPORT);

			try
			{
				InputStream is = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				
				OutputStream os = socket.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
				PrintWriter pw = new PrintWriter(osw, true);
				
				while (true)
				{
					String value = br.readLine();
					if (value.equals(""))
						break;
					pw.println(value);
				}			
			}
			catch (Exception e) 
			{
				// TODO: handle exception
				e.printStackTrace();
			}
			finally 
			{
				try
				{

					if (socket != null && !socket.isClosed())
						socket.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		} 
	
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
				try {
					if (serverSocket != null && !serverSocket.isClosed())
						serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

}
