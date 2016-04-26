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
	private boolean defeated;
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
		if (defeated) {
			return defeated;
		} else {
			for (Starship member : members) {
				if (member.isIntact()) {
					return false;
				}
			}
			defeated = true;
			return defeated;
		}
	}

	public void setDefeated(final boolean defeated) {
		this.defeated = defeated;
	}

	public Starship getRandomMember() {
		if (members.isEmpty()) {
			return null;
		} else {
			return members.get(rng.nextInt(members.size()));
		}
	}

	/**
	 * Returns the membership ID of the specified ship.
	 *
	 * @param ship the ship which ID is to be gotten
	 *
	 * @return an ID number equals to or greater than 0 if the ship is a part of the team; -1 of it is not
	 */
	public int indexOf(Starship ship) {
		return members.indexOf(ship);
	}

	public String getTeamName(){
		return teamName;
	}

	@Override public Iterator<Starship> iterator() {
		return members.iterator();
	}
}
