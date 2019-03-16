package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public class LeakyRelu extends Operation {
	private final Node input;

	public LeakyRelu(Node input) {
		super(input.getSize());
		this.input = input;
	}

	@Override
	public void compute() {
		final int size = getSize();
		for (int i = 0; i < size; i++) {
			double input = this.input.getValues()[i];
			outputs[i] = input > 0D ? input : input * 0.01;
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
				double result = (input.getValues()[i] > 0) ? 1 : 0.01;
				((Derivable) input).getDerivatives()[i] += result * getDerivatives()[i];
			}
		}
	}
}
