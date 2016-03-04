package weaponry.projectiles;

import java.awt.*;

public interface Projectile {

    public void update();

    public void impact();

    public boolean hasImpact();

    public void draw(final Graphics g, final float scale);
}
