package ch.epfl.moocprog;
import ch.epfl.moocprog.AntEnvironmentView;
import ch.epfl.moocprog.AntRotationProbabilityModel;
import ch.epfl.moocprog.RotationProbability;
import ch.epfl.moocprog.ToricPosition;
import static ch.epfl.moocprog.app.Context.getConfig;
import static ch.epfl.moocprog.config.Config.ALPHA;
import static ch.epfl.moocprog.config.Config.BETA_D;
import static ch.epfl.moocprog.config.Config.Q_ZERO;

public class PheromoneRotationProbabilityModel implements AntRotationProbabilityModel {
	
	static final double beta = getConfig().getDouble(BETA_D);
	static final double QZero = getConfig().getDouble(Q_ZERO);
	static final int alpha = getConfig().getInt(ALPHA);
	
	private static double detection(double x) {
		return 1.0 / (1.0 + Math.exp(-beta * (x - QZero)));
	}
	

	@Override
	public RotationProbability computeRotationProbs(RotationProbability movementMatrix, ToricPosition position,
			double directionAngle, AntEnvironmentView env) {
		double[] I = movementMatrix.getAngles();
		double[] P = movementMatrix.getProbabilities();
		double[] Q = env.getPheromoneQuantitiesPerIntervalForAnt(position, directionAngle, I);
		double[] numerateur = new double[I.length];
		double[] Pprime = new double[I.length];
		double S = 0.0;
		for (int i =0; i < I.length-1; i++) {
			numerateur[i] = P[i] * Math.pow(detection(Q[i]), alpha);
			S += numerateur[i];
		}
		for (int i =0; i < I.length-1; i++) {
			Pprime[i] = numerateur[i]/S;
		}
		
		return new RotationProbability(I, Pprime);
	}

}
