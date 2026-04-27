package generation.items;
import generation.Level;

/**
 * Represents an Hourglass item that freezes all enemies for a fixed number of turns when used.
 * Useful for evading enemy attacks or creating space to collect items.
 * Displayed as 'H' on the map.
 * @author Abdelhamid AKHATAR &lt;abdelhamid.akhatar@etu.cyu.fr&gt;
 */
public class Hourglass extends Item{

	/**
	 * Constructs a new Hourglass item.
	 * Symbol: 'H', Name: "Hourglass", Activable (can be used).
	 */
	public Hourglass() {
		super('H', "Hourglass", true);
	}

	/**
	 * Uses the Hourglass to freeze all enemies for 10 turns.
	 * During frozen turns, enemies cannot move.
	 * @param level The current game level.
	 */
	@Override
	public void use(Level level) {
		level.freeze(10);
	}

}
