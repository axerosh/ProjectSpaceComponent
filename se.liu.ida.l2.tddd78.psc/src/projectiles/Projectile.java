package projectiles;

import java.awt.*;

public interface Projectile {

    public void update();

    public void impact();

    public boolean haveImpact();

    public void draw(final Graphics g, final float scale);
}
