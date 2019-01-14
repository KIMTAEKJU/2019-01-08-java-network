package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sound.midi.Receiver;

public class UDPTimeServer {

	public static final int PORT = 6000;
	public static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			DatagramSocket socket = new DatagramSocket(PORT);
			DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);

			while (true)
			{
				socket.receive(receivePacket);
				byte[] data = receivePacket.getData();
				int length = receivePacket.getLength();
				String message = new String(data, 0, length, "UTF-8");
				System.out.println("[server] received : " + message);
				
				data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a").format(new Date()).getBytes();
				DatagramPacket sendPacket = new DatagramPacket(data, data.length, receivePacket.getAddress(), receivePacket.getPort());
				socket.send(sendPacket);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
