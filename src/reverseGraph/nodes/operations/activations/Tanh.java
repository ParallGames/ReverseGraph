package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public final class Tanh extends Operation {
	private final Node inputs;

	public Tanh(Node inputs) {
		super(inputs.getSize());
		this.inputs = inputs;
	}

	@Override
	public void compute() {
		final int size = getSize();
		for (int i = 0; i < size; i++) {
			values[i] = Math.tanh(inputs.values[i]);
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { inputs };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (inputs instanceof Derivable) {
			final int size = getSize();
			for (int i = 0; i < size; i++) {
				double exp = Math.exp(2 * inputs.values[i]);

				double divider = exp + 1;
				divider *= divider;

				((Derivable) inputs).derivatives[i] += derivatives[i] * exp * 4 / divider;
			}
		}
	}
}
