package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public final class Negate extends Operation {
	private final Node inputs;

	public Negate(Node inputs) {
		super(inputs.values.length);

		this.inputs = inputs;
	}

	@Override
	public void compute() {
		for (int i = 0; i < values.length; i++) {
			values[i] = -inputs.values[i];
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { inputs };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (inputs instanceof Derivable) {
			for (int i = 0; i < derivatives.length; i++) {
				((Derivable) inputs).derivatives[i] -= derivatives[i];
			}
		}
	}
}
