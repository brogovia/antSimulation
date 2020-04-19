package ch.epfl.moocprog;

import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.WORLD_HEIGHT;
import static ch.epfl.moocprog.config.Config.WORLD_WIDTH;
import static ch.epfl.moocprog.config.Config.ANT_MAX_PERCEPTION_DISTANCE;
import static ch.epfl.moocprog.config.Config.ANT_SMELL_MAX_DISTANCE;
import static ch.epfl.moocprog.utils.Utils.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import ch.epfl.moocprog.gfx.EnvironmentRenderer;
import ch.epfl.moocprog.utils.Time;
import ch.epfl.moocprog.utils.Vec2d;



public final class Environment implements FoodGeneratorEnvironmentView, AnimalEnvironmentView, AnthillEnvironmentView, AntEnvironmentView,
AntWorkerEnvironmentView {
	
	private FoodGenerator foodgen; 
	
	private List<Food> foods;
	
	private List<Animal> animals;
	
	private List<Anthill> anthills;
	
	private List<Pheromone> pheromones;
	
	public Environment() {
		this.foodgen = new FoodGenerator();
		this.foods  = new LinkedList<Food>();
		this.animals = new LinkedList<Animal>();
		this.anthills = new LinkedList<Anthill>();
		this.pheromones = new LinkedList<Pheromone>();
	}

	@Override
	public void addFood(Food food) throws IllegalArgumentException {
		
		requireNonNull(food);
		
		this.foods.add(food);
	}
	
	public List<Double> getFoodQuantities(){
		
		List<Double> quants = new ArrayList<>(); 
		this.foods.forEach(food -> quants.add(food.getQuantity()));
		return quants;
	}
	
	public void update(Time dt) {
		//Generation de nourriture
		this.foodgen.update(this, dt);
		
		//Gestion de pheromones
		Iterator<Pheromone> p = pheromones.iterator();
		while(p.hasNext()) {
			Pheromone pheromone = p.next();
			if(pheromone.isNegligible()) {
				p.remove();
			}
			else pheromone.update(dt);
		}
		
		//Gestion des animaux
		for(Anthill anthill:anthills){
			anthill.update(this, dt);
		}
		
		Iterator<Animal> a = animals.iterator();
		while(a.hasNext()) {
			Animal animal = a.next();
			if(animal.isDead()) {
				a.remove();
			}
			else animal.update(this, dt);
		}
		
		//Nettoyage des instances de nourriture avec une quantite nulle
		foods.removeIf(food -> food.getQuantity() <= 0);
	}
	
	public void renderEntities(EnvironmentRenderer environmentRenderer) {
		foods.forEach(environmentRenderer::renderFood);
		animals.forEach(environmentRenderer::renderAnimal);
		anthills.forEach(environmentRenderer::renderAnthill);
		pheromones.forEach(environmentRenderer::renderPheromone);
	}
	
	
	public void addAnthill(Anthill anthill) throws IllegalArgumentException{
		requireNonNull(anthill);
		this.anthills.add(anthill);
	}
	
	public void addAnimal(Animal animal) throws IllegalArgumentException {
		requireNonNull(animal);
		this.animals.add(animal);
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

	@Override
	public void addAnt(Ant ant) {
		requireNonNull(ant);
		addAnimal(ant);
	}

	@Override
	public Food getClosestFoodForAnt(AntWorker antWorker) {
		requireNonNull(antWorker);
		Food closestFood = closestFromPoint(antWorker, this.foods);
		if (closestFood == null || closestFood.getPosition().toricDistance(antWorker.getPosition())>getConfig().getDouble(ANT_MAX_PERCEPTION_DISTANCE)) {
			return null;
		}
		else {
			return closestFood;
			}
		
	}

	@Override
	public boolean dropFood(AntWorker antWorker) {
		
		requireNonNull(antWorker);
		
		Anthill antWorkerAnthill = getAnthillById(antWorker.getAnthillId());
		if(antWorkerAnthill == null || antWorkerAnthill.getPosition().toricDistance(antWorker.getPosition())>getConfig().getDouble(ANT_MAX_PERCEPTION_DISTANCE)) {
			return false;
			}
		
		antWorkerAnthill.dropFood(antWorker.getFoodQuantity());
		return true;
	}

	@Override
	public void selectSpecificBehaviorDispatch(AntWorker antWorker, Time dt) {
		antWorker.seekForFood(this, dt);
	}

	@Override
	public void selectSpecificBehaviorDispatch(AntSoldier antSoldier, Time dt) {
		antSoldier.seekForEnemies(this, dt);
	}
	
	public RotationProbability selectComputeRotationProbsDispatch(Ant ant) {
		return ant.computeRotationProbs(this);
	}

	protected Anthill getAnthillById(Uid uid) {
		requireNonNull(uid);
		for(Anthill anthill:this.anthills) {
			if(anthill.getAnthillId().equals(uid)) {
				return anthill;
			}
		}
		return null;
	}

	@Override
	public void addPheromone(Pheromone pheromone) {
		requireNonNull(pheromone);
		this.pheromones.add(pheromone);	
	}
	
	public List<Double> getPheromonesQuantities(){
		List<Double> quants = new ArrayList<>(); 
		this.pheromones.forEach(pheromone -> quants.add(pheromone.getQuantity()));
		return quants;
	}

	@Override
	public double[] getPheromoneQuantitiesPerIntervalForAnt(ToricPosition position, double directionAngleRad, double[] angles) {
		
		requireNonNull(angles);
		
		double[] T = new double[angles.length];
		for(Pheromone pheromone:pheromones) {
			if(!pheromone.isNegligible() && pheromone.getPosition().toricDistance(position) <= getConfig().getDouble(ANT_SMELL_MAX_DISTANCE)) {
				
				Vec2d v = position.toricVector(pheromone.getPosition());
				double beta = v.angle()-directionAngleRad;
				
				int closestIndice = 0;
				for(int i = 0; i<angles.length; i++) {
					if(closestAngleFrom(angles[i], beta) < (closestAngleFrom(angles[closestIndice], beta))) closestIndice = i;
				}
				T[closestIndice]+=pheromone.getQuantity();
			}
		}
		return T;
	}
	
	private static double normalizedAngle(double angle) {
		while(angle < 0) angle += Math.PI;
		while(angle >= 2*Math.PI) angle -= Math.PI;
		return angle;
	}
	
	private static double closestAngleFrom(double angle, double target) {
		double diff = normalizedAngle(angle - target);
		return Math.min(diff, 2*Math.PI - diff);
	}

	@Override
	public void selectAfterMoveDispatch(Ant ant, Time dt) {
		ant.afterMoveAnt(this, dt);
		
	}


}
