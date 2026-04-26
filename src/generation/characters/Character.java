package generation;

/**
 * Abstract base class for all characters in the game (player and enemies).
 * Holds common state: name, lives, current position, and initial spawn position.
 * @author Abdelhamid AKHATAR <abdelhamid.akhatar@etu.cyu.fr>
 */
public abstract class Character {
	/** Number of lives remaining. */
	protected int lives;
	/** Display name of this character. */
	protected String name;
	/** Current grid position {row, col}. */
	protected int[] position = {0,0};
	/** Spawn position used when resetting after a collision or trap. */
	protected int[] initialPosition = {0,0};

	/**
	 * Constructs a Character with the given name, lives, and starting position.
	 * @param name The character's name.
	 * @param lives The starting number of lives.
	 * @param position The initial grid coordinates {row, col}.
	 */
	public Character(String name, int lives, int[] position) {
		this.name = name;
		this.lives = lives;
		this.position = position.clone();
		this.initialPosition = position.clone();
	}

	/**
	 * Gets the current number of lives.
	 * @return The number of lives remaining.
	 */
	public int getLives() {
		return this.lives;
	}

	/**
	 * Gets the character's name.
	 * @return The name string.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the current grid position.
	 * @return An array {row, col} representing the current position.
	 */
	public int[] getPosition() {
		return this.position;
	}

	/**
	 * Gets the initial spawn position.
	 * @return A copy of the initial position array {row, col}.
	 */
	public int[] getInitialPosition() {
		return this.initialPosition.clone();
	}

	/**
	 * Sets the number of lives, clamping negative values to 0.
	 * @param lives The new number of lives.
	 */
	public void setLives(int lives) {
		if(lives>=0) {
			this.lives = lives;
		} else {
			this.lives = 0;
		}
	}

	/**
	 * Sets the current position.
	 * @param position An array {row, col} representing the new position.
	 */
	public void setPosition(int[] position) {
		this.position = position.clone();
	}

	/**
	 * Sets the initial spawn position used when resetting.
	 * @param initialPosition An array {row, col} representing the spawn position.
	 */
	public void setInitialPosition(int[] initialPosition) {
		this.initialPosition = initialPosition.clone();
	}

	/**
	 * Moves the character in the given direction, wrapping around map edges.
	 * @param move The {@link Movement} direction to apply.
	 * @param map The current level's cell grid, used for boundary wrapping.
	 */
	public void move(Movement move, Cell[][] map) {
		int y = move.getMovement()[0];
		int x = move.getMovement()[1];

		if(this.position[0] + y > map.length-1) {
			this.position[0] = 0;
		} else if(this.position[0] + y < 0) {
			this.position[0] = map.length - 1;
		} else if(this.position[1] + x > map[0].length -1) {
			this.position[1] = 0;
		} else if(this.position[1] + x < 0) {
			this.position[1] = map[0].length - 1;
		} else {
			this.position[0] += y;
			this.position[1] += x;
		}
	}

	/**
	 * Decides the next movement for this character.
	 * @return The chosen {@link Movement} direction.
	 */
	public abstract Movement chooseMovement();

}
