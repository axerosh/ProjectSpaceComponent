package graphics;

import java.awt.*;

/**
 * A utility class for drawing stat bars.
 */
public final class Statbar
{

	private Statbar() {}

	public static void drawHorizontal(final Graphics g, final int screenPosX, final int screenPosY, final int renderedWidth,
									  final int renderedHeight, final int currentStatLevel, final int maxStatLevel,
									  final int levelsPerCell, final Color fillColor)
	{
		if (renderedWidth <= 0 || renderedHeight <= 0) {
			throw new IllegalArgumentException("Invalid dimensions width = " + renderedWidth + ", height = " +
											   renderedHeight + ". Only positive integers are permitted.");
		} else if (currentStatLevel < 0) {
			throw new IllegalArgumentException("The specified stat level current stat level = " + currentStatLevel +
											   " is invalid. It can not be negative.");
		} else if (maxStatLevel <= 0) {
			throw new IllegalArgumentException("The specified max stat level = " + maxStatLevel + " is invalid. " +
											   "It must be greater than 0.");
		} else if (levelsPerCell <= 0) {
			throw new IllegalArgumentException("The specified number of stat levels per cell = " + levelsPerCell +
											   " is invalid. It must be greater than 0.");
		}

		//Outline/Background
		final Color backgroundColor = new Color(0, 0, 0, 127);
		g.setColor(backgroundColor);
		g.fillRect(screenPosX, screenPosY, renderedWidth, renderedHeight);

		int outlineThickness = 1;
		int gapThickness = 1;

		int containerHeight = renderedHeight - 2 * outlineThickness;
		int containerWidth = renderedWidth - 2 * outlineThickness;
		int numberOfCells = maxStatLevel / levelsPerCell;
		int numberOfGaps = numberOfCells - 1;
		float avarageCellWidth = (containerWidth - numberOfGaps * gapThickness) / (float) numberOfCells;

		//Fill
		g.setColor(fillColor);
		int usedContainerWidth = 0;
		for (int cellNr = 1; cellNr <= currentStatLevel; cellNr++) {
			int nextUsedContainerWidth = Math.round(cellNr * (avarageCellWidth + gapThickness));
			int cellWidth = nextUsedContainerWidth - usedContainerWidth - gapThickness;
			g.fillRect(screenPosX + outlineThickness + usedContainerWidth, screenPosY + outlineThickness, cellWidth,
					   containerHeight);
			usedContainerWidth = nextUsedContainerWidth;
		}
	}
}
