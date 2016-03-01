package shipcomponents.utilitycomponents;

import java.awt.*;

/**
 * Component that add dodgechance to a ship.
 */
public class EngineComponent extends UtilityComponent{
    public EngineComponent(final int maxHp, final int dodgePercentage) {
	super(maxHp, dodgePercentage);
	output = 10;
    }

    @Override public void performAction() {

    }

    @Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
	//super.draw(g, scale, virtualX, virtualY);
	g.setColor(Color.ORANGE);
	int screenX = (int)(virtualX * scale);
	int screenY = (int)(virtualY * scale);
	int widthOnScreen = (int)scale;
	g.fillRect(screenX, screenY, widthOnScreen, widthOnScreen);
    }
}
