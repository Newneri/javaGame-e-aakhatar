package generation;
import java.util.Random;
import java.nio.file.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.IOException;

/**
 * Represents a game level, managing the map, player movement, and game rules.
 * @author Abdelhamid AKHATAR <abdelhamid.akhatar@etu.cyu.fr>
 */
public class Level {
	private int rows;
	private int cols;
	private Cell[][] map;
	private Player player;
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private HashSet<Cell> occupiedCells = new HashSet<>();
	private static int numberOfLevels = 0;
	private int numberOfCoins;
	
	
	/**
	 * Constructs a new Level from a file.
	 * Randomly places the player on a valid empty spot.
	 * @param filename Path to the level configuration file.
	 * @param name Name of the player.
	 */
	public Level(String filename, Player player) {
		
		numberOfLevels++;
		String[] stringMap = readLevel(filename);
				
		this.rows = stringMap.length;
		this.cols = stringMap[0].length();
		this.setMap(stringMap);
		int x;
		int y;
		boolean verif;
		Random rand = new Random();

		do {
			y = rand.nextInt(this.rows);
			x = rand.nextInt(this.cols);
			verif = !this.map[y][x].getType().equals("empty") || this.isEnemyAt(y, x);
		} while(verif);
		
		int[] initialPosition = {y, x};
		this.player = player;
		this.player.setPosition(initialPosition.clone());
		this.player.setInitialPosition(initialPosition.clone());
		this.numberOfCoins = this.countCoins();
		
	}
	
	/**
	 * Counts the number of coins present in the map.
	 * @return The total number of coins.
	 */
	private int countCoins() {
		int coins = 0;
		for(int y = 0; y < this.rows; y++) {
			for(int x = 0; x < this.cols; x++) {
				if(this.getMap()[y][x].getHasCoin()) {
					coins++;
				}
			}
		}
		return coins;
	}	
	
	/**
	 * Gets the list of all enemies in this level.
	 * @return The list of {@link Enemy} instances.
	 */
	public List<Enemy> getEnemies() {
		return this.enemies;
	}
	

	/**
	 * Reads the level layout from a file.
	 * @param filename The path to the file.
	 * @return An array of strings representing the map rows.
	 * @throws RuntimeException if the file cannot be read.
	 */
	private String[] readLevel(String filename) {
        try {
            Path path = Paths.get(filename);
            
            List<String> lines = Files.readAllLines(path);
            
            return lines.toArray(new String[0]);
            
        } catch (IOException e) {
            System.err.println("Fatal error : Impossible to read file " + filename);
            e.printStackTrace();
            
            throw new RuntimeException("Program Stopped : File not found.");
        }
    }
	
	/**
	 * Gets the current number of coins remaining.
	 * @return The number of coins.
	 */
	public int getNumberOfCoins() { return this.numberOfCoins; }
	
	/**
	 * Sets the number of coins.
	 * @param coins The new number of coins.
	 */
	public void setNumberOfCoins(int coins) {
		if(coins >= 0) {
			this.numberOfCoins = coins;
		} else {
			this.numberOfCoins = 0;
		}
	}

	/**
	 * Gets the total number of levels created.
	 * @return The number of levels.
	 */
	public static int getNumberOfLevels() { return numberOfLevels; }
	
	/**
	 * Gets the number of rows in the map.
	 * @return The number of rows.
	 */
	public int getRows() { return this.rows; }
	
	/**
	 * Gets the number of columns in the map.
	 * @return The number of columns.
	 */
	public int getCols() { return this.cols; }
	
	/**
	 * Gets the map grid.
	 * @return The 2D {@link Cell} array representing the map.
	 */
	public Cell[][] getMap() { return this.map; }
	
