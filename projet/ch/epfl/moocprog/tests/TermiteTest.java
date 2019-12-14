package ch.epfl.moocprog.tests;
//import static ch.epfl.moocprog.app.Context.getConfig;
//import static ch.epfl.moocprog.config.Config.ANIMAL_NEXT_ROTATION_DELAY;
import static org.junit.Assert.assertTrue;
import static ch.epfl.moocprog.config.Config.*;
import java.io.File;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import ch.epfl.moocprog.Environment;
import ch.epfl.moocprog.Termite;
import ch.epfl.moocprog.ToricPosition;
import ch.epfl.moocprog.app.ApplicationInitializer;
import ch.epfl.moocprog.config.ImmutableConfigManager;
import ch.epfl.moocprog.utils.Time;


public class TermiteTest {
	

	
	//final Time nextRotationDelay = getConfig().getTime(ANIMAL_NEXT_ROTATION_DELAY);
	
	@Test
	public void termiteRotationWorks()
	{
		 ApplicationInitializer.initializeApplication(
		            new ImmutableConfigManager(
		                new File("res/app.cfg")
		            )
		        );
		ToricPosition tp1 = new ToricPosition(20, 30);
		Termite termite = new Termite(tp1);
		double initialDirection = termite.getDirection();
		Environment env = new Environment();
		env.addAnimal(termite);
		double directions[] = new double[5];
		boolean directionChanged = false;
		
		for (int i = 0; i < 5; i++) {
			env.update(Time.fromSeconds(1.));
			if (termite.getDirection() != initialDirection) {directionChanged = true;}
			directions[i] = termite.getDirection();
		}
		assertTrue(directionChanged);
		System.out.println(initialDirection);
		System.out.println(Arrays.toString(directions));
		
	}
}


