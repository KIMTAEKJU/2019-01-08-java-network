package http;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;

public class RequestHandler extends Thread {
	private Socket socket;

	public RequestHandler( Socket socket ) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			// logging Remote Host IP Address & Port
			InetSocketAddress inetSocketAddress = ( InetSocketAddress )socket.getRemoteSocketAddress();
			consoleLog( "connected from " + inetSocketAddress.getAddress().getHostAddress() + ":" + inetSocketAddress.getPort() );

			// get IOStream
			OutputStream outputStream = socket.getOutputStream(); // 문자열 이미지를 바이트로보내는게 편함 
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

			//consoleLog("=========Request============");
			String request = null;

			while (true)
			{
				String line = br.readLine();

				// 브라우저가 연결을 끊음
				if (line == null) // 끝까지 다읽음
					break;

				// 헤더만 읽음 개행으로 구분
				if ("".equals(line))
					break;

				// 헤더의 첫번째 라인만 처리
				if (request == null)
					request = line;
			}

			consoleLog(request);
			
			String[] tokens = request.split(" ");
			
			if ("GET".equals(tokens[0]))
			{
				responseStaticResource(outputStream, tokens[1], tokens[2]);
			}
			else // POST, PUT, DELETE
			{
				consoleLog("bad request : " + request);
				response400Error(outputStream, tokens[2]);
				/*
				 	HTTP/1.0 400 Bad Request
			 		Content-Type:text/html; charset=utf-8\r\n
			 		\r\n


				 */
			}



			// 예제 응답입니다.
			// 서버 시작과 테스트를 마친 후, 주석 처리 합니다.
			//outputStream.write( "HTTP/1.1 200 OK\r\n".getBytes( "UTF-8" ) ); // 헤더 개행으로 구분
			//outputStream.write( "Content-Type:text/html; charset=utf-8\r\n".getBytes( "UTF-8" ) ); // 헤더
			//outputStream.write( "\r\n".getBytes() );
			//outputStream.write( "<h1>이 페이지가 잘 보이면 실습과제 SimpleHttpServer를 시작할 준비가 된 것입니다.</h1>".getBytes( "UTF-8" ) );

		} catch( Exception ex ) {
			consoleLog( "error:" + ex );
		} finally {
			// clean-up
			try{
				if( socket != null && socket.isClosed() == false ) {
					socket.close();
				}

			} catch( IOException ex ) {
				consoleLog( "error:" + ex );
			}
		}			
	}

	private void responseStaticResource(OutputStream outputStream, String url, String protocol) throws IOException
	{
		if ("/".equals(url))
			url = "/index.html";

		File file = new File("./webapp/" + url);

		if (!file.exists())
		{
			reponse404Error(outputStream, protocol);
			/* 
			 HTTP/1.0 404 File Not Found\r\n
			 Content-Type:text/html; charset=utf-8\r\n

			 404 파일을 읽어서 바디에붙여서 보냄
			 */
			return;
		}
		
		// nio
		byte[] body = Files.readAllBytes(file.toPath());
		String contentType = Files.probeContentType(file.toPath());

		// 응답
		outputStream.write( "HTTP/1.1 200 OK\r\n".getBytes( "UTF-8" ) ); // 헤더 개행으로 구분
		//outputStream.write( "Content-Type:text/html; charset=utf-8\r\n".getBytes( "UTF-8" ) ); // 헤더
		outputStream.write( ("Content-Type:" + contentType + "; charset=utf-8\r\n").getBytes( "UTF-8" )); // 헤더

		outputStream.write( "\r\n".getBytes() );
		outputStream.write(body);
	}


	public void consoleLog( String message ) {
		System.out.println( "[RequestHandler#" + getId() + "] " + message );
	}

	public void response400Error(OutputStream outputStream, String protocol) throws IOException
	{
		File file_400 = new File("./webapp/error/400.html");

		byte[] body = Files.readAllBytes(file_400.toPath());
		String contentType = Files.probeContentType(file_400.toPath());

		outputStream.write((protocol + " 400 Bad Request\r\n").getBytes("UTF-8"));
		outputStream.write(("Content-Type:" + contentType + "; charset=utf-8\r\n").getBytes("UTF-8"));
		outputStream.write("\r\n".getBytes());
		outputStream.write(body);
	}

	public void reponse404Error(OutputStream outputStream, String protocol) throws IOException
	{
		File file_404 = new File("./webapp/error/404.html");

		byte[] body = Files.readAllBytes(file_404.toPath());
		String contentType = Files.probeContentType(file_404.toPath());

		outputStream.write((protocol + " 404 Bad Request\r\n").getBytes("UTF-8"));
		outputStream.write(("Content-Type:" + contentType + "; charset=utf-8\r\n").getBytes("UTF-8"));
		outputStream.write("\r\n".getBytes());
		outputStream.write(body);
	}

}
