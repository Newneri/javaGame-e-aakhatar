package generation;

enum WinCondition {
	KILL_ALL_ENEMIES("Level Win condition: Kill all the enemies", "KillAllEnemies"),
	PICK_CROWN("Level Win condition: get the Crown Item", "PickCrownItem"),
	GET_ALL_COINS("Level Win condition: get all the coins", "GetAllCoins");
	
	private final String display;
	private final String key;
	
	private WinCondition(String display, String key) {
		this.display = display;
		this.key = key;
	}
	
	public String getDisplayText() {
		return this.display;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public static WinCondition fromkey(String key) {
		for(WinCondition wc: values()) {
			if(wc.getKey().equals(key)) return wc;
		}
		return null;
	}

}
