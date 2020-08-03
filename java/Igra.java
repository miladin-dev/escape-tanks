package mreza;

import java.awt.*;
import java.awt.event.*;

public class Igra extends Frame{
	private Mreza mreza;
	private Label podloga = new Label("Podloga: ");
	private Label poeni = new Label("Poeni: 0");
	private Checkbox[] izbor = new Checkbox[2];
	private CheckboxGroup grupa_izbor = new CheckboxGroup();
	private TextField brNovcica = new TextField("");
	private Button dugme = new Button("Pocni");
	private Rezim rezim = Rezim.IZMENA;
	public enum Rezim {IZMENA, IGRANJE};
	private boolean uToku = false;
	
	public int dohvatiIzbor() {
		if(izbor[0].getState()) return 1;
		if(izbor[1].getState()) return 2;
		else return 0;
	}
	public Rezim dohvatiRezim() {
		return rezim;
	}
	
	public Igra() {
		super("Igra");
		mreza = new Mreza(this);
		mreza.postaviLabeluPoena(poeni);
		
		setSize(900, 700);
		add(mreza);

		add(east(), BorderLayout.EAST);
		add(south(), BorderLayout.SOUTH);
		
		dodajOsluskivace();
		dodajMenu();
		postaviPocetniRezim();
		
		setVisible(true);
	}
	
	
	private void postaviPocetniRezim() {
		rezim = Rezim.IZMENA;
		dugme.setEnabled(false);
	}
	
	private void dodajMenu() {
		MenuBar traka = new MenuBar();
		setMenuBar(traka);
		Menu meni = new Menu("Rezim");
		traka.add(meni);
		
		MenuItem prvi = new MenuItem("Rezim izmena");
		meni.add(prvi);
		
		prvi.addActionListener( ae -> {
			if(mreza.mrezaRadi()) {
				mreza.prekini();
				uToku = false;
			}
			rezim = Rezim.IZMENA;
			dugme.setEnabled(false);
		});
		
		MenuItem drugi = new MenuItem("Rezim igranje");
		meni.add(drugi);
		
		drugi.addActionListener( ae -> {
			rezim = Rezim.IGRANJE;
			dugme.setEnabled(true);
		});
	}


	private Panel south() {
		Panel glavni_panel = new Panel();
		
		glavni_panel.add(new Label("Novcica: "));
		glavni_panel.add(brNovcica);
		glavni_panel.add(poeni);
		glavni_panel.add(dugme);
		
		return glavni_panel;
	}


	private Panel east() {
		Panel glavni_panel = new Panel(new GridLayout(0, 2));
		Panel panel_podloge = new Panel(new BorderLayout());
		Panel desni_panel = new Panel(new GridLayout(2, 0));
		Panel panel_trave = new Panel(new BorderLayout());
		Panel panel_zida = new Panel(new BorderLayout());
		
		izbor[0] = new Checkbox("Trava", false, grupa_izbor);
		izbor[1] = new Checkbox("Zid", false, grupa_izbor);
		
		podloga.setAlignment(Label.LEFT);
		panel_podloge.add(podloga);
		
		panel_trave.setBackground(Color.GREEN);
		panel_trave.add(izbor[0]);
		
		panel_zida.setBackground(Color.LIGHT_GRAY);
		panel_zida.add(izbor[1]);
		
		desni_panel.add(panel_trave);
		desni_panel.add(panel_zida);
		
		
		glavni_panel.add(panel_podloge);
		glavni_panel.add(desni_panel);
		
		//glavni_panel.setPreferredSize(new Dimension(150, 500));
		return glavni_panel;
	}


	private void dodajOsluskivace() {
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				dispose();
				mreza.prekini();

			}
		});
		
		dugme.addActionListener( ae -> {
			if(rezim == Rezim.IZMENA) return;
			
			if(mreza.mrezaRadi()) 
				mreza.prekini();
			
				uToku = true;
				mreza.postaviTextNovcica(brNovcica.getText());
				mreza.napraviNit();
				mreza.pokreniMrezu();
				mreza.requestFocus();
			
		});
		
		
		mreza.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_W: mreza.pomeriIgraca(-1, 0); break;
				case KeyEvent.VK_S: mreza.pomeriIgraca(1, 0); break;
				case KeyEvent.VK_A: mreza.pomeriIgraca(0, -1); break;
				case KeyEvent.VK_D: mreza.pomeriIgraca(0, 1); break;
				}
			}
			
		});
	}
	
	public static void main(String[] args) {
		new Igra();
	}
}
