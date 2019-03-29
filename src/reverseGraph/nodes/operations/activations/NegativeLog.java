package reverseGraph.nodes.operations.activations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.operations.Operation;

public class NegativeLog extends Operation {
	private final Node inputs;

	public NegativeLog(Node inputs) {
		super(inputs.getSize());
		this.inputs = inputs;
	}

	@Override
	public void compute() {
		final int size = getSize();
		for (int i = 0; i < size; i++) {
			values[i] = -Math.log(inputs.values[i]);
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { inputs };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (this.inputs instanceof Derivable) {
			final int size = getSize();
			for (int i = 0; i < size; i++) {
				((Derivable) inputs).derivatives[i] -= derivatives[i] / inputs.values[i];
			}
		}
	}
}
