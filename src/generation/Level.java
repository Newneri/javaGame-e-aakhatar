package generation;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.nio.file.*;
import java.util.List;
import java.io.IOException;

/**
 * Represents a game level containing a map grid and a player.
 * Manages the game state, player movement, and map rendering.
 * 
 * @author Akhatar Abdelhamid abdelhamid.akhatar@etu.cyu.fr
 */
public class Level {
	private int rows;
	private int cols;
	private char[][] map;
	private Player player;
	Random rand = new Random();
	private static int numberOfLevels = 0;
	private boolean gameOn = true;
	
	/**
	 * Constructs a Level from a string array representing the map.
	 * Initializes the grid, randomly places the player on a valid space,
	 * and increments the level count.
	 *
	 * @param stringMap An array of strings where each string represents a row of the map.
	 */
	public Level(String[] stringMap) {
		
		this.rows = stringMap.length;
		this.cols = stringMap[0].length();
		this.map = new char[rows][cols];
		numberOfLevels++;
		
		for (int i = 0; i < this.rows; i++) {
	        this.map[i] = stringMap[i].toCharArray();
	    }
		
		int x;
		int y;
		boolean verif;
		do {
			y = rand.nextInt(this.rows);
			x = rand.nextInt(this.cols);
			verif = this.map[y][x] != ' ';
		} while(verif);
		
		int[] position = {y, x};
		this.player = new Player(position);
		
		this.map[y][x] = String.valueOf(Player.getNumberOfPlayers()).charAt(0);
		
	}
	
	/**
	 * Constructs a Level by reading the map layout from a specified file.
	 *
	 * @param filename The path to the file containing the level layout.
	 */
	public Level(String filename) {
	    this(readLevel(filename));
	}
	
	/**
	 * Reads the lines of a file and converts them into a string array.
	 *
	 * @param filename The path to the file to read.
	 * @return A string array where each element is a line from the file.
	 * @throws RuntimeException If an IOException occurs while reading the file.
	 */
	public static String[] readLevel(String filename) {
        try {
            Path path = Paths.get(filename);
            
            List<String> lines = Files.readAllLines(path);
            
            return lines.toArray(new String[0]);
            
        } catch (IOException e) {
            System.err.println("Erreur fatale : Impossible de lire le fichier " + filename);
            e.printStackTrace();
            
            throw new RuntimeException("Arrêt du programme : Fichier niveau introuvable.");
        }
    }
	
	/**
	 * Checks if the game is currently running.
	 *
	 * @return true if the game is on, false otherwise.
	 */
	public boolean getGameOn() { return this.gameOn; }
	
	/**
	 * Sets the running state of the game.
	 *
	 * @param state The new state of the game (true for running, false for stopped).
	 */
	public void setGameOn(boolean state) {
		this.gameOn = state;
	}
	
	/**
	 * Returns the total number of Level instances created.
	 *
	 * @return The number of levels.
	 */
	public static int getNumberOfLevels() { return numberOfLevels; }
	
	/**
	 * Gets the number of rows in the map.
	 *
	 * @return The number of rows.
	 */
	public int getRows() { return this.rows; }
	
	/**
	 * Gets the number of columns in the map.
	 *
	 * @return The number of columns.
	 */
	public int getCols() { return this.cols; }
	
	/**
	 * Gets the 2D character array representing the map.
	 *
	 * @return The map grid.
	 */
	public char[][] getMap() { return this.map; }
	
	/**
	 * Gets the player associated with this level.
	 *
	 * @return The Player object.
	 */
	public Player getPlayer() { return this.player; }
	
	/**
	 * Returns a string representation of the level.
	 *
	 * @return A string like "LevelN".
	 */
	@Override
	public String toString() {
		return "Level" + (getNumberOfLevels());
	}

