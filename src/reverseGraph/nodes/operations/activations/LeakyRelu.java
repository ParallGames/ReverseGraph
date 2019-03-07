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
		for (int i = 0; i < getSize(); i++) {
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
			double[] derivatives = new double[getSize()];

			for (int i = 0; i < getSize(); i++) {
				double result = (input.getValues()[i] > 0) ? 1 : 0.01;
				derivatives[i] = result * getDerivatives()[i];
			}

			((Derivable) input).addToDerivatives(derivatives);
		}
	}
}
