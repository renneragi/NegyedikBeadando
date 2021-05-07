package negyedikBeadandoPcg;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.sun.glass.ui.TouchInputSupport;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.stage.Stage;
import negyedikBeadandoPcg.Vevo.VevoIdentity;

public class Jatekos extends Application {
	public static PrintWriter szerverCsatorna;
	private static Tabla jatekosTabla;
	private static MI AIJatekos;
	private static boolean tablaElerheto = false; 
	public static void main(String[] args) {
		try {
			Socket socket = new Socket(InetAddress.getLocalHost(), 12345);
			new Vevo(socket, VevoIdentity.Jatekos).start();
			
			szerverCsatorna = new PrintWriter(socket.getOutputStream());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		launch(args);

	}
	
	public static void IrjASzervernek (String message) {
		szerverCsatorna.println(message);
		szerverCsatorna.flush();
	}
	
	public void start(Stage primaryStage) throws Exception {
		 new JatekInditoKepernyo();
	}
	
	public static void tablaInit(Parent _root, int[] hajokKiosztasa, int N, int jatekosokSzama) {
		jatekosTabla = new Tabla(_root, hajokKiosztasa, N);
		if (jatekosokSzama == 1) {
			AIJatekos = new MI(hajokKiosztasa, N);
		}
		Stage newStage = new Stage();
		newStage.setTitle("Torpedo játék");
		newStage.setScene(jatekosTabla);
		newStage.show();	
		tablaElerheto = true;
	}
	
	public static synchronized void uzenetASzervertol(String uzenet, VevoIdentity vIdentity) {
		if(vIdentity == VevoIdentity.Jatekos) {
			if (uzenet.contains("talalt") && tablaElerheto) {
				jatekosTabla.lovesEredmenye(uzenet);
			}else if (uzenet.contains("VigyazzLovok") && tablaElerheto) {
				jatekosTabla.lottek(uzenet);
			}
			System.out.println("en jottem");
		}else if(vIdentity == VevoIdentity.AI)  {
			//AInak tovabbitsd az uzenetet
			System.out.println("AI jott");

			if (uzenet.contains("talalt") && tablaElerheto) {
				AIJatekos.lovesEredmenye(uzenet);
			}else if (uzenet.contains("VigyazzLovok") && tablaElerheto) {
				AIJatekos.lottek(uzenet);
			}
		}
		
	}
	
	
}
