package ch.epfl.moocprog;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.PHEROMONE_THRESHOLD;
import static ch.epfl.moocprog.config.Config.PHEROMONE_EVAPORATION_RATE;
import ch.epfl.moocprog.utils.Time;


public final class Pheromone extends Positionable{
	
	private double quantity;

	public Pheromone(ToricPosition position, double quantity) {
		super(position);
		this.quantity = quantity;
	}
	
	public double getQuantity() {
		return this.quantity;
	}
	
	public boolean isNegligeable() {
		if (this.quantity<getConfig().getDouble(PHEROMONE_THRESHOLD)) return true;
		return false;
	}
	
	public void update(Time dt) {
		if(!this.isNegligeable()) {
			this.quantity-=dt.toSeconds()*getConfig().getDouble(PHEROMONE_EVAPORATION_RATE);
			if(this.getQuantity()<0.0) this.quantity = 0.0;
		}
	}
}
