package generation.items;
import generation.Level;
import generation.characters.Enemy;

public class Weapon extends Item {

	public Weapon() {
		super('W', "Weapon", false);
	}
	
	@Override
	public void use(Level level) {
		Enemy enemy = level.getEnemyAt(level.getPlayer().getPosition()[0], level.getPlayer().getPosition()[1]);

		enemy.setLives(enemy.getLives() - 1);
		level.getOccupiedCells().remove(level.getMap()[enemy.getPosition()[0]][enemy.getPosition()[1]]);
		enemy.setPosition(enemy.getInitialPosition());
		level.getOccupiedCells().add(level.getMap()[enemy.getPosition()[0]][enemy.getPosition()[1]]);
	}

}
