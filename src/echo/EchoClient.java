package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner scan = null;
		Socket socket = null;
		try 
		{
			socket = new Socket();
			scan = new Scanner(System.in);
			socket.connect(new InetSocketAddress("218.39.221.94", 5000));
			
			InputStream is = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "UTF-8");
			BufferedReader br = new BufferedReader(isr);
			
			OutputStream os = socket.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
			PrintWriter pw = new PrintWriter(osw, true);
			
			while (true)
			{
				System.out.print(">>");
				String data = scan.nextLine();
				pw.println(data);
				
				String receive = br.readLine();
				if (receive == null)
					break;
				System.out.println("<<" + receive);
			}
			
		} 
		catch (IOException e) 
		{
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
