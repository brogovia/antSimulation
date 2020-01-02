package ch.epfl.moocprog;
import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Vec2d;

import static ch.epfl.moocprog.app.Context.getConfig;
//import static ch.epfl.moocprog.config.Config.ANIMAL_NEXT_ROTATION_DELAY;
import static ch.epfl.moocprog.config.Config.ANT_PHEROMONE_DENSITY;
import static ch.epfl.moocprog.config.Config.ANT_PHEROMONE_ENERGY;

public abstract class Ant extends Animal {
	
	private final double densite = getConfig().getDouble(ANT_PHEROMONE_DENSITY);
	
	private final double energy = getConfig().getDouble(ANT_PHEROMONE_ENERGY);
	
	private Uid anthillId;
	
	private ToricPosition lastPos;

	public Ant(ToricPosition pos, int hitpoints, Time lifespan, Uid anthillId) {
		super(pos, hitpoints, lifespan);
		this.anthillId=anthillId;
		this.lastPos = pos;
	}

	protected final Uid getAnthillId() {
		return anthillId;
	}
	
	private final void spreadPheromones(AntEnvironmentView env) {
		ToricPosition currentPos = this.getPosition();
		double d = lastPos.toricDistance(currentPos);
		Vec2d v = lastPos.toricVector(currentPos);
		for(int i = 0; i < d*densite; i++) {
			ToricPosition pos_i = lastPos.add(v.scalarProduct(i/(d*densite)));
			new Pheromone(pos_i, energy);
		}		
	}
	
	
}
