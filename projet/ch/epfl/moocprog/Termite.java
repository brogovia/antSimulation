package ch.epfl.moocprog;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.TERMITE_SPEED;
import ch.epfl.moocprog.utils.Time;

import static ch.epfl.moocprog.config.Config.ANT_SOLDIER_ATTACK_DURATION;
import static ch.epfl.moocprog.config.Config.ANT_SOLDIER_MAX_STRENGTH;
import static ch.epfl.moocprog.config.Config.ANT_SOLDIER_MIN_STRENGTH;
import static ch.epfl.moocprog.config.Config.TERMITE_HP;
import static ch.epfl.moocprog.config.Config.TERMITE_LIFESPAN;
import static ch.epfl.moocprog.config.Config.TERMITE_ATTACK_DURATION;
import static ch.epfl.moocprog.config.Config.TERMITE_MAX_STRENGTH;
import static ch.epfl.moocprog.config.Config.TERMITE_MIN_STRENGTH;


public final class Termite extends Animal {

	public Termite(ToricPosition pos) {
		super(pos, getConfig().getInt(TERMITE_HP), getConfig().getTime(TERMITE_LIFESPAN));
	}
	
	public Termite(ToricPosition pos, int hitpoints, Time lifespan) {
		super(pos, hitpoints, lifespan);
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
		env.selectSpecificBehaviorDispatch(this, dt);
	}

	@Override
	protected RotationProbability computeRotationProbsDispatch(AnimalEnvironmentView env) {
		return env.selectComputeRotationProbsDispatch(this);
	}

	@Override
	protected void afterMoveDispatch(AnimalEnvironmentView env, Time dt) {
		env.selectAfterMoveDispatch(this, dt);	
	}
	
	protected void seekForEnemies(AnimalEnvironmentView env,
			Time dt) {
		this.move(env, dt);
	}
	
	protected RotationProbability computeRotationProbs(TermiteEnvironmentView
			env) {
		return this.computeDefaultRotationProbs();
	}
	
	protected void afterMoveTermite(TermiteEnvironmentView env,
			Time dt) {
	}

	@Override
	public boolean isEnemy(Animal animal) {
		return !this.isDead() && !animal.isDead() && animal.isEnemyDispatch(this);
	}

	@Override
	protected boolean isEnemyDispatch(Termite other) {
		return false;
	}

	@Override
	protected boolean isEnemyDispatch(Ant other) {
		return true;
	}
	
	
	@Override
	public int getMinAttackStrength() {
		return getConfig().getInt(TERMITE_MIN_STRENGTH);
	}

	@Override
	public int getMaxAttackStrength() {
		return getConfig().getInt(TERMITE_MAX_STRENGTH);
	}

	@Override
	public Time getMaxAttackDuration() {
		return getConfig().getTime(TERMITE_ATTACK_DURATION);
	}
}
