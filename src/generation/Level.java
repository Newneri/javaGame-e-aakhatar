package generation;
import java.util.Random;
import java.util.Scanner;
import java.nio.file.*;
import java.util.List;
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
	private int[] initialPosition = new int[2];
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
			verif = !this.map[y][x].getType().equals("empty");
		} while(verif);
		
		this.initialPosition[0] = y;
		this.initialPosition[1] = x;
		this.player = player;
		this.player.setPosition(initialPosition.clone());
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
	 * Gets the initial spawn position of the player.
	 * @return A copy of the initial position array.
	 */
	public int[] getInitialPosition() { return this.initialPosition.clone(); }

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
	 * Validates whether a move is possible from the player's current position.
	 * Handles map wrapping at boundaries and blocks movement into walls.
	 * @param move The {@link Movement} direction to validate.
	 * @return {@code true} if the destination cell is not a wall, {@code false} otherwise.
	 */
	private boolean checkMoveValidity(Movement move) {
		int y = move.getMovement()[0];
		int x = move.getMovement()[1];
		Cell nextCell;
		if(this.getPlayer().getPosition()[0] + y > this.getRows() - 1) {
			nextCell = this.getMap()[0][this.getPlayer().getPosition()[1]];
		} else if(this.getPlayer().getPosition()[0] + y < 0) {
			nextCell = this.getMap()[this.getRows() - 1][this.getPlayer().getPosition()[1]];
		} else if(this.getPlayer().getPosition()[1] + x > this.getCols() - 1) {
			nextCell = this.getMap()[this.getPlayer().getPosition()[0]][0];
		}  else if(this.getPlayer().getPosition()[1] + x < 0) {
			nextCell = this.getMap()[this.getPlayer().getPosition()[0]][this.getCols() - 1];
		} else {
			nextCell = this.getMap()[this.getPlayer().getPosition()[0] + y][this.getPlayer().getPosition()[1] + x];
		}
		return (!nextCell.getType().equals("wall"));

	}

	/**
	 * Updates the game state based on player input.
	 * Handles movement, scoring, and collisions.
	 */
	public void update() {
		Movement move = this.getInputKey();
		if(this.checkMoveValidity(move)) {
			
			this.getPlayer().move(move, this.getMap());
			Cell dest = this.getMap()[this.getPlayer().getPosition()[0]][this.getPlayer().getPosition()[1]];
			
			if(dest.getHasCoin()) {
				// Recolt the coin
				this.map[dest.getPosition()[0]][dest.getPosition()[1]] = new Cell(dest.getPosition(), "empty", false);
				this.getPlayer().setScore(this.getPlayer().getScore() + 10);
				this.setNumberOfCoins(this.getNumberOfCoins() - 1);
				
			} else if(dest.getType().equals("trap")) {
				// Fell in trap
				this.getPlayer().setPosition(this.getInitialPosition());
				this.getPlayer().setLives(this.getPlayer().getLives() - 2);
			} 	
		}
		
		System.out.println(this);
	}

	/**
	 * Reads a single character from stdin and maps it to a {@link Movement} direction.
	 * Keys: 'z' = UP, 's' = DOWN, 'q' = LEFT, 'd' = RIGHT. Any other key yields NONE.
	 * @return The {@link Movement} corresponding to the key pressed.
	 */
	private Movement getInputKey() {
		Scanner myScanner = new Scanner(System.in);
		char key = String.valueOf(myScanner.next()).charAt(0);
		switch (key){
			case 'q':
				return Movement.LEFT;
			case 'z':
				return Movement.UP;
			case 'd':
				return Movement.RIGHT;
			case 's':
				return Movement.DOWN;
			default:
				return Movement.NONE;
		}
	}

	/**
	 * Entry point for testing the Level class independently.
	 * @param args Command line arguments (unused).
	 */
	public static void main(String[] args) {}
}
