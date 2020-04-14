package reverseGraph.util;

import java.util.Random;

import reverseGraph.data.Dimensions;
import reverseGraph.data.Tensor;
import reverseGraph.nodes.Constant;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.Param;
import reverseGraph.nodes.operations.Division;
import reverseGraph.nodes.operations.Multiplication;
import reverseGraph.nodes.operations.Operation;
import reverseGraph.nodes.operations.Subtraction;
import reverseGraph.nodes.operations.Sum;
import reverseGraph.nodes.operations.activations.Abs;
import reverseGraph.nodes.operations.activations.Log;
import reverseGraph.nodes.operations.activations.Negate;
import reverseGraph.nodes.operations.activations.Sqrt;
import reverseGraph.nodes.operations.activations.Square;

public class Util {
	private static final Random rand = new Random();

	public static Param createInitializedParam(Dimensions dimensions, double value) {
		Param param = new Param(dimensions);

		for (int i = 0; i < param.values.flat.length; i++) {
			param.values.flat[i] = value;
		}

		return param;
	}

	public static Param createXavierWeights(int inputSize, int outputSize) {
		Param param = new Param(new Dimensions(inputSize, outputSize));

		for (int i = 0; i < param.values.flat.length; i++) {
			param.values.flat[i] = rand.nextGaussian() * 2 / (inputSize + outputSize);
		}

		return param;
	}

	public static Operation createSquareError(Node output, Node desiredOutput) {
		return new Sum(new Square(new Subtraction(output, desiredOutput)));
	}

	public static Operation createMeanSquareError(Node output, Node desiredOutput) {
		Tensor t = new Tensor(Dimensions.SCALAR);
		t.flat[0] = output.values.flat.length;

		return new Division(createSquareError(output, desiredOutput), new Constant(t));
	}

	public static Operation createAbsoluteError(Node output, Node desiredOutput) {
		return new Sum(new Abs(new Subtraction(output, desiredOutput)));
	}

	public static Operation createMeanAbsoluteError(Node output, Node desiredOutput) {
		Tensor t = new Tensor(Dimensions.SCALAR);
		t.flat[0] = output.values.flat.length;

		return new Division(createAbsoluteError(output, desiredOutput), new Constant(t));
	}

	public static Operation createRootMeanSquareError(Node output, Node desiredOutput) {
		return new Sqrt(createMeanSquareError(output, desiredOutput));
	}

	public static Operation createNegativeLogLikelihood(Node output, Node desiredOutput) {
		return new Negate(new Sum(new Multiplication(new Log(output), desiredOutput)));
	}
}
