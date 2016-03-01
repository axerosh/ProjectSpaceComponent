package shipcomponents.utilitycomponents;

import java.awt.*;

/**
 * Components that adds power to a ships power pool.
 */
public class ReactorComponent extends UtilityComponent{

    public ReactorComponent(final int maxHp, final int powerOutput) {
	super(maxHp, powerOutput);
	output = 3;
    }

    @Override public void performAction() {

    }

    @Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
	//super.draw(g, scale, virtualX, virtualY);
	g.setColor(Color.YELLOW);
	int screenX = (int)(virtualX * scale);
	int screenY = (int)(virtualY * scale);
	int widthOnScreen = (int)scale;
	g.fillRect(screenX, screenY, widthOnScreen, widthOnScreen);
    }
}
