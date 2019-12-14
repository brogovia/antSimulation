package ch.epfl.moocprog;
import static ch.epfl.moocprog.random.UniformDistribution.getValue;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.ANIMAL_LIFESPAN_DECREASE_FACTOR;
import static ch.epfl.moocprog.utils.Vec2d.fromAngle;
import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Vec2d;

public abstract class Animal extends Positionable {
	
	private double direction;
	
	private int hitpoints;
	
	private Time lifespan;
	
	public Animal(ToricPosition pos) {
		super(pos);
	}
	
	public Animal(ToricPosition pos, int hitpoints, Time lifespan) {
		super(pos);
		this.hitpoints=hitpoints;
		this.lifespan=lifespan;
		this.direction = getValue(0, 2*Math.PI);
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
	
	public int getHitpoints() {
		return hitpoints;
	}
	
	public Time getLifespan() {
		return lifespan;
	}
	
	public void update(AnimalEnvironmentView env, Time dt) {
		lifespan.minus(dt.times(getConfig().getDouble(ANIMAL_LIFESPAN_DECREASE_FACTOR)));
		//Appel de la methode move() si l'animal est en vie
		if (!isDead()) move(dt);
	}
	
	protected final void move(Time dt) {
		//Angle de direction de l'animal
		Vec2d v = fromAngle(direction);
		//Distance parcourue par l'animal pendant dt
		double distance = dt.toSeconds()*this.getSpeed();
		//Ajout du deplacement a la position courante de l'animal
		this.setPosition(this.getPosition().add(v.scalarProduct(distance)));
	}
	
	public abstract void accept(AnimalVisitor visitor, RenderingMedia s);
	
	public abstract double getSpeed();

	@Override
	public String toString() {
		return super.toString()+"\n"+String.format("Speed : %.1f", this.getSpeed())+"\n"+String.format("Hitpoints : %d", hitpoints)+"\n"+String.format("LifeSpan : %.1f", lifespan.toSeconds());
	}
	
	

}
