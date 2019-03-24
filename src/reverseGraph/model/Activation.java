package reverseGraph.model;

import reverseGraph.nodes.operations.Division;
import reverseGraph.nodes.operations.Expander;
import reverseGraph.nodes.operations.Operation;
import reverseGraph.nodes.operations.Sum;
import reverseGraph.nodes.operations.activations.LeakyRelu;
import reverseGraph.nodes.operations.activations.Sigmoid;
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
			Operation sigm = new Sigmoid(input);

			return new Division(sigm, new Expander(new Sum(sigm), sigm.getSize()));
		default:
			throw new RuntimeException("Unknown activation");
		}
	}
}
