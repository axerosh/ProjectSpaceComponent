package graphics.displayers;

import game.Battlefield;
import game.VisibleEntityListener;
import graphics.displayers.Displayer;

import javax.swing.*;
import java.awt.*;

/**
 * JComponent extension that displays a game.
 */
public class GameDisplayer extends Displayer
{
	private Battlefield battlefield;

	public GameDisplayer(final Battlefield battlefield) {
		this.battlefield = battlefield;
	}

	@Override public Dimension getPreferredSize() {
		final int battlefieldWidth = 13;
		final int battlefieldHeight = 7;
		return new Dimension((int) (battlefieldWidth * SCALE), (int) (battlefieldHeight * SCALE));
	}

	@Override protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		battlefield.draw(g, SCALE);
	}

}
