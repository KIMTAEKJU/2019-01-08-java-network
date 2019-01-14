package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class UDPTimeClient {

	private static final String SERVER_IP = "218.39.221.94";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner scan = null;
		DatagramSocket socket = null;
		
		try {
			scan = new Scanner(System.in);
			
			socket = new DatagramSocket();
			
//			while (true)
//			{
				System.out.println(">> ");
				String message = "";
				
				//if ("quit".equals(message))
					//break;
				
				byte[] data = message.getBytes("UTF-8");
				DatagramPacket sendPacket = new DatagramPacket(data, data.length, new InetSocketAddress(SERVER_IP, UDPTimeServer.PORT));
				socket.send(sendPacket);
				
				DatagramPacket receivePacket = new DatagramPacket(new byte[UDPTimeServer.BUFFER_SIZE], UDPTimeServer.BUFFER_SIZE);
				socket.receive(receivePacket);
				
				message = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
				System.out.println("<< " + message);
	//		}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
