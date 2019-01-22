package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public class LeakyRelu extends Operation {
	private final Node input;

	public LeakyRelu(Node input) {
		this.input = input;
	}

	@Override
	public void compute() {
		this.output = (input.getValue() > 0) ? input.getValue() : input.getValue() * 0.01;
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { input };
	}

	@Override
	public void computeDependenciesDerivative() {
		if (input instanceof Derivable) {
			double result = (input.getValue() > 0) ? 1 : 0.01;

			((Derivable) input).addToDerivative(result * this.derivative);
		}
	}
}
