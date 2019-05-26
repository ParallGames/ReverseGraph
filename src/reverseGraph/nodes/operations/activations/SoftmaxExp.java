package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public class SoftmaxExp extends Operation {
	private final Node inputs;

	public SoftmaxExp(Node inputs) {
		super(inputs.values.length);
		this.inputs = inputs;
	}

	@Override
	public void compute() {
		double max = Double.NEGATIVE_INFINITY;

		for (int i = 0; i < inputs.values.length; i++) {
			if (inputs.values[i] > max) {
				max = inputs.values[i];
			}
		}

		for (int i = 0; i < values.length; i++) {
			values[i] = Math.exp(inputs.values[i] - max);
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { inputs };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (this.inputs instanceof Derivable) {
			for (int i = 0; i < derivatives.length; i++) {
				((Derivable) inputs).derivatives[i] += derivatives[i] * values[i];
			}
		}
	}
}
