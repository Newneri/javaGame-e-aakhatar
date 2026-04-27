package generation.items;

import generation.Level;

/**
 * Represents the Crown item, a win condition object that can be picked up to complete certain levels.
 * The Crown has no active effect when used — it serves only as a collectible goal.
 * Displayed as 'K' on the map.
 * @author Abdelhamid AKHATAR &lt;abdelhamid.akhatar@etu.cyu.fr&gt;
 */
public class Crown extends Item{

	/**
	 * Constructs a new Crown item.
	 * Symbol: 'K', Name: "Crown", Non-activable (passive win condition item).
	 */
	public Crown() {
		super('K', "Crown", false);
	}

	/**
	 * Uses the Crown item (no effect).
	 * The Crown serves as a win condition item and has no active effect.
	 * @param level The current game level.
	 */
	@Override
	public void use(Level level) {
		// Does nothing, it's a win condition Item
	}

}
