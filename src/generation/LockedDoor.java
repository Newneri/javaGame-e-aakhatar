package generation;

/**
 * Represents a locked door cell in the game map.
 * A locked door acts as a wall until it is unlocked with the correct key.
 * @author Abdelhamid AKHATAR <abdelhamid.akhatar@etu.cyu.fr>
 */
public class LockedDoor extends Wall {
	/** Whether this door is currently locked. */
	private boolean isLocked = true;
	/** The key string required to unlock this door. */
	private String requiredKey;

	/**
	 * Constructs a new LockedDoor at the given position.
	 * @param position The grid coordinates {row, col} of this door.
	 * @param requiredKey The key string that must be provided to unlock this door.
	 */
	public LockedDoor(int[] position, String requiredKey) {
		super(position);
		this.requiredKey = requiredKey;
	}

	/**
	 * Unlocks the door, making it passable.
	 */
	public void unlock() {
		this.isLocked = false;
	}

	/**
	 * Checks whether the door is currently locked.
	 * @return {@code true} if the door is locked, {@code false} otherwise.
	 */
	public boolean isLocked() {
		return this.isLocked;
	}

	/**
	 * Gets the key required to unlock this door.
	 * @return The required key string.
	 */
	public String getRequiredKey() {
		return this.requiredKey;
	}
}
