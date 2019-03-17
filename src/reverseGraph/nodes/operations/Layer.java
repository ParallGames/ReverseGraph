package reverseGraph.nodes.operations;

import reverseGraph.DifferentSizeException;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class Layer extends Operation {
	private final Node inputs;
	private final Node weights;
	private final Node biases;

	public Layer(Node inputs, Node weights, Node biases) {
		super(biases.getSize());

		if (inputs.getSize() * biases.getSize() != weights.getSize()) {
			throw new DifferentSizeException(weights.getSize(), inputs.getSize() * biases.getSize());
		}

		this.inputs = inputs;
		this.weights = weights;
		this.biases = biases;
	}

	@Override
	public void compute() {
		final int outputSize = getSize();
		final int inputSize = inputs.getSize();

		for (int o = 0; o < outputSize; o++) {
			double sum = biases.values[o];
			for (int i = 0; i < inputSize; i++) {
				sum += inputs.values[i] * weights.values[o * inputSize + i];
			}
			values[o] = sum;
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { inputs, weights, biases };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (biases instanceof Derivable) {
			((Derivable) biases).addToDerivatives(derivatives);
		}

		if (weights instanceof Derivable) {
			final int outputSize = getSize();
			final int inputSize = inputs.getSize();
			for (int o = 0; o < outputSize; o++) {
				for (int i = 0; i < inputSize; i++) {
					((Derivable) weights).derivatives[o * inputSize + i] += inputs.values[i] * derivatives[o];
				}
			}
		}

		if (inputs instanceof Derivable) {
			final int outputSize = getSize();
			final int inputSize = inputs.getSize();
			for (int i = 0; i < inputSize; i++) {
				double derivative = 0;
				for (int o = 0; o < outputSize; o++) {
					derivative += weights.values[o * inputSize + i] * derivatives[o];
				}
				((Derivable) inputs).derivatives[i] += derivative;
			}
		}
	}
}
