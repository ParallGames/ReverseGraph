package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public final class Square extends Operation {
	private final Node inputs;

	public Square(Node inputs) {
		super(inputs.values.length);
		this.inputs = inputs;
	}

	@Override
	public void compute() {
		for (int i = 0; i < values.length; i++) {
			double input = inputs.values[i];
			values[i] = input * input;
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { inputs };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (this.inputs instanceof Derivable) {
			for (int i = 0; i < derivatives.length; i++) {
				double input = inputs.values[i];
				((Derivable) inputs).derivatives[i] += 2 * input * derivatives[i];
			}
		}
	}
}
