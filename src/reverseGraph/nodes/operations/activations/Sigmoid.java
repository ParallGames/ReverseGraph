package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public final class Sigmoid extends Operation {
	private final Node inputs;

	public Sigmoid(Node inputs) {
		super(inputs.values.dimensions);
		this.inputs = inputs;
	}

	@Override
	public void compute() {
		for (int i = 0; i < values.flat.length; i++) {
			values.flat[i] = 1D / (1D + Math.exp(-inputs.values.flat[i]));
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { inputs };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (inputs instanceof Derivable) {
			for (int i = 0; i < derivatives.flat.length; i++) {
				double n = values.flat[i];

				((Derivable) inputs).derivatives.flat[i] += derivatives.flat[i] * n * (1 - n);
			}
		}
	}
}
