package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClientThread extends Thread{

	Socket socket;
	//PrintWriter pw;
	BufferedReader br;
	
	public ChatClientThread(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
		//this.pw = pw;
		//this.br = br;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Scanner scan = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			//scan = new Scanner(System.in);
			while (true)
			{
				String data = br.readLine();
				System.out.println(data);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
