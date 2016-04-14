package graphics.displayers;

import game.VisibleEntityListener;
import graphics.PSCGraphics;

import javax.swing.*;
import java.awt.*;

public abstract class Displayer extends JComponent implements PSCGraphics, VisibleEntityListener{

	/**
	 * The scale from virtual coordinates/distances to ones one the screen. (If set to an integer, it is equal to a components
	 * width in pixels.)
	 */
	protected float scale;
	//As of 2016-03-02, this number need to be equal to or greater than ~16 for shielding/power bars to be readable.
	//Can definelty not be 0! (Will result in division by 0)


	public Displayer(float scale, final int witdh, final int height) {
		this.scale = scale;
		WIDTH = (int) (witdh * scale);
		HEIGHT = (int) (height * scale);
	}

	protected int WIDTH;
	protected int HEIGHT;

	@Override public float getVirtualX(int screenX) {
		return screenX / scale;
	}

	@Override public float getVirtualY(int screenY) {
		return screenY / scale;
	}

	@Override public void visualUpdateRequested() {
		repaint();
	}

	@Override public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
}
