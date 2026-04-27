package generation.items;

import generation.Level;

/**
 * Represents a Lockpicking ability that allows the player to pass through locked doors.
 * This is a passive ability that grants movement permissions — it has no active use effect.
 * Once obtained, the player can traverse locked doors freely.
 * Displayed as 'L' on the inventory.
 * @author Abdelhamid AKHATAR &lt;abdelhamid.akhatar@etu.cyu.fr&gt;
 */
public class Lockpicking extends Ability {

	/**
	 * Constructs a new Lockpicking ability.
	 * Symbol: 'L', Name: "Lockpicking", Non-activable (passive, no use effect).
	 */
	public Lockpicking() {
		super('L', "Lockpicking", false);
	}

	/**
	 * Uses the Lockpicking ability (no effect).
	 * Lockpicking is a passive ability that affects movement rules, not an active power.
	 * @param level The current game level.
	 */
	@Override
	public void use(Level level) {
		//Passive item, does nothing just allows player to go through locked doors.
	}

}
