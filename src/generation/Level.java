package generation;
import java.util.Random;

import generation.characters.*;
import generation.characters.Character;
import generation.map.*;
import generation.items.*;

import java.nio.file.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.io.IOException;

/**
 * Represents a game level, managing the map, player movement, and game rules.
 * @author Abdelhamid AKHATAR &lt;abdelhamid.akhatar@etu.cyu.fr&gt;
 */
public class Level {
	private int rows;
	private int cols;
	private Cell[][] map;
	private Player player;
	private List<Enemy> enemies = new ArrayList<Enemy>();
	private HashSet<Cell> occupiedCells = new HashSet<>();
	private int frozenTurns = 0;
	private static int numberOfLevels = 0;
	private int numberOfCoins;
	public Random rand = new Random();
	private WinCondition winCondition;

	
	
	/**
	 * Constructs a new Level from a file.
	 * Randomly places the player on a valid empty spot.
	 * @param filename Path to the level configuration file.
	 * @param player The player to place in this level.
	 */
	public Level(String filename, Player player) {
		
		numberOfLevels++;
		String[] stringMap = readLevel(filename);
		
		winCondition = WinCondition.fromkey(stringMap[0]);
				
		this.rows = stringMap.length - 1;
		this.cols = stringMap[1].length();
		this.setMap(Arrays.copyOfRange(stringMap, 1, stringMap.length));
		int x;
		int y;
		boolean verif;

		do {
			y = rand.nextInt(this.rows);
			x = rand.nextInt(this.cols);
			verif = !this.map[y][x].getType().equals("empty") || this.isEnemyAt(y, x) || this.map[y][x].hasItem();
		} while(verif);
		
		int[] initialPosition = {y, x};
		this.player = player;
		this.player.setPosition(initialPosition.clone());
		this.player.setInitialPosition(initialPosition.clone());
		this.numberOfCoins = this.countCoins();
		
	}
	
