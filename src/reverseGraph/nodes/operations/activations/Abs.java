package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public class Abs extends Operation {
	private final Node input;

	public Abs(Node input) {
		this.input = input;
	}

	@Override
	public void compute() {
		double input = this.input.getOutput();

		this.output = input > 0 ? input : -input;
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { input };
	}

	@Override
	public void computeDependenciesDerivative() {
		if (this.input instanceof Derivable) {
			double input = this.input.getOutput();
			((Derivable) this.input).addToDerivative(input > 0 ? derivative : -derivative);
		}
	}
}
