package generation.items;
import java.util.Comparator;

/**
 * Custom comparator for sorting inventory items.
 * Prioritizes Items (weapons) before Abilities, then sorts alphabetically by name within each category.
 * This ensures weapons are always displayed first in the player's inventory.
 * @author Abdelhamid AKHATAR &lt;abdelhamid.akhatar@etu.cyu.fr&gt;
 */
public class InventoryComparator implements Comparator<Usable>{

	/**
	 * Constructs a new InventoryComparator.
	 */
	public InventoryComparator() {
	}

	/**
	 * Compares two usable objects for sorting.
	 * Prioritizes Items (weapons) before Abilities, then uses alphabetical ordering.
	 * @param o1 The first usable object to compare.
	 * @param o2 The second usable object to compare.
	 * @return Negative if o1 should come before o2, positive if after, zero if equal priority.
	 */
	@Override
	public int compare(Usable o1, Usable o2) {
		if(o1 instanceof Item && o2 instanceof Ability) {
			return -1;
		} else if(o1 instanceof Ability && o2 instanceof Item) {
			return 1;
		}
		return o1.compareTo(o2);
	}

}
