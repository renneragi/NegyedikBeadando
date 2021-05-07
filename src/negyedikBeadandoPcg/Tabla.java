package negyedikBeadandoPcg;


import java.util.Map;
import java.util.Random;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import negyedikBeadandoPcg.Mezo.Allapot;
import java.util.HashMap;

public class Tabla extends Scene{
	private int N = 0;
	private Mezo[][] sajatTabla;
	private Mezo[][] ellenfelTabla;
	private int[] hajoKiosztas;
	private Map<String, Button> sajatGombok;
	private Map<String, Button> ellenGombok;

	public Tabla(Parent _root, int[] hajokKiosztasa, int N) {
		super(_root, 2*26*N+270,26*N + 135);
		this.N = N;
		this.hajoKiosztas = hajokKiosztasa;
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
		
		sajatGombok = new HashMap<String, Button>();
		ellenGombok = new HashMap<String, Button>();
		
		AnchorPane root = new AnchorPane();
		Node balTabla = this.sajatTablaGeneralasa(N);
		Node jobbTabla = this.ellenfelTablaGeneralas(N);

		root.getChildren().addAll(balTabla, jobbTabla);
		AnchorPane.setRightAnchor(jobbTabla, 90d);
		AnchorPane.setBottomAnchor(jobbTabla, 45d);
		AnchorPane.setLeftAnchor(balTabla, 90d);
		AnchorPane.setBottomAnchor(balTabla, 45d);

		this.setRoot(root);
		this.getStylesheets().add("style.css");
	}
	
	public void lovok(int i, int j) {
		Jatekos.IrjASzervernek("VigyazzLovok " + i + " " + j);
	}
	
	public void lovesEredmenye(String eredmeny) {
		String[] e = eredmeny.split(" ");
		if (e.length == 3) {
			int i = Integer.parseInt(e[1]);
			int j = Integer.parseInt(e[2]);

			if (e[0].equals("talalt")) {
				loTablaFrissitese(i, j, Allapot.talalt);
				this.ellenfelTabla[i][j].setAllapot(Allapot.talalt);
			}else {
				loTablaFrissitese(i, j, Allapot.nemtalalt);
				this.ellenfelTabla[i][j].setAllapot(Allapot.nemtalalt);
			}
		}
	}
	
	private void loTablaFrissitese(int i, int j, Allapot a) {
		ellenGombok.get(i + "" + j).getStyleClass().clear();
		if (a == Allapot.nemtalalt) {
			ellenGombok.get(i + "" + j).getStyleClass().add("nemtalalt");
		} else if (a == Allapot.talalt) {
			ellenGombok.get(i + "" + j).getStyleClass().add("talalt");
		}
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
			
			this.szinekFrissitese(i, j, sajatTabla[i][j].getAllapot());
			
			//megirja a szervernek az eredmenyt
			Jatekos.IrjASzervernek( talalt ? "talalt " + i + " " + j : "nemtalalt " + i + " " + j );
		}
	}
	
	private void szinekFrissitese(int i, int j, Allapot a) {
		sajatGombok.get(i + "" + j).getStyleClass().clear();
		if (a == Allapot.nemtalalt) {
			sajatGombok.get(i + "" + j).getStyleClass().add("nemtalalt");
		} else if (a == Allapot.talalt) {
			sajatGombok.get(i + "" + j).getStyleClass().add("talalt");
		}
	}
	
	private Node addMezo(int i, int j) {	
		Button mezo = new Button();
		mezo.setPrefHeight(26d);
		mezo.setPrefWidth(26d);
		if (this.sajatTabla[i][j].getAllapot() == Allapot.tenger) {
			mezo.getStyleClass().clear();
			mezo.getStyleClass().add("tenger");
		}else if  (this.sajatTabla[i][j].getAllapot() == Allapot.hajo) {
			mezo.getStyleClass().clear();
			mezo.getStyleClass().add("hajo");
		}
		mezo.setOnAction(EventHandler -> {
			System.out.println(this.sajatTabla[i][j].toString());
		});
		
		this.sajatGombok.put(i + "" + j, mezo);
		return mezo;

	}
	
	private Node addEllenfelMezo(int i, int j) {	
		Button mezo = new Button();
		mezo.setPrefHeight(26d);
		mezo.setPrefWidth(26d);
		if (this.ellenfelTabla[i][j].getAllapot() == Allapot.megnemtudjuk) {
			mezo.getStyleClass().clear();
			mezo.getStyleClass().add("megnemtudjuk");
		}
		mezo.setOnAction(EventHandler -> {
			this.lovok(i, j);
		});
		this.ellenGombok.put(i + "" + j, mezo);

		return mezo;

	}
	
	private Node ellenfelTablaGeneralas(int N) {
		GridPane root = new GridPane();
		root.getStyleClass().add("gridPane");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				root.add(this.addEllenfelMezo(i, j), i, j);
			}
		}
		return root;
	}
	
	private Node sajatTablaGeneralasa(int N) {
		GridPane root = new GridPane();
		root.getStyleClass().add("gridPane");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				root.add(this.addMezo(i, j), i, j);
			}
		}
		return root;
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

}

