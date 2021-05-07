package negyedikBeadandoSzerver;

public class Szerver {

	public static void main(String[] args) {
		Listener hallgato = new Listener();
		Thread hallgatoSzal = new Thread(hallgato);
		hallgatoSzal.start();
	}

}