	public boolean isComplete() {
		switch(this.winCondition) {
			case WinCondition.KILL_ALL_ENEMIES:
				return this.getEnemies().isEmpty();
			case WinCondition.GET_ALL_COINS:
				return this.countCoins() == 0;
			case WinCondition.PICK_CROWN:
				return this.getPlayer().hasItem(Crown.class);
			default:
				return false;
		}
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
	 * Checks if there is an enemy at the specified coordinates.
	 * @param y The y-coordinate to check.
	 * @param x The x-coordinate to check.
	 * @return {@code true} if an enemy is present, {@code false} otherwise.
	 */	public boolean isEnemyAt(int y, int x) {
		return this.getOccupiedCells().contains(this.getMap()[y][x]);
	}

	/**
	 * Gets the enemy located at the specified coordinates.
	 * @param y The y-coordinate of the enemy.
	 * @param x The x-coordinate of the enemy.
	 * @return The {@link Enemy} at the given position, or {@code null} if none exists.
	 */	public Enemy getEnemyAt(int y, int x) {
		for(Enemy enemy: this.getEnemies()) {
			if(enemy.getPosition()[0] == y && enemy.getPosition()[1] == x) {
				return enemy;
			}
		}
		return null;
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
						this.getOccupiedCells().add(this.map[i][j]);
					}
					case 'G' -> {
						this.map[i][j] = new Cell(new int[]{i, j}, "empty", false);
						this.getEnemies().add(new Ghost("Ghost" + this.getEnemies().size(), 1, new int[]{i,j}));
						this.getOccupiedCells().add(this.map[i][j]);
					}
					case 'C' -> {
						this.map[i][j] = new Cell(new int[]{i, j}, "empty", false);
						this.getEnemies().add(new Hunter("Hunter" + this.getEnemies().size(), 3, new int[]{i,j}));
						this.getOccupiedCells().add(this.map[i][j]);
					}
					case 'W' -> {
						this.map[i][j] = new Cell(new int[]{i, j}, "empty", false);
						this.map[i][j].setItem(new Weapon());
					}
					case 'H' -> {
						this.map[i][j] = new Cell(new int[]{i, j}, "empty", false);
						this.map[i][j].setItem(new Hourglass());
					}
					case 'K' -> {
						this.map[i][j] = new Cell(new int[]{i, j}, "empty", false);
						this.map[i][j].setItem(new Crown());
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

	public HashSet<Cell> getOccupiedCells(){
		return this.occupiedCells;
	}

	/**
	 * Gets the current number of turns enemies are frozen.
	 * When frozen, enemies cannot move.
	 * @return The number of remaining frozen turns (0 if not frozen).
	 */
	public int getFrozenTurns() {
		return this.frozenTurns;
	}

	/**
	 * Freezes all enemies for a given number of turns.
	 * During frozen turns, enemies will not move each update cycle.
	 * @param turns The number of turns to freeze enemies for.
	 */
	public void freeze(int turns) {
		this.frozenTurns = turns;
	}
	
	/**
	 * Returns a full string representation of the level for console display.
	 * Includes player stats, level info, and the map with the player's position overlaid.
	 * @return A multi-line string representing the current level state.
	 */
	@Override
	public String toString() {
		String show = this.winCondition.getDisplayText() + "\n";
		show += this.getPlayer() + "---" + "Level " + (getNumberOfLevels()) + " Coins: " + this.getNumberOfCoins() + "\n";
		int[] playerPos = this.getPlayer().getPosition();
		for(int i = 0; i < this.getRows(); i++) {
            for(int j = 0; j < this.getCols(); j++) {
            	if(i == playerPos[0] && j == playerPos[1]) {
            		show += this.getPlayer().getName().charAt(0);
            	} else if(this.isEnemyAt(i, j)) {
            		if(this.getEnemyAt(i, j) instanceof Ghost) {
            			show += 'G';
            		} else if (this.getEnemyAt(i, j) instanceof Hunter){
            			show += 'C';
            		} else{
            			show += 'R';
            		}
            	} else if(this.getMap()[i][j].hasItem()) {
            		show += this.getMap()[i][j].getItem().getSymbol();
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
	private boolean checkMoveValidity(Movement move, Character character) {
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
		if(character instanceof Ghost) {
			return true;
		} else if(character instanceof Player) {
			if(((Player) character).hasItem(Lockpicking.class)) {
				return (!nextCell.getType().equals("wall") || nextCell instanceof LockedDoor);
			} else {
				return (!nextCell.getType().equals("wall"));
			}
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
			this.getOccupiedCells().remove(this.getMap()[enemy.getPosition()[0]][enemy.getPosition()[1]]);
			enemy.setPosition(enemy.getInitialPosition());
			this.getOccupiedCells().add(this.getMap()[enemy.getPosition()[0]][enemy.getPosition()[1]]);
		}
	}

	/**
	 * Updates the game state based on player input.
	 * Handles movement, scoring, and collisions.
	 */
	public void update() {
		if(!this.getPlayer().hasItem(Lockpicking.class) && this.getPlayer().getScore() >= 100) {
			this.getPlayer().addItem(new Lockpicking());
		}
		if(!this.getPlayer().hasItem(Teleportation.class) && this.getPlayer().getEnemiesDefeated() >= 3) {
			this.getPlayer().addItem(new Teleportation());
		}
		
		int usedItem = 0;
		if(this.getPlayer().getInventory().size() > 0) {
			char key = this.getPlayer().readKey();
			switch(key) {
				case '&'-> {
					usedItem += this.getPlayer().activateSlot(0, this);
					break;
				}
				case 'é'-> {
					usedItem += this.getPlayer().activateSlot(1, this);
					break;
				}
				case '"' -> {
					usedItem += this.getPlayer().activateSlot(2, this);
					break;
				}
				case '\'' -> {
					usedItem += this.getPlayer().activateSlot(3, this);
					break;
				}
				case '(' -> {
					usedItem += this.getPlayer().activateSlot(4, this);
					break;
				}
				default -> {
					break;
				}
			}
		}
		
		if(this.getFrozenTurns() > 0) {
			this.freeze(this.getFrozenTurns() - 1);
		} else {
			for(Enemy enemy: this.getEnemies()) {
				if(enemy instanceof Ghost) {
					((Ghost) enemy).setTarget(this.getPlayer());
				} else if(enemy instanceof Hunter) {
					((Hunter) enemy).setTarget(this.getPlayer(), this.getMap());
				}
				Movement moveEnemy = enemy.chooseMovement();
				if(this.checkMoveValidity(moveEnemy, enemy)) {
					this.getOccupiedCells().remove(this.getMap()[enemy.getPosition()[0]][enemy.getPosition()[1]]);
					enemy.move(moveEnemy, this.getMap());	
					this.getOccupiedCells().add(this.getMap()[enemy.getPosition()[0]][enemy.getPosition()[1]]);
				}
			}
		}
		
		Movement move = usedItem > 0 ? Movement.NONE : this.getPlayer().chooseMovement();

		if(this.checkMoveValidity(move, this.getPlayer())) {
			
			this.getPlayer().move(move, this.getMap());
			Cell dest = this.getMap()[this.getPlayer().getPosition()[0]][this.getPlayer().getPosition()[1]];
			
			if(dest.getHasCoin()) {
				// Recolt the coin
				this.getMap()[dest.getPosition()[0]][dest.getPosition()[1]] = new Cell(dest.getPosition(), "empty", false);
				this.getPlayer().setScore(this.getPlayer().getScore() + 10);
				this.setNumberOfCoins(this.getNumberOfCoins() - 1);
				
			} else if(dest.getType().equals("trap")) {
				// Fell in trap -> reset player position and player loses 2 lives
				this.getPlayer().setPosition(getPlayer().getInitialPosition());
				this.getPlayer().setLives(this.getPlayer().getLives() - 2);
				this.resetEnemies();
			} else if(dest.hasItem()) {
				this.getPlayer().addItem(dest.getItem());
				dest.setItem(null);
			}
		}
		
		if(this.isEnemyAt(this.getPlayer().getPosition()[0], this.getPlayer().getPosition()[1])) {
			if(this.getPlayer().hasItem(Weapon.class)) {
				this.getPlayer().useItem(this.getPlayer().getItemIndex(Weapon.class), this);
				
			} else {
				Enemy enemy = this.getEnemyAt(this.getPlayer().getPosition()[0], this.getPlayer().getPosition()[1]);
				if(enemy instanceof Hunter) {
					this.getPlayer().setLives(this.getPlayer().getLives() - 2);
				} else {
					this.getPlayer().setLives(this.getPlayer().getLives() - 1);
				}
				System.out.println(this.getPlayer().getName() + " collided with " + enemy.getName());
				this.resetEnemies();
			}
		}
		
		for(Enemy enemy: this.getEnemies()) {
			if(enemy.getLives() == 0){
				this.getPlayer().incrementEnemiesDefeated();
				this.getOccupiedCells().remove(this.getMap()[enemy.getPosition()[0]][enemy.getPosition()[1]]);
			}
		}
		this.getEnemies().removeIf(e -> e.getLives() == 0);
		
		System.out.println(this);
	}
}
