package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public class SoftPlus extends Operation {
	private final Node input;

	public SoftPlus(Node input) {
		this.input = input;
	}

	@Override
	public void compute() {
		this.output = Math.log(Math.exp(input.getValue()) + 1);
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { input };
	}

	@Override
	public void computeDependenciesDerivative() {
		if (input instanceof Derivable) {
			double sigm = 1D / (1D + Math.exp(-input.getValue()));

			((Derivable) input).addToDerivative(sigm * this.derivative);
		}
	}
}
