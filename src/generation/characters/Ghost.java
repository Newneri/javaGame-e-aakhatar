package generation.characters;

import generation.map.Movement;

/**
 * Represents a Ghost enemy that moves in a straight line and ignores obstacles.
 * When aligned with the player on the same row or column, it changes direction to intercept.
 * Displayed as {@code G} on the map.
 * @author Abdelhamid AKHATAR &lt;abdelhamid.akhatar@etu.cyu.fr&gt;
 */
public class Ghost extends Enemy {
	/** The direction the Ghost is currently committed to moving. */
	private Movement currentDirection = Movement.values()[rand.nextInt(Movement.values().length - 1)];
	/** The player being tracked for interception. Set each turn via {@link #setTarget}. */
	private Player target;

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
	 * Sets the player target used to compute interception direction.
	 * Must be called once per turn before {@link #chooseMovement()}.
	 * @param player The player to intercept.
	 */
	public void setTarget(Player player) {
		this.target = player;
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
			if(this.getPosition()[0] == this.target.getPosition()[0]) {
				if(this.getPosition()[1] > this.target.getPosition()[1]) {
					this.setDirection(Movement.LEFT);
				} else {
					this.setDirection(Movement.RIGHT);
				}
			} else if(this.getPosition()[1] == this.target.getPosition()[1]) {
				if(this.getPosition()[0] > this.target.getPosition()[0]) {
					this.setDirection(Movement.UP);
				} else {
					this.setDirection(Movement.DOWN);
				}
			}
		return this.currentDirection;
	}

}
