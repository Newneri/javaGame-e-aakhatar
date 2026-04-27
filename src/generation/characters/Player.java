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
	 * Gets the player's current score.
	 * @return The player's score.
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * Sets the player's score to a specific value.
	 * @param score The new score to set.
	 * @return
	 */
	public void setScore(int score) {
		if(score >= 0) {
			this.score = score;
		} else {
			this.score = 0;
		}
	}

	/**
	 * Gets the total number of players instantiated.
	 * @return The count of players.
	 */
	public static int getNumberOfPlayers() { return numberOfPlayers; }

	/**
	 * Gets the number of enemies defeated by this player.
	 * @return The count of defeated enemies.
	 */
	public int getEnemiesDefeated() {
		return this.enemiesDefeated;
	}

	/**
	 * Increments the count of enemies defeated by this player by 1.
	 */
	public void incrementEnemiesDefeated() {
		this.enemiesDefeated++;
	}

	/**
	 * Gets the player's inventory list.
	 * @return A list of {@link Usable} items/abilities in the inventory (max 5).
	 */
	public List<Usable> getInventory(){
		return this.inventory;
	}

	/**
	 * Checks whether the player has an item of a specific type in their inventory.
	 * @param itemClass The class to search for (e.g., Weapon.class, Lockpicking.class).
	 * @return {@code true} if an item of that type exists in inventory, {@code false} otherwise.
	 */
	public boolean hasItem(Class<?> itemClass) {
		for(Usable usable: this.inventory) {
			if(itemClass.isInstance(usable)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Finds the inventory index of an item of a specific type.
	 * @param itemClass The class to search for (e.g., Weapon.class, Lockpicking.class).
	 * @return The inventory index (0-4) if found, or -1 if not found.
	 */
	public int getItemIndex(Class<?> itemClass) {
		for(int i = 0; i < this.inventory.size(); i++) {
			if(itemClass.isInstance(this.inventory.get(i))) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Reads a single character from user input.
	 * @return The first character of the next user input.
	 */
	public char readKey() {
		return String.valueOf(myScanner.next()).charAt(0);
	}

	
	/**
	 * Reads a single character from stdin and maps it to a {@link Movement} direction.
	 * Keys: 'z' = UP, 's' = DOWN, 'q' = LEFT, 'd' = RIGHT. Any other key yields NONE.
	 * @return The {@link Movement} corresponding to the key pressed.
	 */
	public Movement chooseMovement() {
		char key = this.readKey();

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

	/**
	 * Sorts the inventory using {@link InventoryComparator}.
	 * Items appear before Abilities, and both are sorted alphabetically by name.
	 */
	public void sortInventory() {
		Collections.sort(this.getInventory(), this.invSorter);
	}


	/**
	 * Returns a formatted string representation of the player for console display.
	 * Includes player stats (name, score, lives, kills) and a visual inventory table.
	 * @return A multi-line string showing player information and inventory.
	 */
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

	/**
	 * Compares this player with another object for equality.
	 * Two players are equal if their names are the same (case-insensitive).
	 * @param obj The object to compare with.
	 * @return {@code true} if both are players with the same name, {@code false} otherwise.
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
	 * Adds an item to the player's inventory if there is space (max 5 items).
	 * @param usable The item or ability to add to inventory.
	 */
	public void addItem(Usable usable) {
		if(this.getInventory().size() < 5) {
			this.getInventory().add(usable);
		}
	}

	/**
	 * Uses an item from the inventory at the given index.
	 * If the item is consumable, it is removed from inventory after use.
	 * @param index The inventory slot (0-4) to use.
	 * @param level The current game level context.
	 */
	public void useItem(int index, Level level) {
		this.getInventory().get(index).use(level);
		if(this.getInventory().get(index).isConsummed()) {
			this.getInventory().remove(index);
		}
	}

	/**
	 * Checks if an inventory slot can be activated (used).
	 * @param index The inventory slot (0-4) to check.
	 * @return {@code true} if the slot exists, contains an item, and the item is activable.
	 */
	public boolean tryUseSlot(int index) {
		if(this.getInventory().size() > index && this.getInventory().get(index) != null
				&& this.getInventory().get(index).getActivable()) {
			return true;
		}
		return false;
	}

	/**
	 * Attempts to activate and use an item in the inventory slot.
	 * @param index The inventory slot (0-4) to activate.
	 * @param level The current game level context.
	 */
	public void activateSlot(int index, Level level) {
		if(this.tryUseSlot(index)) {
			this.useItem(index, level);
		}
	}
}

