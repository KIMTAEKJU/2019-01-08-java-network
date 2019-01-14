package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class ChatServerThread extends Thread {

	ArrayList<PrintWriter> printWriterPool;	
	private Socket socket;
	private String nickName;
	
	public ChatServerThread(ArrayList<PrintWriter> printWriterPool, Socket socket) {
		// TODO Auto-generated constructor stub
		this.printWriterPool = printWriterPool;
		this.socket = socket;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		InetAddress socketIPAddress = socket.getInetAddress();
		int socketPORT = socket.getLocalPort();
		System.out.println("[client] " + socketIPAddress + ":" + socketPORT);
		
		try 
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			Scanner scan = new Scanner(System.in);
			
			
			while (true)
			{
				try
				{
					String request = br.readLine();
					System.out.println(request);
					
					if (request == null)
					{
						doQuit(pw);
						break;
					}
					
					String[] tokens = request.split(":");
					
					if ("join".equals(tokens[0]))
						doJoin(tokens[1], pw);
					
					else if("msg".equals(tokens[0]))
						doMsg(tokens[1]);
					
					else if("quit".equals(tokens[0]))
					{
						doQuit(pw);
						break;
					} 
				}
				catch(SocketException e)
				{
					System.out.println("강제종료");
					break;
				}
				finally {
					if (socket == null && !socket.isClosed())
						socket.close();
				}
			
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void doQuit(PrintWriter pw)
	{
		printWriterPool.remove(pw);
		broadCast(nickName + "님이 퇴장하셨습니다");
	}
	
	public void broadCast(String data)
	{
		System.out.println("브로드캐스트");
		synchronized (printWriterPool) {
			for (PrintWriter p : printWriterPool)
			{
				System.out.println("#%$#%");
				p.println(data);
				p.flush();
			}
		}
	}
	
	public void doJoin(String nickname, PrintWriter writer) 
	{
		System.out.println("dojoin 실행");
		this.nickName = nickname;
		     
		String data = nickname + "님이 입장하였습니다.";
	    broadCast(data);
		     
	    addWriter(writer);
	}
	
	public void doMsg(String msg)
	{
		System.out.println("doMsg 실행");
		broadCast(this.nickName + " : " + msg);
	}
	 
	public void addWriter(PrintWriter writer)
	{
		synchronized (writer) {
			printWriterPool.add(writer);
		}
	}


}
