package ch.epfl.moocprog;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.ANTHILL_WORKER_PROB_DEFAULT;

public final class Anthill extends Positionable {
	
	private Uid anthillId;
	
	private double foodQuantity;
	
	private double probability;

	public Anthill(ToricPosition position, double probability) {
		super(position);
		this.anthillId = Uid.createUid();
		this.probability=probability;
	}
	
	public Anthill(ToricPosition position) {
		super(position);
		this.anthillId = Uid.createUid();
		this.probability=getConfig().getDouble(ANTHILL_WORKER_PROB_DEFAULT);
	}
	
	public double getFoodQuantity() {
		return foodQuantity;
	}
	
	
	public final Uid getAnthillId() {
		return anthillId;
	}

	public void dropFood(double toDrop) {
		foodQuantity += toDrop;
	}

	@Override
	public String toString() {
		return super.toString()+"\n"+String.format("Quantity : %.1f", this.getFoodQuantity());
	}
	
	

}
