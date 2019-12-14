package ch.epfl.moocprog;
import static ch.epfl.moocprog.utils.Utils.*;

public class RotationProbability {
	
	private double[] angles;
	private double[] probabilities;
	
	public RotationProbability(double[] angles, double[] probas) throws IllegalArgumentException {
		requireNonNull(angles);
		requireNonNull(probas);
		this.angles = angles.clone();
		this.probabilities = probas.clone();
	}

	protected final double[] getAngles() {
		return angles;
	}

	protected final double[] getProbabilities() {
		return probabilities;
	}
	
	

}
