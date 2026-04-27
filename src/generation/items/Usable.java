package generation.items;
import generation.Level;

public interface Usable extends Comparable<Usable> {

	public void use(Level level);
	public boolean isConsummed();
	
	public char getSymbol();
	public String getName();
	public boolean getActivable();
	
	default int compareTo(Usable other) {
		return this.getName().compareTo(other.getName());
	}
}
