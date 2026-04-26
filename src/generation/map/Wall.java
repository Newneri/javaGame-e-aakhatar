package generation.map;

/**
 * Represents an impassable wall cell in the game map.
 * Extends {@link Cell} with type "wall" and no coin.
 * @author Abdelhamid AKHATAR &lt;abdelhamid.akhatar@etu.cyu.fr&gt;
 */
public class Wall extends Cell {

	/**
	 * Constructs a new Wall cell at the given position.
	 * @param position The grid coordinates {row, col} of this wall.
	 */
	public Wall(int[] position) {
		super(position, "wall", false);
	}
}
