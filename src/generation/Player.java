package generation;

/**
 * Represents a player in the game with score, lives, and position.
 * @author Abdelhamid AKHATAR <abdelhamid.akhatar@etu.cyu.fr>
 */
public class Player {
	private String name;
	private int score;
	private int lives;
	private int[] position = {0, 0};
	private static int numberOfPlayers = 0;

	/**
	 * Constructs a new Player.
	 * @param position Initial coordinates {y, x}.
	 * @param name The player's name.
	 */
	public Player(int[] position, String name) { 
		this.position = position;
		this.score = 0;
		this.lives = 5;
		this.name = name;
		numberOfPlayers++;
	}

	/**
	 * Gets the total number of players instantiated.
	 * @return The count of players.
	 */
	public static int getNumberOfPlayers() { return numberOfPlayers; }
	
	/**
	 * Gets the current lives of the player.
	 * @return The number of lives.
	 */
	public int getLives() { return this.lives; }
	
	/**
	 * Sets the player's lives.
	 * @param lives The new number of lives (maps negative values to 0).
	 */
	public void setLives(int lives) {
		if(lives>=0) {
			this.lives = lives;
		} else {
			this.lives = 0;
		}
	}

	/**
	 * Gets the player's name.
	 * @return The name string.
	 */
	public String getName() { return this.name; }

	/**
	 * Gets the player's current score.
	 * @return The score.
	 */
	public int getScore() { return this.score; }
	
	/**
	 * Sets the player's position.
	 * @param position An array {y, x}.
	 */
	public void setPosition(int[] position) {
		this.position = position;
	}

	/**
	 * Gets the player's current position.
	 * @return The position reference {y, x}.
	 */
	public int[] getPosition() { return this.position; }

	/**
	 * Moves the player by a specified offset.
	 * @param y Vertical offset.
	 * @param x Horizontal offset.
	 */
	public void move(int y, int x) {
		this.position[0] += y;
		this.position[1] += x;
	}

	/**
	 * Sets the player's score.
	 * @param score The new score (ignored if <= 0).
	 */
	public void setScore(int score) {
		if(score > 0) {
			this.score = score;
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

