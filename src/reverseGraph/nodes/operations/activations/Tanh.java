package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public final class Tanh extends Operation {
	private final Node inputs;

	public Tanh(Node inputs) {
		super(inputs.values.length);
		this.inputs = inputs;
	}

	@Override
	public void compute() {
		for (int i = 0; i < values.length; i++) {
			values[i] = 2D / (1D + Math.exp(-2D * inputs.values[i])) - 1D;
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

			for (int i = 0; i < derivatives.length; i++) {
				double exp = Math.exp(2D * inputs.values[i]);

				double divider = exp + 1D;
				divider *= divider;

				derivable.derivatives[i] += derivatives[i] * exp * 4D / divider;
			}
		}
	}
}
