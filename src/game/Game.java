package game;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;

import generation.*;

/**
 * Main game class that manages the game loop and level transitions.
 * @author Abdelhamid AKHATAR
 * @email abdelhamid.akhatar@etu.cyu.fr
 */
public class Game {

	private boolean gameOn = true;
	private String[] levels;
	private int index = 0;
	private Level level;
	private String name;

	/**
	 * Constructs a new Game instance.
	 * @param args Array of level file paths.
	 * @param name The name of the player.
	 */
	public Game(String[] args, String name){
		this.levels = args.clone();
		this.name = name;
		this.level = new Level(this.levels[index], name);
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
		int[] stats = {this.level.getPlayer().getScore(), this.level.getPlayer().getLives()};
		this.level = null;
		this.level = new Level(this.levels[this.index], this.name);
		this.level.getPlayer().setScore(stats[0]);
		this.level.getPlayer().setLives(stats[1]);
		this.level.show();
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
			game.level.show();
            while(game.getGameOn()) {
            	TimeUnit.MILLISECONDS.sleep(500);
            	game.level.update();
            	if(game.level.getPlayer().getLives() == 0) {
            		System.out.println("GAME OVER");
            		System.out.print("Do you want to try again ? (yes/no) ");
            		answer = myScanner.next();
            		if(answer.equals("yes")) {
            			game = null;
            			game = new Game(args, name);
            			game.level.show();
            		} else {
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
            		}
            	} 
            }
            
		} catch (Exception e) {
            System.err.println(e.getMessage());            
        }
	}

}
