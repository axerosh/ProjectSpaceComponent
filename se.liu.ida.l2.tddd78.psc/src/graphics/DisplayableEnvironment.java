package graphics;

import java.awt.Graphics;

/**
 * An environment that can be displayed, e.g. by a Displayer.
 *
 * @see Displayer
 */
public interface DisplayableEnvironment {

	public void display(final Graphics g, final float scale);

	public float getWidth();

	public float getHeight();
}
