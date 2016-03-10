package control;

import game.Battlefield;
import game.Starship;
import ship_components.ShipComponent;
import weaponry.projectiles.Projectile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class BasicAI {
	private Battlefield field;
	private Starship aiShip;
	private List<Projectile> projetilesToFire;
	private Random random;

	public BasicAI(final Battlefield field, final Starship aiShip) {
		this.field = field;
		this.aiShip = aiShip;
		projetilesToFire = new ArrayList<>();
	}

	public void update() {
		aiShip.update();
		addProjectiles(aiShip.getProjectilesToFire());

		Starship targetShip;
		ShipComponent targetComponent;
		for (Projectile p : projetilesToFire) {
			targetShip = field.getRandomFriendlyShip();


		}
	}

	public void addProjectiles(final Collection<Projectile> projectiles) {
		projetilesToFire.addAll(projectiles);
	}
}
