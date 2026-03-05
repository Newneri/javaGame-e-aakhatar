package generation;

/**
 * Represents a single cell in the game map grid.
 * A cell has a position, a type (e.g. "empty", "wall", "trap"), and may contain a coin.
 * @author Abdelhamid AKHATAR <abdelhamid.akhatar@etu.cyu.fr>
 */
public class Cell {
	/** Grid coordinates {row, col} of this cell. */
	private int[] position;
	/** The type of this cell: "empty", "wall", or "trap". */
	private String type;
	/** Whether this cell currently contains a collectible coin. */
	private boolean hasCoin;

	/**
	 * Constructs a new Cell.
	 * @param position The grid coordinates {row, col} of the cell.
	 * @param type The type of the cell ("empty", "wall", "trap").
	 * @param hasCoin {@code true} if the cell contains a coin, {@code false} otherwise.
	 */
	public Cell(int[] position, String type, boolean hasCoin) {
		this.position = position;
		this.type = type;
		this.hasCoin = hasCoin;
	}

	/**
	 * Gets the grid coordinates of this cell.
	 * @return An array {row, col} representing the cell's position.
	 */
	public int[] getPosition() {
		return this.position;
	}

	/**
	 * Gets the type of this cell.
	 * @return A string representing the type: "empty", "wall", or "trap".
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Checks whether this cell contains a coin.
	 * @return {@code true} if the cell has a coin, {@code false} otherwise.
	 */
	public boolean getHasCoin() {
		return this.hasCoin;
	}

	/**
	 * Returns the character representation of this cell for console display.
	 * A {@link LockedDoor} is shown as 'D', a regular wall as '#', a trap as '*',
	 * a coin as '.', and an empty cell as ' '.
	 * @return A single-character string representing this cell.
	 */
	@Override 
	public String toString() {
		if(this.getType().equals("wall")) {
			if(this instanceof LockedDoor) {
				return "D";
			}
			return "#";
		} else if(this.getType().equals("trap")) {
			return "*";
		} else if(this.getHasCoin()) {
			return ".";
		} else {
			return " ";
		}
	}
}