package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public class Tanh extends Operation {
	private final Node input;

	public Tanh(Node input) {
		this.input = input;
	}

	@Override
	public void compute() {
		this.output = Math.tanh(input.getValue());
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { input };
	}

	@Override
	public void computeDependenciesDerivative() {
		if (input instanceof Derivable) {
			double exp = Math.exp(2 * input.getValue());

			double divider = exp + 1;
			divider *= divider;

			double result = exp * 4 / divider;

			((Derivable) input).addToDerivative(result * this.derivative);
		}
	}
}
