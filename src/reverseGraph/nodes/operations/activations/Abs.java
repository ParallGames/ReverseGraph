package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public final class Abs extends Operation {
	private final Node inputs;

	public Abs(Node inputs) {
		super(inputs.values.dimensions);
		this.inputs = inputs;
	}

	@Override
	public void compute() {
		for (int i = 0; i < values.flat.length; i++) {
			double input = inputs.values.flat[i];
			values.flat[i] = input > 0 ? input : -input;
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { inputs };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (this.inputs instanceof Derivable) {
			for (int i = 0; i < derivatives.flat.length; i++) {
				double derivative = derivatives.flat[i];
				((Derivable) inputs).derivatives.flat[i] += inputs.values.flat[i] > 0 ? derivative : -derivative;
			}
		}
	}
}
