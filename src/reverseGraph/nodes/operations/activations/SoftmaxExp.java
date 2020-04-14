package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public class SoftmaxExp extends Operation {
	private final Node inputs;

	public SoftmaxExp(Node inputs) {
		super(inputs.values.dimensions);
		this.inputs = inputs;
	}

	@Override
	public void compute() {
		double max = Double.NEGATIVE_INFINITY;

		for (int i = 0; i < inputs.values.flat.length; i++) {
			if (inputs.values.flat[i] > max) {
				max = inputs.values.flat[i];
			}
		}

		for (int i = 0; i < values.flat.length; i++) {
			values.flat[i] = Math.exp(inputs.values.flat[i] - max);
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { inputs };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (this.inputs instanceof Derivable) {
			for (int i = 0; i < derivatives.flat.length; i++) {
				((Derivable) inputs).derivatives.flat[i] += derivatives.flat[i] * values.flat[i];
			}
		}
	}
}
