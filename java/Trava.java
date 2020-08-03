package mreza;

import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class Trava extends Polje {

	public Trava(Mreza m) {
		super(m);
		//setBackground(Color.GREEN);
	}

	@Override
	public boolean mozeFigura() {
		return true;
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

}
