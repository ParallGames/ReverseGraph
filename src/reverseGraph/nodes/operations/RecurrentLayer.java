package reverseGraph.nodes.operations;

import reverseGraph.data.Dimensions;
import reverseGraph.exceptions.Assert;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class RecurrentLayer extends Operation {
	private final Node inputs;
	private final Node weights;
	private final Node biases;
	private final Node recurrence;
	private final Node recurrenceWeights;

	public RecurrentLayer(Node inputs, Node weights, Node biases, Node recurrence, Node recurrenceWeights) {
		super(biases.values.dimensions);

		Assert.sameDimensions(weights.values.dimensions,
				new Dimensions(inputs.values.flat.length, biases.values.flat.length));
		Assert.sameDimensions(recurrenceWeights.values.dimensions, new Dimensions(recurrence.values.flat.length, biases.values.flat.length));
		Assert.sameDimensions(recurrence.values.dimensions, biases.values.dimensions);

		this.inputs = inputs;
		this.weights = weights;
		this.biases = biases;
		this.recurrence = recurrence;
		this.recurrenceWeights = recurrenceWeights;
	}

	@Override
	public void compute() {
		System.arraycopy(biases.values.flat, 0, values.flat, 0, biases.values.flat.length);
		for (int i = 0; i < inputs.values.flat.length; i++) {
			for (int o = 0; o < values.flat.length; o++) {
				values.flat[o] += inputs.values.flat[i] * weights.values.flat[o + i * values.flat.length];
			}
		}
		
		for (int i = 0; i < recurrence.values.flat.length; i++) {
			for (int o = 0; o < values.flat.length; o++) {
				values.flat[o] += recurrence.values.flat[i] * recurrenceWeights.values.flat[o + i * values.flat.length];
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
		
		if (recurrenceWeights instanceof Derivable) {
			Derivable derivable = (Derivable) recurrenceWeights;

			for (int i = 0; i < recurrence.values.flat.length; i++) {
				for (int o = 0; o < derivatives.flat.length; o++) {
					derivable.derivatives.flat[o + i * values.flat.length] += recurrence.values.flat[i]
							* derivatives.flat[o];
				}
			}
		}

		if (recurrence instanceof Derivable) {
			Derivable derivable = (Derivable) recurrence;

			for (int i = 0; i < recurrence.values.flat.length; i++) {
				for (int o = 0; o < derivatives.flat.length; o++) {
					derivable.derivatives.flat[i] += recurrenceWeights.values.flat[o + i * values.flat.length]
							* derivatives.flat[o];
				}
			}
		}
	}
}
