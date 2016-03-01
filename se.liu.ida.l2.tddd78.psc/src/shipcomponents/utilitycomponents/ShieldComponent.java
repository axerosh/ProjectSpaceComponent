package shipcomponents.utilitycomponents;

import java.awt.*;

/**
 * Component that add shield to the ships shield pool.
 */
public class ShieldComponent extends UtilityComponent{

    public ShieldComponent(final int maxHp, final int shieldOutput) {
        super(maxHp, shieldOutput);
    }

    public void update(){
	output = power * 2;
    }

    @Override public void performAction() {

    }

    @Override public void draw(final Graphics g, final float scale, final float virtualX, final float virtualY) {
        //super.draw(g, scale, virtualX, virtualY);
	g.setColor(Color.BLUE);
	int screenX = (int)(virtualX * scale);
	int screenY = (int)(virtualY * scale);
	int widthOnScreen = (int)scale;
	g.fillRect(screenX, screenY, widthOnScreen, widthOnScreen);
    }
}
