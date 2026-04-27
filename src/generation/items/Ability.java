package generation.items;
import generation.Level;

/**
 * Abstract base class for reusable special abilities in the game.
 * Abilities (unlike Items) are not consumed when used and can be activated multiple times.
 * @author Abdelhamid AKHATAR &lt;abdelhamid.akhatar@etu.cyu.fr&gt;
 */
public abstract class Ability implements Usable{
	/** Character used to display this ability on the map. */
	private char symbol;
	/** Name of this ability. */
	private String name;
	/** Whether this ability can be activated (used) by the player. */
	private boolean activable;

	/**
	 * Constructs a new Ability with the given properties.
	 * @param symbol The character representation of this ability on the map.
	 * @param name The name of this ability.
	 * @param activable {@code true} if this ability can be used, {@code false} if it's passive.
	 */
	public Ability(char symbol, String name, boolean activable) {
		this.symbol = symbol;
		this.name = name;
		this.activable = activable;
	}

	/**
	 * Gets the name of this ability.
	 * @return The ability's name.
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the character symbol for this ability.
	 * @return The symbol used to display this ability on the map.
	 */
	@Override
	public char getSymbol() {
		return this.symbol;
	}

	/**
	 * Gets whether this ability can be activated (used) by the player.
	 * @return {@code true} if the ability is activable, {@code false} otherwise.
	 */
	@Override
	public boolean getActivable() {
		return this.activable;
	}

	/**
	 * Uses this ability according to its specific effect.
	 * Implemented by subclasses to define ability behavior.
	 * @param level The current game level.
	 */
	@Override
	public abstract void use(Level level);

	/**
	 * Checks if this ability is consumed when used.
	 * Abilities are never consumed (they can be used repeatedly).
	 * @return {@code false} indicating the ability is not consumed.
	 */
	@Override
	public boolean isConsummed() {
		return false;
	}
}
