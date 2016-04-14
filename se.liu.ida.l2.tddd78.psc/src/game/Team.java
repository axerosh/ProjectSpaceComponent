package game;

import ship.Starship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A team of ships.
 *
 * @see Starship
 */
public class Team implements Iterable<Starship> {

	private final List<Starship> members;
	private Random rng;
	private boolean isDefeated;
	private String teamName;

	public Team(String teamName) {
		rng = new Random();
		members = new ArrayList<>();
		this.teamName = teamName;
	}

	public void add(Starship ship) {
		members.add(ship);
	}

	public boolean isDefeated() {
		if (isDefeated) {
			return isDefeated;
		} else {
			for (Starship member : members) {
				if (member.isIntact()) {
					return false;
				}
			}
			isDefeated = true;
			return isDefeated;
		}
	}

	public Starship getRandomMember() {
		if (members.isEmpty()) {
			return null;
		} else {
			return members.get(rng.nextInt(members.size()));
		}

	}

	public int getTeamSize(){
		return members.size();
	}

	public String getTeamName(){
		return teamName;
	}

	@Override public Iterator<Starship> iterator() {
		return members.iterator();
	}
}
