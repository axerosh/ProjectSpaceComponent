package graphics.displayers;

import game.Battlefield;

import java.awt.*;

/**
 * JComponent extension that displays a game.
 */
public class GameDisplayer extends Displayer
{
	private Battlefield battlefield;

	public GameDisplayer(final Battlefield battlefield, float scale, int width,int height) {
		super(scale, width, height);
		this.battlefield = battlefield;
	}


	@Override protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		battlefield.draw(g, scale);
	}

}
