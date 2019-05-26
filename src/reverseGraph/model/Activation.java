package reverseGraph.model;

import reverseGraph.nodes.operations.DivideAll;
import reverseGraph.nodes.operations.Operation;
import reverseGraph.nodes.operations.Sum;
import reverseGraph.nodes.operations.activations.LeakyRelu;
import reverseGraph.nodes.operations.activations.Sigmoid;
import reverseGraph.nodes.operations.activations.SoftmaxExp;
import reverseGraph.nodes.operations.activations.Tanh;

public enum Activation {
	IDENTITY, SIGMOID, TANH, LEAKYRELU, SOFTMAX;

	public Operation apply(Operation input) {
		switch (this) {
		case IDENTITY:
			return input;
		case LEAKYRELU:
			return new LeakyRelu(input);
		case SIGMOID:
			return new Sigmoid(input);
		case TANH:
			return new Tanh(input);
		case SOFTMAX:
			SoftmaxExp exp = new SoftmaxExp(input);

			return new DivideAll(exp, new Sum(exp));
		default:
			throw new RuntimeException("Unknown activation");
		}
	}
}
