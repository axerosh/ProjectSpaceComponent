package graphics;

import javax.swing.*;
import java.awt.*;

public class Displayer extends JComponent {

	/**
	 * The scale from virtual coordinates/distances to ones one the screen. (If set to an integer, it is equal to a components
	 * width in pixels.)
	 */
	private float scale;
	//As of 2016-03-02, this number need to be equal to or greater than ~16 for shielding/power bars to be readable.
	//Can definelty not be 0! (Will result in division by 0)
	private int displayWidth;
	private int displayHeight;
	private Displayable displayedEnvironment;


	public Displayer(final Displayable displayedEnvironment, float scale, final int virtualWidth, final int virtualHeight) {
		this.scale = scale;
		displayWidth = (int) (virtualWidth * scale);
		displayHeight = (int) (virtualHeight * scale);
		this.displayedEnvironment = displayedEnvironment;
	}

	@Override protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		displayedEnvironment.display(g, scale);
		System.out.println("I am Happy! My width is " + displayWidth + ", my height is " + displayHeight + " and my scale is " + scale + ". It's so cooool... sunuvabitch!ccc");
	}

	public void setDisplayedEnvironment(final Displayable displayedEnvironment) {
		this.displayedEnvironment = displayedEnvironment;
	}

	public void setScale(final float scale) {
		this.scale = scale;
	}

	public void setDisplayWidth(final int displayWidth) {
		this.displayWidth = displayWidth;
	}

	public void setDisplayHeight(final int displayHeight) {
		this.displayHeight = displayHeight;
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

	@Override public Dimension getPreferredSize() {
		return new Dimension(displayWidth, displayHeight);
	}
}
