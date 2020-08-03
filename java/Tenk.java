package mreza;

import java.awt.*;

public class Tenk extends Figura implements Runnable {
	private Thread nitTenk;
	private boolean radi = false;
	private Smer smer;
	
	private enum Smer {LEVO, DESNO, GORE, DOLE, DIJAG};
	public Tenk(Polje polje) {
		super(polje);
		mPolje = polje;
		nitTenk = new Thread(this);
		nitTenk.start();
	}

	@Override
	public void iscrtajFiguru() {
		Graphics g = mPolje.getGraphics();
		g.setColor(Color.BLACK);
		int sirinaPolja = mPolje.getWidth();
		int visinaPolja = mPolje.getHeight();
		
		g.drawLine(0, 0, sirinaPolja, visinaPolja);
		g.drawLine(sirinaPolja, 0, 0, visinaPolja);
	}

	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				synchronized(this) {
					while(!radi) {
						wait();
					}
				}
				Thread.sleep(500);
				
				int i = 0; int j = 0;
				boolean flag = false;
				
				while(!flag) {	
						i = (int)(Math.random() * 3) - 1;
						j = (int)(Math.random() * 3) - 1;
						String s = i + "," + j;
						
					//moze i da se generise izmedju 0 - 1 broj i onda na osnovu 1/4 odredjivati parametre
					switch(s) {
					case "0,-1": flag = true; smer = Smer.LEVO; break;
					case "0,1": flag = true; smer = Smer.DESNO; break;
					case "1,0": flag = true; smer = Smer.DOLE;  break;
					case "-1,0": flag = true; smer = Smer.GORE; break;
					default: smer = Smer.DIJAG;
					}
					
					
					Polje novoPolje = mPolje.udaljenoPolje(i, j);
					if(novoPolje != null && novoPolje.mozeFigura() && smer != Smer.DIJAG) {
						mPolje.repaint();				//repaintuje staro polje - zeleno ce se iscrtati - poziva se za Travu repaint
						pomeriFiguru(novoPolje);		// a figuru mi iscrtava mreza
					}
					else flag = false;
				}
			}
		}
		catch(InterruptedException e) { }
		
	}
	
	public synchronized void pokreniTenk() {
		radi = true;
		notify();
	}
	
	public synchronized void prekini() {
		radi = false;
		if(nitTenk != null)
		nitTenk.interrupt();
	}

}
