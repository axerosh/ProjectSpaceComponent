package game;

import java.util.ArrayList;
import java.util.List;

/**
 * General implementation of the VisibleEnity interface.
 */
public class GeneralVisibleEntity implements VisibleEntity {

	protected List<VisibleEntityListener> visibleEntityListeners;

	public GeneralVisibleEntity() {
		visibleEntityListeners = new ArrayList<>();
	}

	@Override public void addVisibleEntityListener(final VisibleEntityListener listener) {
		if (!visibleEntityListeners.contains(listener)) {
			visibleEntityListeners.add(listener);
		}
	}

	/**
	 * Request an visual update from all VisibleEntityListeners. Each unique VisibleEntityListener may only be requested once
	 * per call of this method.
	 */
	protected void requestVisualUpdate() {
		for (VisibleEntityListener listener : visibleEntityListeners) {
			listener.visualUpdateRequested();
		}
	}

	protected Iterable<VisibleEntityListener> getVisibleEntityListeners() {
		return visibleEntityListeners;
	}
}
