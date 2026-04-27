package generation.items;

import generation.Level;


public class Teleportation extends Ability {

	public Teleportation() {
		super('T', "TP", true);
	}

	@Override
	public void use(Level level) {
		int y;
		int x;
		boolean verif;
		do {
			y = level.rand.nextInt(level.getRows());
			x = level.rand.nextInt(level.getCols());
			verif = !level.getMap()[y][x].getType().equals("empty") || level.isEnemyAt(y, x);
		} while(verif);
		
		int[] newPosition = {y, x};
		level.getPlayer().setPosition(newPosition.clone());

	}

}
