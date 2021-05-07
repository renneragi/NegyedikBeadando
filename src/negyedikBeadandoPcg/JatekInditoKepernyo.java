package negyedikBeadandoPcg;

import java.util.stream.IntStream;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class JatekInditoKepernyo extends Stage {

	private int jatekosokSzama = 1;
	private int[] hajokSzama = {1,1,1,1};

	private Node hajo(int hanyHosszu) {
		HBox hajok = new HBox(3);
		hajok.setAlignment(Pos.BASELINE_CENTER);
		Label neve = new Label(hanyHosszu + " hosszú hajó");
		Button novel = new Button("+");
		Button csokkent = new Button("-");
		Label hanyDB = new Label(this.hajokSzama[hanyHosszu-2]+ " db hajó");
		novel.setOnAction(EventHandler -> {
			this.hajokSzama[hanyHosszu-2]++;
			hanyDB.setText(this.hajokSzama[hanyHosszu-2]+ " db hajó");
		});
		csokkent.setOnAction(EventHandler -> {
			int sum = IntStream.of(this.hajokSzama).sum();
			if ( this.hajokSzama[hanyHosszu-2] > 0 && sum > 1 ) {
				this.hajokSzama[hanyHosszu-2]--;
			}
			hanyDB.setText(this.hajokSzama[hanyHosszu-2]+ " db hajó");
		});
		
		hajok.getChildren().add(neve);
		hajok.getChildren().add(csokkent);
		hajok.getChildren().add(novel);
		hajok.getChildren().add(hanyDB);
		return hajok;
	}
	
	private Node jatekos() {
		HBox jatekos = new HBox(3);
		jatekos.setAlignment(Pos.BASELINE_CENTER);
		Label neve = new Label("Játékos száma: ");
		Button novel = new Button("+");
		Button csokkent = new Button("-");
		Label hanyDB = new Label(this.jatekosokSzama+ " db");
		novel.setOnAction(EventHandler -> {
			this.jatekosokSzama++;
			hanyDB.setText(this.jatekosokSzama+ " db");
		});
		csokkent.setOnAction(EventHandler -> {
			if(this.jatekosokSzama > 1) {
				this.jatekosokSzama--;
			}
			hanyDB.setText(this.jatekosokSzama + " db");
		});
		
		jatekos.getChildren().add(neve);
		jatekos.getChildren().add(csokkent);
		jatekos.getChildren().add(novel);
		jatekos.getChildren().add(hanyDB);
		return jatekos;
	}

	public JatekInditoKepernyo() {
		setTitle("Torpedó");
	    VBox root = new VBox(10);
	    root.setAlignment(Pos.TOP_CENTER);
	    // set title
	    Label title = new Label("Játék beállítás");
	    title.setFont(Font.font(24));
	    root.getChildren().add(title);
	    for (int i = 0; i < 4; i++ ) {
	    		root.getChildren().add(this.hajo(i+2));
	    }
	    root.getChildren().add(this.jatekos());
	    Button inditas = new Button("Játék indítása");
	    inditas.setOnAction(EventHandler -> {
	    		Parent tablaRoot = new HBox(2);
	    		int szummaHajokHossza = (this.hajokSzama[0] * 1 + this.hajokSzama[1] * 2 + this.hajokSzama[2] * 3 + this.hajokSzama[3] * 4);
	    		int n = (int) Math.ceil((szummaHajokHossza * Math.ceil((double)this.jatekosokSzama / 2.0) + 1 ) / 2.0);
	    		Jatekos.tablaInit(tablaRoot, this.hajokSzama, n, this.jatekosokSzama);
	    });
	    root.getChildren().add(inditas);
	    Scene scene = new Scene(root, 300, 300);
	    setScene(scene);
	    show();
	 }
}
