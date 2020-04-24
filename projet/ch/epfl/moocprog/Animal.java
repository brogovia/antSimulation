package ch.epfl.moocprog;
import static ch.epfl.moocprog.random.UniformDistribution.getValue;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.ANIMAL_LIFESPAN_DECREASE_FACTOR;
import static ch.epfl.moocprog.config.Config.ANIMAL_NEXT_ROTATION_DELAY;
import static ch.epfl.moocprog.utils.Vec2d.fromAngle;
import static java.lang.Math.toRadians;
import static ch.epfl.moocprog.utils.Utils.*;

import ch.epfl.moocprog.random.UniformDistribution;
import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Utils;
import ch.epfl.moocprog.utils.Vec2d;

public abstract class Animal extends Positionable {
	
	protected abstract boolean isEnemy(Animal entity) ;
	
	protected abstract boolean isEnemyDispatch(Termite other) ;
	
	protected abstract boolean isEnemyDispatch(Ant other) ;
	
	protected abstract int getMinAttackStrength();
	
	protected abstract int getMaxAttackStrength();
	
	protected abstract Time getMaxAttackDuration();
	
	protected abstract RotationProbability computeRotationProbsDispatch(AnimalEnvironmentView env);
	
	protected abstract void afterMoveDispatch(AnimalEnvironmentView env, Time dt);
	
	public abstract void accept(AnimalVisitor visitor, RenderingMedia s);
	
	public abstract double getSpeed();
	
	protected abstract void specificBehaviorDispatch(AnimalEnvironmentView env, Time dt);
	
	public enum State {IDLE, ESCAPING, ATTACK};
	
	private final Time nextRotationDelay = getConfig().getTime(ANIMAL_NEXT_ROTATION_DELAY);
	
	private double direction;
	
	private int hitpoints;
	
	private Time lifespan;
	
	private Time rotationDelay;
	
	private Time attackDuration;
	
	private State state;
	
	public Animal(ToricPosition pos) {
		super(pos);
	}
	
	public Animal(ToricPosition pos, int hitpoints, Time lifespan) {
		super(pos);
		this.hitpoints=hitpoints;
		this.lifespan=lifespan;
		this.direction = getValue(0, 2*Math.PI);
		this.rotationDelay = Time.ZERO;
		this.attackDuration = Time.ZERO;
		this.state = State.IDLE;
	}
	
	public final double getDirection() {
		return direction;
	}
	
	public final void setDirection(double angle) {
		this.direction = angle;
	}
	
	public final boolean isDead() {
		boolean state = false;
		if((hitpoints <= 0.0) || !(lifespan.isPositive())) state = true;
		return state;
	}
	
	public final int getHitpoints() {
		return hitpoints;
	}
	
	public final Time getLifespan() {
		return lifespan;
	}
	
	public final State getState() {
		return this.state;
	}
	
	public void setState(State newState){
		this.state = newState;
	}
	
	final public void update(AnimalEnvironmentView env, Time dt) {	
		lifespan.minus(dt.times(getConfig().getDouble(ANIMAL_LIFESPAN_DECREASE_FACTOR)));
		
		if(this.getState().equals(State.ATTACK)) {
			if(this.canAttack()) {
				this.fight(env, dt);
			}
			else {
				this.setState(State.ESCAPING);
				this.attackDuration=Time.ZERO;
			}
		}
		else if(this.getState().equals(State.ESCAPING)) {
			this.escape(env, dt);
		}
		else {
			this.specificBehaviorDispatch(env, dt);
		}
	}
	
	protected final void move(AnimalEnvironmentView env, Time dt) {
		this.rotationDelay = this.rotationDelay.plus(dt);
		if (!isDead()) {
			while(this.rotationDelay.compareTo(nextRotationDelay) >= 0.0) {
				rotate(env);
				this.rotationDelay = this.rotationDelay.minus(nextRotationDelay);
			}
		}
		//Angle de direction de l'animal
		Vec2d v = fromAngle(direction);
		//Distance parcourue par l'animal pendant dt
		double distance = dt.toSeconds()*this.getSpeed();
		//Ajout du deplacement a la position courante de l'animal
		this.setPosition(this.getPosition().add(v.scalarProduct(distance)));
		afterMoveDispatch(env, dt);
	}
	
	private void rotate(AnimalEnvironmentView env) {
		this.direction += pickValue(this.computeRotationProbsDispatch(env).getAngles(),this.computeRotationProbsDispatch(env).getProbabilities());
	}
	
	protected final RotationProbability computeDefaultRotationProbs() {
		double[] angles = new double[] {-180.00, -100.00, -55.00, -25.00, -10.00, 0.00, 10.00, 25.00, 55.00, 100.00, 180.00};
		for (int i = 0; i<angles.length;i++) {
			angles[i] = toRadians(angles[i]);
		}
		double[] probabilities = new double[] {0.0000, 0.0000, 0.0005, 0.0010, 0.0050,0.9870,0.0050, 0.0010, 0.0005, 0.0000, 0.0000};
		return new RotationProbability(angles,probabilities);
	}
	
	public void makeUturn() {
		
		  double angle = this.getDirection(); if(angle + Math.PI > 2*Math.PI) {
		  this.setDirection(angle-Math.PI); } else { this.setDirection(angle+Math.PI);
		  }
		 
	}
	@Override
	public String toString() {
		return super.toString()+"\n"+String.format("Speed : %.1f", this.getSpeed())+"\n"
				+String.format("Hitpoints : %d", hitpoints)+"\n"
				+String.format("LifeSpan : %.1f", lifespan.toSeconds())+"\n"
				+String.format("State : %s", this.getState());
	}
	
	protected boolean canAttack() {
		if(this.state != State.ESCAPING && this.attackDuration.compareTo(getMaxAttackDuration()) <= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	protected void escape(AnimalEnvironmentView env, Time dt) {
		this.move(env, dt);
		if(!env.isVisibleFromEnemies(this)){
			this.setState(State.IDLE);
		}
	}
	
	protected void fight(AnimalEnvironmentView env, Time dt) {
		Animal ennemy = Utils.closestFromPoint(this, env.getVisibleEnemiesForAnimal(this));
		if (ennemy != null) {
			double attackStrength = UniformDistribution.getValue(this.getMinAttackStrength(), this.getMaxAttackStrength());
			ennemy.setState(State.ATTACK);
			this.setState(State.ATTACK);
			ennemy.hitpoints -= attackStrength;
			this.attackDuration = this.attackDuration.plus(dt);
		}
		else {
			attackDuration = Time.ZERO;
			if(this.getState().equals(State.ATTACK)) {
				this.setState(State.IDLE);
			}
		}
				
	}



	

}
