package graphics;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A utility class for drawing stat bars.
 */
public final class Statbar
{

	private Statbar() {}

	public static void drawHorizontal(final Graphics g, final int screenPosX, final int screenPosY, final int renderedWidth,
									  final int renderedHeight, final float currentStatLevel, final float maxStatLevel,
									  final int levelsPerCell, final Color fillColor)
	{
		if (renderedWidth <= 0 || renderedHeight <= 0) {
			String message = "Invalid dimensions width = " + renderedWidth + ", height = " +
							 renderedHeight + ". Only positive integers are permitted.";
			IllegalArgumentException excpetion =  new IllegalArgumentException(message);
			Logger.getGlobal().log(Level.SEVERE, message, excpetion);
			throw excpetion;

		} else if (currentStatLevel < 0) {
			String message = "The specified stat level current stat level = " + currentStatLevel +
							 " is invalid. It can not be negative.";
			IllegalArgumentException excpetion =  new IllegalArgumentException(message);
			Logger.getGlobal().log(Level.SEVERE, message, excpetion);
			throw excpetion;

		} else if (maxStatLevel <= 0) {
			String message = "The specified max stat level = " + maxStatLevel + " is invalid. " +
							 "It must be greater than 0.";
			IllegalArgumentException excpetion =  new IllegalArgumentException(message);
			Logger.getGlobal().log(Level.SEVERE, message, excpetion);
			throw excpetion;

		} else if (levelsPerCell <= 0) {
			String message = "The specified number of stat levels per cell = " + levelsPerCell +
							 " is invalid. It must be greater than 0.";
			IllegalArgumentException excpetion =  new IllegalArgumentException(message);
			Logger.getGlobal().log(Level.SEVERE, message, excpetion);
			throw excpetion;
		}

		//Outline/Background
		final Color backgroundColor = new Color(0, 0, 0, 127);
		g.setColor(backgroundColor);
		g.fillRect(screenPosX, screenPosY, renderedWidth, renderedHeight);

		int outlineThickness = 1;
		int gapThickness = 1;

		int containerHeight = renderedHeight - 2 * outlineThickness;
		int containerWidth = renderedWidth - 2 * outlineThickness;
		int numberOfCells = (int) (maxStatLevel / levelsPerCell);
		int numberOfGaps = numberOfCells - 1;
		int totalFillWidthExclGaps = containerWidth - numberOfGaps * gapThickness;
		float avarageCellWidth = totalFillWidthExclGaps / (float) numberOfCells;
		int totalfillWidthInclGaps =
				numberOfGaps * gapThickness + (int) (totalFillWidthExclGaps * currentStatLevel / maxStatLevel);

		//Fill
		g.setColor(fillColor);
		int usedContainerWidth = 0;
		for (int cellNr = 1; cellNr <= currentStatLevel; cellNr++) {
			int nextUsedContainerWidth = Math.round(cellNr * (avarageCellWidth + gapThickness));
			int cellWidth =
					nextUsedContainerWidth /*Math.min(nextUsedContainerWidth, totalfillWidthInclGaps)*/ - usedContainerWidth -
					gapThickness;
			g.fillRect(screenPosX + outlineThickness + usedContainerWidth, screenPosY + outlineThickness, cellWidth,
					   containerHeight);
			usedContainerWidth = nextUsedContainerWidth;
		}
	}

	public static void drawOval(final Graphics g, final int screenPosX, final int screenPosY, final int renderedWidth,
								final int renderedHeight, final float currentStatLevel, final float maxStatLevel,
								final Color fillColor)
	{
		if (renderedWidth <= 0 || renderedHeight <= 0) {
			String message = "Invalid dimensions width = " + renderedWidth + ", height = " +
							 renderedHeight + ". Only positive integers are permitted.";
			IllegalArgumentException excpetion =  new IllegalArgumentException(message);
			Logger.getGlobal().log(Level.SEVERE, message, excpetion);
			throw excpetion;
		} else if (currentStatLevel < 0) {
			String message = "The specified stat level current stat level = " + currentStatLevel +
							 " is invalid. It can not be negative.";
			IllegalArgumentException excpetion =  new IllegalArgumentException(message);
			Logger.getGlobal().log(Level.SEVERE, message, excpetion);
			throw excpetion;
		} else if (maxStatLevel <= 0) {
			String message = "The specified max stat level = " + maxStatLevel + " is invalid. " +
							 "It must be greater than 0.";
			IllegalArgumentException excpetion =  new IllegalArgumentException(message);
			Logger.getGlobal().log(Level.SEVERE, message, excpetion);
			throw excpetion;
		}

		//Outline/Background
		final Color backgroundColor = new Color(0, 0, 0, 127);
		g.setColor(backgroundColor);
		g.fillOval(screenPosX, screenPosY, renderedWidth, renderedHeight);

		final int topRotation = 90;
		final int fullRotation = 360;
		int fillAngle = (int) (fullRotation * currentStatLevel / maxStatLevel);

		//Fill
		g.setColor(fillColor);
		g.fillArc(screenPosX, screenPosY, renderedWidth, renderedHeight, topRotation, fillAngle);
	}
}
