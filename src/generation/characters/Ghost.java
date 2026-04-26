package generation;

/**
 * Represents a Ghost enemy that moves in a straight line and ignores obstacles.
 * When aligned with the player on the same row or column, it changes direction to intercept.
 * Displayed as {@code G} on the map.
 * @author Abdelhamid AKHATAR <abdelhamid.akhatar@etu.cyu.fr>
 */
public class Ghost extends Enemy {
	/** The direction the Ghost is currently committed to moving. */
	private Movement currentDirection = Movement.values()[rand.nextInt(Movement.values().length - 1)];

	/**
	 * Constructs a new Ghost with the given name, lives, and starting position.
	 * Starts moving in a random direction.
	 * @param name The ghost's name.
	 * @param lives The starting number of lives.
	 * @param position The initial grid coordinates {row, col}.
	 */
	public Ghost(String name, int lives, int[] position) {
		super(name, lives, position);
	}

	/**
	 * Sets the Ghost's current movement direction.
	 * Called by the level when the Ghost is aligned with the player.
	 * @param direction The new {@link Movement} direction to commit to.
	 */
	public void setDirection(Movement direction) {
		this.currentDirection = direction;
	}

	/**
	 * Returns the Ghost's current committed direction without any randomness.
	 * @return The current {@link Movement} direction.
	 */
	@Override
	public Movement chooseMovement() {
		return this.currentDirection;
	}

}
