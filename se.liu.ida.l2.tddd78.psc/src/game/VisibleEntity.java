package game;

/**
 * An enity that requests VisibleEntityListeners to update their visual representation. Each unique VisibleEntityListener may
 * only be notified once per update request.
 */
public interface VisibleEntity {

	/**
	 * Adds the specified listener to this VisibleEntity.
	 *
	 * @param listener the lister to add
	 */
	public void addVisibleEntityListener(VisibleEntityListener listener);
}
