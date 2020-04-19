package ch.epfl.moocprog;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.ANT_WORKER_HP;
import static ch.epfl.moocprog.config.Config.ANT_WORKER_LIFESPAN;
import static ch.epfl.moocprog.config.Config.ANT_WORKER_SPEED;
import static ch.epfl.moocprog.config.Config.ANT_MAX_FOOD;

import ch.epfl.moocprog.utils.Time;

public class AntWorker extends Ant {
	
	private double foodQuantity;

	public AntWorker(ToricPosition pos, Uid anthillId) {
		super(pos,getConfig().getInt(ANT_WORKER_HP), getConfig().getTime(ANT_WORKER_LIFESPAN),anthillId);
	}
	
	public AntWorker(ToricPosition pos, Uid anthillId, AntRotationProbabilityModel probModel) {
		super(pos,getConfig().getInt(ANT_WORKER_HP), getConfig().getTime(ANT_WORKER_LIFESPAN),anthillId, probModel);
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
	
	private final void setFoodQuantity(double foodQuantity) {
		this.foodQuantity=foodQuantity;
	}
	
	protected void seekForFood(AntWorkerEnvironmentView env, Time dt) {
		this.move(env, dt);
		if(env.getClosestFoodForAnt(this) != null && this.foodQuantity == 0.0) {
			double foodQuantity = env.getClosestFoodForAnt(this).takeQuantity(getConfig().getDouble(ANT_MAX_FOOD));
			this.setFoodQuantity(foodQuantity);
			this.makeUturn();
		}
		if(env.dropFood(this)==true) {
			this.setFoodQuantity(0.0);
			this.makeUturn();
		}
	}

	@Override
	public String toString() {
		return super.toString()+"\n"+String.format("Quantity : %.1f", this.getFoodQuantity());
	}

	@Override
	public void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt) {
		env.selectSpecificBehaviorDispatch(this, dt);
	}
	
	
	
	
	
	

}
