package generation.map;

import generation.items.Usable;
import java.util.Objects;

/**
 * Represents a single cell in the game map grid.
 * A cell has a position, a type (e.g. "empty", "wall", "trap"), and may contain a coin.
 * @author Abdelhamid AKHATAR &lt;abdelhamid.akhatar@etu.cyu.fr&gt;
 */
public class Cell {
	/** Grid coordinates {row, col} of this cell. */
	private int[] position;
	/** The type of this cell: "empty", "wall", or "trap". */
	private String type;
	/** Whether this cell currently contains a collectible coin. */
	private boolean hasCoin;
	private Usable item = null;

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
	 * Sets the usable item placed on this cell.
	 * @param item The usable item to place, or {@code null} to remove any item.
	 */
	public void setItem(Usable item) {
		this.item = item;
	}

	/**
	 * Gets the usable item placed on this cell, if any.
	 * @return The {@link Usable} item on this cell, or {@code null} if none.
	 */
	public Usable getItem() {
		return this.item;
	}

	/**
	 * Checks whether this cell contains an item.
	 * @return {@code true} if an item is on this cell, {@code false} otherwise.
	 */
	public boolean hasItem() {
		return this.item != null;
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
	
	@Override 
	public int hashCode() {
		return Objects.hash(this.position[0], this.position[1]);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Cell) {
			Cell cell = (Cell) obj;
			return(cell.getPosition()[0] == this.getPosition()[0] && cell.getPosition()[1] == this.getPosition()[1]);
		}
		return false;
	}
}
