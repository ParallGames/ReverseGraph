package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public final class LeakyRelu extends Operation {
	private final Node inputs;

	public LeakyRelu(Node inputs) {
		super(inputs.values.dimensions);
		this.inputs = inputs;
	}

	@Override
	public void compute() {
		for (int i = 0; i < values.flat.length; i++) {
			double input = inputs.values.flat[i];
			values.flat[i] = input > 0D ? input : input * 0.01;
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { inputs };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (inputs instanceof Derivable) {
			for (int i = 0; i < derivatives.flat.length; i++) {
				double result = (inputs.values.flat[i] > 0) ? 1 : 0.01;
				((Derivable) inputs).derivatives.flat[i] += result * derivatives.flat[i];
			}
		}
	}
}
