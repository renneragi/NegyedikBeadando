package negyedikBeadandoPcg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Vevo extends Thread {
	private Socket socket;
	
	public enum VevoIdentity{
		Jatekos, AI,
	}
	private VevoIdentity vevoIdentity;
	
	public Vevo(Socket socket, VevoIdentity V) {
		this.socket = socket;
		this.vevoIdentity = V;
	}

	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line;
			while (true) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (in.ready()) {
					line = in.readLine();
					Jatekos.uzenetASzervertol(line, this.vevoIdentity);
					System.out.println(line + this.vevoIdentity);
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

