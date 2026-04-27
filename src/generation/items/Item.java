package generation.items;
import generation.Level;

/**
 * Abstract base class for consumable items in the game.
 * Items are usable objects that are consumed when used (unlike Abilities which are reusable).
 * @author Abdelhamid AKHATAR &lt;abdelhamid.akhatar@etu.cyu.fr&gt;
 */
public abstract class Item implements Usable{
	/** Character used to display this item on the map. */
	private char symbol;
	/** Name of this item. */
	private String name;
	/** Whether this item can be activated (used) by the player. */
	private boolean activable;

	/**
	 * Constructs a new Item with the given properties.
	 * @param symbol The character representation of this item on the map.
	 * @param name The name of this item.
	 * @param activable {@code true} if this item can be used, {@code false} if it's passive.
	 */
	public Item(char symbol, String name, boolean activable) {
		this.symbol = symbol;
		this.name = name;
		this.activable = activable;
	}

	/**
	 * Gets the name of this item.
	 * @return The item's name.
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the character symbol for this item.
	 * @return The symbol used to display this item on the map.
	 */
	@Override
	public char getSymbol() {
		return this.symbol;
	}

	/**
	 * Gets whether this item can be activated (used) by the player.
	 * @return {@code true} if the item is activable, {@code false} otherwise.
	 */
	@Override
	public boolean getActivable() {
		return this.activable;
	}

	/**
	 * Uses this item according to its specific effect.
	 * Implemented by subclasses to define item behavior.
	 * @param level The current game level.
	 */
	@Override
	public abstract void use(Level level);

	/**
	 * Checks if this item is consumed when used.
	 * Items are always consumed (unlike Abilities which are reusable).
	 * @return {@code true} indicating the item is consumed.
	 */
	@Override
	public boolean isConsummed() {
		return true;
	}
}