	/**
	 * Displays the current state of the level and player to the console.
	 * Prints the player status and the map grid.
	 */
	public void show() {
		System.out.println(this.player + " --- " + this);
		for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                System.out.print(this.map[i][j]);
            }
            System.out.println(); 
        }
	}
	
	/**
	 * Checks if a proposed move is valid.
	 * A move is valid if it is within bounds and doesn't hit a wall ('#').
	 *
	 * @param y The vertical displacement.
	 * @param x The horizontal displacement.
	 * @return true if the move is valid, false otherwise.
	 */
	public boolean checkMoveValidity(int y, int x) {
		return (this.getMap()[this.getPlayer().getPosition()[0] + y][this.getPlayer().getPosition()[1]+x] != '#' && 
        		( (this.getPlayer().getPosition()[1] + x ) >= 0 && this.getCols() > (this.getPlayer().getPosition()[1] + x) ) && 
        		( (this.getPlayer().getPosition()[0] + y ) >= 0 && this.getRows() > (this.getPlayer().getPosition()[0] + y) ));
	}
	
	/**
	 * Updates the game state based on a player's move.
	 * If the move is valid, updates the player position and the map grid visual.
	 * Finally, displays the updated state.
	 *
	 * @param y The vertical displacement.
	 * @param x The horizontal displacement.
	 */
	public void update(int y, int x) {
		if(this.checkMoveValidity(y,x)) {
			this.map[this.player.getPosition()[0]][this.player.getPosition()[1]] = ' ';
			this.map[this.player.getPosition()[0] + y][this.player.getPosition()[1] + x] = String.valueOf(Player.getNumberOfPlayers()).charAt(0);
			this.player.move(y, x);
		}
		System.out.println("---" + this + "--- " + this.player);
		this.show();
	}
	
	/**
	 * Captures keyboard input to control player movement.
	 * Supports 'z' (up), 's' (down), 'q' (left), 'd' (right).
	 */
	public void getInputKey() {
		Scanner myScanner = new Scanner(System.in);
		int[] move = {0, 0};
		char key = String.valueOf(myScanner.nextLine()).charAt(0);
		switch (key){
			case 'q':
				move[1] = -1;
				move[0] = 0;
				break;
			case 'z':
				move[1] = 0;
				move[0] = -1;
				break;
			case 'd':
				move[1] = 1;
				move[0] = 0;
				break;
			case 's':
				move[1] = 0;
				move[0] = 1;
				break;
		}
		this.update(move[0], move[1]);
	}

	/**
	 * Main method to run the level logic.
	 * Loads a level file and enters the game loop.
	 *
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		/*
		 * String[] lines = {
		        "########################################",
		        "#                  #                   #",
		        "#    ######        #     ###   ###     #",
		        "#    #             #     #       #     #",
		        "#    #                   #       #     #",
		        "#    ######        #     #########     #",
		        "#       #          #                   #",
		        "#       ############                   #",
		        "#                                      #",
		        "#    #######   ###### #####   #####    #",
		        "#          #   #          #   #        #",
		        "#          #   #          #   #        #",
		        "#    #######   #          #   #####    #",
		        "#              #          #            #",
		        "########################################"
		    };
		 */


		try {
            Level level = new Level("./src/generation/level2.txt");
			/*
             * 
             * Manual movements in all 4 directions 
             * 
            // Move to left if possible
        	level1.update(0, -1);
        	System.out.println("Moved Left");

            // Move to top if possible
        	level1.update(-1, 0);
        	System.out.println("Moved Up");

            // Move to right if possible
        	level1.update(0, 1);
        	System.out.println("Moved Right");
            
            // Move down if possible
        	level1.update(1, 0);
        	System.out.println("Moved Down");
			*/
            
            level.show();
            while(level.getGameOn()) {
            	TimeUnit.SECONDS.sleep(1);
            	level.getInputKey();
            	
            }
            
              
        } catch (Exception e) {
            System.err.println("Placement error : " + e.getMessage());
            System.err.println(e.getLocalizedMessage());
        }
        
		
	}

}
