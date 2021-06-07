package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public class Elu extends Operation {
	private final Node inputs;

	public Elu(Node inputs) {
		super(inputs.values.dimensions);
		this.inputs = inputs;
	}

	@Override
	public void compute() {
		for (int i = 0; i < values.flat.length; i++) {
			double n = inputs.values.flat[i];

			values.flat[i] = (n > 0) ? n : (Math.exp(n) - 1);
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
				double n = inputs.values.flat[i];

				((Derivable) inputs).derivatives.flat[i] += (n > 0 ? 1 : Math.exp(n)) * derivatives.flat[i];
			}
		}
	}
}
