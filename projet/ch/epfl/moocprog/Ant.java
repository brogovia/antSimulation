package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Time;

public abstract class Ant extends Animal {
	
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
		
		
	}
	
	
}
