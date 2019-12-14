package ch.epfl.moocprog;
import ch.epfl.moocprog.utils.Time;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.*;

public final class FoodGenerator {
	
	private final Time foodDelay = getConfig().getTime(FOOD_GENERATOR_DELAY);
	final double min = getConfig().getDouble(NEW_FOOD_QUANTITY_MIN);
	final double max = getConfig().getDouble(NEW_FOOD_QUANTITY_MAX);
	final double ww = getConfig().getInt(WORLD_WIDTH);
	final double wh = getConfig().getInt(WORLD_HEIGHT);
	
	private Time time;

	public FoodGenerator() {
		this.time = Time.ZERO;
	}
	
	public void update(FoodGeneratorEnvironmentView env, Time dt) {
		
		this.time = this.time.plus(dt);
		
		while(this.time.compareTo(foodDelay) >= 0.0) {
			
			double quantite = ch.epfl.moocprog.random.UniformDistribution.getValue(min, max);	
			double x = ch.epfl.moocprog.random.NormalDistribution.getValue(ww/2.0, ww*ww/16.0);
			double y = ch.epfl.moocprog.random.NormalDistribution.getValue(wh/2.0, wh*wh/16.0);
			
			this.time = this.time.minus(foodDelay);
			
			Food food = new Food(new ToricPosition(x,y), quantite);

			env.addFood(food);
		}
	}

}
