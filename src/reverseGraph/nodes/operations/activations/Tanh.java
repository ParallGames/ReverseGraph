package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public final class Tanh extends Operation {
	private final Node inputs;

	public Tanh(Node inputs) {
		super(inputs.values.dimensions);
		this.inputs = inputs;
	}

	@Override
	public void compute() {
		for (int i = 0; i < values.flat.length; i++) {
			values.flat[i] = Math.tanh(inputs.values.flat[i]);
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { inputs };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (inputs instanceof Derivable) {
			Derivable derivable = ((Derivable) inputs);

			for (int i = 0; i < derivatives.flat.length; i++) {
				double n = values.flat[i];

				derivable.derivatives.flat[i] += derivatives.flat[i] * (1 - n * n);
			}
		}
	}
}
