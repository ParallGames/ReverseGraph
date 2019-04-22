package reverseGraph.util;

import java.util.Random;

import reverseGraph.nodes.Constant;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.Param;
import reverseGraph.nodes.operations.Division;
import reverseGraph.nodes.operations.Multiplication;
import reverseGraph.nodes.operations.Operation;
import reverseGraph.nodes.operations.Substraction;
import reverseGraph.nodes.operations.Sum;
import reverseGraph.nodes.operations.activations.Abs;
import reverseGraph.nodes.operations.activations.Log;
import reverseGraph.nodes.operations.activations.Negate;
import reverseGraph.nodes.operations.activations.Sqrt;
import reverseGraph.nodes.operations.activations.Square;

public class Util {
	private static final Random rand = new Random();

	public static Param createInitializedParam(int size, double value) {
		double[] values = new double[size];

		for (int i = 0; i < values.length; i++) {
			values[i] = value;
		}

		return new Param(values);
	}

	public static Param createXavierWeights(int inputSize, int outputSize) {
		double[] weights = new double[inputSize * outputSize];

		for (int i = 0; i < weights.length; i++) {
			weights[i] = rand.nextGaussian() * 2 / (inputSize + outputSize);
		}

		return new Param(weights);
	}

	public static Operation createSquareError(Node output, Node desiredOutput) {
		return new Sum(new Square(new Substraction(output, desiredOutput)));
	}

	public static Operation createMeanSquareError(Node output, Node desiredOutput) {
		return new Division(createSquareError(output, desiredOutput),
				new Constant(new double[] { output.values.length }));
	}

	public static Operation createAbsoluteError(Node output, Node desiredOutput) {
		return new Sum(new Abs(new Substraction(output, desiredOutput)));
	}

	public static Operation createMeanAbsoluteError(Node output, Node desiredOutput) {
		return new Division(createAbsoluteError(output, desiredOutput),
				new Constant(new double[] { output.values.length }));
	}

	public static Operation createRootMeanSquareError(Node output, Node desiredOutput) {
		return new Sqrt(createMeanSquareError(output, desiredOutput));
	}

	public static Operation createNegativeLogLikelihood(Node output, Node desiredOutput) {
		return new Negate(new Sum(new Multiplication(new Log(output), desiredOutput)));
	}
}
