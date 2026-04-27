package generation.items;
import generation.Level;
import generation.characters.Enemy;

/**
 * Represents a weapon item that deals damage to an enemy at the player's current location.
 * When used, it reduces the targeted enemy's life by 1 and resets the enemy to its initial position.
 * @author Abdelhamid AKHATAR &lt;abdelhamid.akhatar@etu.cyu.fr&gt;
 */
public class Weapon extends Item {

	/**
	 * Constructs a new Weapon item.
	 * Symbol: 'W', Name: "Weapon", Non-activable (consumable on use).
	 */
	public Weapon() {
		super('W', "Weapon", false);
	}

	/**
	 * Uses the weapon to damage the enemy at the player's current position.
	 * Reduces the enemy's life by 1 and resets the enemy to its spawn position.
	 * @param level The current game level containing the player and enemies.
	 */
	@Override
	public void use(Level level) {
		Enemy enemy = level.getEnemyAt(level.getPlayer().getPosition()[0], level.getPlayer().getPosition()[1]);

		enemy.setLives(enemy.getLives() - 1);
		level.getOccupiedCells().remove(level.getMap()[enemy.getPosition()[0]][enemy.getPosition()[1]]);
		enemy.setPosition(enemy.getInitialPosition());
		level.getOccupiedCells().add(level.getMap()[enemy.getPosition()[0]][enemy.getPosition()[1]]);
	}

}
