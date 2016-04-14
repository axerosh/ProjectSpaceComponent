package graphics.displayers;

import game.BattleSpace;

import java.awt.*;

/**
 * JComponent extension that displays a game.
 */
public class BattleSpaceDisplayer extends Displayer
{
	private BattleSpace battleSpace;

	public BattleSpaceDisplayer(final BattleSpace battleSpace, float scale, int width, int height) {
		super(scale, width, height);
		this.battleSpace = battleSpace;
	}

	@Override protected void paintComponent(final Graphics g) {
		super.paintComponent(g);
		battleSpace.draw(g, scale);
	}

}
