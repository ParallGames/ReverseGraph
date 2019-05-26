package reverseGraph.nodes.operations;

import reverseGraph.DifferentSizeException;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class WeightedSum extends Operation {
	private final Node addends;
	private final Node weights;

	public WeightedSum(Node addends, Node weights) {
		super(1);

		if (addends.values.length != weights.values.length) {
			throw new DifferentSizeException(addends.values.length, weights.values.length);
		}

		this.addends = addends;
		this.weights = weights;
	}

	@Override
	public void compute() {
		double output = 0;

		for (int i = 0; i < values.length; i++) {
			output += addends.values[i] * weights.values[i];
		}

		values[0] = output;
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { addends, weights };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (addends instanceof Derivable) {
			for (int i = 0; i < derivatives.length; i++) {
				((Derivable) addends).derivatives[i] += derivatives[0] * weights.values[i];
			}
		}

		if (weights instanceof Derivable) {
			for (int i = 0; i < derivatives.length; i++) {
				((Derivable) weights).derivatives[i] += derivatives[0] * addends.values[i];
			}
		}
	}

}
