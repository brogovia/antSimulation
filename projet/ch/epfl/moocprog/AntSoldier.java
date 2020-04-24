package ch.epfl.moocprog;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.ANT_SOLDIER_HP;
import static ch.epfl.moocprog.config.Config.ANT_SOLDIER_LIFESPAN;
import static ch.epfl.moocprog.config.Config.ANT_SOLDIER_SPEED;
import static ch.epfl.moocprog.config.Config.ANT_SOLDIER_ATTACK_DURATION;
import static ch.epfl.moocprog.config.Config.ANT_SOLDIER_MAX_STRENGTH;
import static ch.epfl.moocprog.config.Config.ANT_SOLDIER_MIN_STRENGTH;


import ch.epfl.moocprog.utils.Time;

public class AntSoldier extends Ant {

	public AntSoldier(ToricPosition pos, Uid anthillId) {
		super(pos,getConfig().getInt(ANT_SOLDIER_HP), getConfig().getTime(ANT_SOLDIER_LIFESPAN), anthillId);
	}
	
	public AntSoldier(ToricPosition pos, Uid anthillId, AntRotationProbabilityModel probModel) {
		super(pos,getConfig().getInt(ANT_SOLDIER_HP), getConfig().getTime(ANT_SOLDIER_LIFESPAN), anthillId, probModel);
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
	
	@Override
	protected int getMinAttackStrength() {
		return getConfig().getInt(ANT_SOLDIER_MIN_STRENGTH);
	}

	@Override
	protected int getMaxAttackStrength() {
		return getConfig().getInt(ANT_SOLDIER_MAX_STRENGTH);
	}

	@Override
	protected Time getMaxAttackDuration() {
		return getConfig().getTime(ANT_SOLDIER_ATTACK_DURATION);
	}

}
