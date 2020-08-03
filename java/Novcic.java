package mreza;

import java.awt.Color;
import java.awt.Graphics;

public class Novcic extends Figura {

	public Novcic(Polje polje) {
		super(polje);
	}
	
	@Override
	public void iscrtajFiguru() {
		Graphics g = mPolje.getGraphics();
		g.setColor(Color.YELLOW);
		int sirinaPolja = mPolje.getWidth();
		int visinaPolja = mPolje.getHeight();
		
		int sredinaX = sirinaPolja / 2;
		int sredinaY = visinaPolja / 2;
		int x1 = sredinaX - sredinaX / 2;
		int y1 = sredinaY - sredinaY / 2;

		g.fillOval(x1, y1, sirinaPolja / 2, visinaPolja / 2);
	}

}
