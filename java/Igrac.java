package mreza;

import java.awt.*;
import java.awt.event.*;

public class Igrac extends Figura {

	public Igrac(Polje polje) {
		super(polje);
	}

	@Override
	public void iscrtajFiguru() {
		Graphics g = mPolje.getGraphics();
		g.setColor(Color.RED);
		int sirinaPolja = mPolje.getWidth();
		int visinaPolja = mPolje.getHeight();
		
		g.drawLine(sirinaPolja / 2, 0, sirinaPolja / 2, visinaPolja);
		g.drawLine(0, visinaPolja / 2, sirinaPolja, visinaPolja / 2);
	}

}
