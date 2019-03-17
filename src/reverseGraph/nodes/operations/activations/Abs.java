package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public final class Abs extends Operation {
	private final Node inputs;

	public Abs(Node inputs) {
		super(inputs.getSize());
		this.inputs = inputs;
	}

	@Override
	public void compute() {
		final int size = getSize();
		for (int i = 0; i < size; i++) {
			double input = this.inputs.values[i];
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
			final int size = getSize();
			for (int i = 0; i < size; i++) {
				double input = this.inputs.values[i];
				double derivative = derivatives[i];
				((Derivable) this.inputs).derivatives[i] += input > 0 ? derivative : -derivative;
			}
		}
	}
}
