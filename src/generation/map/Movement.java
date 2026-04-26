package generation.map;

/**
 * Enum representing the possible movement directions for the player.
 * Each constant encodes a row offset (y) and a column offset (x).
 * @author Abdelhamid AKHATAR &lt;abdelhamid.akhatar@etu.cyu.fr&gt;
 */
public enum Movement {
	/** Move one row up (y = -1). */
	UP(-1, 0),
	/** Move one row down (y = +1). */
	DOWN(1, 0),
	/** Move one column to the right (x = +1). */
	RIGHT(0, 1),
	/** Move one column to the left (x = -1). */
	LEFT(0, -1),
	/** No movement (y = 0, x = 0). */
	NONE(0, 0);

	/** Row offset applied by this movement. */
	private final int y;
	/** Column offset applied by this movement. */
	private final int x;

	/**
	 * Constructs a Movement constant with the given offsets.
	 * @param y Row offset (negative = up, positive = down).
	 * @param x Column offset (negative = left, positive = right).
	 */
	private Movement(int y, int x) {
		this.y = y;
		this.x = x;
	}

	/**
	 * Returns the movement offsets as an array.
	 * @return An array {y, x} representing the row and column offsets.
	 */
	public int[] getMovement() { return new int[]{this.y, this.x}; }
}
