package negyedikBeadandoSzerver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;


public class Kliens implements Runnable {
		
	static int numberOfClients = 0;
	static List<Socket> clients = new LinkedList<Socket>();
	
	private Socket socket;
	private int id;
	
	private BufferedReader inputStream;
	private PrintWriter outputStream;

	public Kliens(Socket socket) {
		this.socket = socket;
		this.id = numberOfClients++;
		clients.add(socket);
		
	}
	
	@Override
	public void run() {
		try {
			this.inputStream = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			this.outputStream = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()));
			
			while(true) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String line = "";
				if (this.inputStream.ready()) {
					line = this.inputStream.readLine();
					System.out.println(line);
					for (Socket socket : clients) {
						if (socket != this.socket) {
							PrintWriter pwr = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
							pwr.println(line);
							pwr.flush();
						}
					}
				}	
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}

}
