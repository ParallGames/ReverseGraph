package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public final class Sigmoid extends Operation {
	private final Node inputs;

	public Sigmoid(Node inputs) {
		super(inputs.values.length);
		this.inputs = inputs;
	}

	@Override
	public void compute() {
		for (int i = 0; i < values.length; i++) {
			values[i] = 1D / (1D + Math.exp(-inputs.values[i]));
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
				double exp = Math.exp(inputs.values[i]);

				double divider = exp + 1;
				divider *= divider;

				((Derivable) inputs).derivatives[i] += derivatives[i] * exp / divider;
			}
		}
	}
}
