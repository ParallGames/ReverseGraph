package reverseGraph.nodes.operations;

import reverseGraph.data.Dimensions;
import reverseGraph.exceptions.Assert;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class RecurrentLayer extends Operation {
	private final Node inputs;
	private final Node weights;
	private final Node biases;
	private final Node recurrences;
	private final Node recurrencesWeights;

	public RecurrentLayer(Node inputs, Node weights, Node biases, Node recurrences, Node recurrencesWeights) {
		super(biases.values.dimensions);

		Assert.sameDimensions(weights.values.dimensions,
				new Dimensions(inputs.values.flat.length, biases.values.flat.length));
		Assert.sameDimensions(biases.values.dimensions, recurrences.values.dimensions);
		Assert.sameDimensions(biases.values.dimensions, recurrencesWeights.values.dimensions);

		this.inputs = inputs;
		this.weights = weights;
		this.biases = biases;
		this.recurrences = recurrences;
		this.recurrencesWeights = recurrencesWeights;
	}

	@Override
	public void compute() {
		for (int o = 0; o < values.flat.length; o++) {
			values.flat[o] = biases.values.flat[o] + recurrences.values.flat[o] * recurrencesWeights.values.flat[o];
			for (int i = 0; i < inputs.values.flat.length; i++) {
				values.flat[o] += inputs.values.flat[i] * weights.values.flat[o + i * values.flat.length];
			}
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { inputs, weights, biases, recurrences, recurrencesWeights };
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

			for (int o = 0; o < derivatives.flat.length; o++) {
				for (int i = 0; i < inputs.values.flat.length; i++) {
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

		if (recurrences instanceof Derivable) {
			Derivable derivable = (Derivable) recurrences;

			for (int i = 0; i < derivatives.flat.length; i++) {
				derivable.derivatives.flat[i] += derivatives.flat[i] * recurrencesWeights.values.flat[i];
			}
		}

		if (recurrencesWeights instanceof Derivable) {
			Derivable derivable = (Derivable) recurrencesWeights;

			for (int i = 0; i < derivatives.flat.length; i++) {
				derivable.derivatives.flat[i] += derivatives.flat[i] * recurrences.values.flat[i];
			}
		}
	}
}
