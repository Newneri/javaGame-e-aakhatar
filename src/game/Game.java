package game;
import java.util.concurrent.TimeUnit;
import java.lang.IllegalArgumentException;
import java.util.Scanner;

import generation.*;
import generation.characters.Player;

/**
 * Main game class that manages the game loop and level transitions.
 * @author Abdelhamid AKHATAR &lt;abdelhamid.akhatar@etu.cyu.fr&gt;
 */
public class Game {

	/** Whether the game loop should continue running. */
	private boolean gameOn = true;
	/** Paths to the level configuration files, in play order. */
	private String[] levels;
	/** Index of the currently active level within {@link #levels}. */
	private int index = 0;
	/** The currently active level instance. */
	private Level level;
	/** The player that persists and carries stats across all levels. */
	private Player player;

	/**
	 * Constructs a new Game instance.
	 * @param args Array of level file paths.
	 * @param name The name of the player.
	 * @throws IllegalArgumentException if no level file path is provided.
	 */
	public Game(String[] args, String name) throws IllegalArgumentException{
		if(args.length == 0) {
			throw new IllegalArgumentException("Veuillez saisir au moins 1 fichier de niveau en argument");
		}
		this.levels = args.clone();
		this.player = new Player(new int[] {0,0}, name, 5);
		this.level = new Level(this.levels[index], player);
	}

	/**
	 * Sets the game state.
	 * @param status {@code true} if the game should keep running, {@code false} to stop.
	 */
	public void setGameOn(boolean status){
		this.gameOn = status;
	}

	/**
	 * Checks if the game is currently running.
	 * @return {@code true} if the game is on, {@code false} otherwise.
	 */
	public boolean getGameOn() {
		return this.gameOn;
	}

	/**
	 * Advances the game to the next level.
	 * The same {@link Player} instance is reused so score and lives carry over.
	 */
	public void nextLevel() {
		this.index++;
		this.level = new Level(this.levels[this.index], this.player);
		System.out.println(this.level);
	}

	/**
	 * Main entry point for the game.
	 * Prompts for a player name, runs the game loop, and handles level transitions.
	 * @param args Command-line arguments containing level file paths.
	 */
	public static void main(String[] args) {
		try {
			Scanner myScanner = new Scanner(System.in);
			String name;
			System.out.println("What is your Player name ? ");
			name = myScanner.next();
			Game game = new Game(args, name);
			String answer;
			System.out.println(game.level);
            while(game.getGameOn()) {
            	TimeUnit.MILLISECONDS.sleep(500);
            	game.level.update();
            	if(game.level.getPlayer().getLives() == 0) {
            		System.out.println("GAME OVER");
            		System.out.print("Do you want to try again ? (yes/no) ");
            		answer = myScanner.next();
            		if(answer.equals("yes")) {
            			game = new Game(args, name);
            			System.out.println(game.level);
            		} else {
            			System.out.println("Okey, see you !");
            			game.setGameOn(false);
            		}
            	}
            	if(game.level.isComplete()) {
            		System.out.println("LEVEL FINISHED");
            		if(game.index < game.levels.length - 1) {
            			game.nextLevel();
            		} else if(game.index == game.levels.length - 1) {
            			System.out.println("GAME FINISHED");
            			System.out.println("Thanks for playing " + game.level.getPlayer().getName() + "!");
            			game.setGameOn(false);
            		}
            	}
            }
			myScanner.close();

		} catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
	}

}
