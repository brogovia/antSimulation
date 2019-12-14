package ch.epfl.moocprog;

import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.WORLD_HEIGHT;
import static ch.epfl.moocprog.config.Config.WORLD_WIDTH;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import ch.epfl.moocprog.gfx.EnvironmentRenderer;
import ch.epfl.moocprog.utils.Time;

public final class Environment implements FoodGeneratorEnvironmentView, AnimalEnvironmentView {
	
	private FoodGenerator foodgen; 
	
	private List<Food> foods;
	
	private List<Animal> animals;
	
	public Environment() {
		this.foodgen = new FoodGenerator();
		this.foods  = new LinkedList<Food>();
		this.animals = new LinkedList<Animal>();
	}

	@Override
	public void addFood(Food food) throws IllegalArgumentException {
		if (food.equals(null)) throw new IllegalArgumentException();
		else {
			this.foods.add(food);
		}
	}
	
	public List<Double> getFoodQuantities(){
		List<Double> quants = new ArrayList<>(); 
		this.foods.forEach(food -> quants.add(food.getQuantity()));
		return quants;
	}
	
	public void update(Time dt) {
		this.foodgen.update(this, dt);
		
		Iterator<Animal> i = animals.iterator();
		while(i.hasNext()) {
			Animal animal = i.next();
			if(animal.isDead()) {
				i.remove();
			}
			else animal.update(this, dt);
		}
		
		foods.removeIf(food -> food.getQuantity() <= 0);
	}
	
	public void renderEntities(EnvironmentRenderer environmentRenderer) {
		foods.forEach(environmentRenderer::renderFood);
		animals.forEach(environmentRenderer::renderAnimal);
	}
	
	public void addAnthill(Anthill anthill) {
		
	}
	
	
	public void addAnimal(Animal animal) throws IllegalArgumentException {
		if (animal.equals(null)) throw new IllegalArgumentException();
		else {
			this.animals.add(animal);
		}
	}
	
	public int getWidth() {
		return getConfig().getInt(WORLD_WIDTH);
	}
	
	public int getHeight() {
		return getConfig().getInt(WORLD_HEIGHT);
	}
	
	public List<ToricPosition> getAnimalsPosition(){
		List<ToricPosition> positions = new ArrayList<>();
		this.animals.forEach(a -> positions.add(a.getPosition()));
		return positions;
	}


}
