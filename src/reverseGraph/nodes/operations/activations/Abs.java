package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public class Abs extends Operation {
	private final Node input;

	public Abs(Node input) {
		super(input.getSize());
		this.input = input;
	}

	@Override
	public void compute() {
		final int size = getSize();
		for (int i = 0; i < size; i++) {
			double input = this.input.getValues()[i];
			outputs[i] = input > 0 ? input : -input;
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { input };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (this.input instanceof Derivable) {
			final int size = getSize();
			for (int i = 0; i < size; i++) {
				double input = this.input.getValues()[i];
				double derivative = getDerivatives()[i];
				((Derivable) this.input).getDerivatives()[i] += input > 0 ? derivative : -derivative;
			}
		}
	}
}
