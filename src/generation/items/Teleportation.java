package generation.items;

import generation.Level;

/**
 * Represents a Teleportation ability that instantly moves the player to a random valid location on the map.
 * The destination is randomly selected from empty cells without enemies.
 * Useful for escaping dangerous situations or reaching distant areas.
 * Displayed as 'T' on the inventory.
 * @author Abdelhamid AKHATAR &lt;abdelhamid.akhatar@etu.cyu.fr&gt;
 */
public class Teleportation extends Ability {

	/**
	 * Constructs a new Teleportation ability.
	 * Symbol: 'T', Name: "TP", Activable (can be used).
	 */
	public Teleportation() {
		super('T', "TP", true);
	}

	/**
	 * Uses the Teleportation ability to move the player to a random safe location.
	 * Selects a random empty cell without enemies as the destination.
	 * @param level The current game level.
	 */
	@Override
	public void use(Level level) {
		int y;
		int x;
		boolean verif;
		do {
			y = level.rand.nextInt(level.getRows());
			x = level.rand.nextInt(level.getCols());
			verif = !level.getMap()[y][x].getType().equals("empty") || level.isEnemyAt(y, x);
		} while(verif);

		int[] newPosition = {y, x};
		level.getPlayer().setPosition(newPosition.clone());

	}

}
