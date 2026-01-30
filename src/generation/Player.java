package generation;

/**
 * Represents a player in the game with a name, score, and position.
 * Keeps track of the total number of players created.
 * 
 * @author Akhatar Abdelhamid abdelhamid.akhatar@etu.cyu.fr
 */
public class Player {
	final String name;
	private int score;
	private int[] position = {0, 0};
	private static int numberOfPlayers = 0;
	
	/**
	 * Constructs a new Player with the specified name.
	 * Initializes score to 0 and increments the player count.
	 *
	 * @param name The name of the player.
	 */
	public Player(String name) {
		this.name = name;
		this.score = 0;
		numberOfPlayers++;
	}
	
	/**
	 * Constructs a new Player with a default name "PlayerN".
	 * Uses the total number of players to generate the name.
	 */
	public Player() {
		this("Player" + (getNumberOfPlayers() + 1));
	}
	
	/**
	 * Constructs a new Player with a default name and a specified starting position.
	 *
	 * @param position An array of two integers representing the [y, x] coordinates.
	 */
	public Player(int[] position) { 
		this("Player" + (getNumberOfPlayers() + 1));
		this.position = position;
	}
	
	/**
	 * Returns the total number of Player instances created.
	 *
	 * @return The number of players.
	 */
	public static int getNumberOfPlayers() { return numberOfPlayers; }
	
	/**
	 * Gets the name of the player.
	 *
	 * @return The player's name.
	 */
	public String getName() { return this.name; }
	
	/**
	 * Gets the current score of the player.
	 *
	 * @return The player's score.
	 */
	public int getScore() { return this.score; }
	
	/**
	 * Gets the current position of the player.
	 *
	 * @return An array containing the coordinates [y, x].
	 */
	public int[] getPosition() { return this.position; }
	
	/**
	 * Moves the player by the specified amount in y (row) and x (column) directions.
	 *
	 * @param y The displacement in the vertical direction.
	 * @param x The displacement in the horizontal direction.
	 */
	public void move(int y, int x) {
		this.position[0] += y;
		this.position[1] += x;
	}
	
	/**
	 * Updates the player's score by adding the specified amount.
	 * The score cannot be negative; if the result is less than 0, the score is set to 0.
	 *
	 * @param scoreChange The amount to change the score by (can be negative).
	 */
	public void setScore(int scoreChange) {
		if(this.score + scoreChange < 0) {
			this.score = 0;
		} else {
			this.score += scoreChange;
		}
	}
	
	/**
	 * Returns a string representation of the player, including name, score, and position.
	 *
	 * @return A string describing the player.
	 */
	@Override
	public String toString() {
		return this.name + " has " + this.getScore() + " points" + " row: "+this.position[0]+", col: "+this.position[1];
	}
	
	/**
	 * Compares this player to another object.
	 * Two players are considered equal if they have the same name (case-insensitive).
	 *
	 * @param obj The object to compare with.
	 * @return true if the objects are equal, false otherwise.
	 */
	@Override 
	public boolean equals(Object obj) {
		if (obj instanceof Player) {
			Player player = (Player) obj;
			return this.getName().toLowerCase().equals(player.getName().toLowerCase());
		}
		return false;
	}
	
	/**
	 * Main method for testing the Player class functionality.
	 *
	 * @param args Command line arguments (not used).
	 */
	public static void main(String[] args) {
		System.out.println("Number of players created: " + Player.getNumberOfPlayers());
		
		Player p1 = new Player("Alice");
		Player p2 = new Player("Bob");
			
		p1.setScore(5);
		p2.setScore(-6);
		
		System.out.println(p1);
		System.out.println(p2);
		
		System.out.println(p1.equals("Alice")); // Player p1 equals "Alice" ? False -> not even the same instance type
		System.out.println(p1.equals(p2)); // Player p1 equals Player p2 ? False -> this players have different names 
		Player p3 = new Player("BOB");
		System.out.println(p2.equals(p3)); // Player p2 equals Player p3 ? True because redefined equals is not case sensitive
		System.out.println(p1 == p2); // p1 and p2 reference the same instance ? False -> they reference different instances
		Player p2_2 = p2; 
		System.out.println(p2_2 == p2); // p2_2 and p2 reference the same instance ? True -> in the previous line we are referencing p2_2 to the same instance as p2
		
		p1 = null; // p1 was the only reference to his instance so the garbage collector will delete it now that we set p1 to null
		
		Player p4 = new Player();
		Player p5 = new Player();
		
		System.out.println(p4);
		System.out.println(p5);
		
		System.out.println("Number of players created: " + Player.getNumberOfPlayers());
	}
}


