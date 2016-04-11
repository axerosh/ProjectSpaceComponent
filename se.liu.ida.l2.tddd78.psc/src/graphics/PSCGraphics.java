package graphics;

/**
 * Graphical component for displaying the game.
 */
public interface PSCGraphics
{

	/**
	 * Returns the specified screen x-position converted to the corresponding virtual x-position
	 *
	 * @param screenX the x-coordinate of a screen position
	 *
	 * @return the virtual x-coordinate that corresponds to the specified screen x-coordinate
	 */
	public int getVirtualX(int screenX);

	/**
	 * Returns the specified screen y-position converted to the corresponding virtual y-position
	 *
	 * @param screenY the y-coordinate of a screen position
	 *
	 * @return the virtual y-coordinate that corresponds to the specified screen y-coordinate
	 */
	public int getVirtualY(int screenY);
}
