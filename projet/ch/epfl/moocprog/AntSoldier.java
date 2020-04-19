package ch.epfl.moocprog;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.ANT_SOLDIER_HP;
import static ch.epfl.moocprog.config.Config.ANT_SOLDIER_LIFESPAN;
import static ch.epfl.moocprog.config.Config.ANT_SOLDIER_SPEED;

import ch.epfl.moocprog.utils.Time;

public class AntSoldier extends Ant {

	public AntSoldier(ToricPosition pos, Uid anthillId) {
		super(pos,getConfig().getInt(ANT_SOLDIER_HP), getConfig().getTime(ANT_SOLDIER_LIFESPAN), anthillId);
	}

	@Override
	public void accept(AnimalVisitor visitor, RenderingMedia s) {
		visitor.visit(this, s);
	}

	@Override
	public double getSpeed() {
		return getConfig().getDouble(ANT_SOLDIER_SPEED);
	}
	
	protected void seekForEnemies(AntEnvironmentView env, Time dt) {
		this.move(env, dt);
	}

	@Override
	public void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt) {
		env.selectSpecificBehaviorDispatch(this, dt);
	}

}
