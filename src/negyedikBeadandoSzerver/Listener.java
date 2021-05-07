package negyedikBeadandoSzerver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Listener implements Runnable {
	
	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(12345);
			serverSocket.setSoTimeout(50);
			
			while (!Thread.interrupted()) {
				try {
					Socket clientSocket = serverSocket.accept();
					System.out.println(clientSocket.getInetAddress().getHostName());
					Kliens kliens = new Kliens(clientSocket);
					Thread kliensSzal = new Thread(kliens);
					kliensSzal.start();
				} catch (SocketTimeoutException e) {
				}
			}
			
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

