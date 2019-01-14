package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class UDPEchoClient 
{
	private static final String SERVER_IP = "218.39.221.93";
	
	public static void main(String[] args)
	{
		Scanner scan = null;
		DatagramSocket socket = null;

		try
		{
			// 1. 키보드 연결
			scan = new Scanner(System.in);

			// 2. 소켓 생성
			socket = new DatagramSocket();

			while (true)
			{
				// 3. 키보드 입력
				System.out.print(">>");
				String message = scan.nextLine();

				if ("quit".equals(message))
					break;

				// 4. 메세지 전송
				byte[] data = message.getBytes("UTF-8");
				DatagramPacket sendPacket = new DatagramPacket(data, data.length, new InetSocketAddress(SERVER_IP, UDPEchoServer.PORT));

				socket.send(sendPacket);

				// 5. 메세지 수신
				DatagramPacket receivePacket = new DatagramPacket(new byte[UDPEchoServer.BUFFER_SIZE], UDPEchoServer.BUFFER_SIZE);
				socket.receive(receivePacket);
				
				message = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
				System.out.println("<<" + message);
			}

		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			// 자원정리
			if (scan != null)
				scan.close();
			
			if (socket != null && !socket.isClosed())
				socket.close();
		}
	}

}
