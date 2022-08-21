package reverseGraph.model;

import reverseGraph.nodes.operations.Operation;
import reverseGraph.nodes.operations.activations.Elu;
import reverseGraph.nodes.operations.activations.LeakyRelu;
import reverseGraph.nodes.operations.activations.Sigmoid;
import reverseGraph.nodes.operations.activations.Softmax;
import reverseGraph.nodes.operations.activations.Tanh;

public enum Activation {
	IDENTITY, SIGMOID, TANH, LEAKY_RELU, ELU, SOFTMAX;

	public Operation apply(Operation input) {
		switch (this) {
		case IDENTITY:
			return input;
		case LEAKY_RELU:
			return new LeakyRelu(input);
		case SIGMOID:
			return new Sigmoid(input);
		case TANH:
			return new Tanh(input);
		case ELU:
			return new Elu(input);
		case SOFTMAX:
			return new Softmax(input);
		default:
			throw new RuntimeException("Unknown activation");
		}
	}
}
