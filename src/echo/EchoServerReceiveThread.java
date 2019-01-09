package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerReceiveThread extends Thread {

	private Socket socket;
	
	public EchoServerReceiveThread(Socket socket) {
		// TODO Auto-generated constructor stub
		
		this.socket = socket;
	}
	
	@Override
	public void run() {
		
		
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
				System.out.println(value);
				if (value.equals("exit"))
				{
					System.out.println("closed Client");
					break;
				}
				
				pw.println(value);
			}			
		}
		catch (SocketException e) 
		{
			// TODO: handle exception
			System.out.println("[server] abnormal close");
		}
		catch (IOException e) 
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
}
