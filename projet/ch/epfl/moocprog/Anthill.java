package ch.epfl.moocprog;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.ANTHILL_WORKER_PROB_DEFAULT;
import static ch.epfl.moocprog.config.Config.ANTHILL_SPAWN_DELAY;

import ch.epfl.moocprog.random.UniformDistribution;
import ch.epfl.moocprog.utils.Time;

public final class Anthill extends Positionable {

	private final Time antGenerationDelay = getConfig().getTime(ANTHILL_SPAWN_DELAY);
	
	private Uid anthillId;
	
	private double foodQuantity;
	
	private double probability;
	
	private Time time;

	public Anthill(ToricPosition position, double probability) {
		super(position);
		this.anthillId = Uid.createUid();
		this.probability=probability;
		this.time = Time.ZERO;
	}
	
	public Anthill(ToricPosition position) {
		super(position);
		this.anthillId = Uid.createUid();
		this.probability=getConfig().getDouble(ANTHILL_WORKER_PROB_DEFAULT);
		this.time = Time.ZERO;
	}
	
	public void update(AnthillEnvironmentView env, Time dt) {
		
		this.time = this.time.plus(dt);
		while(this.time.compareTo(antGenerationDelay) >= 0.0) {
			double r = UniformDistribution.getValue(0.0, 1.0);
			if(r<=this.probability) {
				AntWorker antWorker = new AntWorker(this.getPosition(), this.anthillId);
				env.addAnt(antWorker);
			}
			else {
				AntSoldier antSoldier = new AntSoldier(this.getPosition(), this.anthillId);
				env.addAnt(antSoldier);
			}
			this.time = this.time.minus(antGenerationDelay);
		}
		
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
