package generation.items;

import generation.Level;

public class Lockpicking extends Ability {

	public Lockpicking() {
		super('L', "Lockpicking", false);
	}

	@Override
	public void use(Level level) {
		//Passive item, does nothing just allows player to go through locked doors.
	}

}
