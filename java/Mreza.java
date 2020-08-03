package mreza;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Mreza extends Panel implements Runnable {
	private Igra mIgra;
	private Polje[][] matPolja;
	private boolean[][] slobodnaPolja;
	private int dim;
	private List<Igrac> listaIgraca = new ArrayList<>();
	private List<Novcic> listaNovcica = new ArrayList<>();
	private List<Tenk> listaTenkova = new ArrayList<>();
	private Thread nitMreza;
	private boolean radi = false;
	private Label mPoeni;
	
	private int brojNovcica = 0;
	private int brojTenkova = 0;
	private int brojPoena = 0;
	
	public Mreza(Igra igra) {
		this(17, igra);
	}
	
	public Mreza(int dim, Igra igra) {
		
		this.mIgra = igra;
		this.dim = dim;
		
		matPolja = new Polje[dim][dim];
		slobodnaPolja = new boolean[dim][dim];
		setLayout(new GridLayout(dim, dim));
		
		//setBackground(Color.GREEN);
		
		dodajPolja();
		dodajOsluskivacePolja();
		setFocusable(true);
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
				
				Thread.sleep(40);
				igracNaNovcicu();
				igracNaTenku();
				repaint();
			}
		}
		catch(InterruptedException e) { }
	}
	
	public void postaviTextNovcica(String strNovac) {
		brojNovcica = Integer.parseInt(strNovac);
		brojTenkova = brojNovcica / 3;
		rasporediNovcice();
		rasporediTenkove();
		rasporediIgraca();
		requestFocus();
		setFocusable(true);
		repaint();
	}
	
	private void rasporediIgraca() {
		while(true) {
			int rI = (int)(Math.random() * dim);
			int rJ = (int)(Math.random() * dim);
			
			if(slobodnaPolja[rI][rJ] == false) continue;
			if(!(matPolja[rI][rJ]).mozeFigura()) continue;
			
			Igrac igrac = new Igrac(matPolja[rI][rJ]);
			listaIgraca.add(igrac);
			slobodnaPolja[rI][rJ] = false;
			break;
		}
		
	}

	private void rasporediTenkove() {
		int counter = 0;
		
		while(counter < brojTenkova) {
			
			while(true) {
				int rI = (int)(Math.random() * dim);
				int rJ = (int)(Math.random() * dim);
				
				if(slobodnaPolja[rI][rJ] == false) continue;
				if(!(matPolja[rI][rJ]).mozeFigura()) continue;
				
				Tenk tenk = new Tenk(matPolja[rI][rJ]);
				tenk.pokreniTenk();
				slobodnaPolja[rI][rJ] = false;
				listaTenkova.add(tenk);
				break;
			}
			counter++;
		}
		
	}

	private void rasporediNovcice() {
		int novac = 0;
		
		while(novac < brojNovcica) {
			
			while(true) {
				int rI = (int)(Math.random() * dim);
				int rJ = (int)(Math.random() * dim);
				
				if(!(matPolja[rI][rJ]).mozeFigura()) continue;
				if(slobodnaPolja[rI][rJ] == false) continue;
				Novcic nov = new Novcic(matPolja[rI][rJ]);
				listaNovcica.add(nov);
				slobodnaPolja[rI][rJ] = false;
				break;
			}
			novac++;
		}
	}
	
	private void igracNaTenku() {
		boolean prekini =false;
		for(Igrac igrac : listaIgraca) {
			for(int i = 0; i < listaTenkova.size(); i++) {
				Tenk tenk = listaTenkova.get(i);
				if(!igrac.equals(tenk)) continue;
				prekini = true;
				break;
			}
		}
		if(prekini) this.prekini();
		
	}

	private void igracNaNovcicu() {
		int[] brisi = new int[listaNovcica.size()];
		
		for(Igrac igrac : listaIgraca) {
			
			for(int i = 0; i < listaNovcica.size(); i++) {
				Novcic nov = listaNovcica.get(i);
				if(igrac.equals(nov)) {
					brisi[i] = 1;
					mPoeni.setText("Poeni: " + ++brojPoena);
				}
			}
			
			for(int i = 0; i < brisi.length; i++) {
				if(brisi[i] == 1) {
					Polje poljeNovcica = listaNovcica.get(i).getmPolje();
					listaNovcica.remove(i);
					poljeNovcica.repaint();
				}
			}
			
		}
		
		if(brojPoena >= brojNovcica) this.prekini();		//Ako bih dole prekinuo, unistio bih igraca, a on ovamo treba posle da se vrati i da uradi break
										//Igrac igrac : listaIgraca -> a meni je vec taj igrac obrisan iz liste.
	}

	private void dodajOsluskivacePolja() {
		
		for(int i = 0; i < dim; i++) {
			for(int j = 0; j < dim; j++) {
				int x = i; int y = j;
				(matPolja[i][j]).addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						requestFocus();
						int izbor = mIgra.dohvatiIzbor();
						azurirajPolja(x, y, izbor);
						
					}
					
					@Override
					public void mousePressed(MouseEvent e) {
						requestFocus();
						int izbor = mIgra.dohvatiIzbor();
						azurirajPolja(x, y, izbor);
					}
					
					@Override
					public void mouseEntered(MouseEvent e) {
						requestFocus();
					}
				});
			}
		}
	}
	
	private void azurirajPolja(int i, int j,int izbor) {
		boolean trava = false; boolean zid = false;
		if(izbor == 0) return;
		if(mIgra.dohvatiRezim().ordinal() == 1) return;
		if(matPolja[i][j] instanceof Trava) trava = true;
		if(matPolja[i][j] instanceof Zid) zid = true;
		
		if(trava) {
			if(izbor == 1) return;
			if(izbor == 2) {
				remove(i*dim + j);
				matPolja[i][j] = new Zid(this);
				add(matPolja[i][j], i * dim + j);
				
				revalidate();
				repaint();
			}
				
		}
		if(zid) {
			if(izbor == 2) return;
			if(izbor == 1) {
				remove(i*dim + j);
				matPolja[i][j] = new Trava(this);
				add(matPolja[i][j], i * dim + j);
				
				revalidate();
				repaint();
			}
		}
		
		matPolja[i][j].postaviPoziciju(i, j);
		dodajOsluskivacePolja();			//novo matPolje[i][j] nema osluskivac, jer pokazuje na new nesto.
	}

	
	// Iz Konstruktora pozivam
	private void dodajPolja() {
		int brTrave = (int)(0.8 * dim * dim);	//0
		int brZida = dim * dim - brTrave; //1

		for(int i = 0; i < dim; i++) {
			for(int j = 0; j < dim; j++) {
				Trava trava = new Trava(this);
				matPolja[i][j] = trava;
				slobodnaPolja[i][j] = true;
				add(trava);
			}
		}
		
		int counter = 0;
		while(counter < brZida) {
			int randI = (int)(Math.random() * dim);
			int randJ = (int)(Math.random() * dim);
			
			
			if(matPolja[randI][randJ] instanceof Zid) continue;
			
			remove(randI * dim + randJ);
			Zid zid = new Zid(this);
			matPolja[randI][randJ] = zid;
			
			add(matPolja[randI][randJ], randI * dim + randJ);
			
			revalidate();
			repaint();
			counter++;
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for(Novcic n : listaNovcica) {
			n.iscrtajFiguru();
		}
		for(Tenk t : listaTenkova) {
			t.iscrtajFiguru();
			
		}
		for(Igrac i : listaIgraca) {
			i.iscrtajFiguru();
		}
	
	}

	public Polje[][] getMatPolja() {
		return matPolja;
	}
	public List<Igrac> getListaIgraca() {
		return listaIgraca;
	}
	public List<Novcic> getListaNovcica() {
		return listaNovcica;
	}
	public List<Tenk> getListaTenkova() {
		return listaTenkova;
	}
	
	public void pomeriIgraca(int i, int j) {
		for(Igrac igrac : listaIgraca) {
			Polje novo = igrac.getmPolje().udaljenoPolje(i, j);
			if(novo != null) {
				igrac.getmPolje().repaint();
				igrac.pomeriFiguru(novo);
			}
		}
		
	}
	
	/*
	 * 
	 *  Thread implement. ~~~~~~~~
	 * 
	 */
	public void napraviNit() {
		brojPoena = 0;
		mPoeni.setText("Poeni: " + brojPoena);
		nitMreza = new Thread(this);
		nitMreza.start();
	}

	public synchronized void pokreniMrezu() {
		radi = true;
		notify();
	}
	
	public boolean mrezaRadi() {
		return radi;
	}
	
	public synchronized void prekini() {
		if(!radi) return;
		for(int i = 0; i < listaTenkova.size(); i++) {
			Tenk tenk = listaTenkova.get(i);
			tenk.prekini();
			tenk.getmPolje().repaint();
		}
		for(int i = 0; i < listaNovcica.size(); i++) {
			Novcic novcic = listaNovcica.get(i);
			novcic.getmPolje().repaint();		//prekrij zelenom
		}
		
		for(int i = 0; i < listaIgraca.size(); i++) {
			Igrac igrac = listaIgraca.get(i);
			igrac.getmPolje().repaint();
		}
		
		listaNovcica.clear();
		listaIgraca.clear();
		listaTenkova.clear();
		
		radi = false;
		nitMreza.interrupt();
	}

	public void postaviLabeluPoena(Label poeni) {
		mPoeni = poeni;
	}
	
	
	
	
}
