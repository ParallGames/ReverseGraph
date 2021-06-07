package reverseGraph.nodes.operations;

import reverseGraph.data.Dimensions;
import reverseGraph.exceptions.Assert;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class ZeroSumLayer extends Operation {
	private final Node inputs;
	private final Node weights;
	private final Node biases;

	public ZeroSumLayer(Node inputs, Node weights, Node biases) {
		super(biases.values.dimensions);

		Assert.sameDimensions(weights.values.dimensions,
				new Dimensions(inputs.values.flat.length, biases.values.flat.length));

		this.inputs = inputs;
		this.weights = weights;
		this.biases = biases;
	}

	@Override
	public void compute() {
		for (int o = 0; o < values.flat.length; o++) {
			double sum = 0;
			for (int i = 0; i < inputs.values.flat.length; i++) {
				sum += weights.values.flat[o + i * values.flat.length];
			}
			double mean = sum / inputs.values.flat.length;

			values.flat[o] = biases.values.flat[o];
			for (int i = 0; i < inputs.values.flat.length; i++) {
				values.flat[o] += inputs.values.flat[i] * (weights.values.flat[o + i * values.flat.length] - mean);
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

			double inputMean = 0;
			for (int i = 0; i < inputs.values.flat.length; i++) {
				inputMean += inputs.values.flat[i];
			}
			inputMean /= inputs.values.flat.length;

			for (int o = 0; o < derivatives.flat.length; o++) {
				for (int i = 0; i < inputs.values.flat.length; i++) {
					derivable.derivatives.flat[o + i * values.flat.length] += (inputs.values.flat[i] - inputMean)
							* derivatives.flat[o];
				}
			}
		}

		if (inputs instanceof Derivable) {
			Derivable derivable = (Derivable) inputs;

			for (int o = 0; o < derivatives.flat.length; o++) {
				double sum = 0;
				for (int i = 0; i < inputs.values.flat.length; i++) {
					sum += weights.values.flat[o + i * values.flat.length];
				}
				double mean = sum / inputs.values.flat.length;

				for (int i = 0; i < inputs.values.flat.length; i++) {
					derivable.derivatives.flat[i] += (weights.values.flat[o + i * values.flat.length] - mean)
							* derivatives.flat[o];
				}
			}
		}
	}
}
