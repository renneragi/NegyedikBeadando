package negyedikBeadandoPcg;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import negyedikBeadandoPcg.Mezo.Allapot;
import negyedikBeadandoPcg.Vevo.VevoIdentity;

public class MI {
	private int N = 0;
	private Mezo[][] sajatTabla;
	private Mezo[][] ellenfelTabla;
	private int[] hajoKiosztas;
	private Socket socket;
	public PrintWriter szerverCsatorna;
	public MI(int[] hajokKiosztasa, int N) {
		this.N = N;
		this.hajoKiosztas = hajokKiosztasa;
		this.socket = socket;
		try {
			socket = new Socket(InetAddress.getLocalHost(), 12345);
			new Vevo(socket, VevoIdentity.AI).start();
			szerverCsatorna = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sajatTabla = new Mezo[N][N];
		ellenfelTabla = new Mezo[N][N];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				sajatTabla[i][j] = new Mezo(Allapot.tenger, i, j);
			}
		}
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				ellenfelTabla[i][j] = new Mezo(Allapot.megnemtudjuk, i, j);
			}
		}
		
		this.osszdKiAHajokat(this.hajoKiosztas);
	}
	
	private void IrjASzervernek (String message) {
		szerverCsatorna.println(message);
		szerverCsatorna.flush();
	}
	
	private void osszdKiAHajokat(int[] hajokiosztas) {
		Random random = new Random();
		for (int hajoHossz = 5; hajoHossz > 1; hajoHossz--) {
			for (int hajokSzama = 0; hajokSzama < hajokiosztas[hajoHossz-2]; hajokSzama++) {
				boolean vizszintesIrany = random.nextBoolean();
				boolean hajoLerakva = false;
				while (!hajoLerakva) {
					if (vizszintesIrany) {
						int randX = random.nextInt(N - hajoHossz);
						int randY = random.nextInt(N);
						if (this.szabadATerulet(vizszintesIrany, randX, randY, hajoHossz)) {
							hajoLerakva = true;
						}
					}else {
						int randY = random.nextInt(N - hajoHossz);
						int randX = random.nextInt(N);
						if (this.szabadATerulet(vizszintesIrany, randX, randY, hajoHossz)) {
							hajoLerakva = true;
						}
					}
				}
			}
		}
	}
	
	private boolean szabadATerulet(boolean vizszintes, int x, int y, int hajoHossz) {
		for (int i = 0 ; i < hajoHossz; i++) {
			if (vizszintes) {
				Allapot allapot = this.sajatTabla[x+ i][y].getAllapot();
				if (allapot != Allapot.tenger) {
					return false;
				}
			} else {
				Allapot allapot = this.sajatTabla[x][y + i].getAllapot();
				if (allapot != Allapot.tenger) {
					return false;
				}
			}
		}
		for (int i = 0 ; i < hajoHossz; i++) {
			if (vizszintes) {
				this.sajatTabla[x+ i][y].setAllapot(Allapot.hajo);
			} else {
				this.sajatTabla[x][y + i].setAllapot(Allapot.hajo);
			}
		}
		
		return true;
	}
	
	private void lovok() {
		Random random = new Random();
		int x = 0;
		int y = 0;
		boolean vanJoXY = false;
		while (!vanJoXY) {
			x = random.nextInt(N);
			y = random.nextInt(N);
			if (ellenfelTabla[x][y].getAllapot() == Allapot.megnemtudjuk) {
				vanJoXY = true;
			}
		}
		Jatekos.IrjASzervernek("VigyazzLovok " + x + " " + y);
	}
	
	public void lottek(String u) {
		String[] e = u.split(" ");
		if (e.length == 3) {
			int i = Integer.parseInt(e[1]);
			int j = Integer.parseInt(e[2]);
			//ha talal akkor beirja maganak
			boolean talalt = false;
			
			if (sajatTabla[i][j].getAllapot() == Allapot.hajo) {
				talalt = true;
				sajatTabla[i][j].setAllapot(Allapot.talalt);
			}else if (sajatTabla[i][j].getAllapot() == Allapot.tenger){
				sajatTabla[i][j].setAllapot(Allapot.nemtalalt);
			}
						
			//megirja a szervernek az eredmenyt
			Jatekos.IrjASzervernek( talalt ? "talalt " + i + " " + j : "nemtalalt " + i + " " + j );
			if(!talalt) {
				this.lovok();
			}
		}
	}
	
	public void lovesEredmenye(String eredmeny) {
		String[] e = eredmeny.split(" ");
		if (e.length == 3) {
			int i = Integer.parseInt(e[1]);
			int j = Integer.parseInt(e[2]);

			if (e[0].equals("talalt")) {
				this.ellenfelTabla[i][j].setAllapot(Allapot.talalt);
			}else {
				this.ellenfelTabla[i][j].setAllapot(Allapot.nemtalalt);
			}
		}
	}

}
