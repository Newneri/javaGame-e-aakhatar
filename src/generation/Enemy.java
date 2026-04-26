package generation;
import java.util.Random;

/**
 * Represents a basic enemy (Zombie) that moves randomly each turn.
 * Displayed as {@code R} on the map.
 * @author Abdelhamid AKHATAR <abdelhamid.akhatar@etu.cyu.fr>
 */
public class Enemy extends Character {
	/** Shared random generator available to subclasses for movement decisions. */
	Random rand = new Random();

	/**
	 * Constructs a new Enemy with the given name, lives, and starting position.
	 * @param name The enemy's name.
	 * @param lives The starting number of lives.
	 * @param position The initial grid coordinates {row, col}.
	 */
	public Enemy(String name, int lives, int[] position) {
		super(name, lives, position);
	}

	/**
	 * Chooses a random direction (UP, DOWN, LEFT, or RIGHT — never NONE).
	 * @return A randomly selected {@link Movement}.
	 */
	@Override
	public Movement chooseMovement() {
		return (Movement.values()[rand.nextInt(Movement.values().length - 1)]);
	}

}
