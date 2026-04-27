package generation.characters;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import generation.map.Movement;
import generation.Level;
import generation.items.*;

/**
 * Represents a player in the game with score, lives, and position.
 * @author Abdelhamid AKHATAR &lt;abdelhamid.akhatar@etu.cyu.fr&gt;
 */
public class Player extends Character{
	private int score;
	private static int numberOfPlayers = 0;
	private Scanner myScanner = new Scanner(System.in);
	private List<Usable> inventory = new ArrayList<Usable>();
	private int enemiesDefeated = 0;
	private InventoryComparator invSorter = new InventoryComparator();
	

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

	public int getEnemiesDefeated() {
		return this.enemiesDefeated;
	}
	
	public void incrementEnemiesDefeated() {
		this.enemiesDefeated++;
	}

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
	
	public List<Usable> getInventory(){
		return this.inventory;
	}
	
	public boolean hasItem(Class<?> itemClass) {
		for(Usable usable: this.inventory) {
			if(itemClass.isInstance(usable)) {
				return true;
			}
		}
		return false;
	}
	
	public int getItemIndex(Class<?> itemClass) {
		for(int i = 0; i < this.inventory.size(); i++) {
			if(itemClass.isInstance(this.inventory.get(i))) {
				return i;
			}
		}
		return -1;
	}
	
	public char readKey() {
		return String.valueOf(myScanner.next()).charAt(0);
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
	
	public void sortInventory() {
		Collections.sort(this.getInventory(), this.invSorter);
	}


	@Override
	public String toString() {
		this.sortInventory();
		String pts = this.score > 1 ? "pts" : "pt";
		String stats = this.name + " | " + this.score + " " + pts + " | " + this.lives + " lives | " + this.getEnemiesDefeated() + " kills\n" ;

		String top = "╔═══════╦═══════╦═══════╦═══════╦═══════╗\n";
		String mid = "║";
		String bot = "╚═══════╩═══════╩═══════╩═══════╩═══════╝\n";
		String names = "║";

		for(int i = 0; i < 5; i++) {
			if(i < this.inventory.size()) {
				Usable item = this.inventory.get(i);
				mid   += "  [" + item.getSymbol() + "]  ║";
				String n = item.getName();
				if(n.length() > 7) n = n.substring(0, 6) + ".";
				names += String.format("%-7s║", n);
			} else {
				mid   += "       ║";
				names += "  ---  ║";
			}
		}

		return stats + top + mid + "\n" + names + "\n" + bot;
	}

	@Override 
	public boolean equals(Object obj) {
		if (obj instanceof Player) {
			Player player = (Player) obj;
			return this.getName().toLowerCase().equals(player.getName().toLowerCase());
		}
		return false;
	}
	
	public void addItem(Usable usable) {
		if(this.getInventory().size() < 5) {			
			this.getInventory().add(usable);
		}
	}
	
	public void useItem(int index, Level level) {
		this.getInventory().get(index).use(level);
		if(this.getInventory().get(index).isConsummed()) {
			this.getInventory().remove(index);
		}
	}
	
	public boolean tryUseSlot(int index) {
		if(this.getInventory().size() > index && this.getInventory().get(index) != null 
				&& this.getInventory().get(index).getActivable()) {
			return true;
		}
		return false;
	}
	
	public int activateSlot(int index, Level level) {
		if(this.tryUseSlot(index)) {
			this.useItem(index, level);
			return 1;
		}
		return 0;
	}
}

