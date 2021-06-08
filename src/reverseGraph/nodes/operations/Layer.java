package reverseGraph.nodes.operations;

import reverseGraph.data.Dimensions;
import reverseGraph.exceptions.Assert;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class Layer extends Operation {
	private final Node inputs;
	private final Node weights;
	private final Node biases;

	public Layer(Node inputs, Node weights, Node biases) {
		super(biases.values.dimensions);

		Assert.sameDimensions(weights.values.dimensions,
				new Dimensions(inputs.values.flat.length, biases.values.flat.length));

		this.inputs = inputs;
		this.weights = weights;
		this.biases = biases;
	}

	@Override
	public void compute() {
		System.arraycopy(biases.values.flat, 0, values.flat, 0, biases.values.flat.length);
		for (int i = 0; i < inputs.values.flat.length; i++) {
			for (int o = 0; o < values.flat.length; o++) {
				values.flat[o] += inputs.values.flat[i] * weights.values.flat[o + i * values.flat.length];
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

			for (int i = 0; i < derivatives.flat.length; i++) {
				derivable.derivatives.flat[i] += derivatives.flat[i];
			}
		}

		if (weights instanceof Derivable) {
			Derivable derivable = (Derivable) weights;

			for (int i = 0; i < inputs.values.flat.length; i++) {
				for (int o = 0; o < derivatives.flat.length; o++) {
					derivable.derivatives.flat[o + i * values.flat.length] += inputs.values.flat[i]
							* derivatives.flat[o];
				}
			}
		}

		if (inputs instanceof Derivable) {
			Derivable derivable = (Derivable) inputs;

			for (int i = 0; i < inputs.values.flat.length; i++) {
				for (int o = 0; o < derivatives.flat.length; o++) {
					derivable.derivatives.flat[i] += weights.values.flat[o + i * values.flat.length]
							* derivatives.flat[o];
				}
			}
		}
	}
}
