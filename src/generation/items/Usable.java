package generation.items;
import generation.Level;

/**
 * Interface for all usable objects in the game (items and abilities).
 * Defines the contract for items that can be collected, stored in inventory, and used.
 * Items are sorted alphabetically by name via the default {@link #compareTo} implementation.
 * @author Abdelhamid AKHATAR &lt;abdelhamid.akhatar@etu.cyu.fr&gt;
 */
public interface Usable extends Comparable<Usable> {

	/**
	 * Uses this usable object according to its specific game effect.
	 * @param level The current game level context.
	 */
	public void use(Level level);

	/**
	 * Checks whether this usable object is consumed when used.
	 * @return {@code true} if the item is consumed (removed from inventory), {@code false} if reusable.
	 */
	public boolean isConsummed();

	/**
	 * Gets the character symbol used to display this item on the map.
	 * @return A single character representing this usable.
	 */
	public char getSymbol();

	/**
	 * Gets the name of this usable object.
	 * @return The name string.
	 */
	public String getName();

	/**
	 * Gets whether this usable object can be activated (used) by the player.
	 * @return {@code true} if the item can be used, {@code false} if it's passive.
	 */
	public boolean getActivable();

	/**
	 * Default comparison method that sorts usables alphabetically by name.
	 * @param other The usable to compare to.
	 * @return Negative if this name comes before other's, positive if after, zero if equal.
	 */
	default int compareTo(Usable other) {
		return this.getName().compareTo(other.getName());
	}
}
