package ch.epfl.moocprog;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.TERMITE_SPEED;

import ch.epfl.moocprog.utils.Time;

import static ch.epfl.moocprog.config.Config.TERMITE_HP;
import static ch.epfl.moocprog.config.Config.TERMITE_LIFESPAN;

public final class Termite extends Animal {

	public Termite(ToricPosition pos) {
		super(pos, getConfig().getInt(TERMITE_HP), getConfig().getTime(TERMITE_LIFESPAN));
	}
	
	


	public Termite(ToricPosition pos, int hitpoints, Time lifespan) {
		super(pos, hitpoints, lifespan);
		// TODO Auto-generated constructor stub
	}




	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this, s);
	}

	@Override
	public double getSpeed() {
		return getConfig().getDouble(TERMITE_SPEED);
	}


	@Override
	public String toString() {
		return super.toString();
	}




	@Override
	public void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt) {
		
	}
	
	
	
	

}
