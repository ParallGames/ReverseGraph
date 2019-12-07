package reverseGraph.model;

import java.util.Random;

import reverseGraph.node.matrix.MatrixParam;

public class Parameters {
	private static final Random rand = new Random(0);

	public static MatrixParam createXavierWeights(int inputSize, int outputSize) {
		MatrixParam param = new MatrixParam(inputSize, outputSize);

		for (int i = 0; i < param.size; i++) {
			param.setFlat(i, (rand.nextGaussian()) * 2 / (inputSize + outputSize));
		}

		return param;
	}
}
