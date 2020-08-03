package mreza;

import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class Zid extends Polje {

	public Zid(Mreza m) {
		super(m);
		//setBackground(Color.LIGHT_GRAY);
	}

	@Override
	public boolean mozeFigura() {
		return false;
	}
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

}
