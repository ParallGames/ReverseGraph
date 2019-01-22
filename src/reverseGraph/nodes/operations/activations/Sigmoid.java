package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public class Sigmoid extends Operation {
	private final Node input;

	public Sigmoid(Node input) {
		this.input = input;
	}

	@Override
	public void compute() {
		this.output = 1D / (1D + Math.exp(-input.getValue()));
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { input };
	}

	@Override
	public void computeDependenciesDerivative() {
		if (input instanceof Derivable) {
			double exp = Math.exp(input.getValue());

			double divider = exp + 1;
			divider *= divider;

			double result = exp / divider;

			((Derivable) input).addToDerivative(result * this.derivative);
		}
	}
}
