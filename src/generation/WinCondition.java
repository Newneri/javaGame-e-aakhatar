package generation;

/**
 * Enum representing the possible win conditions for a game level.
 * Each level is configured with one of these conditions that must be met to complete it.
 * @author Abdelhamid AKHATAR &lt;abdelhamid.akhatar@etu.cyu.fr&gt;
 */
enum WinCondition {
	/** Level is won by eliminating all enemies. */
	KILL_ALL_ENEMIES("Level Win condition: Kill all the enemies", "KillAllEnemies"),
	/** Level is won by collecting the Crown item. */
	PICK_CROWN("Level Win condition: get the Crown Item", "PickCrownItem"),
	/** Level is won by collecting all coins on the map. */
	GET_ALL_COINS("Level Win condition: get all the coins", "GetAllCoins");

	/** Human-readable description of this win condition. */
	private final String display;
	/** Key string used to identify this condition in level configuration files. */
	private final String key;

	/**
	 * Constructs a WinCondition with display text and key identifier.
	 * @param display A human-readable description of the win condition.
	 * @param key The string key used in level files to specify this condition.
	 */
	private WinCondition(String display, String key) {
		this.display = display;
		this.key = key;
	}

	/**
	 * Gets the human-readable display text for this win condition.
	 * @return A description of what must be accomplished to win.
	 */
	public String getDisplayText() {
		return this.display;
	}

	/**
	 * Gets the key string identifying this condition in level configuration files.
	 * @return The condition's key identifier.
	 */
	public String getKey() {
		return this.key;
	}

	/**
	 * Looks up a WinCondition by its key string from a level configuration file.
	 * @param key The key to search for.
	 * @return The matching {@link WinCondition}, or {@code null} if no match is found.
	 */
	public static WinCondition fromkey(String key) {
		for(WinCondition wc: values()) {
			if(wc.getKey().equals(key)) return wc;
		}
		return null;
	}

}
