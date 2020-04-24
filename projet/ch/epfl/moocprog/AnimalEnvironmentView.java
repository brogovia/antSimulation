package ch.epfl.moocprog;
import java.util.List;

import ch.epfl.moocprog.utils.Time;

public interface AnimalEnvironmentView {
	
	public void selectSpecificBehaviorDispatch(AntWorker antWorker, Time dt);
	
	public void selectSpecificBehaviorDispatch(AntSoldier antSoldier, Time dt);
	
	public RotationProbability selectComputeRotationProbsDispatch(Ant ant);
	
	public void selectAfterMoveDispatch(Ant ant, Time dt);
	
	public void selectSpecificBehaviorDispatch(Termite termite, Time dt);
	
	public void selectAfterMoveDispatch(Termite termite, Time dt);
	
	public RotationProbability selectComputeRotationProbsDispatch(Termite termite);
	
	public List<Animal> getVisibleEnemiesForAnimal(Animal from);
	
	public boolean isVisibleFromEnemies(Animal from);
	
}
