package generation.items;
import java.util.Comparator;

public class InventoryComparator implements Comparator<Usable>{

	public InventoryComparator() {
	}
	

	@Override
	public int compare(Usable o1, Usable o2) {
		if(o1 instanceof Item && o2 instanceof Ability) {
			return -1;
		} else if(o1 instanceof Ability && o2 instanceof Item) {
			return 1;
		}
		return o1.compareTo(o2);
	}

}