	/**
	 * Initializes the map from string array.
	 * @param stringMap Array of strings representing rows.
	 */
	public void setMap(String[] stringMap) {
		this.map = new Cell[this.getRows()][this.getCols()];		
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < stringMap[i].length(); j++) {
				char c = stringMap[i].charAt(j);
				switch (c) {
					case '#' -> this.map[i][j] = new Wall(new int[] {i, j});
					case 'D' -> this.map[i][j] = new LockedDoor(new int[] {i, j}, "password");
					case '.' -> this.map[i][j] = new Cell(new int[]{i, j}, "empty", true);
					case '*' -> this.map[i][j] = new Cell(new int[]{i, j}, "trap", false);	
					case 'R' -> {
						this.map[i][j] = new Cell(new int[]{i, j}, "empty", false);
						this.getEnemies().add(new Enemy("Zombie" + this.getEnemies().size(), 1, new int[]{i,j}));
						this.occupiedCells.add(this.map[i][j]);
					}
					case 'G' -> {
						this.map[i][j] = new Cell(new int[]{i, j}, "empty", false);
						this.getEnemies().add(new Ghost("Ghost" + this.getEnemies().size(), 1, new int[]{i,j}));
						this.occupiedCells.add(this.map[i][j]);
					}
					default  -> this.map[i][j] = new Cell(new int[]{i, j}, "empty", false);
				}
			}
		}
		
	}

	/**
	 * Gets the player instance.
	 * @return The player.
	 */
	public Player getPlayer() { return this.player; }
	
	/**
	 * Checks in O(1) whether any enemy occupies the cell at (i, j).
	 * @param i The row index.
	 * @param j The column index.
	 * @return {@code true} if an enemy is on that cell, {@code false} otherwise.
	 */
	public boolean isEnemyAt(int i, int j) {
		return this.occupiedCells.contains(this.getMap()[i][j]);
	}
	
	/**
	 * Returns the enemy at position (i, j), or {@code null} if none is present.
	 * Used for display purposes to determine the enemy type (e.g. Ghost vs Zombie).
	 * @param i The row index.
	 * @param j The column index.
	 * @return The {@link Enemy} at that position, or {@code null}.
	 */
	public Enemy getEnemyAt(int i, int j) {
		for(Enemy enemy: this.getEnemies()) {
			if(enemy.getPosition()[0] == i && enemy.getPosition()[1] == j) {
				return enemy;
			}
		}
		return null;
	}
	
	/**
	 * Returns a full string representation of the level for console display.
	 * Includes player stats, level info, and the map with the player's position overlaid.
	 * @return A multi-line string representing the current level state.
	 */
	@Override
	public String toString() {
		String show = "";
		show += this.getPlayer() + "---" + "Level " + (getNumberOfLevels()) + " Coins: " + this.getNumberOfCoins() + "\n";
		int[] playerPos = this.getPlayer().getPosition();
		for(int i = 0; i < this.getRows(); i++) {
            for(int j = 0; j < this.getCols(); j++) {
            	if(i == playerPos[0] && j == playerPos[1]) {
            		show += this.getPlayer().getName().charAt(0);
            	} else if(this.isEnemyAt(i, j)) {
            		if(this.getEnemyAt(i, j) instanceof Ghost) {
            			show += 'G';
            		} else {
            			show += 'R';
            		}
            	} else {
            		Cell cell = this.getMap()[i][j];
            		show += cell;
            	}
            }
            show += "\n";
		}
		return show;
	}

	/**
	 * Validates whether a move is possible for a given character.
	 * Handles map wrapping at boundaries and blocks movement into walls.
	 * @param move The {@link Movement} direction to validate.
	 * @param character The character attempting the move.
	 * @param canGoOnTrap {@code true} if the character is allowed to step on traps (player only).
	 * @return {@code true} if the destination is reachable, {@code false} otherwise.
	 */
	private boolean checkMoveValidity(Movement move, Character character, Boolean canGoOnTrap) {
		int y = move.getMovement()[0];
		int x = move.getMovement()[1];
		Cell nextCell;
		if(character.getPosition()[0] + y > this.getRows() - 1) {
			nextCell = this.getMap()[0][character.getPosition()[1]];
		} else if(character.getPosition()[0] + y < 0) {
			nextCell = this.getMap()[this.getRows() - 1][character.getPosition()[1]];
		} else if(character.getPosition()[1] + x > this.getCols() - 1) {
			nextCell = this.getMap()[character.getPosition()[0]][0];
		}  else if(character.getPosition()[1] + x < 0) {
			nextCell = this.getMap()[character.getPosition()[0]][this.getCols() - 1];
		} else {
			nextCell = this.getMap()[character.getPosition()[0] + y][character.getPosition()[1] + x];
		}
		if(canGoOnTrap) {
			return (!nextCell.getType().equals("wall"));
		} else {
			return (!nextCell.getType().equals("wall") && !nextCell.getType().equals("trap"));
		}
	}
	
	/**
	 * Resets all enemies to their initial positions and rebuilds the occupied cells set.
	 * Called after the player collides with an enemy or falls into a trap.
	 */
	public void resetEnemies() {
		for(Enemy enemy: this.getEnemies()) {
			this.occupiedCells.remove(this.getMap()[enemy.getPosition()[0]][enemy.getPosition()[1]]);
			enemy.setPosition(enemy.getInitialPosition());
			this.occupiedCells.add(this.getMap()[enemy.getPosition()[0]][enemy.getPosition()[1]]);
		}
	}

	/**
	 * Updates the game state based on player input.
	 * Handles movement, scoring, and collisions.
	 */
	public void update() {
		Movement move = this.getPlayer().chooseMovement();
		for(Enemy enemy: this.getEnemies()) {
			if(enemy instanceof Ghost) {
				if(enemy.getPosition()[0] == this.getPlayer().getPosition()[0]) {
					if(enemy.getPosition()[1] > this.getPlayer().getPosition()[1]) {
						((Ghost) enemy).setDirection(Movement.LEFT);
					} else {
						((Ghost) enemy).setDirection(Movement.RIGHT);
					}
				} else if(enemy.getPosition()[1] == this.getPlayer().getPosition()[1]) {
					if(enemy.getPosition()[0] > this.getPlayer().getPosition()[0]) {
						((Ghost) enemy).setDirection(Movement.UP);
					} else {
						((Ghost) enemy).setDirection(Movement.DOWN);
					}
				}
			}
			Movement moveEnemy = enemy.chooseMovement();
			if(enemy instanceof Ghost || this.checkMoveValidity(moveEnemy, enemy, false)) {
				this.occupiedCells.remove(this.getMap()[enemy.getPosition()[0]][enemy.getPosition()[1]]);
				enemy.move(moveEnemy, this.getMap());	
				this.occupiedCells.add(this.getMap()[enemy.getPosition()[0]][enemy.getPosition()[1]]);
			}
		}

		if(this.checkMoveValidity(move, this.getPlayer(), true)) {
			
			this.getPlayer().move(move, this.getMap());
			Cell dest = this.getMap()[this.getPlayer().getPosition()[0]][this.getPlayer().getPosition()[1]];
			
			if(dest.getHasCoin()) {
				// Recolt the coin
				this.map[dest.getPosition()[0]][dest.getPosition()[1]] = new Cell(dest.getPosition(), "empty", false);
				this.getPlayer().setScore(this.getPlayer().getScore() + 10);
				this.setNumberOfCoins(this.getNumberOfCoins() - 1);
				
			} else if(dest.getType().equals("trap")) {
				// Fell in trap -> reset player position and player loses 2 lives
				this.getPlayer().setPosition(getPlayer().getInitialPosition());
				this.getPlayer().setLives(this.getPlayer().getLives() - 2);
				this.resetEnemies();
			} 
		}
		
		if(this.isEnemyAt(this.getPlayer().getPosition()[0], this.getPlayer().getPosition()[1])) {
			// Collision with enemy -> reset enemies positions and player loses 1 life
			this.getPlayer().setLives(this.getPlayer().getLives() - 1);
			System.out.println(this.getPlayer().getName() + " collided with " + this.getEnemyAt(this.getPlayer().getPosition()[0], this.getPlayer().getPosition()[1]).getName());
			this.resetEnemies();
		}
		
		System.out.println(this);
	}

	/**
	 * Entry point for testing the Level class independently.
	 * @param args Command line arguments (unused).
	 */
	public static void main(String[] args) {}
}
