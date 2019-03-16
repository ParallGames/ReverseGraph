package reverseGraph.nodes.operations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public class Layer extends Operation {
	private final Node inputs;
	private final Node weights;
	private final Node biases;

	public Layer(Node inputs, Node weights, Node biases) {
		super(biases.getSize());

		this.inputs = inputs;
		this.weights = weights;
		this.biases = biases;
	}

	@Override
	public void compute() {
		for (int o = 0; o < getSize(); o++) {
			double sum = biases.getValues()[o];
			for (int i = 0; i < inputs.getSize(); i++) {
				sum += inputs.getValues()[i] * weights.getValues()[o * inputs.getSize() + i];
			}
			outputs[o] = sum;
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { inputs, weights, biases };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (biases instanceof Derivable) {
			((Derivable) biases).addToDerivatives(this.getDerivatives());
		}

		if (weights instanceof Derivable) {
			double[] derivatives = new double[weights.getSize()];

			for (int o = 0; o < getSize(); o++) {
				for (int i = 0; i < inputs.getSize(); i++) {
					derivatives[o * inputs.getSize() + i] = inputs.getValues()[i] * getDerivatives()[o];
				}
			}

			((Derivable) weights).addToDerivatives(derivatives);
		}

		if (inputs instanceof Derivable) {
			double[] derivatives = new double[inputs.getSize()];

			for (int i = 0; i < inputs.getSize(); i++) {
				double derivative = 0;
				for (int o = 0; o < getSize(); o++) {
					derivative += weights.getValues()[o * inputs.getSize() + i] * getDerivatives()[o];
				}
				derivatives[i] = derivative;
			}

			((Derivable) inputs).addToDerivatives(derivatives);
		}
	}
}
