package reverseGraph.nodes.operations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class Concatenate extends Operation {
	private static int totalSize(Node[] nodes) {
		int size = 0;

		for (Node node : nodes) {
			size += node.values.length;
		}

		return size;
	}

	Node[] inputs;

	public Concatenate(Node... inputs) {
		super(totalSize(inputs));

		this.inputs = inputs;
	}

	@Override
	public void compute() {
		int index = 0;

		for (int i = 0; i < inputs.length; i++) {
			System.arraycopy(inputs[i].values, 0, values, index, inputs[i].values.length);
			index += inputs[i].values.length;
		}
	}

	@Override
	public Node[] getDependencies() {
		return inputs;
	}

	@Override
	public void computeDependenciesDerivatives() {
		int index = 0;

		for (int i = 0; i < inputs.length; i++) {
			if (inputs[i] instanceof Derivable) {
				Derivable derivable = ((Derivable) inputs[i]);

				System.arraycopy(derivatives, index, derivable.derivatives, 0, derivable.derivatives.length);
			}
			index += inputs[i].values.length;
		}
	}
}
