package reverseGraph.nodes.operations;

import reverseGraph.data.Dimensions;
import reverseGraph.exceptions.Assert;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class WeightedSum extends Operation {
	private final Node addends;
	private final Node weights;

	public WeightedSum(Node addends, Node weights) {
		super(Dimensions.SCALAR);

		Assert.sameDimensions(addends.values.dimensions, weights.values.dimensions);

		this.addends = addends;
		this.weights = weights;
	}

	@Override
	public void compute() {
		double sum = 0;

		for (int i = 0; i < values.flat.length; i++) {
			sum += addends.values.flat[i] * weights.values.flat[i];
		}

		values.flat[0] = sum;
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { addends, weights };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (addends instanceof Derivable) {
			for (int i = 0; i < derivatives.flat.length; i++) {
				((Derivable) addends).derivatives.flat[i] += derivatives.flat[0] * weights.values.flat[i];
			}
		}

		if (weights instanceof Derivable) {
			for (int i = 0; i < derivatives.flat.length; i++) {
				((Derivable) weights).derivatives.flat[i] += derivatives.flat[0] * addends.values.flat[i];
			}
		}
	}

}
