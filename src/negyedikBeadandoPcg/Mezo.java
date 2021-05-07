package negyedikBeadandoPcg;

public class Mezo {
	public enum Allapot {
		talalt, nemtalalt, hajo, tenger, megnemtudjuk,
	}
	private Allapot allapot;
	private int coordX;
	private int coordY;
	
	public Mezo() {
		this.allapot = Allapot.tenger;
		this.coordX = 0;
		this.coordY = 0;
	}
	
	public Mezo(Allapot a, int x, int y) {
		this.allapot = a;
		this.coordX = x;
		this.coordY = y;
	}
	
	public Allapot getAllapot() {
		return allapot;
	}
	
	public void setAllapot(Allapot allapot) {
		this.allapot = allapot;
	}
	
	public int getCoordX() {
		return coordX;
	}
	
	public int getCoordY() {
		return coordY;
	}
	
	public String toString() {
		return this.allapot + "  " + this.coordX + "  " + this.coordY;
	}

}
