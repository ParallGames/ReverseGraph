package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public final class Abs extends Operation {
	private final Node inputs;

	public Abs(Node inputs) {
		super(inputs.values.length);
		this.inputs = inputs;
	}

	@Override
	public void compute() {
		for (int i = 0; i < values.length; i++) {
			double input = inputs.values[i];
			values[i] = input > 0 ? input : -input;
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
				double derivative = derivatives[i];
				((Derivable) inputs).derivatives[i] += inputs.values[i] > 0 ? derivative : -derivative;
			}
		}
	}
}
