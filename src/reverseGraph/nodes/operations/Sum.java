package reverseGraph.nodes.operations;

import reverseGraph.data.Dimensions;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class Sum extends Operation {
	private final Node[] addends;

	public Sum(Node... addends) {
		super(Dimensions.SCALAR);
		this.addends = addends;
	}

	@Override
	public void compute() {
		double sum = 0;

		for (Node node : addends) {
			for (double value : node.values.flat) {
				sum += value;
			}
		}

		values.flat[0] = sum;
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
				for (int i = 0; i < node.values.flat.length; i++) {
					derivable.derivatives.flat[i] += derivatives.flat[0];
				}
			}
		}
	}
}
