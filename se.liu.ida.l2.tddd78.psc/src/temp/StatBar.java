package temp;

import java.awt.*;

public class StatBar {

	private final Color color;
	private final float x;
	private final float y;
	private final float width;
	private final float height;

	public StatBar(final Color color, final float x, final float y, final float width, final float height) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("Invalid dimensions width = " + width + ", height = " + height + ". " +
											   "Only positive  are permitted.");
		}

		this.color = color;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	/**
	 * Checks whether this stat bar contains the specified position.
	 *
	 * @param x the x-position
	 * @param y the y-position
	 * @return true if his stat bar contains the specified position
	 */
	public boolean contains(float x, float y) {
		System.out.println("StatBar checks if it contains position x = " + x + ", y = " + y);
		return x >= this.x && x <= this.x + this.width &&
			   y >= this.y && y <= this.y + this.height;
	}

	public void draw(final Graphics g, final float scale, final int originScreenPosX, final int originScreenPosY,
					  final int currentStatLevel, final int maxStatLevel, final boolean visible) {
		if (currentStatLevel < 0) {
			throw new IllegalArgumentException("The specified stat level current stat level = " +  currentStatLevel +
											   " is invalid. It can not be negative.");
		} else if (maxStatLevel <= 0) {
			throw new IllegalArgumentException("The specified max stat level = " + maxStatLevel + " is invalid. " +
											   "It must be greater than 0.");
		}

		int screenPosX = originScreenPosX + Math.round(x * scale);
		int screenPosY = originScreenPosY + Math.round(y * scale);
		int renderedWidth = Math.round(width * scale);
		int renderedHeight = Math.round(height * scale);

		//Outline/Background
		final Color transparentBlack = new Color(0, 0, 0, 70);
		g.setColor(transparentBlack);
		g.fillRect(screenPosX, screenPosY, renderedWidth, renderedHeight);

		if (visible) {
			int outlineThickness = 1;
			int containerHeight = renderedHeight - 2 * outlineThickness;
			int fillWidth = renderedWidth - 2 * outlineThickness;
			int fillHeight = Math.round(containerHeight * currentStatLevel / (float)maxStatLevel);
			int fillPosY = screenPosY + renderedHeight - fillHeight - outlineThickness;

			//Fill
			g.setColor(color);
			g.fillRect(screenPosX + outlineThickness, fillPosY, fillWidth, fillHeight);
		}
	}
}
