package reverseGraph.util;

import java.util.Random;

public class Util {
	private static final Random rand = new Random();

	public static double[] createXavierWeights(int inputSize, int outputSize) {
		double[] weights = new double[inputSize * outputSize];

		for (int i = 0; i < weights.length; i++) {
			weights[i] = rand.nextGaussian() * 2 / (inputSize + outputSize);
		}

		return weights;
	}
}
