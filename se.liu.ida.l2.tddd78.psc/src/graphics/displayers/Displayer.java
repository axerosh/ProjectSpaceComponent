package graphics.displayers;

import game.VisibleEntityListener;

import javax.swing.*;
import java.awt.*;

public abstract class Displayer extends JComponent implements VisibleEntityListener {

	/**
	 * The scale from virtual coordinates/distances to ones one the screen. (If set to an integer, it is equal to a components
	 * width in pixels.)
	 */
	protected float scale;
	//As of 2016-03-02, this number need to be equal to or greater than ~16 for shielding/power bars to be readable.
	//Can definelty not be 0! (Will result in division by 0)
	protected int displayWidth;
	protected int displayHeight;

	protected Displayer(float scale, final int witdh, final int height) {
		this.scale = scale;
		displayWidth = (int) (witdh * scale);
		displayHeight = (int) (height * scale);
	}

	/**
	 * Returns the specified screen x-position converted to the corresponding virtual x-position
	 *
	 * @param screenX the x-coordinate of a screen position
	 *
	 * @return the virtual x-coordinate that corresponds to the specified screen x-coordinate
	 */
	public float getVirtualX(int screenX) {
		return screenX / scale;
	}


	/**
	 * Returns the specified screen y-position converted to the corresponding virtual y-position
	 *
	 * @param screenY the y-coordinate of a screen position
	 *
	 * @return the virtual y-coordinate that corresponds to the specified screen y-coordinate
	 */
	public float getVirtualY(int screenY) {
		return screenY / scale;
	}

	@Override public void visualUpdateRequested() {
		repaint();
	}

	@Override public Dimension getPreferredSize() {
		return new Dimension(displayWidth, displayHeight);
	}
}
