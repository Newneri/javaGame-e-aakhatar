package generation.items;

import generation.Level;

public class Crown extends Item{

	public Crown() {
		super('K', "Crown", false);
	}

	@Override
	public void use(Level level) {
		// Does nothing, it's a win condition Item
	}
	

}
