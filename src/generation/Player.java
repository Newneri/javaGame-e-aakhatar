package generation;

public class Player {
	private String name;
	private int score;
	private int lives;
	private int[] position = {0, 0};
	private static int numberOfPlayers = 0;

	public Player(int[] position, String name) { 
		this.position = position;
		this.score = 0;
		this.lives = 5;
		this.name = name;
		numberOfPlayers++;
	}

	public static int getNumberOfPlayers() { return numberOfPlayers; }
	
	public int getLives() { return this.lives; }
	
	public void setLives(int lives) {
		if(lives>=0) {
			this.lives = lives;
		} else {
			this.lives = 0;
		}
	}

	public String getName() { return this.name; }

	public int getScore() { return this.score; }
	
	public void setPosition(int[] position) {
		this.position = position;
	}

	public int[] getPosition() { return this.position; }

	public void move(int y, int x) {
		this.position[0] += y;
		this.position[1] += x;
	}

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

	public static void main(String[] args) {
	}
}

