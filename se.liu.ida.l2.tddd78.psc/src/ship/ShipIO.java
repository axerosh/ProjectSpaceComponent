package ship;

/**
 * Utility class for saving and loading Staship designs.
 *
 * @see Starship
 */
public final class ShipIO {

	private ShipIO() {
	}

	/**
	 * Returns a text representation of the the specified ship.
	 *
	 * @param ship a starship
	 *
	 * @return a text representation of the the specified ship
	 */
	/*public static String toText(Starship ship) {
		StringBuilder textRep = new StringBuilder();

		textRep.append("width=" + ship.getWidth() + "; ") textRep.append("height=" + ship.getHeight() + "; ")
		textRep.append("intergrity=" + ship.getMax() + "; ")

		Iterable<ShipComponent> components = ship.getShipComponents();
		for (ShipComponent component : components) {
			textRep.append(componentSymbolMap.get(component.getClass()));
		}

		return textRep.toString();
	}*/

	/**
	 * Returns a ship of the design of the specified ship text representation.
	 *
	 * @param x       the x position at which the ship is to be located
	 * @param y       the y position at which the ship is to be located
	 * @param textRep a text representation of a starship
	 *
	 * @return a ship of the design of the specified ship text representation
	 */
	/*public static Starship toShip(float x, float y, String textRep) {
		int shipWidth = 5;
		int shipHeight = 5;
		float shipIntegerity = 2;

		Starship ship = new Starship(x, y, shipWidth, shipHeight, shipIntegerity);

		Iterable<ShipComponent> components = ship.getShipComponents();
		for (ShipComponent component : components) {
			textRep.append(componentSymbolMap.get(component.getClass()));
		}

		return ship;
	}*/
}
