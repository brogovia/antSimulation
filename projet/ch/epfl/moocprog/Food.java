package ch.epfl.moocprog;
import static java.lang.Math.*;

public final class Food extends Positionable {
	
	private double quantity;

	public Food(ToricPosition position, double quantity) {	
		super(position);	
		if (quantity > 0.0) this.quantity = quantity;
		else quantity = 0.0;	
	}
	
	public double getQuantity() {
		return quantity;
	}
	
	public double takeQuantity(double prelev) throws IllegalArgumentException {
		if (prelev < 0.0) throw new IllegalArgumentException();
		else {
			double r = min(this.quantity,prelev);
			this.quantity -= r;
			return r;
		}	
	}

	@Override
	public String toString() {
		return super.toString()+"\n"+String.format("Quantity : %.2f", getQuantity());
	}
	
	
	

}
