package mreza;

public abstract class Figura {
	protected Polje mPolje;
	
	public Figura(Polje polje) {
		mPolje = polje;
	}
	
	public Polje getmPolje() {
		return mPolje;
	}
	
	public void pomeriFiguru(Polje polje) {
		if(polje.mozeFigura())
		mPolje = polje;	
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Figura other = (Figura) obj;
		
		if(mPolje.odrediPoziciju()[0] == other.mPolje.odrediPoziciju()[0] && mPolje.odrediPoziciju()[1] == other.mPolje.odrediPoziciju()[1])
			return true;
		
		else return false;
	}
	
	public abstract void iscrtajFiguru();
}
