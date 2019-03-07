package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public class Tanh extends Operation {
	private final Node input;

	public Tanh(Node input) {
		super(input.getSize());
		this.input = input;
	}

	@Override
	public void compute() {
		for (int i = 0; i < getSize(); i++) {
			outputs[i] = Math.tanh(input.getValues()[i]);
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { input };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (input instanceof Derivable) {
			double[] derivatives = new double[getSize()];

			for (int i = 0; i < getSize(); i++) {
				double exp = Math.exp(2 * input.getValues()[i]);

				double divider = exp + 1;
				divider *= divider;

				double result = exp * 4 / divider;

				derivatives[i] = result * getDerivatives()[i];
			}

			((Derivable) input).addToDerivatives(derivatives);
		}
	}
}
