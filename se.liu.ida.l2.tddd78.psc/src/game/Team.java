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

	private final List<Starship> aliveMembers;
	private final List<Starship> deadMembers;
	private final List<Starship> allMembers;
	private Random rng;
	private boolean defeated;
	private String teamName;

	public Team(String teamName) {
		rng = new Random();
		aliveMembers = new ArrayList<>();
		deadMembers = new ArrayList<>();
		allMembers = new ArrayList<>();
		this.teamName = teamName;
	}

	public void add(Starship ship) {
		allMembers.add(ship);
		aliveMembers.add(ship);
	}

	public void update() {
		int cursor = 0;
		while (cursor < aliveMembers.size()) {
			Starship ship = aliveMembers.get(cursor);
			if (!ship.isIntact()) {
				deadMembers.add(ship);
				aliveMembers.remove(ship);
				continue;
			}
			cursor++;
		}
	}

	public void reset() {
		aliveMembers.addAll(deadMembers);
		deadMembers.clear();
		defeated = false;
	}

	public boolean isDefeated() {
		if (defeated) {
			return defeated;
		} else {
			for (Starship member : aliveMembers) {
				if (member.isIntact()) {
					return false;
				}
			}
			defeated = true;
			return defeated;
		}
	}

	public Starship getRandomMember() {
		if (aliveMembers.isEmpty()) {
			return null;
		} else {
			return aliveMembers.get(rng.nextInt(aliveMembers.size()));
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
		return aliveMembers.indexOf(ship);
	}

	public String getTeamName() {
		return teamName;
	}

	@Override public Iterator<Starship> iterator() {
		return allMembers.iterator();
	}
}
