package ch.epfl.moocprog;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.ANT_WORKER_HP;
import static ch.epfl.moocprog.config.Config.ANT_WORKER_LIFESPAN;
import static ch.epfl.moocprog.config.Config.ANT_WORKER_SPEED;

public class AntWorker extends Ant {
	
	private double foodQuantity;

	public AntWorker(ToricPosition pos, Uid anthillId) {
		super(pos,getConfig().getInt(ANT_WORKER_HP), getConfig().getTime(ANT_WORKER_LIFESPAN),anthillId);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this, s);
	}

	@Override
	public double getSpeed() {
		return getConfig().getDouble(ANT_WORKER_SPEED);
	}

	protected final double getFoodQuantity() {
		return foodQuantity;
	}

	@Override
	public String toString() {
		return super.toString()+"\n"+String.format("Quantity : %.1f", this.getFoodQuantity());
	}
	
	
	
	

}
