package reverseGraph.nodes.operations;

import reverseGraph.DifferentSizeException;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class Layer extends Operation {
	private final Node inputs;
	private final Node weights;
	private final Node biases;

	public Layer(Node inputs, Node weights, Node biases) {
		super(biases.values.length);

		if (inputs.values.length * biases.values.length != weights.values.length) {
			throw new DifferentSizeException(weights.values.length, inputs.values.length * biases.values.length);
		}

		this.inputs = inputs;
		this.weights = weights;
		this.biases = biases;
	}

	@Override
	public void compute() {
		for (int o = 0; o < values.length; o++) {
			values[o] = biases.values[o];
			for (int i = 0; i < inputs.values.length; i++) {
				values[o] += inputs.values[i] * weights.values[o * inputs.values.length + i];
			}
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { inputs, weights, biases };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (biases instanceof Derivable) {
			Derivable derivable = (Derivable) biases;

			for (int i = 0; i < derivatives.length; i++) {
				derivable.derivatives[i] += derivatives[i];
			}
		}

		if (weights instanceof Derivable) {
			Derivable derivable = (Derivable) weights;

			for (int o = 0; o < derivatives.length; o++) {
				for (int i = 0; i < inputs.values.length; i++) {
					derivable.derivatives[o * inputs.values.length + i] += inputs.values[i] * derivatives[o];
				}
			}
		}

		if (inputs instanceof Derivable) {
			Derivable derivable = (Derivable) inputs;

			for (int i = 0; i < inputs.values.length; i++) {
				for (int o = 0; o < derivatives.length; o++) {
					derivable.derivatives[i] += weights.values[o * inputs.values.length + i] * derivatives[o];
				}
			}
		}
	}
}
