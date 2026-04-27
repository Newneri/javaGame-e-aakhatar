package generation.items;
import generation.Level;

public abstract class Item implements Usable{
	private char symbol;
	private String name;
	private boolean activable;
	
	public Item(char symbol, String name, boolean activable) {
		this.symbol = symbol;
		this.name = name;
		this.activable = activable;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	@Override
	public char getSymbol() {
		return this.symbol;
	}
	
	@Override
	public boolean getActivable() {
		return this.activable;
	}
	
	@Override
	public abstract void use(Level level);
	
	@Override
	public boolean isConsummed() {
		return true;
	}
}
