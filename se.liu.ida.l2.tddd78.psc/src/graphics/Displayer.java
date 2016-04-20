package graphics;

import javax.swing.*;
import java.awt.*;

/**
 * JComponent to display DisplayableEnviroments
 */
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
	private DisplayableEnvironment displayedEnvironment;

	/**
	 *
	 * @param displayedEnvironment the environment to be displayed
	 * @param scale the scale from virtual positions and lengths to screen positions and lengths
	 */
	public Displayer(final DisplayableEnvironment displayedEnvironment, float scale) {
		this.scale = scale;
		displayWidth = (int) (displayedEnvironment.getWidth() * scale);
		displayHeight = (int) (displayedEnvironment.getHeight() * scale);
		this.displayedEnvironment = displayedEnvironment;
	}

	@Override protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		displayedEnvironment.display(g, scale);
	}

	public void setDisplayedEnvironment(final DisplayableEnvironment displayedEnvironment, final float scale) {
		this.displayedEnvironment = displayedEnvironment;
		this.scale = scale;
		displayWidth = (int) (displayedEnvironment.getWidth() * scale);
		displayHeight = (int) (displayedEnvironment.getHeight() * scale);
		setSize(displayWidth, displayHeight);
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

	@Override public Dimension getMinimumSize() {
		return new Dimension(displayWidth, displayHeight);
	}
}
