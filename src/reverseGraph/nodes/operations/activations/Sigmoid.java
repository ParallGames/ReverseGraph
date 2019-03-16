package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public class Sigmoid extends Operation {
	private final Node input;

	public Sigmoid(Node input) {
		super(input.getSize());
		this.input = input;
	}

	@Override
	public void compute() {
		final int size = getSize();
		for (int i = 0; i < size; i++) {
			outputs[i] = 1D / (1D + Math.exp(-input.getValues()[i]));
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { input };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (input instanceof Derivable) {
			final int size = getSize();
			for (int i = 0; i < size; i++) {
				double exp = Math.exp(input.getValues()[i]);

				double divider = exp + 1;
				divider *= divider;

				double result = exp / divider;

				((Derivable) input).getDerivatives()[i] += result * getDerivatives()[i];
			}
		}
	}
}
