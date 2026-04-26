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

	private boolean gameOn = true;
	private String[] levels;
	private int index = 0;
	private Level level;
	private Player player;

	/**
	 * Constructs a new Game instance.
	 * @param args Array of level file paths.
	 * @param name The name of the player.
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
	 * @param status true if the game is running, false otherwise.
	 */
	public void setGameOn(boolean status){
		this.gameOn = status;
	}

	/**
	 * Checks if the game is currently running.
	 * @return true if the game is on, false otherwise.
	 */
	public boolean getGameOn() {
		return this.gameOn;
	}
	
	/**
	 * Advances the game to the next level.
	 * Preserves player stats (score, lives) across levels.
	 */
	public void nextLevel() {
		this.index++;
		this.level = new Level(this.levels[this.index], this.player);
		System.out.println(this.level);
	}

	
	/**
	 * Main entry point for the game.
	 * @param args Command line arguments containing level file paths.
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
            	if(game.level.getNumberOfCoins() == 0) {
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
