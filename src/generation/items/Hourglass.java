package generation.items;
import generation.Level;

public class Hourglass extends Item{

	public Hourglass() {
		super('H', "Hourglass", true);
	}
	
	@Override
	public void use(Level level) {
		level.freeze(10);
	}

}
