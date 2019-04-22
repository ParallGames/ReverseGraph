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
			for (int a = 0; a < inputs[i].values.length; a++) {
				values[index] = inputs[i].values[a];
				index++;
			}
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
				for (int a = 0; a < inputs[i].values.length; a++) {
					((Derivable) inputs[i]).derivatives[a] += derivatives[index];
					index++;
				}
			} else {
				index += inputs[i].values.length;
			}
		}
	}
}
