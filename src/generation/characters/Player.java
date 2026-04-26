package generation.characters;
import java.util.Scanner;

import generation.map.Movement;

/**
 * Represents a player in the game with score, lives, and position.
 * @author Abdelhamid AKHATAR &lt;abdelhamid.akhatar@etu.cyu.fr&gt;
 */
public class Player extends Character{
	private int score;
	private static int numberOfPlayers = 0;
	private Scanner myScanner = new Scanner(System.in);

	/**
	 * Constructs a new Player.
	 * @param position Initial coordinates {y, x}.
	 * @param name The player's name.
	 * @param lives The starting number of lives.
	 */
	public Player(int[] position, String name, int lives) { 
		super(name, lives, position);
		this.score = 0;
		numberOfPlayers++;
	}

	/**
	 * Gets the total number of players instantiated.
	 * @return The count of players.
	 */
	public static int getNumberOfPlayers() { return numberOfPlayers; }


	/**
	 * Gets the player's current score.
	 * @return The score.
	 */
	public int getScore() { return this.score; }
	

	/**
	 * Sets the player's score.
	 * @param score The new score (ignored if negative).
	 */
	public void setScore(int score) {
		if(score >= 0) {
			this.score = score;
		}
	}

	
	/**
	 * Reads a single character from stdin and maps it to a {@link Movement} direction.
	 * Keys: 'z' = UP, 's' = DOWN, 'q' = LEFT, 'd' = RIGHT. Any other key yields NONE.
	 * @return The {@link Movement} corresponding to the key pressed.
	 */
	@Override
	public Movement chooseMovement() {
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


	@Override
	public String toString() {
		String var;
		if(this.score > 1) {
			var = "pts";
		} else {
			var = "pt";
		}
		
		return this.name + ": " + this.getScore() + var + " " + this.getLives() + " lives"; 
	}

	@Override 
	public boolean equals(Object obj) {
		if (obj instanceof Player) {
			Player player = (Player) obj;
			return this.getName().toLowerCase().equals(player.getName().toLowerCase());
		}
		return false;
	}

	/**
	 * Main method for testing Player class functionality independently.
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
	}
}

