package game;

/**
 * Listener that listens to VisibleEntities request of visual updates.
 *
 * @see #visualUpdateRequested()
 */
public interface VisibleEntityListener {

	/**
	 * Is invoked when a VisibleEntity requests a visual update.
	 */
	public void visualUpdateRequested();
}
