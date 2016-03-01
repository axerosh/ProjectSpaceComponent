package shipcomponents.utilitycomponents;

import java.awt.*;

/**
 * Component that add shield to the ships shield pool.
 */
public class ShieldComponent extends UtilityComponent{

    public ShieldComponent(final int maxHp, final int shieldOutput) {
        super(maxHp, shieldOutput);
    }

    @Override public void performAction() {

    }

    @Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
        super.draw(g, scale, virtualX, virtualY);
	g.setColor(Color.BLUE);
    }
}
