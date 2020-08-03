package mreza;

import java.awt.Canvas;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public abstract class Polje extends Canvas{
	protected Mreza mMreza;
	protected int[] niz;	
	
	public Polje(Mreza m) {
		this.mMreza = m;
		niz = new int[2];
	}
	

	public Mreza getmMreza() {
		return mMreza;
	}
	
	public int[] odrediPoziciju(){
		int[] niz = new int[2];
		Polje[][] mat = mMreza.getMatPolja();
		for(int i = 0; i < mat.length; i++) {
			for(int j = 0; j < mat.length; j++) {
				if(mat[i][j] != null)
				if(mat[i][j].equals(this)) {
					niz[0] = i; niz[1] = j;
					return niz;
				}
			}
		}
		
		return null;
	}
	
	public void postaviPoziciju(int i, int j) {
		//niz[0] = i; niz[1] = j;
	}
	
	public Polje udaljenoPolje(int i, int j) {
		Polje [][] mat = mMreza.getMatPolja();
		//int mI = niz[0]; int mJ = niz[1];
		int mI = odrediPoziciju()[0]; int mJ = odrediPoziciju()[1];
		int dim = mat.length;
	
		
		if(mI + i >= dim || mJ + j >= dim || mI + i < 0 || mJ + j < 0) return null;
		else return mat[mI + i][mJ + j];
	}
	
	public abstract boolean mozeFigura();
	
}
