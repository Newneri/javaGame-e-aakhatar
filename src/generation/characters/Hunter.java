package generation.characters;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.HashMap;

import generation.map.Cell;
import generation.map.Movement;

/**
 * Represents a Hunter enemy that actively pursues the player using BFS pathfinding.
 * Finds the shortest path to the player each turn, avoiding walls and traps.
 * Displayed as {@code C} on the map.
 * @author Abdelhamid AKHATAR &lt;abdelhamid.akhatar@etu.cyu.fr&gt;
 */
public class Hunter extends Enemy {
	/** The player being pursued. Set each turn via {@link #setTarget}. */
	private Player target;
	/** The current level map used for BFS pathfinding. */
	private Cell[][] map;

	/**
	 * Constructs a new Hunter with the given name, lives, and starting position.
	 * @param name The hunter's name.
	 * @param lives The starting number of lives.
	 * @param position The initial grid coordinates {row, col}.
	 */
	public Hunter(String name, int lives, int[] position) {
		super(name, lives, position);
	}

	/**
	 * Sets the player target and map reference before each movement decision.
	 * Must be called once per turn before {@link #chooseMovement()}.
	 * @param player The player to pursue.
	 * @param map The current level's cell grid.
	 */
	public void setTarget(Player player, Cell[][] map) {
		this.target = player;
		this.map = map;
	}

	/**
	 * Runs BFS from the Hunter's current position toward the player.
	 * @return The first {@link Cell} on the shortest path to the player,
	 *         or {@code null} if no path exists.
	 */
	public Cell bsf() {
		LinkedList<Cell> queue = new LinkedList<Cell>();
		HashSet<Cell> visited = new HashSet<Cell>();
		HashMap<Cell, Cell> parentMap = new HashMap<Cell, Cell>();

		queue.add(this.map[this.getPosition()[0]][this.getPosition()[1]]);
		visited.add(this.map[this.getPosition()[0]][this.getPosition()[1]]);

		while(!queue.isEmpty()) {
			Cell currentCell = queue.poll();
			if(currentCell.equals(this.map[this.target.getPosition()[0]][this.target.getPosition()[1]])) {
				while(!parentMap.get(currentCell).equals(this.map[this.getPosition()[0]][this.getPosition()[1]])) {
					currentCell = parentMap.get(currentCell);
				}
				return currentCell;
			}
			for(Movement direction: Movement.values()) {
				if(direction == Movement.NONE) {
					continue;
				}
				int newRow = currentCell.getPosition()[0] + direction.getMovement()[0];
				int newCol = currentCell.getPosition()[1] + direction.getMovement()[1];
				if((newRow >= 0 && newRow < this.map.length) && (newCol >= 0 && newCol < this.map[0].length)) {
					Cell newCell = this.map[newRow][newCol];
					if(!visited.contains(newCell) && !newCell.getType().equals("wall") && !newCell.getType().equals("trap")) {
						visited.add(newCell);
						parentMap.put(newCell, currentCell);
						queue.add(newCell);
					}
				}
			}
		}
		return null;
	}

	/**
	 * Determines the next move by running BFS and converting the first-step cell
	 * into a {@link Movement} direction. Returns {@link Movement#NONE} if no path exists.
	 * @return The {@link Movement} direction toward the player.
	 */
	@Override
	public Movement chooseMovement() {
		Cell nextCell = this.bsf();
		if(nextCell == null) return Movement.NONE;

		if(nextCell.getPosition()[0] == this.getPosition()[0]) {
			if(nextCell.getPosition()[1] > this.getPosition()[1]) {
				return Movement.RIGHT;
			} else {
				return Movement.LEFT;
			}
		} else if(nextCell.getPosition()[1] == this.getPosition()[1]) {
			if(nextCell.getPosition()[0] > this.getPosition()[0]) {
				return Movement.DOWN;
			} else {
				return Movement.UP;
			}
		}
		return Movement.NONE;
	}

}
