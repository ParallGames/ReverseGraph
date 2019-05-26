package reverseGraph.nodes.operations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class Sum extends Operation {
	private final Node[] addends;

	public Sum(Node... addends) {
		super(1);
		this.addends = addends;
	}

	@Override
	public void compute() {
		values[0] = 0;

		for (Node node : addends) {
			for (double value : node.values) {
				values[0] += value;
			}
		}
	}

	@Override
	public Node[] getDependencies() {
		return addends;
	}

	@Override
	public void computeDependenciesDerivatives() {
		for (Node node : addends) {
			Derivable derivable = (Derivable) node;
			if (node instanceof Derivable) {
				for (int i = 0; i < node.values.length; i++) {
					derivable.derivatives[i] += derivatives[0];
				}
			}
		}
	}
}
