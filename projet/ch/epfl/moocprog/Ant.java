package ch.epfl.moocprog;

import ch.epfl.moocprog.utils.Time;

public abstract class Ant extends Animal {
	
	private Uid anthillId;

	public Ant(ToricPosition pos, int hitpoints, Time lifespan, Uid anthillId) {
		super(pos, hitpoints, lifespan);
		this.anthillId=anthillId;
	}

	/*public Ant(ToricPosition pos){
		super(pos);
	}*/

	protected final Uid getAnthillId() {
		return anthillId;
	}
	
	
}
